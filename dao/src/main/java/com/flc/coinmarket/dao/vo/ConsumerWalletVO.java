package com.flc.coinmarket.dao.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("用户钱包信息")
public class ConsumerWalletVO {
    @ApiModelProperty(name = "totalFunds", value = "用户总资产", example = "1")
    private BigDecimal totalFunds;
    @ApiModelProperty(name = "lockrepoFunds", value = "用户锁仓资产", example = "1")
    private BigDecimal lockrepoFunds;
    @ApiModelProperty(name = "profitsFunds", value = "用户收益资产", example = "1")
    private BigDecimal profitsFunds;
    @ApiModelProperty(name = "floatingFunds", value = "用户流动资产", example = "1")
    private BigDecimal floatingFunds;
    @ApiModelProperty(name = "profitsLockrepo", value = "用户锁仓收益", example = "1")
    private BigDecimal profitsLockrepo;
    @ApiModelProperty(name = "profitsReferee", value = "用户推荐收益", example = "1")
    private BigDecimal profitsReferee;
    @ApiModelProperty(name = "profitsTeam", value = "用户团队收益", example = "1")
    private BigDecimal profitsTeam;
    @ApiModelProperty(name = "profitsLockrepoDaily", value = "昨日用户锁仓收益", example = "1")
    private BigDecimal profitsLockrepoDaily;
    @ApiModelProperty(name = "profitsRefereeDaily", value = "昨日用户推荐收益", example = "1")
    private BigDecimal profitsRefereeDaily;
    @ApiModelProperty(name = "profitsTeamDaily", value = "昨日用户团队收益", example = "1")
    private BigDecimal profitsTeamDaily;
    @ApiModelProperty(name = "leftTotalFunds", value = "团队一总资产", example = "1")
    private BigDecimal leftTotalFunds;
    @ApiModelProperty(name = "rightTotalFunds", value = "团队二总资产", example = "1")
    private BigDecimal rightTotalFunds;
    @ApiModelProperty(name = "leftDimesionCode", value = "团队一推荐码", example = "1")
    private String leftDimesionCode;
    @ApiModelProperty(name = "rightDimesionCode", value = "团队二推荐码", example = "1")
    private String rightDimesionCode;
    @ApiModelProperty(name = "lockReleseFlag", value = "是否锁仓释放", example = "0-不释放1-释放")
    private String lockReleseFlag;
    @ApiModelProperty(name = "tranceFee", value = "交易手续费", example = "0。05")
    private BigDecimal tranceFee;
    @ApiModelProperty(name = "releaseLockrepoRatio", value = "自动复投锁仓资产资金", example = "0。05")
    private BigDecimal releaseLockrepoRatio;


    public ConsumerWalletVO() {
    }

    public ConsumerWalletVO(BigDecimal totalFunds, BigDecimal lockrepoFunds, BigDecimal profitsFunds, BigDecimal floatingFunds, BigDecimal profitsLockrepo, BigDecimal profitsReferee, BigDecimal profitsTeam, BigDecimal profitsLockrepoDaily, BigDecimal profitsRefereeDaily, BigDecimal profitsTeamDaily, BigDecimal leftTotalFunds, BigDecimal rightTotalFunds, String leftDimesionCode, String rightDimesionCode,String lockReleseFlag,BigDecimal tranceFee,BigDecimal releaseLockrepoRatio) {
        this.totalFunds = totalFunds;
        this.lockrepoFunds = lockrepoFunds;
        this.profitsFunds = profitsFunds;
        this.floatingFunds = floatingFunds;
        this.profitsLockrepo = profitsLockrepo;
        this.profitsReferee = profitsReferee;
        this.profitsTeam = profitsTeam;
        this.profitsLockrepoDaily = profitsLockrepoDaily;
        this.profitsRefereeDaily = profitsRefereeDaily;
        this.profitsTeamDaily = profitsTeamDaily;
        this.leftTotalFunds = leftTotalFunds;
        this.rightTotalFunds = rightTotalFunds;
        this.leftDimesionCode = leftDimesionCode;
        this.rightDimesionCode = rightDimesionCode;
        this.lockReleseFlag=lockReleseFlag;
        this.tranceFee=tranceFee;
        this.releaseLockrepoRatio=releaseLockrepoRatio;
    }

    public BigDecimal getTranceFee() {
        return tranceFee;
    }

    public void setTranceFee(BigDecimal tranceFee) {
        this.tranceFee = tranceFee;
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

    public BigDecimal getProfitsLockrepo() {
        return profitsLockrepo;
    }

    public void setProfitsLockrepo(BigDecimal profitsLockrepo) {
        this.profitsLockrepo = profitsLockrepo;
    }

    public BigDecimal getProfitsReferee() {
        return profitsReferee;
    }

    public void setProfitsReferee(BigDecimal profitsReferee) {
        this.profitsReferee = profitsReferee;
    }

    public BigDecimal getProfitsTeam() {
        return profitsTeam;
    }

    public void setProfitsTeam(BigDecimal profitsTeam) {
        this.profitsTeam = profitsTeam;
    }

    public BigDecimal getProfitsLockrepoDaily() {
        return profitsLockrepoDaily;
    }

    public void setProfitsLockrepoDaily(BigDecimal profitsLockrepoDaily) {
        this.profitsLockrepoDaily = profitsLockrepoDaily;
    }

    public BigDecimal getProfitsRefereeDaily() {
        return profitsRefereeDaily;
    }

    public void setProfitsRefereeDaily(BigDecimal profitsRefereeDaily) {
        this.profitsRefereeDaily = profitsRefereeDaily;
    }

    public BigDecimal getProfitsTeamDaily() {
        return profitsTeamDaily;
    }

    public void setProfitsTeamDaily(BigDecimal profitsTeamDaily) {
        this.profitsTeamDaily = profitsTeamDaily;
    }

    public BigDecimal getLeftTotalFunds() {
        return leftTotalFunds;
    }

    public void setLeftTotalFunds(BigDecimal leftTotalFunds) {
        this.leftTotalFunds = leftTotalFunds;
    }

    public BigDecimal getRightTotalFunds() {
        return rightTotalFunds;
    }

    public void setRightTotalFunds(BigDecimal rightTotalFunds) {
        this.rightTotalFunds = rightTotalFunds;
    }

    public String getLeftDimesionCode() {
        return leftDimesionCode;
    }

    public void setLeftDimesionCode(String leftDimesionCode) {
        this.leftDimesionCode = leftDimesionCode;
    }

    public String getRightDimesionCode() {
        return rightDimesionCode;
    }

    public void setRightDimesionCode(String rightDimesionCode) {
        this.rightDimesionCode = rightDimesionCode;
    }

    public String getLockReleseFlag() {
        return lockReleseFlag;
    }

    public void setLockReleseFlag(String lockReleseFlag) {
        this.lockReleseFlag = lockReleseFlag;
    }

    public BigDecimal getReleaseLockrepoRatio() {
        return releaseLockrepoRatio;
    }

    public void setReleaseLockrepoRatio(BigDecimal releaseLockrepoRatio) {
        this.releaseLockrepoRatio = releaseLockrepoRatio;
    }
}
