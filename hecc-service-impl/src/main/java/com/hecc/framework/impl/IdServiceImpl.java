package com.hecc.framework.impl;

import com.hecc.framework.api.IdProducer;
import com.hecc.framework.api.IdService;
import com.hecc.framework.exception.IdProduceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuhoujun
 * @description:
 * @date: Created In 上午12:07 on 2018/4/20.
 */
@Service
public class IdServiceImpl implements IdService {

    private static final Logger logger = LoggerFactory.getLogger(IdServiceImpl.class);

    @Autowired
    private IdProducer idProducer;

    @Override
    public long getId(String key) {
        try {
            return idProducer.consume(key);
        } catch (IdProduceException e) {
            logger.error("获取id异常，key="+key,e);
            throw new RuntimeException(e);
        }
    }
}
