package com.flc.coinmarket.admin.controller;

import com.flc.coinmarket.admin.service.SystemUserService;
import com.flc.coinmarket.dao.mysql.model.system.SysUser;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("admin/manage/sysuser")
@Api(value="系统用户接口", tags = "系统用户接口", description="系统用户接口API")
public class SystemUserController {
    private static Logger logger = LoggerFactory.getLogger(SystemUserController.class);
    @Autowired
    private SystemUserService systemUserService;

    @PostMapping("login")
    @ApiOperation(value = "系统管理用户登录", notes = "系统管理用户登录", tags = "系统管理用户登录",httpMethod = "POST")
    public BaseResponse<SysUser> login(@RequestBody SysUser sysUser, HttpSession session){
        BaseResponse<SysUser> baseResponse;
        try {
            baseResponse = systemUserService.login(sysUser);
            session.setAttribute("sysUser", baseResponse.getData());
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse = new BaseResponse<>();
            logger.error(e.getMessage());
            baseResponse.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            baseResponse.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());

        }
        return baseResponse;
    }

    @PostMapping("logout")
    @ApiOperation(value = "系统管理用户登出", notes = "系统管理用户登出", tags = "系统管理用户登出",httpMethod = "POST")
    public BaseResponse<SysUser> logout(HttpSession session){
        BaseResponse response = new BaseResponse();
        try {
            //退出登录
            session.removeAttribute("sysUser");
            response.setResponseCode(ResponseCode.OK.getCode());
            response.setResponseMsg(ResponseCode.OK.getMessage());
            return response;
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
    }

    @GetMapping("add")
    public BaseResponse<SysUser> add(String account,String password){
        BaseResponse<SysUser> baseResponse;
        try {
            baseResponse = systemUserService.add(account,password);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse = new BaseResponse<>();
            logger.error(e.getMessage());
            baseResponse.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            baseResponse.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());

        }
        return baseResponse;
    }

    @PostMapping("getSysTime")
    public BaseResponse<Long>  getSysTime(){
        BaseResponse<Long> baseResponse=new BaseResponse<>();
        try {
            Long time = System.currentTimeMillis();
            baseResponse.setData(time);
            baseResponse.setResponseMsg(ResponseCode.OK.getMessage());
            baseResponse.setResponseCode(ResponseCode.OK.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse = new BaseResponse<>();
            logger.error(e.getMessage());
            baseResponse.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            baseResponse.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return baseResponse;
    }
}
