package com.flc.coinmarket.dao.mysql.mapper.consumer;

import com.flc.coinmarket.dao.mysql.model.consumer.Consumer;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerExample;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerWithBLOBs;

import java.math.BigDecimal;
import java.util.List;

import com.flc.coinmarket.dao.pojo.ConsumerQuery;
import com.flc.coinmarket.dao.vo.ConsumerInfoVO;
import org.apache.ibatis.annotations.Param;

public interface ConsumerMapper {
    long countByExample(ConsumerExample example);

    int deleteByExample(ConsumerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerWithBLOBs record);

    int insertSelective(ConsumerWithBLOBs record);

    List<ConsumerWithBLOBs> selectByExampleWithBLOBs(ConsumerExample example);

    List<Consumer> selectByExample(ConsumerExample example);

    ConsumerWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerWithBLOBs record, @Param("example") ConsumerExample example);

    int updateByExampleWithBLOBs(@Param("record") ConsumerWithBLOBs record, @Param("example") ConsumerExample example);

    int updateByExample(@Param("record") Consumer record, @Param("example") ConsumerExample example);

    int updateByPrimaryKeySelective(ConsumerWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ConsumerWithBLOBs record);

    int updateByPrimaryKey(Consumer record);

    ConsumerWithBLOBs queryLeafCustomerByLeftCode(@Param("length")Integer length,@Param("pathD")String pathD,
                                                  @Param("refereeFullPath") String refereeFullPath);

    List<ConsumerInfoVO> selectConsumerInfo(ConsumerQuery consumerQuery);

    List<ConsumerInfoVO> selectConsumerMember(String fullPath);

    String selectMaxTeamPostCode();

    //联想查询手机号
    List<String> phoneNoAssociate(String phoneNo);

    //根据手机号查询余额
    BigDecimal queryBalanceByPhoneNo(String phoneNo);

    List<String> selectConsumerMemberPhoneNo(String fullPath);
}