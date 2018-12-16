package com.flc.coinmarket.dao.mysql.model.statistics;

import com.flc.coinmarket.core.util.DateConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("用户总量")
public class ConsumerCountTotal implements Serializable {

    @ApiModelProperty(name = "id", value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(name = "count", value = "用户总量", example = "1")
    private Integer count;

    @ApiModelProperty(name = "createdTime", value = "创建时间", example = "1")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public String getCreatTime(){
        return  DateConvertUtil.convert(createdTime);
    }
}