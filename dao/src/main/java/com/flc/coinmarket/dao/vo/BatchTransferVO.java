package com.flc.coinmarket.dao.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel("批量转账")
public class BatchTransferVO implements Serializable{
    @ApiModelProperty(name = "transferor", value = "转出方", example = "0-系统 1-个人")
    private String transferor;
    @ApiModelProperty(name = "balance", value = "余额", example = "100.00")
    private BigDecimal balance;
    @ApiModelProperty(name = "transferorPhone", value = "转出方手机号", example = "")
    private String transferorPhone;
    @ApiModelProperty(name = "transferAmt", value = "转出金额", example = "100.00")
    private BigDecimal transferAmt;
    @ApiModelProperty(name = "transfereePhones", value = "转入方手机号", example = "[]")
    private List<String> transfereePhones;
    @ApiModelProperty(name = "addressFlag", value = "地址标志", example = "0-消费地址1-锁仓地址")
    private String addressFlag;


    public String getTransferor() {
        return transferor;
    }

    public void setTransferor(String transferor) {
        this.transferor = transferor;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getTransferorPhone() {
        return transferorPhone;
    }

    public void setTransferorPhone(String transferorPhone) {
        this.transferorPhone = transferorPhone;
    }

    public BigDecimal getTransferAmt() {
        return transferAmt;
    }

    public void setTransferAmt(BigDecimal transferAmt) {
        this.transferAmt = transferAmt;
    }

    public List<String> getTransfereePhones() {
        return transfereePhones;
    }

    public void setTransfereePhones(List<String> transfereePhones) {
        this.transfereePhones = transfereePhones;
    }

    public String getAddressFlag() {
        return addressFlag;
    }

    public void setAddressFlag(String addressFlag) {
        this.addressFlag = addressFlag;
    }
}
