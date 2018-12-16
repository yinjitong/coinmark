package com.flc.coinmarket.dao.mysql.model.statistics;

import com.flc.coinmarket.core.util.DateConvertUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsumerProfitsDaily implements Serializable {
    private Integer id;

    private BigDecimal profitsTeam;

    private BigDecimal profitsReferee;

    private BigDecimal profitsLockrepo;

    private Date createdTime;

    private Integer consumerId;

    private Integer accountId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getProfitsTeam() {
        return profitsTeam;
    }

    public void setProfitsTeam(BigDecimal profitsTeam) {
        this.profitsTeam = profitsTeam;
    }

    public BigDecimal getProfitsReferee() {
        return profitsReferee;
    }

    public void setProfitsReferee(BigDecimal profitsReferee) {
        this.profitsReferee = profitsReferee;
    }

    public BigDecimal getProfitsLockrepo() {
        return profitsLockrepo;
    }

    public void setProfitsLockrepo(BigDecimal profitsLockrepo) {
        this.profitsLockrepo = profitsLockrepo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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

    public String getCreatTime(){
        return  DateConvertUtil.convert(createdTime);
    }
}