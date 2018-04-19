package com.hecc.framework.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * @author xuhoujun
 * @description: id服务单元测试
 * @date: Created In 上午12:31 on 2018/4/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IdServiceImplTest {

    @Autowired
    private IdServiceImpl idService;

    @Test
    public void getNumId() {
        CountDownLatch latch = new CountDownLatch(100);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                String key = "test";
                long numId = idService.getId(key);
                latch.countDown();
                System.out.println("=================numberId:" + numId + ",threadId:" + Thread.currentThread().getId());
            });
        }
        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}