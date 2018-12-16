package com.flc.coinmarket.dao.mysql.model.consumer;

import java.io.Serializable;

public class ConsumerSettings implements Serializable {
    private Integer id;

    private Integer consumerId;

    private String headPortrait;

    private String nickName;

    private String autoTransfer;

    private String capitalPassword;

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

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAutoTransfer() {
        return autoTransfer;
    }

    public void setAutoTransfer(String autoTransfer) {
        this.autoTransfer = autoTransfer;
    }

    public String getCapitalPassword() {
        return capitalPassword;
    }

    public void setCapitalPassword(String capitalPassword) {
        this.capitalPassword = capitalPassword;
    }
}