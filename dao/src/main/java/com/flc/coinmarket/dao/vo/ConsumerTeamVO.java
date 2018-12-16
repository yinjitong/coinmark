package com.flc.coinmarket.dao.vo;

import com.flc.coinmarket.core.util.DateConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("用户团队信息")
public class ConsumerTeamVO {
    @ApiModelProperty(name = "id", value = "用户团队信息ID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "nickName", value = "昵称", example = "哈哈哈")
    private String nickName;
    @ApiModelProperty(name = "phoneNo", value = "电话号码", example = "13333333333")
    private String phoneNo;
    @ApiModelProperty(name = "createdTime", value = "创建时间", example = "")
    private Date createdTime;
    @ApiModelProperty(name = "totalFunds", value = "团队总资产", example = "1")
    private BigDecimal totalFunds;
    @ApiModelProperty(name = "floatingFunds", value = "团队流动资产", example = "1")
    private BigDecimal floatingFunds;
    @ApiModelProperty(name = "lockrepoFunds", value = "团队锁仓资产", example = "1")
    private BigDecimal lockrepoFunds;
    @ApiModelProperty(name = "profitsFunds", value = "团队收益资产", example = "1")
    private BigDecimal profitsFunds;
    @ApiModelProperty(name = "leftTotalMember", value = "左团队人员数", example = "1")
    private Integer leftTotalMember;
    @ApiModelProperty(name = "leftFunds", value = "左团队资产", example = "1")
    private BigDecimal leftFunds;
    @ApiModelProperty(name = "rightTotalMember", value = "右团队人员", example = "1")
    private Integer rightTotalMember;
    @ApiModelProperty(name = "rightFunds", value = "右团队资产", example = "1")
    private BigDecimal rightFunds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public BigDecimal getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(BigDecimal totalFunds) {
        this.totalFunds = totalFunds;
    }

    public BigDecimal getFloatingFunds() {
        return floatingFunds;
    }

    public void setFloatingFunds(BigDecimal floatingFunds) {
        this.floatingFunds = floatingFunds;
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

    public Integer getLeftTotalMember() {
        return leftTotalMember;
    }

    public void setLeftTotalMember(Integer leftTotalMember) {
        this.leftTotalMember = leftTotalMember;
    }

    public BigDecimal getLeftFunds() {
        return leftFunds;
    }

    public void setLeftFunds(BigDecimal leftFunds) {
        this.leftFunds = leftFunds;
    }

    public Integer getRightTotalMember() {
        return rightTotalMember;
    }

    public void setRightTotalMember(Integer rightTotalMember) {
        this.rightTotalMember = rightTotalMember;
    }

    public BigDecimal getRightFunds() {
        return rightFunds;
    }

    public void setRightFunds(BigDecimal rightFunds) {
        this.rightFunds = rightFunds;
    }

    public String getCreatTime(){
        if(createdTime==null){
            return null;
        }
        return  DateConvertUtil.convert(createdTime);
    }
}
