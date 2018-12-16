package com.flc.coinmarket.dao.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("发送验证码")
public class SendMessageQuery {

    @ApiModelProperty(name = "phoneNo", value = "电话号码", example = "133333333")
    private String phoneNo;
    @ApiModelProperty(name = "source", value = "来源", example = "regist,forgetPwd,updatePwd,updateFundsPwd")
    private String  source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
