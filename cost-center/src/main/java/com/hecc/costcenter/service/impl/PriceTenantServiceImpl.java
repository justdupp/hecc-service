package com.hecc.costcenter.service.impl;

import com.hecc.costcenter.entity.PriceTenantEntity;
import com.hecc.costcenter.mapper.PriceTenantDao;
import com.hecc.costcenter.service.PriceTenantService;
import com.hecc.costcenter.service.feign.IdClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuhoujun
 * @description: 定价租户实现
 * @date: Created In 下午9:09 on 2018/4/23.
 */
@Service
public class PriceTenantServiceImpl implements PriceTenantService {

    @Autowired
    private IdClient idClient;

    @Autowired
    private PriceTenantDao priceTenantDao;

    @Override
    public List<PriceTenantEntity> getListByPid(Long pid) {
        return priceTenantDao.getListByPid(pid);
    }

    @Override
    public List<PriceTenantEntity> getListByRuleIds(Long tenantId, List<Long> ruleIdList) {
        return this.priceTenantDao.getListByRuleIds(tenantId, ruleIdList);
    }

    @Override
    public void deleteByPid(Long pid) {
        priceTenantDao.deleteByPid(pid);
    }

    @Override
    public Integer createBatch(List<PriceTenantEntity> list) {
        for(PriceTenantEntity entity : list){
            entity.setId(idClient.getId("_id"));
        }
        return priceTenantDao.createBatch(list);
    }
}
