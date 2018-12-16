package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.LockrepoDestroyTotal;
import com.flc.coinmarket.dao.mysql.model.statistics.LockrepoDestroyTotalExample;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LockrepoDestroyTotalMapper {
    long countByExample(LockrepoDestroyTotalExample example);

    int deleteByExample(LockrepoDestroyTotalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LockrepoDestroyTotal record);

    int insertSelective(LockrepoDestroyTotal record);

    List<LockrepoDestroyTotal> selectByExample(LockrepoDestroyTotalExample example);

    LockrepoDestroyTotal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LockrepoDestroyTotal record, @Param("example") LockrepoDestroyTotalExample example);

    int updateByExample(@Param("record") LockrepoDestroyTotal record, @Param("example") LockrepoDestroyTotalExample example);

    int updateByPrimaryKeySelective(LockrepoDestroyTotal record);

    int updateByPrimaryKey(LockrepoDestroyTotal record);

    int insertIncrease(@Param("daily") BigDecimal daily,@Param("createdTime") Date createdTime);
}