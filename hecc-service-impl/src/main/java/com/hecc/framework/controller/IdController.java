package com.hecc.framework.controller;

import com.hecc.framework.impl.IdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuhoujun
 * @description:
 * @date: Created In 下午8:38 on 2018/4/20.
 */
@RestController
@RequestMapping("/base")
public class IdController {
    @Autowired
    private IdServiceImpl idService;

    @RequestMapping(value= "/getId", method = RequestMethod.GET)
    public long getId(String bizKey){
        return idService.getId(bizKey);
    }
}
