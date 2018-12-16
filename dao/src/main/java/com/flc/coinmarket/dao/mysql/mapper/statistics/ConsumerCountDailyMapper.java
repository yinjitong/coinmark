package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerCountDaily;
import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerCountDailyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerCountDailyMapper {
    long countByExample(ConsumerCountDailyExample example);

    int deleteByExample(ConsumerCountDailyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerCountDaily record);

    int insertSelective(ConsumerCountDaily record);

    List<ConsumerCountDaily> selectByExample(ConsumerCountDailyExample example);

    ConsumerCountDaily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerCountDaily record, @Param("example") ConsumerCountDailyExample example);

    int updateByExample(@Param("record") ConsumerCountDaily record, @Param("example") ConsumerCountDailyExample example);

    int updateByPrimaryKeySelective(ConsumerCountDaily record);

    int updateByPrimaryKey(ConsumerCountDaily record);
}