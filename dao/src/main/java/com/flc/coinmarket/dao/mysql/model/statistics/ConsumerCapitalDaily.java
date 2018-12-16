package com.flc.coinmarket.dao.mysql.model.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("用户资金日增量")
public class ConsumerCapitalDaily implements Serializable {

    @ApiModelProperty(name = "id", value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(name = "consumerId", value = "用户ID", example = "1")
    private Integer consumerId;

    @ApiModelProperty(name = "accountId", value = "资金账户ID", example = "1")
    private Integer accountId;

    @ApiModelProperty(name = "tranceType", value = "交易分类", example = "1")
    private String tranceType;

    @ApiModelProperty(name = "sourceType", value = "交易类型", example = "1")
    private String sourceType;

    @ApiModelProperty(name = "dailyFunds", value = "日总资产", example = "1")
    private BigDecimal dailyFunds;

    @ApiModelProperty(name = "floatingFunds", value = "流动资金", example = "1")
    private BigDecimal floatingFunds;

    @ApiModelProperty(name = "lockrepoFunds", value = "锁仓资金", example = "1")
    private BigDecimal lockrepoFunds;

    @ApiModelProperty(name = "profitsFunds", value = "收益", example = "1")
    private BigDecimal profitsFunds;

    @ApiModelProperty(name = "createTime", value = "创建时间", example = "1")
    private Date createTime;

    @ApiModelProperty(name = "updatedTime", value = "更新时间", example = "1")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getTranceType() {
        return tranceType;
    }

    public void setTranceType(String tranceType) {
        this.tranceType = tranceType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public BigDecimal getDailyFunds() {
        return dailyFunds;
    }

    public void setDailyFunds(BigDecimal dailyFunds) {
        this.dailyFunds = dailyFunds;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}