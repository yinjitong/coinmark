package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.FeeDaily;
import com.flc.coinmarket.dao.mysql.model.statistics.FeeDailyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeeDailyMapper {
    long countByExample(FeeDailyExample example);

    int deleteByExample(FeeDailyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FeeDaily record);

    int insertSelective(FeeDaily record);

    List<FeeDaily> selectByExample(FeeDailyExample example);

    FeeDaily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FeeDaily record, @Param("example") FeeDailyExample example);

    int updateByExample(@Param("record") FeeDaily record, @Param("example") FeeDailyExample example);

    int updateByPrimaryKeySelective(FeeDaily record);

    int updateByPrimaryKey(FeeDaily record);
}