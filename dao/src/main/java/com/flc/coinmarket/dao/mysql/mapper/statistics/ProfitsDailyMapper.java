package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.ProfitsDaily;
import com.flc.coinmarket.dao.mysql.model.statistics.ProfitsDailyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProfitsDailyMapper {
    long countByExample(ProfitsDailyExample example);

    int deleteByExample(ProfitsDailyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProfitsDaily record);

    int insertSelective(ProfitsDaily record);

    List<ProfitsDaily> selectByExample(ProfitsDailyExample example);

    ProfitsDaily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProfitsDaily record, @Param("example") ProfitsDailyExample example);

    int updateByExample(@Param("record") ProfitsDaily record, @Param("example") ProfitsDailyExample example);

    int updateByPrimaryKeySelective(ProfitsDaily record);

    int updateByPrimaryKey(ProfitsDaily record);

    List<ProfitsDaily> selectTodayProfitsDaily( @Param("todayYMD") String todayYMD);
}