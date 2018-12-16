package com.flc.coinmarket.dao.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("app用户登录")
public class ConsumerLogin {
    @ApiModelProperty(name = "phoneNo", value = "电话号码", example = "1")
    private String phoneNo;
    @ApiModelProperty(name = "password", value = "密码", example = "1")
    private String password;
    @ApiModelProperty(name = "ipAddress", value = "ipAddress", example = "1")
    private String ipAddress;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
