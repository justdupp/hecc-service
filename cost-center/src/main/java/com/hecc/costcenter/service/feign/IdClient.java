package com.hecc.costcenter.service.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xuhoujun
 * @description: id服务
 * @date: Created In 下午9:03 on 2018/4/23.
 */
@FeignClient("base-service")
public interface IdClient {

    @RequestMapping(value = "base/getId", method = RequestMethod.GET)
    long getId(@RequestParam("bizKey") String bizKey);
}
