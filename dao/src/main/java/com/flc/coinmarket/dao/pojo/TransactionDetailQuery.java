package com.flc.coinmarket.dao.pojo;

import com.flc.coinmarket.core.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户交易明细查询条件")
public class TransactionDetailQuery extends BaseRequest {
    @ApiModelProperty(name = "consumerId", value = "用户ID", example = "1")
    private Integer consumerId;
    @ApiModelProperty(name = "transType", value = "交易分类", example = "1")
    private String  transType;
    @ApiModelProperty(name = "sourceType", value = "交易类型", example = "1")
    private String sourceType;

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
