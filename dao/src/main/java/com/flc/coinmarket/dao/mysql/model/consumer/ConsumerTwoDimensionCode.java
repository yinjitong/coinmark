package com.flc.coinmarket.dao.mysql.model.consumer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("客户二维码信息")
public class ConsumerTwoDimensionCode implements Serializable {

    @ApiModelProperty(name = "id", value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(name = "consumerId", value = "用户ID", example = "1")
    private Integer consumerId;

    @ApiModelProperty(name = "leftDimesionCode", value = "左二维码编码", example = "1")
    private String leftDimesionCode;

    @ApiModelProperty(name = "rightDimensionCode", value = "右二维码编码", example = "1")
    private String rightDimensionCode;

    @ApiModelProperty(name = "transferDimensionCode", value = "交易地址二维码编码", example = "1")
    private String transferDimensionCode;

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

    public String getLeftDimesionCode() {
        return leftDimesionCode;
    }

    public void setLeftDimesionCode(String leftDimesionCode) {
        this.leftDimesionCode = leftDimesionCode;
    }

    public String getRightDimensionCode() {
        return rightDimensionCode;
    }

    public void setRightDimensionCode(String rightDimensionCode) {
        this.rightDimensionCode = rightDimensionCode;
    }

    public String getTransferDimensionCode() {
        return transferDimensionCode;
    }

    public void setTransferDimensionCode(String transferDimensionCode) {
        this.transferDimensionCode = transferDimensionCode;
    }
}