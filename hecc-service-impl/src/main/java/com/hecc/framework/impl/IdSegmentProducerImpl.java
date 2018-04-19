package com.hecc.framework.impl;

import com.hecc.framework.api.IdSegmentProducer;
import com.hecc.framework.entity.IdSegment;
import com.hecc.framework.exception.IdProduceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author xuhoujun
 * @description: id生产者默认实现
 * @date: Created In 上午12:09 on 2018/4/20.
 */
@Service
public class IdSegmentProducerImpl implements IdSegmentProducer {

    private static final Logger logger = LoggerFactory.getLogger(IdSegmentProducerImpl.class);

    /**
     * 每次获取id片断的长度
     */
    @Value("${id.producer.idSegmentSize:1000}")
    private int idSegmentSize = 1000;

    /**
     * 队列长度
     */
    @Value("${id.producer.idSegmentQueueSize:3}")
    private int idSegmentQueueSize = 5;

    /**
     * id片断产生速度，单位：毫秒
     */
    @Value("${id.producer.idSegmentProduceRate:100}")
    private long idSegmentProduceRate = 100L;

    /**
     * id片断消费超时
     */
    @Value("${id.producer.idSegmentConsumeTimeout:20000}")
    private long idSegmentConsumeTimeout = 20 * 1000;
    /**
     * 线程组
     */
    private static final ConcurrentMap<String, Thread> PRODUCER_THREADS = new ConcurrentHashMap<>();

    /**
     * id片断队列组
     */
    private static final ConcurrentMap<String, Queue<IdSegment>> ID_SEGMENT_QUEUES = new ConcurrentHashMap<>();

    @Autowired
    private JedisPool jedisPool;

    /**
     * 在队列中消费一个id片断，采用FIFO方式
     *
     * @param bizKey 业务键
     * @return id片断
     */
    @Override
    public IdSegment consumeSegment(String bizKey) throws IdProduceException {
        final Queue<IdSegment> idQueue = getIdSegmentQueue(bizKey);
        maintainProducerThread(bizKey, idQueue);
        IdSegment idSegment = pullSegment(idQueue);
        return idSegment;
    }

    private IdSegment pullSegment(Queue<IdSegment> idSegmentQueue) throws IdProduceException {
        final long historicTimeMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - historicTimeMillis <= idSegmentConsumeTimeout) {
            final IdSegment result = idSegmentQueue.poll();
            if (result != null) {
                return result;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                logger.error("获取IdSegment的线程被中断", e);
            }
        }
        throw new IdProduceException(String.format("在（%d）秒内无法从Redis获取Id片断。",
                TimeUnit.MILLISECONDS.toSeconds(idSegmentConsumeTimeout)));
    }

    private void maintainProducerThread(String bizKey, Queue<IdSegment> idSegmentQueue) {
        final Thread producerThread = getProducerThread(bizKey,
                getThreadSupplier(bizKey, idSegmentQueue));
        if (producerThread.getState() == Thread.State.TERMINATED) {
            replaceProducerThread(bizKey, getThreadSupplier(bizKey, idSegmentQueue));
        }
    }

    private Supplier<Thread> getThreadSupplier(String bizKey, Queue<IdSegment> idSegmentQueue) {
        return () -> new Thread(
                () -> {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            if (idSegmentQueue.size() < idSegmentQueueSize){
                                obtainAndPushOneSegment(bizKey, idSegmentQueue);
                            }
                            Thread.sleep(idSegmentProduceRate);
                        }
                    } catch (JedisConnectionException e) {
                        Thread.currentThread().interrupt();
                        logger.error("JedisConnectionException", e);
                    } catch (InterruptedException e) {
                        logger.error("获取IdSegment时，线程被中断", e);
                        Thread.currentThread().interrupt();
                    }
                });
    }

    private void obtainAndPushOneSegment(String bizKey, Queue<IdSegment> idSegmentQueue) {
        final IdSegment result = withinRedisScope(
                redis -> {
                    long end = redis.incrBy(bizKey, idSegmentSize);
                    return new IdSegment(end - idSegmentSize + 1, end);
                });
        if (idSegmentQueue.contains(result)){
            return;
        }
        idSegmentQueue.offer(result);
    }

    private <T> T withinRedisScope(Function<Jedis, T> function) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return function.apply(jedis);
        } catch (JedisConnectionException e) {
            logger.error("redis连接异常", e);
            if (jedis != null) {
                jedisPool.returnBrokenResource(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
    }

    private Thread getProducerThread(String bizKey, Supplier<Thread> threadSupplier) {
        return PRODUCER_THREADS.computeIfAbsent(bizKey,
                key -> {
                    final Thread producerThread = threadSupplier.get();
                    producerThread.start();
                    return producerThread;
                });
    }

    private Thread replaceProducerThread(String bizKey, Supplier<Thread> threadSupplier) {
        return PRODUCER_THREADS.computeIfPresent(bizKey,
                (key, originalThread) -> {
                    final Thread producerThread = threadSupplier.get();
                    producerThread.start();
                    return producerThread;
                });
    }

    private Queue<IdSegment> getIdSegmentQueue(String bizKey) {
        return ID_SEGMENT_QUEUES.computeIfAbsent(bizKey, key -> new ConcurrentLinkedQueue<>());
    }
}
