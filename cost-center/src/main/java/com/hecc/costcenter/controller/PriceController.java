package com.hecc.costcenter.controller;

import com.hecc.costcenter.entity.PriceRuleEntity;
import com.hecc.costcenter.entity.PriceTenantEntity;
import com.hecc.costcenter.entity.ProductEntity;
import com.hecc.costcenter.param.PriceParamInfo;
import com.hecc.costcenter.service.PriceRuleService;
import com.hecc.costcenter.service.PriceTenantService;
import com.hecc.costcenter.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuhoujun
 * @description: 定价控制器
 * @date: Created In 下午9:35 on 2018/4/25.
 */
@Controller
@RequestMapping(value = "/back/price")
public class PriceController {
    private static final Logger logger = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    private PriceRuleService priceRuleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceTenantService priceTenantService;

    /**
     * 产品定价-列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPriceList(PriceParamInfo paramInfo) {
        Integer total = 0;
        Map<String, Object> map = new HashMap<>();
        List<PriceRuleEntity> priceRuleList = new ArrayList<>();
        try {
            priceRuleList = this.priceRuleService.getListByParam(paramInfo);
            if (!priceRuleList.isEmpty()) {
                total = this.priceRuleService.getTotalByParam(paramInfo);
                //产品名称
                for (PriceRuleEntity ruleInfo : priceRuleList) {
                    if (ruleInfo.getProductId() != null) {
                        ProductEntity productInfo = this.productService.getEntityById(ruleInfo.getProductId());
                        if (productInfo != null) {
                            ruleInfo.setProductName(productInfo.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取产品定价列表异常", e);
        }
        map.put("rows", priceRuleList);
        map.put("total", total);
        return map;
    }

    /**
     * 新增产品定价策略
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody PriceRuleEntity priceRuleEntity) {
        try {
            priceRuleEntity.setCreateName("admin");
            priceRuleEntity.setModifyName("admin");
            this.priceRuleService.create(priceRuleEntity);
            return "保存成功";
        } catch (Exception e) {
            logger.error("新增定价策略异常", e);
        }
        return "保存失败";
    }

    /**
     * 产品定价-删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deletePrice(Long id) {
        try {
            if (id == null) {
                return "参数错误";
            }
            PriceRuleEntity priceRuleInfo = new PriceRuleEntity();
            priceRuleInfo.setId(id);
            priceRuleInfo.setModifyName("admin");
            this.priceRuleService.delete(priceRuleInfo);
            return "删除成功";
        } catch (Exception e) {
            logger.error("删除商品定价异常", e);
        }
        return "删除失败";
    }

    /**
     * 产品定价策略--详情
     *
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public PriceRuleEntity getPriceRuleById(Long id) {
        PriceRuleEntity priceRuleEntity = this.priceRuleService.getEntityById(id);
        if (priceRuleEntity != null && priceRuleEntity.getProductId() != null) {
            //产品名称
            ProductEntity productInfo = this.productService.getEntityById(priceRuleEntity.getProductId());
            if (productInfo != null) {
                priceRuleEntity.setProductName(productInfo.getName());
            }
            //定价包含租户
            List<PriceTenantEntity> tenantInfoList = this.priceTenantService.getListByPid(priceRuleEntity.getId());
            if (tenantInfoList != null && tenantInfoList.size() > 0) {
                for (PriceTenantEntity tenantInfo : tenantInfoList) {
                    // TODO: 2018-04-21  这里可以调用租户服务 来获取租户相关信息
                }
                priceRuleEntity.setTenantEntityList(tenantInfoList);
            }
        } else {
            priceRuleEntity = new PriceRuleEntity();
        }
        return priceRuleEntity;
    }

    /**
     * 产品定价--修改
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updatePrice(@RequestBody PriceRuleEntity priceRuleInfo) {
        try {
            if (priceRuleInfo == null || priceRuleInfo.getId() == null) {
                return "参数错误";
            }
            priceRuleInfo.setModifyName("admin");
            this.priceRuleService.update(priceRuleInfo);
            return "保存成功";
        } catch (Exception e) {
            logger.error("修改商品定价异常", e);
        }
        return "保存失败";
    }

    /**
     * 定价租户--保存
     *
     * @return
     */
    @RequestMapping(value = "save/tenant", method = RequestMethod.POST)
    @ResponseBody
    public String saveTenant(@RequestBody PriceRuleEntity priceRuleInfo) {
        try {
            if (priceRuleInfo == null || priceRuleInfo.getId() == null) {
                return "参数错误";
            }
            //tenantId重复校验
            if (!priceRuleInfo.getTenantEntityList().isEmpty()) {
                List<Long> idList = new ArrayList<>();
                List<Long> repeatList = new ArrayList<>();
                for (PriceTenantEntity inf : priceRuleInfo.getTenantEntityList()) {
                    if (idList.contains(inf.getTenantId())) {
                        repeatList.add(inf.getTenantId());
                    } else {
                        idList.add(inf.getTenantId());
                    }
                }
                if (repeatList.size() > 0) {
                    return "租户id" + repeatList.toString() + "重复设置";
                }
            }
            //校验租户是否在其他策略中
            PriceRuleEntity entity = this.priceRuleService.getEntityById(priceRuleInfo.getId());
            PriceParamInfo paramInfo = new PriceParamInfo();
            paramInfo.setProductId(entity.getProductId());
            List<PriceRuleEntity> ruleInfoList = this.priceRuleService.getRuleListByProductId(paramInfo);

            //若选择租户，租户全选标志必须非全选，租户只能有一个策略
            List<Long> ruleIdList = new ArrayList<>();
            for (PriceRuleEntity ruleInfo : ruleInfoList) {
                if (!ruleInfo.getId().equals(priceRuleInfo.getId())) {
                    ruleIdList.add(ruleInfo.getId()); //不含当前策略id
                }
            }
            if (ruleIdList.size() > 0) {
                for (PriceTenantEntity tenantInfo : priceRuleInfo.getTenantEntityList()) {
                    List<PriceTenantEntity> tenantList = this.priceTenantService.getListByRuleIds(tenantInfo.getTenantId(), ruleIdList);
                    if (!tenantList.isEmpty()) {
                        PriceRuleEntity otherRule = this.priceRuleService.getEntityById(tenantList.get(0).getPid());
                        return "租户[" + tenantInfo.getTenantId() + "]已在定价[" + otherRule.getRuleName() + "]中";
                    }
                }
            }
            this.priceRuleService.saveTenant(priceRuleInfo);
            return "保存成功";
        } catch (Exception e) {
            logger.error("修改定价租户异常", e);
        }
        return "保存失败";
    }
}
