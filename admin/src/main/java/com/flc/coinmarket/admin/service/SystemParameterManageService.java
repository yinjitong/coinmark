package com.flc.coinmarket.admin.service;

import com.flc.coinmarket.dao.mysql.mapper.system.ProfitsLockrepoRatioMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.ProfitsTeamRatioMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysParameterMapper;
import com.flc.coinmarket.dao.mysql.model.system.*;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemParameterManageService {

    @Autowired
    private ProfitsLockrepoRatioMapper profitsLockrepoRatioMapper;
    @Autowired
    private ProfitsTeamRatioMapper profitsTeamRatioMapper;
    @Autowired
    private SysParameterMapper sysParameterMapper;

    /**
     * 查询所有锁仓收益参数
     *
     * @return
     */
    public BaseResponse<List<ProfitsRatio>> lockrepos() {
        BaseResponse<List<ProfitsRatio>> response = new BaseResponse<>();
        List<ProfitsRatio> profitsLockrepoRatios = profitsLockrepoRatioMapper.selectByExample(null);
        response.setData(profitsLockrepoRatios);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 新增或修改锁仓收益参数
     *
     * @param profitsLockrepoRatio
     * @return
     */
    public BaseResponse<ProfitsLockrepoRatio> save(ProfitsLockrepoRatio profitsLockrepoRatio, SysUser sysUser) {
        BaseResponse baseResponse = new BaseResponse();
        if(sysUser==null){
            baseResponse.setResponseMsg(ResponseCode.PLEASE_LOGIN.getMessage());
            baseResponse.setResponseCode(ResponseCode.PLEASE_LOGIN.getCode());
            return baseResponse;
        }
        if(StringUtils.isBlank(profitsLockrepoRatio.getProfitsCode())){
            baseResponse.setResponseMsg(ResponseCode.PROFIS_CODE_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.PROFIS_CODE_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if(profitsLockrepoRatio.getCardinalNumber()==null){
            baseResponse.setResponseMsg(ResponseCode.CARDINALNUMVER_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.CARDINALNUMVER_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if(profitsLockrepoRatio.getLowerLimit()==null){
            baseResponse.setResponseMsg(ResponseCode.CARDINALNUMVER_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.CARDINALNUMVER_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if(profitsLockrepoRatio.getUpperLimit()==null){
            baseResponse.setResponseMsg(ResponseCode.UPPERLIMIT_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.UPPERLIMIT_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if(profitsLockrepoRatio.getRatio()==null){
            baseResponse.setResponseMsg(ResponseCode.RATIO_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.RATIO_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if (profitsLockrepoRatio.getId() != null) {
            ProfitsLockrepoRatio profitsRatio = (ProfitsLockrepoRatio)profitsLockrepoRatioMapper.selectByPrimaryKey(profitsLockrepoRatio.getId());
            profitsRatio.setProfitsCode(profitsLockrepoRatio.getProfitsCode());
            profitsRatio.setLowerLimit(profitsLockrepoRatio.getLowerLimit());
            profitsRatio.setUpperLimit(profitsLockrepoRatio.getUpperLimit());
            profitsRatio.setRatio(profitsLockrepoRatio.getRatio());
            profitsRatio.setCardinalNumber(profitsLockrepoRatio.getCardinalNumber());
            profitsRatio.setUpdatedUser(sysUser.getId());
            profitsRatio.setUpdatedTime(new Date());
            profitsLockrepoRatioMapper.updateByPrimaryKey(profitsRatio);
            baseResponse.setData(profitsRatio);
        } else {
            profitsLockrepoRatio.setCreatedUser(sysUser.getId());
            profitsLockrepoRatio.setCreatedTime(new Date());
            profitsLockrepoRatioMapper.insertSelective(profitsLockrepoRatio);
            baseResponse.setData(profitsLockrepoRatio);
        }
        baseResponse.setResponseMsg(ResponseCode.OK.getMessage());
        baseResponse.setResponseCode(ResponseCode.OK.getCode());
        return baseResponse;
    }

    /**
     * 根据id返回锁仓收益实体
     *
     * @param id
     * @return
     */
    public BaseResponse<ProfitsRatio> lockrepo(Integer id) {
        BaseResponse<ProfitsRatio> response = new BaseResponse<>();
        ProfitsRatio profitsLockrepoRatio = profitsLockrepoRatioMapper.selectByPrimaryKey(id);
        response.setData(profitsLockrepoRatio);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 根据id删除锁仓收益参数
     *
     * @param id
     * @return
     */
    public BaseResponse<Integer> deleteLockrepo(Integer id) {
        BaseResponse<Integer> response = new BaseResponse<>();
        int deleteId = profitsLockrepoRatioMapper.deleteByPrimaryKey(id);
        response.setData(deleteId);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 批量删除锁仓收益参数
     *
     * @param ids
     * @return
     */
    public BaseResponse deleteLockrepos(List<Integer> ids) {
        BaseResponse response = new BaseResponse<>();
        for (Integer id : ids) {
            profitsLockrepoRatioMapper.deleteByPrimaryKey(id);
        }
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 查询所有团队收益参数
     *
     * @return
     */
    public BaseResponse<List<ProfitsRatio>> teamprofitses() {
        BaseResponse<List<ProfitsRatio>> response = new BaseResponse<>();
        List<ProfitsRatio> profitsTeamRatios = profitsTeamRatioMapper.selectByExample(null);
        response.setData(profitsTeamRatios);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 根据id查询团队收益参数
     *
     * @param id
     * @return
     */
    public BaseResponse<ProfitsRatio> teamprofits(Integer id) {
        BaseResponse<ProfitsRatio> response = new BaseResponse<>();
        ProfitsRatio profitsTeamRatio = profitsTeamRatioMapper.selectByPrimaryKey(id);
        response.setData(profitsTeamRatio);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 新增或修改团队收益参数
     *
     * @param profitsTeamRatio
     * @return
     */
    public BaseResponse<ProfitsTeamRatio> saveTeamprofits(@RequestBody  ProfitsTeamRatio profitsTeamRatio, SysUser sysUser) {
        BaseResponse baseResponse = new BaseResponse();
        if(sysUser==null){
            baseResponse.setResponseMsg(ResponseCode.PLEASE_LOGIN.getMessage());
            baseResponse.setResponseCode(ResponseCode.PLEASE_LOGIN.getCode());
            return baseResponse;
        }
        if(StringUtils.isBlank(profitsTeamRatio.getProfitsCode())){
            baseResponse.setResponseMsg(ResponseCode.PROFIS_CODE_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.PROFIS_CODE_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if(profitsTeamRatio.getCardinalNumber()==null){
            baseResponse.setResponseMsg(ResponseCode.CARDINALNUMVER_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.CARDINALNUMVER_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if(profitsTeamRatio.getLowerLimit()==null){
            baseResponse.setResponseMsg(ResponseCode.CARDINALNUMVER_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.CARDINALNUMVER_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if(profitsTeamRatio.getUpperLimit()==null){
            baseResponse.setResponseMsg(ResponseCode.UPPERLIMIT_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.UPPERLIMIT_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if(profitsTeamRatio.getRatio()==null){
            baseResponse.setResponseMsg(ResponseCode.RATIO_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.RATIO_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if (profitsTeamRatio.getId() != null) {
            ProfitsTeamRatio profitsRatio = (ProfitsTeamRatio)profitsTeamRatioMapper.selectByPrimaryKey(profitsTeamRatio.getId());
            profitsRatio.setUpdatedTime(new Date());
            profitsRatio.setUpdatedUser(sysUser.getId());
            profitsRatio.setCardinalNumber(profitsTeamRatio.getCardinalNumber());
            profitsRatio.setLowerLimit(profitsTeamRatio.getLowerLimit());
            profitsRatio.setUpperLimit(profitsTeamRatio.getUpperLimit());
            profitsRatio.setProfitsCode(profitsTeamRatio.getProfitsCode());
            profitsRatio.setRatio(profitsTeamRatio.getRatio());
            profitsTeamRatioMapper.updateByPrimaryKey(profitsRatio);
            baseResponse.setData(profitsRatio);
        } else {
            profitsTeamRatio.setCreatedTime(new Date());
            profitsTeamRatio.setCreatedUser(sysUser.getId());
            profitsTeamRatioMapper.insertSelective(profitsTeamRatio);
            baseResponse.setData(profitsTeamRatio);
        }
        baseResponse.setResponseMsg(ResponseCode.OK.getMessage());
        baseResponse.setResponseCode(ResponseCode.OK.getCode());
        return baseResponse;
    }

    /**
     * 根据id删除团队收益参数
     *
     * @param id
     * @return
     */
    public BaseResponse<Integer> deleteTeamprofit(Integer id) {
        BaseResponse<Integer> response = new BaseResponse<>();
        int deleteId = profitsTeamRatioMapper.deleteByPrimaryKey(id);
        response.setData(deleteId);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 批量删除团队收益参数
     * @param ids
     * @return
     */
    public BaseResponse<Integer> deleteTeamprofits(List<Integer> ids) {
        BaseResponse response = new BaseResponse<>();
        for (Integer id : ids) {
            profitsTeamRatioMapper.deleteByPrimaryKey(id);
        }
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }


    /**
     * 查询所有其他参数列表
     * @return
     */
    public BaseResponse<List<SysParameter>> sysParameters() {
        BaseResponse<List<SysParameter>> response = new BaseResponse<>();
        List<SysParameter> sysParameters = sysParameterMapper.selectByExample(null);
        response.setData(sysParameters);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 根据id查询其他参数
     * @param id
     * @return
     */
    public BaseResponse<SysParameter> sysParameter(Integer id) {
        BaseResponse<SysParameter> response = new BaseResponse<>();
        SysParameter sysParameter = sysParameterMapper.selectByPrimaryKey(id);
        response.setData(sysParameter);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 新增或者修改其他参数
     * @param sysParameter
     * @return
     */
    public BaseResponse<SysParameter> saveSysParameter(SysParameter sysParameter,SysUser sysUser) {
        BaseResponse baseResponse = new BaseResponse();
        if(sysUser==null){
            baseResponse.setResponseMsg(ResponseCode.PLEASE_LOGIN.getMessage());
            baseResponse.setResponseCode(ResponseCode.PLEASE_LOGIN.getCode());
            return baseResponse;
        }
        if(StringUtils.isBlank(sysParameter.getParamCode())){
            baseResponse.setResponseMsg(ResponseCode.PARAMCODE_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.PARAMCODE_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if(StringUtils.isBlank(sysParameter.getParamName())){
            baseResponse.setResponseMsg(ResponseCode.RATIO_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.RATIO_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if(sysParameter.getParamValue()==null){
            baseResponse.setResponseMsg(ResponseCode.PARAMVALUE_CANT_BE_NULL.getMessage());
            baseResponse.setResponseCode(ResponseCode.PARAMVALUE_CANT_BE_NULL.getCode());
            return baseResponse;
        }
        if (sysParameter.getId() != null) {
            SysParameter sys = sysParameterMapper.selectByPrimaryKey(sysParameter.getId());
            sys.setParamCode(sysParameter.getParamCode());
            sys.setParamName(sysParameter.getParamName());
            sys.setParamValue(sysParameter.getParamValue());
            sys.setUpdatedTime(new Date());
            sys.setUpdatedUser(sysUser.getId());
            sysParameterMapper.updateByPrimaryKey(sys);
            baseResponse.setData(sys);
        } else {
            sysParameter.setCreatedTime(new Date());
            sysParameter.setCreatedUser(sysUser.getId());
            sysParameterMapper.insertSelective(sysParameter);
            baseResponse.setData(sysParameter);
        }
        baseResponse.setResponseMsg(ResponseCode.OK.getMessage());
        baseResponse.setResponseCode(ResponseCode.OK.getCode());
        return baseResponse;
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public BaseResponse<Integer> deleteSysParameter(Integer id) {
        BaseResponse<Integer> response = new BaseResponse<>();
        int deleteId = sysParameterMapper.deleteByPrimaryKey(id);
        response.setData(deleteId);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 批量删除其他参数
     * @param ids
     * @return
     */
    public BaseResponse<Integer> deleteSysParameters(List<Integer> ids) {
        BaseResponse response = new BaseResponse<>();
        for (Integer id : ids) {
            sysParameterMapper.deleteByPrimaryKey(id);
        }
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 根据paramCode查询paramValue
     * @param paramCode
     * @return
     */
    public BaseResponse queryParamValue(String paramCode) {
        BaseResponse response = new BaseResponse<>();
        SysParameterExample sysParameterExample=new SysParameterExample();
        sysParameterExample.createCriteria().andParamCodeEqualTo(paramCode);
        List<SysParameter> sysParameters = sysParameterMapper.selectByExample(sysParameterExample);
        if(sysParameters.isEmpty()){
            response.setResponseMsg(ResponseCode.PARAMETER_ISNULL.getMessage());
            response.setResponseCode(ResponseCode.PARAMETER_ISNULL.getCode());
            return response;
        }
        BigDecimal paramValue = sysParameters.get(0).getParamValue();
        Map<String,BigDecimal> map=new HashMap<>();
        map.put("paramValue",paramValue);
        response.setData(map);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }
}
