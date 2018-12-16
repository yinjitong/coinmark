package com.flc.coinmarket.dao.mysql.mapper.system;

import com.flc.coinmarket.dao.mysql.model.system.ProfitsRatio;
import com.flc.coinmarket.dao.mysql.model.system.ProfitsTeamRatio;
import com.flc.coinmarket.dao.mysql.model.system.ProfitsTeamRatioExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProfitsTeamRatioMapper {
    long countByExample(ProfitsTeamRatioExample example);

    int deleteByExample(ProfitsTeamRatioExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProfitsTeamRatio record);

    int insertSelective(ProfitsTeamRatio record);

    List<ProfitsRatio> selectByExample(ProfitsTeamRatioExample example);

    ProfitsRatio selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProfitsTeamRatio record, @Param("example") ProfitsTeamRatioExample example);

    int updateByExample(@Param("record") ProfitsTeamRatio record, @Param("example") ProfitsTeamRatioExample example);

    int updateByPrimaryKeySelective(ProfitsTeamRatio record);

    int updateByPrimaryKey(ProfitsTeamRatio record);
}