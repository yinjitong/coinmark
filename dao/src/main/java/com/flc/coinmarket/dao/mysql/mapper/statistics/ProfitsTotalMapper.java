package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.ProfitsTotal;
import com.flc.coinmarket.dao.mysql.model.statistics.ProfitsTotalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProfitsTotalMapper {
    long countByExample(ProfitsTotalExample example);

    int deleteByExample(ProfitsTotalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProfitsTotal record);

    int insertSelective(ProfitsTotal record);

    List<ProfitsTotal> selectByExample(ProfitsTotalExample example);

    ProfitsTotal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProfitsTotal record, @Param("example") ProfitsTotalExample example);

    int updateByExample(@Param("record") ProfitsTotal record, @Param("example") ProfitsTotalExample example);

    int updateByPrimaryKeySelective(ProfitsTotal record);

    int updateByPrimaryKey(ProfitsTotal record);

    List<ProfitsTotal> selectTodayProfitsTotal(@Param("todayYMD") String todayYMD);

    List<ProfitsTotal> selectLatest();
}