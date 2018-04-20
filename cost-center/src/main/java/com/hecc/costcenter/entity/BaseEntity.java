package com.hecc.costcenter.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xuhoujun
 * @description: 实体基础类
 * @date: Created In 下午10:16 on 2018/4/20.
 */
public class BaseEntity implements Serializable {

    /**
     * 主键
     */
    public Long id;

    /**
     * 创建时间
     */
    public Date createDate;

    /**
     * 修改时间
     */

    public Date modifyDate;

    /**
     * 版本号--乐观锁
     */
    public Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
