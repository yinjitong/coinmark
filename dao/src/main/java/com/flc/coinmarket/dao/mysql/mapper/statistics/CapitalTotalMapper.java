package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.CapitalTotal;
import com.flc.coinmarket.dao.mysql.model.statistics.CapitalTotalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CapitalTotalMapper {
    long countByExample(CapitalTotalExample example);

    int deleteByExample(CapitalTotalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CapitalTotal record);

    int insertSelective(CapitalTotal record);

    List<CapitalTotal> selectByExample(CapitalTotalExample example);

    CapitalTotal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CapitalTotal record, @Param("example") CapitalTotalExample example);

    int updateByExample(@Param("record") CapitalTotal record, @Param("example") CapitalTotalExample example);

    int updateByPrimaryKeySelective(CapitalTotal record);

    int updateByPrimaryKey(CapitalTotal record);

    List<CapitalTotal> selectTodayCapitalTotal();

    List<CapitalTotal> selectLatest();
}