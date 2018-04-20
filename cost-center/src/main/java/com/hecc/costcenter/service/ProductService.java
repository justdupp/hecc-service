package com.hecc.costcenter.service;

import com.hecc.costcenter.entity.ProductEntity;
import com.hecc.costcenter.param.ProductParamInfo;

import java.util.List;

/**
 * @author xuhoujun
 * @description:
 * @date: Created In 上午12:34 on 2018/4/21.
 */
public interface ProductService {

    List<ProductEntity> getProductListByParam(ProductParamInfo paramInfo);

    Integer getProductTotalByParam(ProductParamInfo paramInfo);

    //新增产品
    String create(ProductEntity entity);

    //更新上架/下架状态
    void updateShelf(ProductEntity entity);

    //产品详情
    ProductEntity getEntityById(Long id);

    //修改产品信息
    String update(ProductEntity entity);
}
