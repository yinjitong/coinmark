package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerCapitalTotal;
import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerCapitalTotalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerCapitalTotalMapper {
    long countByExample(ConsumerCapitalTotalExample example);

    int deleteByExample(ConsumerCapitalTotalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerCapitalTotal record);

    int insertSelective(ConsumerCapitalTotal record);

    List<ConsumerCapitalTotal> selectByExample(ConsumerCapitalTotalExample example);

    ConsumerCapitalTotal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerCapitalTotal record, @Param("example") ConsumerCapitalTotalExample example);

    int updateByExample(@Param("record") ConsumerCapitalTotal record, @Param("example") ConsumerCapitalTotalExample example);

    int updateByPrimaryKeySelective(ConsumerCapitalTotal record);

    int updateByPrimaryKey(ConsumerCapitalTotal record);

    ConsumerCapitalTotal selectTodayConsumerCapitalTotal( Integer consumerId);

    int batchInsert(List<ConsumerCapitalTotal> consumerCapitalTotals);

    List<ConsumerCapitalTotal> selectLatest(Integer consumerId);
}