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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
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

    @GetMapping("exportXlsConsumers")
    @ApiOperation(value = "导出app用户列表", notes = "导出app用户列表", tags = "用户维护", httpMethod = "GET")
    public void exportXlsConsumers( HttpServletResponse servletResponse) {
        BaseResponse<PageInfo<ConsumerInfoVO>> response;
        try {
            ConsumerQuery consumerQuery=new ConsumerQuery();
            consumerQuery.setPageSize(Integer.MAX_VALUE);
            response = consumerService.consumers(consumerQuery);
            try {
                PageInfo<ConsumerInfoVO> consumersPageInfo = response.getData();
                List<ConsumerInfoVO> list = consumersPageInfo.getList();

                // 存在数据可以导出
                // 2.创建excel，创建标题
                // 2.1创建整个excel
                /**
                 * 整个excel：HSSFWorkbook sheet页：HSSFSheet row行：HSSFRow（写）,Row(读)
                 * cell单元格：HSSFCell（写）,Cell（读）
                 */
                XSSFWorkbook wb = new XSSFWorkbook();
                // 2.2在excel中创建一个sheet页
                XSSFSheet sheet = wb.createSheet();
                // 2.3在sheet页中创建标题行
                XSSFRow row = sheet.createRow(0);// 创建第一行，第一行从0开始
                // 2.4在标题行创建标题单元格
                row.createCell(0).setCellValue("昵称");
                row.createCell(1).setCellValue("手机号");
                row.createCell(2).setCellValue("资产总量");
                row.createCell(3).setCellValue("消费资产");
                row.createCell(4).setCellValue("锁仓资产");
                row.createCell(5).setCellValue("推荐人");
                row.createCell(6).setCellValue("左节点");
                row.createCell(7).setCellValue("右节点");
                row.createCell(8).setCellValue("注册时间");
                row.createCell(9).setCellValue("上次登录时间");

                if (null != list && list.size() > 0) {
                    // 3.循环将数据存入excel
                    int index = 1;
                    for (ConsumerInfoVO consumer : list) {
                        // 3.1循环创建行
                        row = sheet.createRow(index++);
                        // 3.2创建行的列,给列赋值
                        row.createCell(0).setCellValue(consumer.getNickName());
                        row.createCell(1).setCellValue(consumer.getPhoneNo());
                        row.createCell(2).setCellValue(consumer.getTotalFunds().doubleValue()+"");
                        row.createCell(3).setCellValue(consumer.getFloatingFunds().doubleValue()+"");
                        row.createCell(4).setCellValue(consumer.getLockrepoFunds().doubleValue()+"");
                        row.createCell(5).setCellValue(consumer.getRefNickName());
                        row.createCell(6).setCellValue(consumer.getLeftNickName());
                        row.createCell(7).setCellValue(consumer.getRefNickName());
                        row.createCell(8).setCellValue(consumer.getCreatTime());
                        row.createCell(9).setCellValue(consumer.getLastLogin());
                    }
                }
                // 4.设置response响应参数：一个流两个头
                String filename = "用户列表.xlsx";
                // 4.1一个流：response的输出流
                ServletOutputStream os = servletResponse.getOutputStream();
                // 4.2两个头之一：content-type，告诉前台浏览器返回数据的格式：xml,css,html,json,xls等等
                servletResponse.setHeader("content-Type", "application/vnd.ms-excel");
                // 4.3两个头之二：content-disposition，告诉前台浏览器数据的打开方式，附件方式打开值如下：attachment;filename=文件名
                servletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
                // 5.将excel通过response返回到前台
                wb.write(os);
                wb.close();

            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(),e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response = new BaseResponse<>();
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        }
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
