package com.flc.coinmarket.dao.mysql.mapper.consumer;

import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerSettings;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerSettingsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerSettingsMapper {
    long countByExample(ConsumerSettingsExample example);

    int deleteByExample(ConsumerSettingsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerSettings record);

    int insertSelective(ConsumerSettings record);

    List<ConsumerSettings> selectByExample(ConsumerSettingsExample example);

    ConsumerSettings selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerSettings record, @Param("example") ConsumerSettingsExample example);

    int updateByExample(@Param("record") ConsumerSettings record, @Param("example") ConsumerSettingsExample example);

    int updateByPrimaryKeySelective(ConsumerSettings record);

    int updateByPrimaryKey(ConsumerSettings record);
}