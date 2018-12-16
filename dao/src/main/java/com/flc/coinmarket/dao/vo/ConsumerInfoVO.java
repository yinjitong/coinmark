package com.flc.coinmarket.dao.vo;

import com.flc.coinmarket.core.util.DateConvertUtil;
import com.flc.coinmarket.dao.mysql.model.consumer.Consumer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("用户信息")
public class ConsumerInfoVO extends Consumer implements Serializable {
    @ApiModelProperty(name = "nickName", value = "昵称", example = "天才")
    private String nickName;
    @ApiModelProperty(name = "totalFunds", value = "用户总资产", example = "1")
    private BigDecimal totalFunds;
    @ApiModelProperty(name = "lockrepoFunds", value = "用户锁仓资产", example = "1")
    private BigDecimal lockrepoFunds;
    @ApiModelProperty(name = "profitsFunds", value = "用户收益资产", example = "1")
    private BigDecimal profitsFunds;
    @ApiModelProperty(name = "floatingFunds", value = "用户流动资产", example = "1")
    private BigDecimal floatingFunds;
    @ApiModelProperty(name = "lastLoginTime", value = "用户上次登录时间", example = "1")
    private Date lastLoginTime;
    @ApiModelProperty(name = "refNickName", value = "推荐人昵称", example = "1")
    private String refNickName;
    @ApiModelProperty(name = "leftNickName", value = "左节点", example = "1")
    private String leftNickName;
    @ApiModelProperty(name = "rightNickName", value = "右节点", example = "1")
    private String rightNickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BigDecimal getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(BigDecimal totalFunds) {
        this.totalFunds = totalFunds;
    }

    public BigDecimal getLockrepoFunds() {
        return lockrepoFunds;
    }

    public void setLockrepoFunds(BigDecimal lockrepoFunds) {
        this.lockrepoFunds = lockrepoFunds;
    }

    public BigDecimal getProfitsFunds() {
        return profitsFunds;
    }

    public void setProfitsFunds(BigDecimal profitsFunds) {
        this.profitsFunds = profitsFunds;
    }

    public BigDecimal getFloatingFunds() {
        return floatingFunds;
    }

    public void setFloatingFunds(BigDecimal floatingFunds) {
        this.floatingFunds = floatingFunds;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getRefNickName() {
        return refNickName;
    }

    public void setRefNickName(String refNickName) {
        this.refNickName = refNickName;
    }

    public String getLeftNickName() {
        return leftNickName;
    }

    public void setLeftNickName(String leftNickName) {
        this.leftNickName = leftNickName;
    }

    public String getRightNickName() {
        return rightNickName;
    }

    public void setRightNickName(String rightNickName) {
        this.rightNickName = rightNickName;
    }

    public String getLastLogin(){
        if(lastLoginTime==null){
           return null;
        }
        return  DateConvertUtil.convert(lastLoginTime);
    }
}
