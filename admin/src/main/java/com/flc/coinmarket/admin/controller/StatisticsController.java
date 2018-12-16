package com.flc.coinmarket.admin.controller;

import com.flc.coinmarket.admin.service.StatisticsService;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.core.util.DateUtil;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;
import com.flc.coinmarket.dao.mysql.model.statistics.*;
import com.flc.coinmarket.dao.vo.ConsumerTeamVO;
import com.flc.coinmarket.dao.vo.EchartsPieVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("admin/manage/statistics")
@Api(value = "统计报表接口", tags = "统计报表接口", description = "统计报表接口")
public class StatisticsController {
    private static Logger logger = LoggerFactory.getLogger(StatisticsController.class);
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("capitaltotal")
    @ApiOperation(value = "总量趋势", notes = "今日统计-总量", tags = "今日统计（资产总览）", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<CapitalTotal>> capitalTotal(@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<CapitalTotal>> response;
        try {
            response = statisticsService.capitalTotal(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("capitaltotalPie")
    @ApiOperation(value = "总量", notes = "今日统计-总量-饼状图", tags = "今日统计", httpMethod = "GET")
    public BaseResponse<List<EchartsPieVO>> capitalTotalPie() {
        BaseResponse<List<EchartsPieVO>> response;
        try {
            response = statisticsService.capitalTotalPie();
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }



    @GetMapping("capitaldaily")
    @ApiOperation(value = "昨日增量", notes = "今日统计-昨日增量", tags = "今日统计（资产总览）", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<CapitalDaily>> capitalDaily(@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<CapitalDaily>> response;
        try {
            response = statisticsService.capitalDaily(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("capitaldailyPie")
    @ApiOperation(value = "昨日增量", notes = "今日统计-昨日增量-饼状图", tags = "今日统计", httpMethod = "GET")
    public BaseResponse<List<EchartsPieVO>> capitalDailyPie() {
        BaseResponse<List<EchartsPieVO>> response;
        try {
            response = statisticsService.capitalDailyPie();
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("consumertotal")
    @ApiOperation(value = "总量趋势", notes = "用户总览-总量趋势", tags = "用户总览", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<ConsumerCountTotal>> consumerTotal(@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<ConsumerCountTotal>> response;
        try {
            response = statisticsService.consumerTotal(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("consumerdaily")
    @ApiOperation(value = "每日分量", notes = "用户总览-每日分量", tags = "用户总览", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<ConsumerCountDaily>> consumerDaily(@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<ConsumerCountDaily>> response;
        try {
            response = statisticsService.consumerDaily(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("rankinlist")
    @ApiOperation(value = "排行榜", notes = "排行榜", tags = "排行榜", httpMethod = "GET")
    public BaseResponse<List<ConsumerTeamVO>> rankingList() {
        BaseResponse<List<ConsumerTeamVO>> response;
        try {
            response = statisticsService.rankingList();
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("profitstotal")
    @ApiOperation(value = "收益情况", notes = "收益情况-支出总量", tags = "今日统计", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<ProfitsTotal>> profitsTotal(@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<ProfitsTotal>> response;
        try {
            response = statisticsService.profitsTotal(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }
    @GetMapping("profitstotalPie")
    @ApiOperation(value = "收益情况", notes = "收益情况-支出总量-饼状图", tags = "今日统计", httpMethod = "GET")
    public BaseResponse<List<EchartsPieVO>> profitsTotalPie() {
        BaseResponse<List<EchartsPieVO>> response;
        try {
            response = statisticsService.profitsTotalPie();
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("profitsdaily")
    @ApiOperation(value = "收益情况", notes = "收益情况-昨日总量", tags = "今日统计", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<ProfitsDaily>> profitsDaily(@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<ProfitsDaily>> response;
        try {
            response = statisticsService.profitsDaily(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }
    @GetMapping("profitsdailyPie")
    @ApiOperation(value = "收益情况", notes = "收益情况-昨日总量-饼状图", tags = "今日统计", httpMethod = "GET")
    public BaseResponse<List<EchartsPieVO>> profitsDailyPie() {
        BaseResponse<List<EchartsPieVO>> response;
        try {
            response = statisticsService.profitsDailyPie();
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("feetotal")
    @ApiOperation(value = "总手续费", notes = "总手续费", tags = "今日统计", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<FeeTotal>> feeTotal(@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<FeeTotal>> response;
        try {
            response = statisticsService.feeTotal(startDate,endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("feedaily")
    @ApiOperation(value = "手续费增量", notes = "手续费增量", tags = "今日统计", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<FeeDaily>> feeDaily(@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<FeeDaily>> response;
        try {
            response = statisticsService.feeDaily(startDate,endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("destroytotal")
    @ApiOperation(value = "总销账", notes = "总销账", tags = "今日统计", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<LockrepoDestroyTotal>> destroyTotal(@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<LockrepoDestroyTotal>> response;
        try {
            response = statisticsService.destroyTotal(startDate,endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("destroydaily")
    @ApiOperation(value = "销账增量", notes = "销账增量", tags = "今日统计", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<LockrepoDestroyDaily>> destroyDaily(@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern="yyyy-MM-dd")Date endDate) {
        BaseResponse<List<LockrepoDestroyDaily>> response;
        try {
            response = statisticsService.destroyDaily(startDate,endDate);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }

    @GetMapping("exportXls")
    @ApiOperation(value = "导出流水", notes = "导出流水", tags = "首页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "起始时间", example = "2018-01-01", required = true, dataType = "date"),
            @ApiImplicitParam(name = "endDate", value = "截止时间", example = "2018-01-01", required = true, dataType = "date")
    })
    public BaseResponse<List<ConsumerTranceDetail>> exportXls() {
        BaseResponse<List<ConsumerTranceDetail>> response;
        try {
//            //1.查询所有流水
//            response = statisticsService.exportXls();
//            try {
//                List<ConsumerTranceDetail> list=response.getData();
//                if (null != list && list.size() > 0) {
//                    // 存在分区数据可以导出
//                    // 2.创建excel，创建标题
//                    // 2.1创建整个excel
//                    /**
//                     * 整个excel：HSSFWorkbook sheet页：HSSFSheet row行：HSSFRow（写）,Row(读)
//                     * cell单元格：HSSFCell（写）,Cell（读）
//                     */
//                    HSSFWorkbook wb = new HSSFWorkbook();
//                    // 2.2在excel中创建一个sheet页
//                    HSSFSheet sheet = wb.createSheet();
//                    // 2.3在sheet页中创建标题行
//                    HSSFRow row = sheet.createRow(0);// 创建第一行，第一行从0开始
//                    // 2.4在标题行创建标题单元格
//                    row.createCell(0).setCellValue("分区编号");
//                    row.createCell(1).setCellValue("分区地址");
//                    row.createCell(2).setCellValue("分区关键字");
//                    row.createCell(3).setCellValue("分区辅助关键字");
//                    row.createCell(4).setCellValue("区域编号");
//                    // 3.循环将数据存入excel
//                    int index = 1;
//                    for (ConsumerTranceDetail detail : list) {
//                        // 3.1循环创建行
//                        row = sheet.createRow(index++);
//                        // 3.2创建行的列,给列赋值
//                        row.createCell(0).setCellValue(detail.getId());
//                        row.createCell(1).setCellValue(detail.getAddress());
//                        row.createCell(2).setCellValue(detail.getKeyWords());
//                        row.createCell(3).setCellValue(detail.getAssistKeyWords());
//                        Area area = subarea.getArea();
//                        if (null != area) {
//                            // 给分区绑定了区域
//                            row.createCell(4).setCellValue(area.getId());
//                        } else {
//                            // 分区未指定区域
//                            row.createCell(4).setCellValue("未绑定区域信息");
//                        }
//                    }
//                    // 4.设置response响应参数：一个流两个头
//                    String filename = "流水数据.xls";
//                    //获取浏览器类型
//                    String agent = ServletActionContext.getRequest().getHeader("User-Agent");
//                    String mimeType = ServletActionContext
//                            .getServletContext().getMimeType(filename);
//                    //根据浏览器类型对文件名编码
//                    filename = FileUtils.encodeDownloadFilename(filename, agent);
//                    // 4.1一个流：response的输出流
//                    ServletOutputStream os = ServletActionContext
//                            .getResponse().getOutputStream();
//                    // 4.2两个头之一：content-type，告诉前台浏览器返回数据的格式：xml,css,html,json,xls等等
//                    ServletActionContext.getResponse().setContentType(mimeType);
//                    // 4.3两个头之二：content-disposition，告诉前台浏览器数据的打开方式，附件方式打开值如下：attachment;filename=文件名
//                    ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
//                    // 5.将excel通过response返回到前台
//                    wb.write(os);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            response=null;
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse<>();
            logger.error(e.getMessage());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        }
        return response;
    }


}
