package com.hecc.costcenter.entity;

import org.apache.ibatis.type.Alias;

/**
 * @author xuhoujun
 * @description: 产品定价实体类
 * @date: Created In 下午10:25 on 2018/4/20.
 */
@Alias("PriceRuleEntity")
public class PriceRuleEntity extends BaseEntity {

    /**
     * 创建者
     */
    private String createName;
    /**
     * 修改者
     */
    private String modifyName;
    /**
     * 定价策略名称
     */
    private String ruleName;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 启用状态0:停用,1:启用
     */
    private boolean useful;
    /**
     * 删除状态0:未删除,1:删除
     */
    private boolean del;
    /**
     * 售价
     */
    private Double salePrice;
    /**
     * 续约价格json
     */
    private String renewPrice;

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isUseful() {
        return useful;
    }

    public void setUseful(boolean useful) {
        this.useful = useful;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getRenewPrice() {
        return renewPrice;
    }

    public void setRenewPrice(String renewPrice) {
        this.renewPrice = renewPrice;
    }
}

