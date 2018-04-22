package com.hecc.costcenter.service;

import com.hecc.costcenter.entity.PriceTenantEntity;

import java.util.List;

/**
 * @author xuhoujun
 * @description: 定价租户服务
 * @date: Created In 下午8:47 on 2018/4/22.
 */
public interface PriceTenantService {

    /**
     * 根据条件查询列表-分页
     *
     * @param pid 产品id
     * @return 定价租户对象列表
     */
    List<PriceTenantEntity> getListByPid(Long pid);

    /**
     * 查询设置租户策略集合
     *
     * @param tenantId
     * @param ruleIdList
     * @return 定价租户对象列表
     */
    List<PriceTenantEntity> getListByRuleIds(Long tenantId, List<Long> ruleIdList);

    /**
     * 根据pid删除所有租户
     *
     * @param pid
     */
    void deleteByPid(Long pid);

    /**
     * 批量新增定价租户
     *
     * @param list
     * @return 批量新增数量
     */
    Integer createBatch(List<PriceTenantEntity> list);
}
