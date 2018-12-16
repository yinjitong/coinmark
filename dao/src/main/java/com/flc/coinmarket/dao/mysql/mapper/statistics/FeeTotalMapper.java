package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.FeeTotal;
import com.flc.coinmarket.dao.mysql.model.statistics.FeeTotalExample;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeeTotalMapper {
    long countByExample(FeeTotalExample example);

    int deleteByExample(FeeTotalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FeeTotal record);

    int insertSelective(FeeTotal record);

    List<FeeTotal> selectByExample(FeeTotalExample example);

    FeeTotal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FeeTotal record, @Param("example") FeeTotalExample example);

    int updateByExample(@Param("record") FeeTotal record, @Param("example") FeeTotalExample example);

    int updateByPrimaryKeySelective(FeeTotal record);

    int updateByPrimaryKey(FeeTotal record);

    int insertIncrease(@Param("daily") BigDecimal daily, @Param("createdTime") Date createdTime);
}