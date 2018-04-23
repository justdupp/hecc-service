package com.hecc.costcenter.mapper;

import com.hecc.costcenter.entity.ProductEntity;
import com.hecc.costcenter.param.ProductParamInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuhoujun
 * @description: 产品dao
 * @date: Created In 下午8:49 on 2018/4/23.
 */
@Repository
public interface ProductDao {

    /**
     * 根据参数获取产品总数
     *
     * @param paramInfo 请求参数
     * @return 总条数
     */
    Integer getProductTotalByParam(@Param(value = "param") ProductParamInfo paramInfo);

    /**
     * 根据参数获取产品列表
     *
     * @param paramInfo 请求参数
     * @return 产品列表
     */
    List<ProductEntity> getProductListByParam(@Param(value = "param") ProductParamInfo paramInfo);

    /**
     * 根据产品名称获取产品列表
     *
     * @param entity 产品对象
     * @return 产品列表
     */
    List<ProductEntity> getListByName(@Param(value = "param") ProductEntity entity);

    /**
     * 根据编码查询列表
     *
     * @param entity 产品对象
     * @return 产品列表
     */
    List<ProductEntity> getListByCode(@Param(value = "param") ProductEntity entity);

    /**
     * 新增产品
     *
     * @param entity 产品对象
     * @return 新增条数
     */
    Integer create(@Param(value = "param") ProductEntity entity);

    /**
     * 更新上架/下架状态
     *
     * @param entity
     */
    void updateShelf(@Param(value = "param") ProductEntity entity);

    /**
     * 产品详情
     *
     * @param id 产品id
     * @return 产品对象
     */
    ProductEntity getEntityById(Long id);

    /**
     * 修改产品信息
     *
     * @param entity
     */
    void update(@Param(value = "param") ProductEntity entity);
}
