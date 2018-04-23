package com.hecc.costcenter.service.impl;

import com.hecc.costcenter.entity.PriceRuleEntity;
import com.hecc.costcenter.entity.PriceTenantEntity;
import com.hecc.costcenter.mapper.PriceRuleDao;
import com.hecc.costcenter.param.PriceParamInfo;
import com.hecc.costcenter.service.PriceRuleService;
import com.hecc.costcenter.service.PriceTenantService;
import com.hecc.costcenter.service.feign.IdClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuhoujun
 * @description: 定价规则服务实现
 * @date: Created In 下午9:29 on 2018/4/23.
 */
@Service
public class PriceRuleServiceImpl implements PriceRuleService {

    @Autowired
    private IdClient idClient;

    @Autowired
    private PriceRuleDao priceRuleDao;

    @Autowired
    private PriceTenantService priceTenantService;

    @Override
    public List<PriceRuleEntity> getListByParam(PriceParamInfo paramInfo) {
        return priceRuleDao.getListByParam(paramInfo);
    }

    @Override
    public Integer getTotalByParam(PriceParamInfo paramInfo) {
        return priceRuleDao.getTotalByParam(paramInfo);
    }

    @Override
    public Integer create(PriceRuleEntity entity) {
        entity.setId(idClient.getId("_id"));
        Integer count = priceRuleDao.create(entity);
        return count;
    }

    @Override
    public void delete(PriceRuleEntity entity) {
        priceRuleDao.delete(entity);
    }

    @Override
    public PriceRuleEntity getEntityById(Long id) {
        return priceRuleDao.getEntityById(id);
    }

    @Override
    public Integer update(PriceRuleEntity entity) throws Exception {
        return priceRuleDao.update(entity);
    }

    @Override
    public List<PriceRuleEntity> getRuleListByProductId(PriceParamInfo paramInfo) {
        return this.priceRuleDao.getRuleListByProductId(paramInfo);
    }

    @Override
    public void saveTenant(PriceRuleEntity priceRuleInfo) throws Exception {
        /**
         * 删除已保存租户
         */
        this.priceTenantService.deleteByPid(priceRuleInfo.getId());
        /**
         * 全量更新包含租户
         */
        if(!priceRuleInfo.getTenantEntityList().isEmpty()){
            for(PriceTenantEntity priceTenantInfo : priceRuleInfo.getTenantEntityList()){
                priceTenantInfo.setPid(priceRuleInfo.getId());
            }
            this.priceTenantService.createBatch(priceRuleInfo.getTenantEntityList());
        }
    }
}
