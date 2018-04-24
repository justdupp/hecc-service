package com.hecc.costcenter.service.impl;

import com.hecc.costcenter.entity.PriceRuleEntity;
import com.hecc.costcenter.param.PriceParamInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author xuhoujun
 * @description:
 * @date: Created In 下午9:49 on 2018/4/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceRuleServiceImplTest {

    @Autowired
    private PriceRuleServiceImpl priceRuleService;

    @Test
    public void testCreate(){
        PriceRuleEntity entity = new PriceRuleEntity();
        entity.setCreateName("xiaohui");
        entity.setProductId(2L);
        entity.setProductName("我的周大宝宝");
        entity.setSalePrice(100d);
        Integer num = priceRuleService.create(entity);
    }

    @Test
    public void testGetListByParam(){
        PriceParamInfo priceParamInfo = new PriceParamInfo();
        priceParamInfo.setProductId(2L);
        priceParamInfo.setPage(0);
        priceParamInfo.setRows(10);
        List<PriceRuleEntity> priceRuleEntityList =  priceRuleService.getListByParam(priceParamInfo);
        System.out.println(priceRuleEntityList.size());
    }

    @Test
    public void testGetTotalByParam(){
        PriceParamInfo priceParamInfo = new PriceParamInfo();
        priceParamInfo.setProductId(2L);
        System.out.println("产品定价条数 "+ priceRuleService.getTotalByParam(priceParamInfo));
    }

    @Test
    public void testDelete(){
        PriceRuleEntity entity = new PriceRuleEntity();
        entity.setId(1L);
        priceRuleService.delete(entity);
    }


}