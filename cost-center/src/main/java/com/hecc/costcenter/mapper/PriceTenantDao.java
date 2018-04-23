package com.hecc.costcenter.mapper;

import com.hecc.costcenter.entity.PriceTenantEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuhoujun
 * @description: 定价租户dao
 * @date: Created In 下午9:10 on 2018/4/23.
 */
@Repository
public interface PriceTenantDao {

    /**
     * 根据条件查询列表-不分页
     *
     * @param pid pid
     * @return 定价租户列表
     */
    List<PriceTenantEntity> getListByPid(@Param(value = "pid") Long pid);

    /**
     * 查询设置租户策略集合
     *
     * @param tenantId   租户id
     * @param ruleIdList 定价规则列表
     * @return 定价租户列表
     */
    List<PriceTenantEntity> getListByRuleIds(@Param(value = "tenantId") Long tenantId, @Param(value = "list") List<Long> ruleIdList);

    /**
     * 根据pid删除所有租户
     *
     * @param pid pid
     */
    void deleteByPid(@Param(value = "pid") Long pid);

    /**
     * 新增定价租户-批量
     *
     * @param list 定价租户列表
     * @return 新增数量
     */
    Integer createBatch(@Param(value = "list") List<PriceTenantEntity> list);
}
