package com.flc.coinmarket.dao.mysql.model.statistics;

import com.flc.coinmarket.core.util.DateConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("销毁资金日增量")
public class LockrepoDestroyDaily implements Serializable {

    @ApiModelProperty(name = "id", value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(name = "destoryCapital", value = "日销毁资金数量", example = "1")
    private BigDecimal destoryCapital;

    @ApiModelProperty(name = "createdTime", value = "创建时间", example = "1")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDestoryCapital() {
        return destoryCapital;
    }

    public void setDestoryCapital(BigDecimal destoryCapital) {
        this.destoryCapital = destoryCapital;
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

    public LockrepoDestroyDaily(){}

    public LockrepoDestroyDaily(BigDecimal destoryCapital, Date createdTime){
        this.destoryCapital = destoryCapital;
        this.createdTime = createdTime;
    }
}