package com.hecc.costcenter.service.impl;

import com.hecc.costcenter.entity.ProductEntity;
import com.hecc.costcenter.param.ProductParamInfo;
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
 * @date: Created In 下午9:47 on 2018/4/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void testCreate(){
        ProductEntity entity = new ProductEntity();
        entity.setCode("xiaohui");
        entity.setName("唐晓辉");
        entity.setShelf(0);
        entity.setProductDesc("妹子");
        String result =  productService.create(entity);
        System.out.println("新增结果： "+result);
    }

    @Test
    public void testGetProductListByParam(){
        ProductParamInfo paramInfo = new ProductParamInfo();
        paramInfo.setPage(0);
        paramInfo.setRows(10);
        List<ProductEntity> list =  productService.getProductListByParam(paramInfo);
        System.out.println(list.size());
    }

    @Test
    public void testGetProductTotalByParam(){
        ProductParamInfo paramInfo = new ProductParamInfo();
        System.out.println(productService.getProductTotalByParam(paramInfo));
    }

    @Test
    public void testGetEntityById(){
        ProductEntity entity = productService.getEntityById(2L);
        System.out.println(entity.getName());
    }

    @Test
    public void testUpdate(){
        ProductEntity updateEntity = new ProductEntity();
        updateEntity.setId(2L);
        updateEntity.setName("我的周大宝宝");
        updateEntity.setCode("zhouyun");
        String result = productService.update(updateEntity);
        System.out.println("修改结果 "+result);
    }

    @Test
    public void testUpdateShelf(){
        ProductEntity entity = new ProductEntity();
        entity.setShelf(1);
        productService.updateShelf(entity);
    }

}