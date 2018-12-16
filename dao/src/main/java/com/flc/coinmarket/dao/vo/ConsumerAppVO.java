package com.flc.coinmarket.dao.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel("app用户信息")
public class ConsumerAppVO implements Serializable {
    @ApiModelProperty(name = "leftDimesionCode", value = "左二维码编码", example = "1")
    private String leftDimesionCode;

    @ApiModelProperty(name = "rightDimensionCode", value = "右二维码编码", example = "1")
    private String rightDimensionCode;

    @ApiModelProperty(name = "transferDimensionCode", value = "交易地址二维码编码", example = "1")
    private String transferDimensionCode;

    @ApiModelProperty(name = "phoneNo", value = "电话", example = "13333333333")
    private String phoneNo;

    @ApiModelProperty(name = "nickName", value = "昵称", example = "小红")
    private String nickName;

    @ApiModelProperty(name = "headPortrait", value = "头像", example = "")
    private String headPortrait;

    @ApiModelProperty(name = "account", value = "账户", example = "13333333333")
    private String account;

    @ApiModelProperty(name="currentPrice", value = "现价", example = "1")
    private BigDecimal currentPrice;

    @ApiModelProperty(name = "autoReinvestFlag", value = "是否自动复投", example = "0-关闭自动复投1-开启自动复投")
    private String autoReinvestFlag;


    public ConsumerAppVO() {
    }

    public ConsumerAppVO(String leftDimesionCode, String rightDimensionCode, String transferDimensionCode, String phoneNo, String nickName, String headPortrait, String account, BigDecimal currentPrice,String autoReinvestFlag) {
        this.leftDimesionCode = leftDimesionCode;
        this.rightDimensionCode = rightDimensionCode;
        this.transferDimensionCode = transferDimensionCode;
        this.phoneNo = phoneNo;
        this.nickName = nickName;
        this.headPortrait = headPortrait;
        this.account = account;
        this.currentPrice = currentPrice;
        this.autoReinvestFlag=autoReinvestFlag;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLeftDimesionCode() {
        return leftDimesionCode;
    }

    public void setLeftDimesionCode(String leftDimesionCode) {
        this.leftDimesionCode = leftDimesionCode;
    }

    public String getRightDimensionCode() {
        return rightDimensionCode;
    }

    public void setRightDimensionCode(String rightDimensionCode) {
        this.rightDimensionCode = rightDimensionCode;
    }

    public String getTransferDimensionCode() {
        return transferDimensionCode;
    }

    public void setTransferDimensionCode(String transferDimensionCode) {
        this.transferDimensionCode = transferDimensionCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getAutoReinvestFlag() {
        return autoReinvestFlag;
    }

    public void setAutoReinvestFlag(String autoReinvestFlag) {
        this.autoReinvestFlag = autoReinvestFlag;
    }
}
