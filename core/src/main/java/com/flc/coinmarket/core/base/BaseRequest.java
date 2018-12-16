package com.flc.coinmarket.core.base;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BaseRequest implements Serializable {
    @ApiModelProperty(name = "pageNo", value = "页码", example = "1")
    private Integer pageNo;
    @ApiModelProperty(name = "pageSize", value = "页大小", example = "1")
    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
