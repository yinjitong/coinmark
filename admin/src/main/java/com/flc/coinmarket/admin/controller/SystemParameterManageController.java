package com.flc.coinmarket.admin.controller;

import com.flc.coinmarket.admin.service.SystemParameterManageService;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.dao.mysql.model.system.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/manage/parameter")
@Api(value="系统参数管理接口", tags = "系统参数管理接口", description="系统参数管理接口")
public class SystemParameterManageController {
    private static Logger logger = LoggerFactory.getLogger(SystemParameterManageController.class);
    @Autowired
    private SystemParameterManageService systemParameterManageService;

    @GetMapping("lockrepo/lockrepos")
    @ApiOperation(value = "锁仓收益参数列表", notes = "锁仓收益参数列表", tags = "锁仓收益参数",httpMethod = "GET")
    public BaseResponse<List<ProfitsRatio>> lockrepos(){
        BaseResponse<List<ProfitsRatio>> response;
        try{
            response=systemParameterManageService.lockrepos();
        }catch(Exception e){
            e.printStackTrace();
            response=new BaseResponse();
            logger.error(e.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }

        return response;
    }

    @GetMapping("lockrepo/lockrepo/{id}")
    @ApiOperation(value = "锁仓收益参数", notes = "锁仓收益参数", tags = "锁仓收益参数",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "参数ID", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<ProfitsRatio> lockrepo(@PathVariable Integer id){

        BaseResponse<ProfitsRatio> response;
        try{
            response=systemParameterManageService.lockrepo(id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }

        return response;
    }

    @PostMapping("lockrepo/savelockrepo")
    @ApiOperation(value = "保存锁仓收益参数", notes = "保存锁仓收益参数", tags = "锁仓收益参数",httpMethod = "POST")
    public BaseResponse<ProfitsLockrepoRatio> saveLockrepo(@RequestBody ProfitsLockrepoRatio profitsLockrepoRatio, HttpSession session){
        BaseResponse response;
        try{
            SysUser sysUser = (SysUser)session.getAttribute("sysUser");
            return systemParameterManageService.save(profitsLockrepoRatio,sysUser);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
    }

    @PostMapping("lockrepo/delete/{id}")
    @ApiOperation(value = "删除锁仓收益参数", notes = "删除锁仓收益参数", tags = "锁仓收益参数",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "参数ID", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<Integer> deleteLockrepo(@PathVariable Integer id){
        BaseResponse<Integer> response;
        try{
            return systemParameterManageService.deleteLockrepo(id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
    }

    @PostMapping("lockrepo/delete")
    @ApiOperation(value = "批量删除锁仓收益参数", notes = "批量删除锁仓收益参数", tags = "锁仓收益参数",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "参数ID集合", example = "[1,2]", required = true, dataType = "int")
    })
    public BaseResponse<Integer> deleteLockrepos(@RequestBody List<Integer> ids){
        BaseResponse response;
        try{
            return systemParameterManageService.deleteLockrepos(ids);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
    }




    @GetMapping("teamprofits/teamprofitses")
    @ApiOperation(value = "团队收益参数列表", notes = "团队收益参数列表", tags = "团队收益参数",httpMethod = "GET")
    public BaseResponse<List<ProfitsRatio>> teamprofitses(){
        BaseResponse<List<ProfitsRatio>> response;
        try{
            response=systemParameterManageService.teamprofitses();
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }

        return response;
    }

    @GetMapping("teamprofits/teamprofits/{id}")
    @ApiOperation(value = "团队收益参数", notes = "团队收益参数", tags = "团队收益参数",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "参数ID", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<ProfitsRatio> teamprofits(@PathVariable Integer id){
        BaseResponse<ProfitsRatio> response;
        try{
            response=systemParameterManageService.teamprofits(id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }

        return response;
    }

    @PostMapping("teamprofits/saveteamprofits")
    @ApiOperation(value = "保存团队收益参数", notes = "保存团队收益参数", tags = "团队收益参数",httpMethod = "POST")
    public BaseResponse<ProfitsTeamRatio> saveTeamprofits(@RequestBody  ProfitsTeamRatio profitsTeamRatio,HttpSession session){
        BaseResponse response;
        try{
            SysUser sysUser = (SysUser)session.getAttribute("sysUser");
            return systemParameterManageService.saveTeamprofits(profitsTeamRatio,sysUser);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
    }

    @PostMapping("teamprofits/delete/{id}")
    @ApiOperation(value = "删除团队收益参数", notes = "删除团队收益参数", tags = "团队收益参数",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "参数ID", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<Integer> deleteTeamprofit(@PathVariable Integer id){
        BaseResponse<Integer> response;
        try{
            return systemParameterManageService.deleteTeamprofit(id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
    }

    @PostMapping("teamprofits/delete")
    @ApiOperation(value = "批量删除团队收益参数", notes = "批量删除团队收益参数", tags = "团队收益参数",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "参数ID集合", example = "[1,2]", required = true, dataType = "int")
    })
    public BaseResponse<Integer> deleteTeamprofits(@RequestBody List<Integer> ids){
        BaseResponse response;
        try{
            return systemParameterManageService.deleteTeamprofits(ids);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
    }




    @GetMapping("sysparam/sysparameters")
    @ApiOperation(value = "其他参数列表", notes = "其他参数列表", tags = "其他参数",httpMethod = "GET")
    public BaseResponse<List<SysParameter>> sysParameters(){
        BaseResponse<List<SysParameter>> response;
        try{
            response=systemParameterManageService.sysParameters();
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }

        return response;
    }

    @GetMapping("sysparam/sysparameter/{id}")
    @ApiOperation(value = "其他参数", notes = "其他参数", tags = "其他参数",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "参数ID", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<SysParameter> sysParameter(@PathVariable Integer id){
        BaseResponse<SysParameter> response;
        try{
            response=systemParameterManageService.sysParameter(id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }

        return response;
    }

    @PostMapping("sysparam/savesysparameter")
    @ApiOperation(value = "保存其他参数", notes = "保存其他参数", tags = "其他参数",httpMethod = "POST")
    public BaseResponse<SysParameter> saveSysParameter(@RequestBody SysParameter sysParameter,HttpSession session){
        BaseResponse response;
        try{
            SysUser sysUser = (SysUser)session.getAttribute("sysUser");
            return systemParameterManageService.saveSysParameter(sysParameter,sysUser);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
    }

    @PostMapping("sysparam/delete/{id}")
    @ApiOperation(value = "删除其他参数", notes = "删除其他参数", tags = "其他参数",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "参数ID", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<Integer> deleteSysParameter(@PathVariable Integer id){
        BaseResponse<Integer> response;
        try{
            return systemParameterManageService.deleteSysParameter(id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
    }

    @PostMapping("sysparam/delete")
    @ApiOperation(value = "批量删除其他参数", notes = "批量删除其他参数", tags = "其他参数",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "参数ID集合", example = "[1,2]", required = true, dataType = "int")
    })
    public BaseResponse<Integer> deleteSysParameters(@RequestBody List<Integer> ids){
        BaseResponse response;
        try{
            return systemParameterManageService.deleteSysParameters(ids);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
    }

    @PostMapping("sysparam/queryParamValue")
    @ApiOperation(value = "根据param_code查询任意参数", notes = "根据param_code查询任意参数", tags = "其他参数",httpMethod = "POST")
    @ApiImplicitParam(name = "ids", value = "参数ID集合", example = "[1,2]", required = true, dataType = "int")
    public BaseResponse queryParamValue(@RequestBody Map map){
        BaseResponse response;
        try{
            String paramValue = (String)map.get("paramCode");
            response= systemParameterManageService.queryParamValue(paramValue);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response=new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

}
