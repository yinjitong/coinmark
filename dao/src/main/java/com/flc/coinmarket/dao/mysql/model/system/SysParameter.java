package com.flc.coinmarket.dao.mysql.model.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "系统其他参数")
public class SysParameter implements Serializable {

    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;

    @ApiModelProperty(name = "paramCode", value = "参数编码", example = "1")
    private String paramCode;

    @ApiModelProperty(name = "paramName", value = "参数名称", example = "1")
    private String paramName;

    @ApiModelProperty(name = "paramValue", value = "参数值", example = "1")
    private BigDecimal paramValue;

    @ApiModelProperty(name = "updatedUser", value = "修改人ID", example = "1")
    private Integer updatedUser;

    @ApiModelProperty(name = "updatedTime", value = "更新时间", example = "1")
    private Date updatedTime;

    @ApiModelProperty(name = "createdUser", value = "创建人ID", example = "1")
    private Integer createdUser;

    @ApiModelProperty(name = "createdTime", value = "创建时间", example = "1")
    private Date createdTime;

    public SysParameter() {
    }

    public SysParameter(Integer id, String paramCode, String paramName, BigDecimal paramValue, Integer updatedUser, Date updatedTime, Integer createdUser, Date createdTime) {
        this.id = id;
        this.paramCode = paramCode;
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.updatedUser = updatedUser;
        this.updatedTime = updatedTime;
        this.createdUser = createdUser;
        this.createdTime = createdTime;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode == null ? null : paramCode.trim();
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public BigDecimal getParamValue() {
        return paramValue;
    }

    public void setParamValue(BigDecimal paramValue) {
        this.paramValue = paramValue;
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
}