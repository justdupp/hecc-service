package com.hecc.costcenter.entity;

import org.apache.ibatis.type.Alias;

/**
 * @author xuhoujun
 * @description: 定价租户实体对象
 * @date: Created In 下午8:51 on 2018/4/22.
 */
@Alias("PriceTenantEntity")
public class PriceTenantEntity extends BaseEntity {

    /**
     * 定价规则id
     */
    private Long pid;
    /**
     * 包含租户id
     */
    private Long tenantId;
    /**
     * 删除标识
     */
    private Boolean del;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 租户编码
     */
    private String code;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
