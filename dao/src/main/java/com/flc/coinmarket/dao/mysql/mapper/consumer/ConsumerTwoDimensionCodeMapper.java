package com.flc.coinmarket.dao.mysql.mapper.consumer;

import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerTwoDimensionCode;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerTwoDimensionCodeExample;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerTwoDimensionCodeWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerTwoDimensionCodeMapper {
    long countByExample(ConsumerTwoDimensionCodeExample example);

    int deleteByExample(ConsumerTwoDimensionCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerTwoDimensionCodeWithBLOBs record);

    int insertSelective(ConsumerTwoDimensionCodeWithBLOBs record);

    List<ConsumerTwoDimensionCodeWithBLOBs> selectByExampleWithBLOBs(ConsumerTwoDimensionCodeExample example);

    List<ConsumerTwoDimensionCode> selectByExample(ConsumerTwoDimensionCodeExample example);

    ConsumerTwoDimensionCodeWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerTwoDimensionCodeWithBLOBs record, @Param("example") ConsumerTwoDimensionCodeExample example);

    int updateByExampleWithBLOBs(@Param("record") ConsumerTwoDimensionCodeWithBLOBs record, @Param("example") ConsumerTwoDimensionCodeExample example);

    int updateByExample(@Param("record") ConsumerTwoDimensionCode record, @Param("example") ConsumerTwoDimensionCodeExample example);

    int updateByPrimaryKeySelective(ConsumerTwoDimensionCodeWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ConsumerTwoDimensionCodeWithBLOBs record);

    int updateByPrimaryKey(ConsumerTwoDimensionCode record);

}