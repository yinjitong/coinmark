package com.flc.coinmarket.dao.pojo;

import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerWithBLOBs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("用户信息新增")
public class ConsumerParam extends ConsumerWithBLOBs {
    @ApiModelProperty(name = "nickName", value = "昵称", example = "23333")
    private  String nickName;
    @ApiModelProperty(name = "floatingFunds", value = "消费资产", example = "1")
    private BigDecimal floatingFunds;
    @ApiModelProperty(name = "refereePhone", value = "推荐人手机号", example = "13333333333")
    private String refereePhone;
    @ApiModelProperty(name = "codeDirection", value = "左右推荐码", example = "0-左 1-右")
    private String codeDirection;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BigDecimal getFloatingFunds() {
        return floatingFunds;
    }

    public void setFloatingFunds(BigDecimal floatingFunds) {
        this.floatingFunds = floatingFunds;
    }

    public String getRefereePhone() {
        return refereePhone;
    }

    public void setRefereePhone(String refereePhone) {
        this.refereePhone = refereePhone;
    }

    public String getCodeDirection() {
        return codeDirection;
    }

    public void setCodeDirection(String codeDirection) {
        this.codeDirection = codeDirection;
    }
}
