package com.flc.coinmarket.dao.mysql.mapper.consumer;

import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerCheckCode;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerCheckCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerCheckCodeMapper {
    long countByExample(ConsumerCheckCodeExample example);

    int deleteByExample(ConsumerCheckCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerCheckCode record);

    int insertSelective(ConsumerCheckCode record);

    List<ConsumerCheckCode> selectByExample(ConsumerCheckCodeExample example);

    ConsumerCheckCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerCheckCode record, @Param("example") ConsumerCheckCodeExample example);

    int updateByExample(@Param("record") ConsumerCheckCode record, @Param("example") ConsumerCheckCodeExample example);

    int updateByPrimaryKeySelective(ConsumerCheckCode record);

    int updateByPrimaryKey(ConsumerCheckCode record);

    ConsumerCheckCode selectCheckCode( @Param("phoneNo")String phoneNo,@Param("source")String source);
}