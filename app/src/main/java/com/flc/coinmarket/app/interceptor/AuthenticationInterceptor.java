package com.flc.coinmarket.app.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.flc.coinmarket.app.annotation.PassToken;
import com.flc.coinmarket.app.annotation.UserLoginToken;
import com.flc.coinmarket.app.controller.ConsumerController;
import com.flc.coinmarket.app.service.ConsumerService;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerWithBLOBs;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private ConsumerService consumerService;

    private static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        BaseResponse<Integer> response = new BaseResponse<>();
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        //从参数中取出token
//        String token =  httpServletRequest.getParameter("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    response.setResponseMsg(ResponseCode.NOT_HAVE_TOKEN.getMessage());
                    response.setResponseCode(ResponseCode.NOT_HAVE_TOKEN.getCode());
                    String jsonString  = JSONObject.toJSONString(response);
                    outputStream.write(jsonString.getBytes());
                    return false;
//                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 consumerId
                String consumerId;
                try {
                    consumerId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                ConsumerWithBLOBs consumer = consumerService.findConsumerById(consumerId);
                if (consumer == null) {
                    response.setResponseMsg(ResponseCode.TOKEN_TIME_OUT.getMessage());
                    response.setResponseCode(ResponseCode.TOKEN_TIME_OUT.getCode());
                    String jsonString  = JSONObject.toJSONString(response);
                    outputStream.write(jsonString.getBytes());
//                    throw new RuntimeException("用户不存在，请重新登录");
                    return false;
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(consumer.getPassWord())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                    response.setResponseMsg(ResponseCode.TOKEN_TIME_OUT.getMessage());
                    response.setResponseCode(ResponseCode.TOKEN_TIME_OUT.getCode());
                    String jsonString  = JSONObject.toJSONString(response);
                    outputStream.write(jsonString.getBytes());
                    return false;
//                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}