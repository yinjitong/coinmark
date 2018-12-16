package com.flc.coinmarket.dao.mysql.mapper.consumer;

import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerLoginHistory;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerLoginHistoryExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerLoginHistoryMapper {
    long countByExample(ConsumerLoginHistoryExample example);

    int deleteByExample(ConsumerLoginHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerLoginHistory record);

    int insertSelective(ConsumerLoginHistory record);

    List<ConsumerLoginHistory> selectByExample(ConsumerLoginHistoryExample example);

    ConsumerLoginHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerLoginHistory record, @Param("example") ConsumerLoginHistoryExample example);

    int updateByExample(@Param("record") ConsumerLoginHistory record, @Param("example") ConsumerLoginHistoryExample example);

    int updateByPrimaryKeySelective(ConsumerLoginHistory record);

    int updateByPrimaryKey(ConsumerLoginHistory record);

    Date selectLastLoginTime(Integer id);
}