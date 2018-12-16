package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.CapitalDaily;
import com.flc.coinmarket.dao.mysql.model.statistics.CapitalDailyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CapitalDailyMapper {
    long countByExample(CapitalDailyExample example);

    int deleteByExample(CapitalDailyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CapitalDaily record);

    int insertSelective(CapitalDaily record);

    List<CapitalDaily> selectByExample(CapitalDailyExample example);

    CapitalDaily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CapitalDaily record, @Param("example") CapitalDailyExample example);

    int updateByExample(@Param("record") CapitalDaily record, @Param("example") CapitalDailyExample example);

    int updateByPrimaryKeySelective(CapitalDaily record);

    int updateByPrimaryKey(CapitalDaily record);

    List<CapitalDaily> selectTodayCapitalDaily(@Param("todayYMD") String todayYMD);
}