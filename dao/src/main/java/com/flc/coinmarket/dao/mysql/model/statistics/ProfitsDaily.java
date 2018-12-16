package com.flc.coinmarket.dao.mysql.model.statistics;

import com.flc.coinmarket.core.util.DateConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("系统支出收益日增量")
public class ProfitsDaily implements Serializable {

    @ApiModelProperty(name = "id", value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(name = "profitsTeam", value = "团队收益支出", example = "1")
    private BigDecimal profitsTeam;

    @ApiModelProperty(name = "profitsReferee", value = "推荐收益支出", example = "1")
    private BigDecimal profitsReferee;

    @ApiModelProperty(name = "profitsLockrepo", value = "锁仓收益支出", example = "1")
    private BigDecimal profitsLockrepo;

    @ApiModelProperty(name = "createdTime", value = "创建时间", example = "1")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getProfitsTeam() {
        return profitsTeam == null ? new BigDecimal(0):profitsTeam;
    }

    public void setProfitsTeam(BigDecimal profitsTeam) {
        this.profitsTeam = profitsTeam;
    }

    public BigDecimal getProfitsReferee() {
        return profitsReferee == null ? new BigDecimal(0):profitsReferee;
    }

    public void setProfitsReferee(BigDecimal profitsReferee) {
        this.profitsReferee = profitsReferee;
    }

    public BigDecimal getProfitsLockrepo() {
        return profitsLockrepo == null ? new BigDecimal(0):profitsLockrepo;
    }

    public void setProfitsLockrepo(BigDecimal profitsLockrepo) {
        this.profitsLockrepo = profitsLockrepo;
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

}