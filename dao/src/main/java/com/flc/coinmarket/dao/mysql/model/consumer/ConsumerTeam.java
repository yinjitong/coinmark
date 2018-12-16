package com.flc.coinmarket.dao.mysql.model.consumer;

import com.flc.coinmarket.core.util.DateConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("用户团队信息")
public class ConsumerTeam implements Serializable {

    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;

    @ApiModelProperty(name = "consumerId", value = "用户ID", example = "1")
    private Integer consumerId;

    @ApiModelProperty(name = "teamPosCode", value = "所在团队位置编码", example = "1")
    private String teamPosCode;

    @ApiModelProperty(name = "leftTotalMember", value = "左团队总人数", example = "1")
    private Integer leftTotalMember;
    @ApiModelProperty(name = "rightTotalMember", value = "右团队总人数", example = "1")
    private Integer rightTotalMember;

    @ApiModelProperty(name = "leftProfitsTotal", value = "左团队收益资产总量", example = "1")
    private BigDecimal leftProfitsTotal;
    @ApiModelProperty(name = "leftLockrepoTotal", value = "左团队锁仓资产总量", example = "1")
    private BigDecimal leftLockrepoTotal;
    @ApiModelProperty(name = "leftFloatingTotal", value = "左团队流动资产总量", example = "1")
    private BigDecimal leftFloatingTotal;

    @ApiModelProperty(name = "rightProfitsTotal", value = "右团队收益资产总量", example = "1")
    private BigDecimal rightProfitsTotal;
    @ApiModelProperty(name = "rightLockrepoTotal", value = "右团队锁仓资产总量", example = "1")
    private BigDecimal rightLockrepoTotal;
    @ApiModelProperty(name = "rightFloatingTotal", value = "右团队流动资产总量", example = "1")
    private BigDecimal rightFloatingTotal;

    @ApiModelProperty(name = "createdTime", value = "创建时间", example = "1")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public String getTeamPosCode() {
        return teamPosCode;
    }

    public void setTeamPosCode(String teamPosCode) {
        this.teamPosCode = teamPosCode;
    }

    public Integer getLeftTotalMember() {
        return leftTotalMember == null ? 0 : leftTotalMember;
    }

    public void setLeftTotalMember(Integer leftTotalMember) {
        this.leftTotalMember = leftTotalMember;
    }

    public Integer getRightTotalMember() {
        return rightTotalMember == null ? 0 : rightTotalMember;
    }

    public void setRightTotalMember(Integer rightTotalMember) {
        this.rightTotalMember = rightTotalMember;
    }

    public BigDecimal getLeftProfitsTotal() {
        return leftProfitsTotal == null ? new BigDecimal(0) : leftProfitsTotal;
    }

    public void setLeftProfitsTotal(BigDecimal leftProfitsTotal) {
        this.leftProfitsTotal = leftProfitsTotal;
    }

    public BigDecimal getLeftLockrepoTotal() {
        return leftLockrepoTotal == null ? new BigDecimal(0) : leftLockrepoTotal;
    }

    public void setLeftLockrepoTotal(BigDecimal leftLockrepoTotal) {
        this.leftLockrepoTotal = leftLockrepoTotal;
    }

    public BigDecimal getLeftFloatingTotal() {
        return leftFloatingTotal == null ? new BigDecimal(0) : leftFloatingTotal;
    }

    public void setLeftFloatingTotal(BigDecimal leftFloatingTotal) {
        this.leftFloatingTotal = leftFloatingTotal;
    }

    public BigDecimal getRightProfitsTotal() {
        return rightProfitsTotal == null ? new BigDecimal(0) : rightProfitsTotal;
    }

    public void setRightProfitsTotal(BigDecimal rightProfitsTotal) {
        this.rightProfitsTotal = rightProfitsTotal;
    }

    public BigDecimal getRightLockrepoTotal() {
        return rightLockrepoTotal == null ? new BigDecimal(0) : rightLockrepoTotal;
    }

    public void setRightLockrepoTotal(BigDecimal rightLockrepoTotal) {
        this.rightLockrepoTotal = rightLockrepoTotal;
    }

    public BigDecimal getRightFloatingTotal() {
        return rightFloatingTotal == null ? new BigDecimal(0) : rightFloatingTotal;
    }

    public void setRightFloatingTotal(BigDecimal rightFloatingTotal) {
        this.rightFloatingTotal = rightFloatingTotal;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public ConsumerTeam(){}

    public ConsumerTeam(Integer consumerId, String teamPosCode){
        this.consumerId = consumerId;
        this.teamPosCode = teamPosCode;
        this.createdTime = new Date();
    }

    public String getCreatTime(){
        if(createdTime==null){
            return null;
        }
        return  DateConvertUtil.convert(createdTime);
    }
}