package com.flc.coinmarket.app.controller;

import com.auth0.jwt.JWT;
import com.flc.coinmarket.app.annotation.PassToken;
import com.flc.coinmarket.app.annotation.UserLoginToken;
import com.flc.coinmarket.app.service.TransactionalService;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;
import com.flc.coinmarket.dao.pojo.ConsumerTrance;
import com.flc.coinmarket.dao.pojo.TransactionDetailQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@UserLoginToken
@PassToken
@RestController
@RequestMapping("app/consumer/transaction")
@Api(value="app用户交易接口", tags = "app用户交易接口", description="app用户交易接口")
public class TransactionController {
    private static Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    private TransactionalService transactionalService;

    @PostMapping("toothers")
    @ApiOperation(value = "对其他用户转账", notes = "对其他用户转账", tags = "app用户-交易",httpMethod = "POST")
    public BaseResponse<BigDecimal> toothers(@RequestBody  ConsumerTrance transaction, HttpServletRequest httpServletRequest){
        BaseResponse response=new BaseResponse();
        try{
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response=transactionalService.toothers(transaction,Integer.parseInt(consumerId));
        }catch (Exception e){
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @PostMapping("tolockrepo")
    @ApiOperation(value = "转账到锁仓账号", notes = "转账到锁仓账号", tags = "app用户-交易",httpMethod = "POST")
    public BaseResponse<BigDecimal> tolockrepo(@RequestBody ConsumerTrance transaction, HttpServletRequest httpServletRequest){
        BaseResponse response=new BaseResponse();
        try{
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response=transactionalService.tolockrepo(transaction,Integer.parseInt(consumerId));
        }catch (Exception e){
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @PostMapping("details")
    @ApiOperation(value = "交易流水", notes = "交易流水", tags = "app用户-交易",httpMethod = "GET")
    public BaseResponse<List<ConsumerTranceDetail>> details(@RequestBody TransactionDetailQuery transactionDetailQuery, HttpServletRequest httpServletRequest){
        BaseResponse<List<ConsumerTranceDetail>> response=new BaseResponse();
        try{
            String token = httpServletRequest.getHeader("token");
            String consumerId = JWT.decode(token).getAudience().get(0);
            response=transactionalService.details(Integer.parseInt(consumerId),transactionDetailQuery);
        }catch (Exception e){
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

}
