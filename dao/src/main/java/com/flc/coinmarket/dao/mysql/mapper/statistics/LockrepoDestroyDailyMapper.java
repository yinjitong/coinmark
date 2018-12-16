package com.flc.coinmarket.dao.mysql.mapper.statistics;

import com.flc.coinmarket.dao.mysql.model.statistics.LockrepoDestroyDaily;
import com.flc.coinmarket.dao.mysql.model.statistics.LockrepoDestroyDailyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LockrepoDestroyDailyMapper {
    long countByExample(LockrepoDestroyDailyExample example);

    int deleteByExample(LockrepoDestroyDailyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LockrepoDestroyDaily record);

    int insertSelective(LockrepoDestroyDaily record);

    List<LockrepoDestroyDaily> selectByExample(LockrepoDestroyDailyExample example);

    LockrepoDestroyDaily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LockrepoDestroyDaily record, @Param("example") LockrepoDestroyDailyExample example);

    int updateByExample(@Param("record") LockrepoDestroyDaily record, @Param("example") LockrepoDestroyDailyExample example);

    int updateByPrimaryKeySelective(LockrepoDestroyDaily record);

    int updateByPrimaryKey(LockrepoDestroyDaily record);
}