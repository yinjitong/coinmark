package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerProfitsTotal;
import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerProfitsTotalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerProfitsTotalMapper {
    long countByExample(ConsumerProfitsTotalExample example);

    int deleteByExample(ConsumerProfitsTotalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerProfitsTotal record);

    int insertSelective(ConsumerProfitsTotal record);

    List<ConsumerProfitsTotal> selectByExample(ConsumerProfitsTotalExample example);

    ConsumerProfitsTotal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerProfitsTotal record, @Param("example") ConsumerProfitsTotalExample example);

    int updateByExample(@Param("record") ConsumerProfitsTotal record, @Param("example") ConsumerProfitsTotalExample example);

    int updateByPrimaryKeySelective(ConsumerProfitsTotal record);

    int updateByPrimaryKey(ConsumerProfitsTotal record);

    ConsumerProfitsTotal selectConsumerProfitsTotal(@Param("id")Integer id);

    int batchInsert( List<ConsumerProfitsTotal> consumerProfitsTotals);

    List<ConsumerProfitsTotal> selectLatest(Integer consumerId);
}