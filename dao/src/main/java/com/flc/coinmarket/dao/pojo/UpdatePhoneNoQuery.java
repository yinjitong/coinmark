package com.flc.coinmarket.dao.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改手机号")
public class UpdatePhoneNoQuery {
    @ApiModelProperty(name = "orgPhoneNo", value = "原手机号", example = "13333333333")
    private String orgPhoneNo;
    @ApiModelProperty(name = "newPhoneNo", value = "新手机号", example = "14444444444")
    private String  newPhoneNo;
    @ApiModelProperty(name = "orgCheckCode", value = "原手机号验证码", example = "1234")
    private String orgCheckCode;
    @ApiModelProperty(name = "newCheckCode", value = "新手机号验证码", example = "1234")
    private String newCheckCode;


    public String getOrgPhoneNo() {
        return orgPhoneNo;
    }

    public void setOrgPhoneNo(String orgPhoneNo) {
        this.orgPhoneNo = orgPhoneNo;
    }

    public String getNewPhoneNo() {
        return newPhoneNo;
    }

    public void setNewPhoneNo(String newPhoneNo) {
        this.newPhoneNo = newPhoneNo;
    }

    public String getOrgCheckCode() {
        return orgCheckCode;
    }

    public void setOrgCheckCode(String orgCheckCode) {
        this.orgCheckCode = orgCheckCode;
    }

    public String getNewCheckCode() {
        return newCheckCode;
    }

    public void setNewCheckCode(String newCheckCode) {
        this.newCheckCode = newCheckCode;
    }
}
