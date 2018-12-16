package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerCapitalDaily;
import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerCapitalDailyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerCapitalDailyMapper {
    long countByExample(ConsumerCapitalDailyExample example);

    int deleteByExample(ConsumerCapitalDailyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerCapitalDaily record);

    int insertSelective(ConsumerCapitalDaily record);

    List<ConsumerCapitalDaily> selectByExample(ConsumerCapitalDailyExample example);

    ConsumerCapitalDaily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerCapitalDaily record, @Param("example") ConsumerCapitalDailyExample example);

    int updateByExample(@Param("record") ConsumerCapitalDaily record, @Param("example") ConsumerCapitalDailyExample example);

    int updateByPrimaryKeySelective(ConsumerCapitalDaily record);

    int updateByPrimaryKey(ConsumerCapitalDaily record);

    int batchInsert(List<ConsumerCapitalDaily> consumerCapitalDailies);
}