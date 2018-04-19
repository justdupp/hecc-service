package com.hecc.framework.exception;

/**
 * @author xuhoujun
 * @description: id生产者统一异常处理
 * @date: Created In 下午11:43 on 2018/4/19.
 */
public class IdProduceException extends Exception {

    public IdProduceException(String msg) {
        super(msg);
    }

    public IdProduceException(Throwable throwable) {
        super(throwable);
    }
}
