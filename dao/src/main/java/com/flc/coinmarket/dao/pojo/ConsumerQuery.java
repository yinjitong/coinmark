package com.flc.coinmarket.dao.pojo;

import com.flc.coinmarket.core.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("用户信息查询条件")
public class ConsumerQuery extends BaseRequest {
    @ApiModelProperty(name = "consumerId", value = "用户ID", example = "1")
    private Integer consumerId;
    @ApiModelProperty(name = "phoneNo", value = "手机号码", example = "13838389438")
    private String phoneNo;
    @ApiModelProperty(name = "nickName", value = "昵称", example = "23333")
    private String nickName;
    @ApiModelProperty(name = "totalCapitalMin", value = "总资产从", example = "10000")
    private Double totalCapitalMin;
    @ApiModelProperty(name = "totalCapitalMax", value = "总资产到", example = "100000")
    private Double totalCapitalMax;
    @ApiModelProperty(name = "registryTimeFrom", value = "注册时间从", example = "2018-01-01")
    private Date registryTimeFrom;
    @ApiModelProperty(name = "registryTimeTo", value = "注册时间到", example = "2018-12-31")
    private Date registryTimeTo;

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Double getTotalCapitalMin() {
        return totalCapitalMin;
    }

    public void setTotalCapitalMin(Double totalCapitalMin) {
        this.totalCapitalMin = totalCapitalMin;
    }

    public Double getTotalCapitalMax() {
        return totalCapitalMax;
    }

    public void setTotalCapitalMax(Double totalCapitalMax) {
        this.totalCapitalMax = totalCapitalMax;
    }

    public Date getRegistryTimeFrom() {
        return registryTimeFrom;
    }

    public void setRegistryTimeFrom(Date registryTimeFrom) {
        this.registryTimeFrom = registryTimeFrom;
    }

    public Date getRegistryTimeTo() {
        return registryTimeTo;
    }

    public void setRegistryTimeTo(Date registryTimeTo) {
        this.registryTimeTo = registryTimeTo;
    }
}
