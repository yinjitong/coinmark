package com.flc.coinmarket.dao.mysql.mapper.system;

import com.flc.coinmarket.dao.mysql.model.system.SysParameter;
import com.flc.coinmarket.dao.mysql.model.system.SysParameterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysParameterMapper {
    long countByExample(SysParameterExample example);

    int deleteByExample(SysParameterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysParameter record);

    int insertSelective(SysParameter record);

    List<SysParameter> selectByExample(SysParameterExample example);

    SysParameter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysParameter record, @Param("example") SysParameterExample example);

    int updateByExample(@Param("record") SysParameter record, @Param("example") SysParameterExample example);

    int updateByPrimaryKeySelective(SysParameter record);

    int updateByPrimaryKey(SysParameter record);
}