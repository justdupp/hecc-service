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

    /**
     * 删除定价规则
     *
     * @param entity 定价规则对象
     */
    void delete(PriceRuleEntity entity);

    /**
     * 查询定价策略详情
     *
     * @param id id
     * @return 定价规则对象
     */
    PriceRuleEntity getEntityById(Long id);

    /**
     * 修改定价
     *
     * @param entity 定价规则对象
     * @return 数量
     * @throws Exception
     */
    Integer update(PriceRuleEntity entity) throws Exception;

    /**
     * 根据产品id查询全部状态定价策略
     *
     * @param paramInfo 定价规则请求参数
     * @return 定价规则列表
     */
    List<PriceRuleEntity> getRuleListByProductId(PriceParamInfo paramInfo);

    /**
     * 保存定价租户
     *
     * @param priceRuleInfo 定价规则独享
     * @throws Exception
     */
    void saveTenant(PriceRuleEntity priceRuleInfo) throws Exception;

}
