package com.flc.coinmarket.dao.mysql.model.consumer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("用户二维码字节码")
public class ConsumerTwoDimensionCodeWithBLOBs extends ConsumerTwoDimensionCode implements Serializable {

    @ApiModelProperty(name = "leftDimesionCode", value = "左二维码字节码", example = "1")
    private byte[] leftDimension;

    @ApiModelProperty(name = "rightDimension", value = "右二维码字节码", example = "1")
    private byte[] rightDimension;

    @ApiModelProperty(name = "trandferDimesion", value = "交易地址二维码字节码", example = "1")
    private byte[] trandferDimesion;

    private static final long serialVersionUID = 1L;

    public byte[] getLeftDimension() {
        return leftDimension;
    }

    public void setLeftDimension(byte[] leftDimension) {
        this.leftDimension = leftDimension;
    }

    public byte[] getRightDimension() {
        return rightDimension;
    }

    public void setRightDimension(byte[] rightDimension) {
        this.rightDimension = rightDimension;
    }

    public byte[] getTrandferDimesion() {
        return trandferDimesion;
    }

    public void setTrandferDimesion(byte[] trandferDimesion) {
        this.trandferDimesion = trandferDimesion;
    }
}