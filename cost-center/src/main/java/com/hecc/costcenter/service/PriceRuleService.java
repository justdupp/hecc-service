package com.hecc.costcenter.service;

import com.hecc.costcenter.entity.PriceRuleEntity;
import com.hecc.costcenter.param.PriceParamInfo;

import java.util.List;

/**
 * @author xuhoujun
 * @description:
 * @date: Created In 上午12:35 on 2018/4/21.
 */
public interface PriceRuleService {

    //根据条件查询列表-分页
    List<PriceRuleEntity> getListByParam(PriceParamInfo paramInfo);

    //根据条件查询总条数
    Integer getTotalByParam(PriceParamInfo paramInfo);

    //新增定价
    Integer create(PriceRuleEntity entity);

}
