package com.flc.coinmarket.dao.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("用户转账")
public class ConsumerTrance {
    @ApiModelProperty(name = "transferAddressTo", value = "转到交易地址", example = "1")
    private String transferAddressTo;
    @ApiModelProperty(name = "funds", value = "转账数量", example = "1")
    private BigDecimal funds;
    @ApiModelProperty(name = "passWord", value = "资金密码", example = "1")
    private  String passWord;

    private  Integer id;//用户id

    public String getTransferAddressTo() {
        return transferAddressTo;
    }

    public void setTransferAddressTo(String transferAddressTo) {
        this.transferAddressTo = transferAddressTo;
    }

    public BigDecimal getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal funds) {
        this.funds = funds;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
