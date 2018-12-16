package com.flc.coinmarket.dao.pojo;

import io.swagger.annotations.ApiModelProperty;

public class ConsumerPwdQuery {
    @ApiModelProperty(name = "phoneNo", value = "手机号", example = "13333333333")
    private String phoneNo;
    @ApiModelProperty(name = "passWord", value = "密码", example = "123456")
    private String passWord;
    @ApiModelProperty(name = "oldPassWord", value = "旧密码", example = "1234")
    private String oldPassWord;
    @ApiModelProperty(name = "checkcode", value = "短信验证码", example = "1234")
    private String checkcode;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
}
