package com.flc.coinmarket.admin.controller;

import com.flc.coinmarket.admin.service.ConsumerService;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;
import com.flc.coinmarket.dao.mysql.model.consumer.Consumer;
import com.flc.coinmarket.dao.mysql.model.statistics.*;
import com.flc.coinmarket.dao.pojo.ConsumerParam;
import com.flc.coinmarket.dao.pojo.ConsumerQuery;
import com.flc.coinmarket.dao.pojo.TransactionDetailQuery;
import com.flc.coinmarket.dao.vo.ConsumerInfoVO;
import com.flc.coinmarket.dao.vo.ConsumerTeamVO;
import com.flc.coinmarket.dao.vo.EchartsPieVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("admin/manage/consumer")
@Api(value = "用户管理接口", tags = "用户管理接口", description = "用户管理接口")
public class ConsumerController {
    private static Logger logger = LoggerFactory.getLogger(ConsumerController.class);
    @Autowired
    private ConsumerService consumerService;

    @GetMapping("consumer/{id}")
    @ApiOperation(value = "app用户信息", notes = "app用户信息", tags = "用户维护", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<ConsumerInfoVO> consumer(@PathVariable Integer id) {
        BaseResponse<ConsumerInfoVO> response;
        try {
            logger.info("你好，git主分支，你看得见我吗？？？？？？？？？？");
            response = consumerService.consumer(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @PostMapping("consumers")
    @ApiOperation(value = "app用户列表", notes = "app用户列表", tags = "用户维护", httpMethod = "GET")
    public BaseResponse<PageInfo<ConsumerInfoVO>> consumers(@RequestBody  ConsumerQuery consumerQuery) {
        BaseResponse<PageInfo<ConsumerInfoVO>> response;
        try {
            response = consumerService.consumers(consumerQuery);
        } catch (Exception e) {
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @PostMapping("add")
    @ApiOperation(value = "新增app用户", notes = "新增app用户", tags = "用户维护", httpMethod = "POST")
    public BaseResponse add(@RequestBody ConsumerParam consumerParam) {
        BaseResponse response;
        try {
            response = consumerService.add(consumerParam);
        }catch(RuntimeException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseMsg(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "删除app用户", notes = "删除app用户", tags = "用户维护", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<Consumer> delete(@PathVariable Integer id) {
        BaseResponse<Consumer> response;
        try {
            response = consumerService.delete(id);
        }catch(RuntimeException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @PostMapping("delete")
    @ApiOperation(value = "批量删除app用户", notes = "批量删除app用户", tags = "用户维护", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户ID集合", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<Consumer> delete(@RequestBody List<Integer> ids) {
        BaseResponse<Consumer> response;
        try {
            response = consumerService.deleteBatch(ids);
        } catch(RuntimeException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseMsg(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
        return response;
    }

    @GetMapping("capitaltotal/{id}")
    @ApiOperation(value = "资产状态-条形图", notes = "资产状态", tags = "用户统计查询", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", example = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<ConsumerCapitalTotal>> capitalTotal(@PathVariable Integer id, @DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        BaseResponse<List<ConsumerCapitalTotal>> response;
        try {
            response = consumerService.consumerCapitalTotal(id, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("capitaltotalPie/{id}")
    @ApiOperation(value = "资产状态-饼状图", notes = "资产状态", tags = "用户统计查询", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "用户ID", example = "1", required = true, dataType = "int")
    public BaseResponse<List<EchartsPieVO>> capitalTotalPie(@PathVariable Integer id) {
        BaseResponse<List<EchartsPieVO>> response;
        try {
            response = consumerService.capitalTotalPie(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("profitstotal/{id}")
    @ApiOperation(value = "收益情况-条状图", notes = "收益情况", tags = "用户统计查询", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", example = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<ConsumerProfitsTotal>> profitsTotal(@PathVariable Integer id,@DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<ConsumerProfitsTotal>> response;
        try {
            response = consumerService.profitsTotal(id, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("profitstotalPie/{id}")
    @ApiOperation(value = "收益情况-饼状图", notes = "收益情况", tags = "用户统计查询", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "用户ID", example = "1", required = true, dataType = "int")
    public BaseResponse<List<EchartsPieVO>> profitsTotalPie(@PathVariable Integer id) {
        BaseResponse<List<EchartsPieVO>> response;
        try {
            response = consumerService.profitsTotalPie(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @PostMapping("transactiondetail")
    @ApiOperation(value = "交易流水", notes = "交易流水", tags = "用户统计查询", httpMethod = "POST")
    public BaseResponse<PageInfo<ConsumerTranceDetail>> transactionDetail(@RequestBody TransactionDetailQuery query) {
        BaseResponse<PageInfo<ConsumerTranceDetail>> response;
        try{
            response = consumerService.transactionDetail(query);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return  response;
    }

    @GetMapping("team/{id}")
    @ApiOperation(value = "团队总览", notes = "团队总览", tags = "用户统计查询", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", example = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "witchteam", value = "用户的哪个团队", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<List<ConsumerTeamVO>> team(@PathVariable Integer id, @DateTimeFormat(pattern="yyyy-MM-dd")Date  startDate,@DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        BaseResponse<List<ConsumerTeamVO>> response;
        try{
            response = consumerService.team(id,startDate,endDate);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return  response;
    }
    @GetMapping("teamMember/{id}")
    @ApiOperation(value = "团队情况-人员明细", notes = "团队总览", tags = "用户统计查询", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", example = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "witchteam", value = "用户的哪个团队", example = "1", required = true, dataType = "int")
    })
    public BaseResponse<List<ConsumerInfoVO>> teamMember(@PathVariable Integer id, Integer witchteam) {
        BaseResponse<List<ConsumerInfoVO>> response;
        try{
            response = consumerService.teamMember(id,witchteam);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return  response;
    }

}
