package com.hecc.costcenter.service;

import com.hecc.costcenter.entity.ProductEntity;
import com.hecc.costcenter.param.ProductParamInfo;

import java.util.List;

/**
 * @author xuhoujun
 * @description: 产品服务
 * @date: Created In 上午12:34 on 2018/4/21.
 */
public interface ProductService {

    /**
     * 根据参数获取产品列表
     *
     * @param paramInfo 产品请求参数
     * @return 产品列表
     */
    List<ProductEntity> getProductListByParam(ProductParamInfo paramInfo);

    /**
     * 根据参数获取总条数
     *
     * @param paramInfo 产品请求参数
     * @return 总数量
     */
    Integer getProductTotalByParam(ProductParamInfo paramInfo);

    /**
     * 新增产品
     *
     * @param entity 产品实体对象
     * @return 新增结果
     */
    String create(ProductEntity entity);

    /**
     * 更新上架/下架状态
     *
     * @param entity
     */
    void updateShelf(ProductEntity entity);

    /**
     * 产品详情
     *
     * @param id 产品id
     * @return
     */
    ProductEntity getEntityById(Long id);

    /**
     * 修改产品信息
     *
     * @param entity 产品对象
     * @return 修改结果
     */
    String update(ProductEntity entity);
}
