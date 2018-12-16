package com.flc.coinmarket.dao.mysql.mapper.consumer;

import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerCapitalAccount;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerCapitalAccountExample;
import java.util.List;

import com.flc.coinmarket.dao.mysql.model.statistics.CapitalTotal;
import org.apache.ibatis.annotations.Param;

public interface ConsumerCapitalAccountMapper {
    long countByExample(ConsumerCapitalAccountExample example);

    int deleteByExample(ConsumerCapitalAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerCapitalAccount record);

    int insertSelective(ConsumerCapitalAccount record);

    List<ConsumerCapitalAccount> selectByExample(ConsumerCapitalAccountExample example);

    ConsumerCapitalAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerCapitalAccount record, @Param("example") ConsumerCapitalAccountExample example);

    int updateByExample(@Param("record") ConsumerCapitalAccount record, @Param("example") ConsumerCapitalAccountExample example);

    int updateByPrimaryKeySelective(ConsumerCapitalAccount record);

    int updateByPrimaryKey(ConsumerCapitalAccount record);

    List<ConsumerCapitalAccount> selectRefereeTeam(Integer refereeId);

    List<Integer> selectRefereeTeamAccountId(Integer refereeId);

    List<ConsumerCapitalAccount> selectLeftTeam(Integer consumerId);

    List<ConsumerCapitalAccount> selectRightTeam(Integer consumerId);

    CapitalTotal selectTotalFunds();
}