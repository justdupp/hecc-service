package com.hecc.framework.api;

import com.hecc.framework.exception.IdProduceException;

/**
 * @author xuhoujun
 * @description: id生产者接口
 * @date: Created In 下午11:47 on 2018/4/19.
 */
public interface IdProducer {

    /**
     * 消费一个id
     *
     * @param bizKey 业务键。
     * @return 返回全局唯一的Id值。
     */
    long consume(String bizKey) throws IdProduceException;
}
