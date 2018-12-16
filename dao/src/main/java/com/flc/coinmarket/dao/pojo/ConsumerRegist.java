package com.flc.coinmarket.dao.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户注册")
public class ConsumerRegist {
    @ApiModelProperty(name = "phoneNo", value = "手机号", example = "13333333333")
    private String phoneNo;
    @ApiModelProperty(name = "passWord", value = "密码", example = "123456")
    private String passWord;
    @ApiModelProperty(name = "checkcode", value = "短信验证码", example = "1234")
    private String checkcode;
    @ApiModelProperty(name = "refereeCode", value = "推荐人码", example = "Led23dds")
    private String refereeCode;

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

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    public String getRefereeCode() {
        return refereeCode;
    }

    public void setRefereeCode(String refereeCode) {
        this.refereeCode = refereeCode;
    }
}
