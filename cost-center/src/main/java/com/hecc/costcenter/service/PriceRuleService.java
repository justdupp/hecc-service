package com.hecc.costcenter.service;

import com.hecc.costcenter.entity.PriceRuleEntity;
import com.hecc.costcenter.param.PriceParamInfo;

import java.util.List;

/**
 * @author xuhoujun
 * @description: 产品定价服务
 * @date: Created In 上午12:35 on 2018/4/21.
 */
public interface PriceRuleService {


    /**
     * 根据条件查询列表
     *
     * @param paramInfo 定价参数
     * @return 产品定价实体类列表
     */
    List<PriceRuleEntity> getListByParam(PriceParamInfo paramInfo);


    /**
     * 根据条件查询总条数
     *
     * @param paramInfo 定价参数
     * @return 总条数
     */
    Integer getTotalByParam(PriceParamInfo paramInfo);


    /**
     * 新增定价
     *
     * @param entity 产品定价实体类
     * @return 新增条数
     */
    Integer create(PriceRuleEntity entity);

}
