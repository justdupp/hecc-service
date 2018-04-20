package com.hecc.costcenter.entity;

import org.apache.ibatis.type.Alias;

/**
 * @author xuhoujun
 * @description: 产品实体类
 * @date: Created In 下午10:22 on 2018/4/20.
 */
@Alias("ProductEntity")
public class ProductEntity extends BaseEntity {

    /**
     * 创建者
     */
    private String createName;
    /**
     * 修改者
     */
    private String modifyName;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品编码
     */
    private String code;
    /**
     * 上架状态0:下架,1:上架
     */
    private int shelf;
    /**
     * 产品描述
     */
    private String productDesc;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getShelf() {
        return shelf;
    }

    public void setShelf(int shelf) {
        this.shelf = shelf;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
