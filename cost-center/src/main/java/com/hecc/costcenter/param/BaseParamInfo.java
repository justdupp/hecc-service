package com.hecc.costcenter.param;

import java.io.Serializable;

/**
 * @author xuhoujun
 * @description: 基本请求参数
 * @date: Created In 上午12:30 on 2018/4/21.
 */
public class BaseParamInfo implements Serializable {

    /**
     * 页数
     */
    private Integer page;
    /**
     * 行数
     */
    private Integer rows;
    /**
     * 是否查询全部
     */
    private Boolean total;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Boolean getTotal() {
        return total;
    }

    public void setTotal(Boolean total) {
        this.total = total;
    }

}
