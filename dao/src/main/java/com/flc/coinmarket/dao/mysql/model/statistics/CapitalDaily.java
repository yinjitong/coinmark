package com.flc.coinmarket.dao.mysql.model.statistics;

import com.flc.coinmarket.core.util.DateConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@ApiModel("系统资产日增量")
public class CapitalDaily implements Serializable {
    @ApiModelProperty(name = "id", value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(name = "total", value = "增量总资产", example = "1")
    private BigDecimal total;
    @ApiModelProperty(name = "floatingFunds", value = "流动资产", example = "1")
    private BigDecimal floatingFunds;
    @ApiModelProperty(name = "lockrepoFunds", value = "锁仓资产", example = "1")
    private BigDecimal lockrepoFunds;
    @ApiModelProperty(name = "profitsFunds", value = "收益资产", example = "1")
    private BigDecimal profitsFunds;
    @ApiModelProperty(name = "createdTime", value = "创建时间", example = "1")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getFloatingFunds() {
        return floatingFunds;
    }

    public void setFloatingFunds(BigDecimal floatingFunds) {
        this.floatingFunds = floatingFunds;
    }

    public BigDecimal getLockrepoFunds() {
        return lockrepoFunds;
    }

    public void setLockrepoFunds(BigDecimal lockrepoFunds) {
        this.lockrepoFunds = lockrepoFunds;
    }

    public BigDecimal getProfitsFunds() {
        return profitsFunds;
    }

    public void setProfitsFunds(BigDecimal profitsFunds) {
        this.profitsFunds = profitsFunds;
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