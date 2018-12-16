package com.flc.coinmarket.dao.mysql.model.consumer;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConsumerCapitalAccount implements Serializable {
    private Integer id;

    private Integer consumerId;

    private String teamPosCode;

    private BigDecimal floatingFunds;

    private BigDecimal lockrepoFunds;

    private BigDecimal profitsFunds;

    private BigDecimal accumulatedProfits;

    private BigDecimal profitsToday;

    private String floatingAddress;

    private String lockrepoAddress;

    private String profitsAddress;

    private String releaseFlag;

    private String reinvestFlag;

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

    public String getTeamPosCode() {
        return teamPosCode;
    }

    public void setTeamPosCode(String teamPosCode) {
        this.teamPosCode = teamPosCode;
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

    public BigDecimal getAccumulatedProfits() {
        return accumulatedProfits;
    }

    public void setAccumulatedProfits(BigDecimal accumulatedProfits) {
        this.accumulatedProfits = accumulatedProfits;
    }

    public BigDecimal getProfitsToday() {
        return profitsToday;
    }

    public void setProfitsToday(BigDecimal profitsToday) {
        this.profitsToday = profitsToday;
    }

    public String getFloatingAddress() {
        return floatingAddress;
    }

    public void setFloatingAddress(String floatingAddress) {
        this.floatingAddress = floatingAddress;
    }

    public String getLockrepoAddress() {
        return lockrepoAddress;
    }

    public void setLockrepoAddress(String lockrepoAddress) {
        this.lockrepoAddress = lockrepoAddress;
    }

    public String getProfitsAddress() {
        return profitsAddress;
    }

    public void setProfitsAddress(String profitsAddress) {
        this.profitsAddress = profitsAddress;
    }

    public String getReleaseFlag() {
        return releaseFlag;
    }

    public void setReleaseFlag(String releaseFlag) {
        this.releaseFlag = releaseFlag;
    }

    public String getReinvestFlag() {
        return reinvestFlag;
    }

    public void setReinvestFlag(String reinvestFlag) {
        this.reinvestFlag = reinvestFlag;
    }
}