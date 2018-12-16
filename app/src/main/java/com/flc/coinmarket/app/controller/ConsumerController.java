package com.flc.coinmarket.app.controller;

import com.auth0.jwt.JWT;
import com.flc.coinmarket.app.annotation.PassToken;
import com.flc.coinmarket.app.annotation.UserLoginToken;
import com.flc.coinmarket.app.service.ConsumerService;
import com.flc.coinmarket.core.base.BaseRequest;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.dao.mysql.model.consumer.Consumer;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerWithBLOBs;
import com.flc.coinmarket.dao.mysql.model.statistics.ConsumerProfitsDaily;
import com.flc.coinmarket.dao.pojo.*;
import com.flc.coinmarket.dao.vo.ConsumerAppVO;
import com.flc.coinmarket.dao.vo.ConsumerWalletVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("app/home/consumer")
@Api(value="app用户首页接口", tags = "app用户首页接口", description="app用户首页接口")
public class ConsumerController {
    private static Logger logger = LoggerFactory.getLogger(ConsumerController.class);
    @Autowired
    private ConsumerService consumerService;

    @PassToken
    @PostMapping("login")
    @ApiOperation(value = "用户登录", notes = "用户登录", tags = "app用户-登录",httpMethod = "POST")
    public BaseResponse login(@RequestBody ConsumerLogin consumerLogin){
        BaseResponse response;
        try {
            response = consumerService.login(consumerLogin);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @UserLoginToken
    @PostMapping("logout")
    @ApiOperation(value = "用户登出", notes = "用户登出", tags = "app用户-登录",httpMethod = "POST")
    public BaseResponse<Consumer> logout(Consumer consumer){
        return null;
    }


    @PostMapping("registry")
    @PassToken
    @ApiOperation(value = "用户注册", notes = "用户注册", tags = "app用户-登录",httpMethod = "POST")
    public BaseResponse<ConsumerWithBLOBs> registry(@RequestBody ConsumerRegist consumerRegist){
        BaseResponse response;
        try {
            response = consumerService.regist(consumerRegist);
        }catch(RuntimeException e){
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.PARAMETER_VALIDATION_FAILED.getCode());
            response.setResponseMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }


    @UserLoginToken
    @PostMapping("statistics")
    @ApiOperation(value = "用户首页总览", notes = "用户首页总览", tags = "app用户-首页",httpMethod = "POST")
    public BaseResponse<ConsumerWalletVO> overview( HttpServletRequest httpServletRequest){
        BaseResponse< ConsumerWalletVO> response=new BaseResponse<>();
        try{
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response=consumerService.overview(Integer.parseInt(consumerId));
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            e.printStackTrace();
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }
    @UserLoginToken
    @PostMapping("profits")
    @ApiOperation(value = "每日收益", notes = "用户首页总览", tags = "app用户-首页",httpMethod = "POST")
    public BaseResponse<List<ConsumerProfitsDaily>> profitsDaily(@RequestBody DateQuery dateQuery, HttpServletRequest httpServletRequest){
        BaseResponse<List<ConsumerProfitsDaily>> response;
        try {
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response = consumerService.profitsDaily(Integer.parseInt(consumerId),dateQuery);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }
    @UserLoginToken
    @PostMapping("update")
    @ApiOperation(value = "app用户信息更新", notes = "app用户信息更新", tags = "app用户-我的",httpMethod = "POST")
    public BaseResponse update(HttpServletRequest httpServletRequest,@RequestBody  ConsumerAppVO consumerAppVO){
        BaseResponse response;
        try {
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response = consumerService.update(Integer.parseInt(consumerId),consumerAppVO);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }


    @UserLoginToken
    @PostMapping("profitsdetail")
    @ApiOperation(value = "收益明细", notes = "收益明细", tags = "app用户-我的",httpMethod = "GET")
    public BaseResponse<PageInfo<List<ConsumerProfitsDaily>>> profitsdetail(@RequestBody BaseRequest request,HttpServletRequest httpServletRequest){
        BaseResponse<PageInfo<List<ConsumerProfitsDaily>>> response;
        try {
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response = consumerService.profitsdetail(Integer.parseInt(consumerId),request.getPageNo());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @UserLoginToken
    @PostMapping("fundspswd")
    @ApiOperation(value = "修改资金密码", notes = "修改资金密码", tags = "app用户-我的",httpMethod = "POST")
    public BaseResponse changeFundspswd(@RequestBody ConsumerPwdQuery consumerPwdQuery , HttpServletRequest httpServletRequest){
        BaseResponse response;
        try {
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response = consumerService.changeFundspswd(consumerPwdQuery,Integer.parseInt(consumerId));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @PassToken
    @PostMapping("forgetPwd")
    @ApiOperation(value = "忘记秘密", notes = "忘记密码", tags = "app用户-登录",httpMethod = "POST")
    public BaseResponse forgetPwd(@RequestBody ConsumerPwdQuery consumerPwdQuery){
        BaseResponse response;
        try {
            response = consumerService.forgetPwd(consumerPwdQuery);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @UserLoginToken
    @PostMapping("updatePwd")
    @ApiOperation(value = "修改密码", notes = "修改密码", tags = "app用户-我的",httpMethod = "POST")
    public BaseResponse updatePwd(@RequestBody ConsumerPwdQuery consumerPwdQuery, HttpServletRequest httpServletRequest){
        BaseResponse response;
        try {
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response = consumerService.updatePwd(consumerPwdQuery,Integer.parseInt(consumerId));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }


    @PassToken
    @PostMapping("sendMessage")
    @ApiOperation(value = "系统短信验证码服务", notes = "系统短信验证码服务", tags = "系统短信验证码服务",httpMethod = "POST")
    public BaseResponse<String> sendMessage(@RequestBody SendMessageQuery sendMessageQuery){
        BaseResponse response;
        try {
            response = consumerService.sendSms(sendMessageQuery);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @UserLoginToken
    @PostMapping("consumer")
    @ApiOperation(value = "获取app用户信息服务", notes = "获取app用户信息服务", tags = "获取app用户信息服务",httpMethod = "POST")
    public BaseResponse<ConsumerAppVO> consumer(HttpServletRequest httpServletRequest){
        BaseResponse<ConsumerAppVO> response;
        try {
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response = consumerService.consumer(Integer.parseInt(consumerId));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @UserLoginToken
    @PostMapping("lockRelease")
    @ApiOperation(value = "释放锁仓资产", notes = "释放锁仓资产", tags = "首页-释放锁仓资产",httpMethod = "POST")
    public BaseResponse lockRelease(HttpServletRequest httpServletRequest, @RequestBody Map map){
        BaseResponse response;
        try {
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response = consumerService.lockRelease(Integer.parseInt(consumerId),(String)map.get("lockReleseFlag"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @UserLoginToken
    @PostMapping("autoReinvest")
    @ApiOperation(value = "自动复投", notes = "自动复投", tags = "app-我的",httpMethod = "POST")
    public BaseResponse autoReinvest(HttpServletRequest httpServletRequest, @RequestBody Map map){
        BaseResponse response;
        try {
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response = consumerService.autoReinvest(Integer.parseInt(consumerId),(String)map.get("autoReinvestFlag"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
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
