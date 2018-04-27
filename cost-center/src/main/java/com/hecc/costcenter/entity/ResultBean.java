package com.hecc.costcenter.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuhoujun
 * @description: 封装返回结果
 * @date: Created In 下午10:09 on 2018/4/26.
 */
public class ResultBean<T> implements Serializable {

    private static final int SUCCESS = 0;
    private static final int FAIL = 1;

    private String msg = "success";
    private Integer code = SUCCESS;

    private List<T> rows;
    private Integer total;

    public ResultBean(List<T> rows, Integer total) {
        this.rows = rows;
        this.total = total;
    }

    public ResultBean(Throwable e) {
        this.msg = e.toString();
        this.code = FAIL;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}