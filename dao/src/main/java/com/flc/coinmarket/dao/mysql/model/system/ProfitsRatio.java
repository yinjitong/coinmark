package com.flc.coinmarket.dao.mysql.model.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("收益率基准参数")
public class ProfitsRatio implements Serializable {

    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;

    @ApiModelProperty(name = "profitsCode", value = "收益率编码", example = "1")
    private String profitsCode;

    @ApiModelProperty(name = "updatedUser", value = "修改人ID", example = "1")
    private Integer updatedUser;

    @ApiModelProperty(name = "updatedTime", value = "更新时间", example = "1")
    private Date updatedTime;

    @ApiModelProperty(name = "createdUser", value = "创建人ID", example = "1")
    private Integer createdUser;

    @ApiModelProperty(name = "createdTime", value = "创建时间", example = "1")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "lowerLimit", value = "基准下限", example = "1")
    private BigDecimal lowerLimit;

    @ApiModelProperty(name = "upperLimit", value = "基准上限", example = "1")
    private BigDecimal upperLimit;

    @ApiModelProperty(name = "ratio", value = "收益率", example = "0.1")
    private BigDecimal ratio;

    @ApiModelProperty(name = "cardinalNumber", value = "计算基数", example = "0.1")
    private BigDecimal cardinalNumber;

  
    public BigDecimal getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(BigDecimal lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public BigDecimal getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(BigDecimal upperLimit) {
        this.upperLimit = upperLimit;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfitsCode() {
        return profitsCode;
    }

    public void setProfitsCode(String profitsCode) {
        this.profitsCode = profitsCode;
    }

    public Integer getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(Integer updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigDecimal getCardinalNumber() {
        return cardinalNumber;
    }

    public void setCardinalNumber(BigDecimal cardinalNumber) {
        this.cardinalNumber = cardinalNumber;
    }
}