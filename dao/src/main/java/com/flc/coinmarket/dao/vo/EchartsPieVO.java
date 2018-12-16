package com.flc.coinmarket.dao.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("饼形图展示模型")
public class EchartsPieVO {
    @ApiModelProperty(name = "name", value = "名称", example = "")
    private String name;
    @ApiModelProperty(name = "value", value = "值", example = "")
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
