package com.flc.coinmarket.dao.mysql.model.statistics;

import com.flc.coinmarket.core.util.DateConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("手续费日增量")
public class FeeDaily implements Serializable {

    @ApiModelProperty(name = "id", value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(name = "total", value = "手续费日增量", example = "1")
    private BigDecimal total;

    @ApiModelProperty(name = "createdTime", value = "创建时间", example = "1")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatTime(){
        return  DateConvertUtil.convert(createdTime);
    }

    public FeeDaily(){}

    public FeeDaily(BigDecimal total, Date createdTime){
        this.total = total;
        this.createdTime = createdTime;
    }
}