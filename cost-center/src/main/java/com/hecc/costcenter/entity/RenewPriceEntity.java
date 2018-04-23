package com.hecc.costcenter.entity;

import java.io.Serializable;

/**
 * @author xuhoujun
 * @description:
 * @date: Created In 下午9:34 on 2018/4/23.
 */
public class RenewPriceEntity implements Serializable {

    /**
     * 1个月价格
     */
    private String oneMonth;
    /**
     * 3个月价格
     */
    private String threeMonth;
    /**
     * 1年价格
     */
    private String oneYear;
    /**
     * 2年价格
     */
    private String twoYyear;
    /**
     * 3年价格
     */
    private String threeYear;

    public String getOneMonth() {
        return oneMonth;
    }

    public void setOneMonth(String oneMonth) {
        this.oneMonth = oneMonth;
    }

    public String getThreeMonth() {
        return threeMonth;
    }

    public void setThreeMonth(String threeMonth) {
        this.threeMonth = threeMonth;
    }

    public String getOneYear() {
        return oneYear;
    }

    public void setOneYear(String oneYear) {
        this.oneYear = oneYear;
    }

    public String getTwoYyear() {
        return twoYyear;
    }

    public void setTwoYyear(String twoYyear) {
        this.twoYyear = twoYyear;
    }

    public String getThreeYear() {
        return threeYear;
    }

    public void setThreeYear(String threeYear) {
        this.threeYear = threeYear;
    }
}
