package com.flc.coinmarket.dao.mysql.mapper.consumer;

import com.flc.coinmarket.dao.mysql.model.consumer.Consumer;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerTeam;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerTeamExample;

import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import com.flc.coinmarket.dao.vo.ConsumerTeamVO;
import org.apache.ibatis.annotations.Param;

public interface ConsumerTeamMapper {
    long countByExample(ConsumerTeamExample example);

    int deleteByExample(ConsumerTeamExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerTeam record);

    int insertSelective(ConsumerTeam record);

    List<ConsumerTeam> selectByExample(ConsumerTeamExample example);

    ConsumerTeam selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerTeam record, @Param("example") ConsumerTeamExample example);

    int updateByExample(@Param("record") ConsumerTeam record, @Param("example") ConsumerTeamExample example);

    int updateByPrimaryKeySelective(ConsumerTeam record);

    int updateByPrimaryKey(ConsumerTeam record);

    List<ConsumerTeamVO> selectTeamInfo();

    ConsumerTeam selectLeftTeam(Integer consumerId);

    ConsumerTeam selectRightTeam(Integer consumerId);

//    int batchUpdate(List<ConsumerTeam> consumerTeams);

    int batchInsert(List<ConsumerTeam> consumerTeams);

    List<ConsumerTeamVO> selectConsumerTeamTrend(@Param("consumerId")Integer id,
                                                 @Param("startDate")Date startDate, @Param("endDate") Date endDate);

    ConsumerTeam selectConsumerTeam(@Param("consumerId")Integer consumerId);
}