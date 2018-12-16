package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerProfitsDaily;
import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerProfitsDailyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerProfitsDailyMapper {
    long countByExample(ConsumerProfitsDailyExample example);

    int deleteByExample(ConsumerProfitsDailyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerProfitsDaily record);

    int insertSelective(ConsumerProfitsDaily record);

    List<ConsumerProfitsDaily> selectByExample(ConsumerProfitsDailyExample example);

    ConsumerProfitsDaily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerProfitsDaily record, @Param("example") ConsumerProfitsDailyExample example);

    int updateByExample(@Param("record") ConsumerProfitsDaily record, @Param("example") ConsumerProfitsDailyExample example);

    int updateByPrimaryKeySelective(ConsumerProfitsDaily record);

    int updateByPrimaryKey(ConsumerProfitsDaily record);

    int batchInsert(List<ConsumerProfitsDaily> consumerProfitsDailies);

    ConsumerProfitsDaily selectYestodayConsumerProfitsDaily(@Param("id")Integer id,@Param("todayYMD")String todayYMD);
}