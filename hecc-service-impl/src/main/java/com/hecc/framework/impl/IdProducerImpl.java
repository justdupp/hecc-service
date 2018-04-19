package com.hecc.framework.impl;

import com.hecc.framework.api.IdProducer;
import com.hecc.framework.api.IdSegmentProducer;
import com.hecc.framework.entity.IdSegment;
import com.hecc.framework.exception.IdProduceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author xuhoujun
 * @description: id生产者默认实现
 * @date: Created In 上午12:22 on 2018/4/20.
 */
@Service
public class IdProducerImpl implements IdProducer {

    private static final Logger logger = LoggerFactory.getLogger(IdProducerImpl.class);

    /**
     * 线程组，每一个业务键一个线程
     */
    private static final ConcurrentMap<String, Thread> producerThreads = new ConcurrentHashMap<>();

    /**
     * id队列组，每一个业务键一个id队列
     */
    private static final ConcurrentMap<String, Queue<Long>> ids = new ConcurrentHashMap<>();

    /**
     * 获取id的超时时间 单位:毫秒
     */
    private static final long idConsumeTimeOut = 20 * 1000;

    /**
     * id产生速度 单位：毫秒
     */
    @Value("${id.producer.idProduceRate:10}")
    private long idProduceRate = 10L;

    /**
     * id片断的长度
     */
    @Value("${id.producer.idSegmentSize:1000}")
    private int idSegmentSize = 1000;

    @Autowired
    private IdSegmentProducer idSegmentProducer;


    /**
     * 消费一个id
     *
     * @param bizKey 业务键。
     * @return 返回全局唯一的id
     */
    @Override
    public long consume(String bizKey) throws IdProduceException {
        final Queue<Long> idQueue = getIdQueue(bizKey);
        maintainProducerThread(bizKey, idQueue);
        long id = pullOne(idQueue);
        return id;
    }

    /**
     * 从队列中获取一个id，如果在超时时间内获取成功，则返回该id，
     * 否则抛出IdProduceException异常
     *
     * @param idQueue 存入id的队列
     * @return long 全局唯一编码
     * @throws IdProduceException
     */
    private long pullOne(Queue<Long> idQueue) throws IdProduceException {
        final long historicTimeMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - historicTimeMillis <= idConsumeTimeOut) {
            Long result = idQueue.poll();
            if (result != null) {
                return result;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
        throw new IdProduceException(String.format("在（%d）秒内无法从Redis获取Id片断。",
                TimeUnit.MILLISECONDS.toSeconds(idConsumeTimeOut)));
    }

    /**
     * 维护id生产者线程
     * 如果缓存中没有id生产者线程，则创建一个放到缓存中。
     * 如果缓存中存在id生产者线程并且该线程已处于终止状态，则创建一个新的线程替换缓存中的线程
     *
     * @param bizKey  业务键
     * @param idQueue 存入id的队列
     */
    private void maintainProducerThread(String bizKey, Queue<Long> idQueue) {
        final Thread producerThread = getProducerThread(bizKey,
                getThreadSupplier(bizKey, idQueue));
        if (producerThread.getState() == Thread.State.TERMINATED){
            replaceProducerThread(bizKey, getThreadSupplier(bizKey, idQueue));
        }
    }

    /**
     * 获取id生产者线程对象提供者
     *
     * @param bizKey  业务键
     * @param idQueue 存放id的队列
     * @return Supplier<Thread> id生产者线程对象提供者
     */
    private Supplier<Thread> getThreadSupplier(String bizKey, Queue<Long> idQueue) {
        return () -> new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    /**
                     * 消费id百分比3/4，然后填充到队列
                     */
                    int consumeNum = idSegmentSize * 3 / 4;
                    if (idQueue.size() < consumeNum) {
                        obtainOneSegmentAndPushToQueue(bizKey, idQueue);
                    }
                    Thread.sleep(idProduceRate);
                }
            } catch (IdProduceException e) {
                Thread.currentThread().interrupt();
                logger.error("IdProduceException", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    /**
     * 获取id片断，并循环id片断，将id存入到队列中
     *
     * @param bizKey  业务键
     * @param idQueue 存放id的队列
     * @throws IdProduceException
     */
    private void obtainOneSegmentAndPushToQueue(String bizKey, Queue<Long> idQueue) throws IdProduceException {
        final IdSegment idSegment = idSegmentProducer.consumeSegment(bizKey);
        for (long id = idSegment.getStart(); id <= idSegment.getEnd(); id++) {
            idQueue.offer(id);
        }
    }

    /**
     * 从map中获取一个线程，如果获取到了，则直接返回，
     * 否则，创建并启动一个线程，并将该线程返回。
     * 该线程用于生成id，并将id存入队列
     *
     * @param bizKey         业务键
     * @param threadSupplier 线程提供者
     * @return Thread id生产者线程对象
     */
    private Thread getProducerThread(String bizKey, Supplier<Thread> threadSupplier) {
        return producerThreads.computeIfAbsent(bizKey,
                key -> {
                    final Thread producerThread = threadSupplier.get();
                    producerThread.start();
                    return producerThread;
                });
    }

    /**
     * 替换map中的id生产者线程
     *
     * @param bizKey         业务键
     * @param threadSupplier id生产者线程对象提供者
     */
    private void replaceProducerThread(String bizKey, Supplier<Thread> threadSupplier) {
        producerThreads.computeIfPresent(bizKey,
                (key, originalThread) -> {
                    final Thread producerThread = threadSupplier.get();
                    producerThread.start();
                    return producerThread;
                });
    }

    /**
     * 使用bizKey从map中获取队列
     * 如果获取到了，则直接返回，否则，创建一个队列放入map，并返回该队列
     *
     * @param bizKey 业务键
     * @return Queue<Long> 存放id的队列
     */
    private Queue<Long> getIdQueue(String bizKey) {
        return ids.computeIfAbsent(bizKey, key -> new ConcurrentLinkedQueue<>());
    }
}
