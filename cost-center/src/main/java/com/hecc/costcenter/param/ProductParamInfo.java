package com.hecc.costcenter.param;

/**
 * @author xuhoujun
 * @description: 产品请求参数
 * @date: Created In 上午12:31 on 2018/4/21.
 */
public class ProductParamInfo extends BaseParamInfo {

    /**
     * 产品id
     */
    private Long id;
    /**
     * 上架状态
     */
    private Integer shelf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getShelf() {
        return shelf;
    }

    public void setShelf(Integer shelf) {
        this.shelf = shelf;
    }
}
