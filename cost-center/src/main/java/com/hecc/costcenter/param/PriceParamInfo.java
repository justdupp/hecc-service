package com.hecc.costcenter.param;

/**
 * @author xuhoujun
 * @description: 定价请求参数
 * @date: Created In 上午12:30 on 2018/4/21.
 */
public class PriceParamInfo extends BaseParamInfo {

    /**
     * 产品id
     */
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
