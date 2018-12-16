package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.CapitalTotal;
import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerCapitalTotal;
import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerCountTotal;
import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerCountTotalExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerCountTotalMapper {
    long countByExample(ConsumerCountTotalExample example);

    int deleteByExample(ConsumerCountTotalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerCountTotal record);

    int insertIncrease(@Param("dailyCount") Integer count,@Param("createdTime") Date createdTime);

    int insertSelective(ConsumerCountTotal record);

    List<ConsumerCountTotal> selectByExample(ConsumerCountTotalExample example);

    ConsumerCountTotal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerCountTotal record, @Param("example") ConsumerCountTotalExample example);

    int updateByExample(@Param("record") ConsumerCountTotal record, @Param("example") ConsumerCountTotalExample example);

    int updateByPrimaryKeySelective(ConsumerCountTotal record);

    int updateByPrimaryKey(ConsumerCountTotal record);

}