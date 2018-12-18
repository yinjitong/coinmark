package com.flc.coinmarket.dao.mongo.model;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Id;
import com.flc.coinmarket.core.util.DateConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Entity(connection = "db")
@ApiModel("用户交易明细")
public class ConsumerTranceDetail extends SimpleEntity {
    @Id
    @ApiModelProperty(name = "id", value = "id", example = "1")
    private String id;
    @ApiModelProperty(name = "tranceNo", value = "交易单号", example = "1")
    private String tranceNo;
    @ApiModelProperty(name = "accountId", value = "资金账户ID", example = "1")
    private Integer accountId;
    @ApiModelProperty(name = "tranceType", value = "交易分类", example = "1")
    private String tranceType;
    @ApiModelProperty(name = "sourceType", value = "交易类型", example = "1")
    private String sourceType;
    @ApiModelProperty(name = "funds", value = "交易资金", example = "1")
    private BigDecimal funds;
    @ApiModelProperty(name = "transferAddressFrom", value = "转出交易地址", example = "1")
    private String transferAddressFrom;
    @ApiModelProperty(name = "transferAddressTo", value = "转到交易地址", example = "1")
    private String transferAddressTo;
    @ApiModelProperty(name = "createdTime", value = "创建时间", example = "1")
    private Date createdTime;
    @ApiModelProperty(name = "updatedTime", value = "更新时间", example = "1")
    private Date updatedTime;
    @ApiModelProperty(name = "transferConsumer", value = "交易对方客户", example = "1")
    private Integer transferConsumer;


    private static final long serialVersionUID = 1L;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTranceNo() {
        return tranceNo == null || "".equals(tranceNo) ? id : tranceNo;
    }

    public void setTranceNo(String tranceNo) {
        this.tranceNo = tranceNo;
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

    public BigDecimal getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal funds) {
        this.funds = funds.setScale(8, RoundingMode.UP);
    }

    public String getTransferAddressFrom() {
        return transferAddressFrom;
    }

    public void setTransferAddressFrom(String transferAddressFrom) {
        this.transferAddressFrom = transferAddressFrom;
    }

    public String getTransferAddressTo() {
        return transferAddressTo;
    }

    public void setTransferAddressTo(String transferAddressTo) {
        this.transferAddressTo = transferAddressTo;
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

    public Integer getTransferConsumer() {
        return transferConsumer;
    }

    public void setTransferConsumer(Integer transferConsumer) {
        this.transferConsumer = transferConsumer;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNickNameFrom() {
        return nickNameFrom;
    }

    public void setNickNameFrom(String nickNameFrom) {
        this.nickNameFrom = nickNameFrom;
    }

    public String getNickNameTo() {
        return nickNameTo;
    }

    public void setNickNameTo(String nickNameTo) {
        this.nickNameTo = nickNameTo;
    }

    public String getPhoneNoFrom() {
        return phoneNoFrom;
    }

    public void setPhoneNoFrom(String phoneNoFrom) {
        this.phoneNoFrom = phoneNoFrom;
    }

    public String getPhoneNoTo() {
        return phoneNoTo;
    }

    public void setPhoneNoTo(String phoneNoTo) {
        this.phoneNoTo = phoneNoTo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCreatTime(){
        if(createdTime==null){
            return null;
        }
        return  DateConvertUtil.convertHMS(createdTime);
    }
}
