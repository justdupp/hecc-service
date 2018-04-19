package com.hecc.framework.api;

/**
 * @author xuhoujun
 * @description: 全局id服务
 * @date: Created In 下午11:35 on 2018/4/19.
 */
public interface IdService {

    /**
     * 获取全局唯一编码
     *
     * @param key 业务键
     * @return id 全局唯一编码
     */
    long getId(String key);
}
