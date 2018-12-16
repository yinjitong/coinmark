package com.flc.coinmarket.dao.mysql.model.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("数据字典")
public class SysDictionary implements Serializable {

    @ApiModelProperty(name = "id", value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(name = "groupName", value = "字典组名称", example = "1")
    private String groupName;

    @ApiModelProperty(name = "groupCode", value = "字典组编码", example = "1")
    private String groupCode;

    @ApiModelProperty(name = "dicName", value = "字典名称", example = "1")
    private String dicName;

    @ApiModelProperty(name = "dicCode", value = "字典编码", example = "1")
    private String dicCode;

    @ApiModelProperty(name = "dicValue", value = "字典值", example = "1")
    private String dicValue;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode == null ? null : groupCode.trim();
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode == null ? null : dicCode.trim();
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue == null ? null : dicValue.trim();
    }
}