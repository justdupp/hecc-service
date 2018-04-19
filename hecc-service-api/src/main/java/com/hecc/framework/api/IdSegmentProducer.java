package com.hecc.framework.api;

import com.hecc.framework.entity.IdSegment;
import com.hecc.framework.exception.IdProduceException;

/**
 * @author xuhoujun
 * @description: id片断生产者接口
 * @date: Created In 下午11:52 on 2018/4/19.
 */
public interface IdSegmentProducer {

    /**
     * 在队列中消费一个Id片断，采用FIFO方式
     *
     * @param bizKey 业务键
     * @return id片断
     * @throws IdProduceException
     */
    IdSegment consumeSegment(String bizKey) throws IdProduceException;
}
