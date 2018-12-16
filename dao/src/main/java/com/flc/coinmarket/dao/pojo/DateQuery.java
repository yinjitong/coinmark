package com.flc.coinmarket.dao.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("日期范围")
public class DateQuery {
    @ApiModelProperty(name = "startDate", value = "开始日期", example = "1")
    private Date startDate;
    @ApiModelProperty(name = "endDate", value = "终止日期", example = "1")
    private Date  endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
