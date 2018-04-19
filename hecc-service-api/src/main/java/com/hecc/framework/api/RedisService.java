package com.hecc.framework.api;

import redis.clients.jedis.Jedis;

/**
 * @author xuhoujun
 * @description: redis接口服务
 * @date: Created In 上午12:35 on 2018/4/20.
 */
public interface RedisService {

    /**
     * 获取资源
     *
     * @return
     */
    Jedis getResource();

    /**
     * 释放资源
     *
     * @param jedis
     */
    void returnResource(Jedis jedis);

    /**
     * 赋值操作
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 取值操作
     *
     * @param key
     * @return
     */
    String get(String key);
}
