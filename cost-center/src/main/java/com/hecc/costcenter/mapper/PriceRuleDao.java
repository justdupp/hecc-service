package com.hecc.costcenter.mapper;

import com.hecc.costcenter.entity.PriceRuleEntity;
import com.hecc.costcenter.param.PriceParamInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuhoujun
 * @description: 定价规则dao
 * @date: Created In 下午9:25 on 2018/4/23.
 */
@Repository
public interface PriceRuleDao {

    /**
     * 根据条件查询列表-分页
     *
     * @param paramInfo 定价请求参数
     * @return 定价规则列表
     */
    List<PriceRuleEntity> getListByParam(@Param(value = "param") PriceParamInfo paramInfo);

    /**
     * 根据条件查询总条数
     *
     * @param paramInfo 定价请求参数
     * @return 总条数
     */
    Integer getTotalByParam(@Param(value = "param") PriceParamInfo paramInfo);

    /**
     * 新增定价
     *
     * @param entity 定价规则对象
     * @return 新增数量
     */
    Integer create(@Param(value = "param") PriceRuleEntity entity);

    /**
     * 删除定价规则
     *
     * @param entity 定价对象
     */
    void delete(@Param(value = "param") PriceRuleEntity entity);

    /**
     * 查询定价策略详情
     *
     * @param id 定价策略id
     * @return 定价规则对象
     */
    PriceRuleEntity getEntityById(Long id);

    /**
     * 修改定价
     *
     * @param entity 定价对象
     * @return 修改数量
     */
    Integer update(@Param(value = "param") PriceRuleEntity entity);

    /**
     * 根据产品id查询全部状态定价策略
     *
     * @param paramInfo 定价规则请求参数
     * @return 定价规则列表
     */
    List<PriceRuleEntity> getRuleListByProductId(@Param(value = "param") PriceParamInfo paramInfo);
}
