package com.flc.coinmarket.dao.mysql.mapper.system;

import com.flc.coinmarket.dao.mysql.model.system.ProfitsLockrepoRatio;
import com.flc.coinmarket.dao.mysql.model.system.ProfitsLockrepoRatioExample;
import java.util.List;

import com.flc.coinmarket.dao.mysql.model.system.ProfitsRatio;
import org.apache.ibatis.annotations.Param;

public interface ProfitsLockrepoRatioMapper {
    long countByExample(ProfitsLockrepoRatioExample example);

    int deleteByExample(ProfitsLockrepoRatioExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProfitsLockrepoRatio record);

    int insertSelective(ProfitsLockrepoRatio record);

    List<ProfitsRatio> selectByExample(ProfitsLockrepoRatioExample example);

    ProfitsRatio selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProfitsLockrepoRatio record, @Param("example") ProfitsLockrepoRatioExample example);

    int updateByExample(@Param("record") ProfitsLockrepoRatio record, @Param("example") ProfitsLockrepoRatioExample example);

    int updateByPrimaryKeySelective(ProfitsLockrepoRatio record);

    int updateByPrimaryKey(ProfitsLockrepoRatio record);
}