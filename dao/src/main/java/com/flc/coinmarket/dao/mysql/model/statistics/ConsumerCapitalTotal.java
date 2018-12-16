package com.flc.coinmarket.dao.mysql.model.statistics;

import com.flc.coinmarket.core.util.DateConvertUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsumerCapitalTotal implements Serializable {
    private Integer id;

    private Integer consumerId;

    private Integer accountId;

    private String tranceType;

    private String sourceType;

    private BigDecimal totalFunds;

    private BigDecimal floatingFunds;

    private BigDecimal lockrepoFunds;

    private BigDecimal profitsFunds;

    private Date createdTime;

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

    public BigDecimal getTotalFunds() {
        return totalFunds == null ? new BigDecimal(0):totalFunds;
    }

    public void setTotalFunds(BigDecimal totalFunds) {
        this.totalFunds = totalFunds;
    }

    public BigDecimal getFloatingFunds() {
        return floatingFunds == null ? new BigDecimal(0):floatingFunds;
    }

    public void setFloatingFunds(BigDecimal floatingFunds) {
        this.floatingFunds = floatingFunds;
    }

    public BigDecimal getLockrepoFunds() {
        return lockrepoFunds == null ? new BigDecimal(0):lockrepoFunds;
    }

    public void setLockrepoFunds(BigDecimal lockrepoFunds) {
        this.lockrepoFunds = lockrepoFunds;
    }

    public BigDecimal getProfitsFunds() {
        return profitsFunds == null ? new BigDecimal(0):profitsFunds;
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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatTime(){
        return  DateConvertUtil.convert(createdTime);
    }
}