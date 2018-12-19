package com.flc.coinmarket.admin.service;

import com.bugull.mongo.BuguQuery;
import com.flc.coinmarket.admin.volidate.DateValidate;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.core.util.DateUtil;
import com.flc.coinmarket.dao.mongo.dao.ConsumerTranceDetailDAO;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerCapitalAccountMapper;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerTeamMapper;
import com.flc.coinmarket.dao.mysql.mapper.statistics.*;
import com.flc.coinmarket.dao.mysql.mapper.system.SysDictionaryMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysParameterMapper;
import com.flc.coinmarket.dao.mysql.model.statistics.*;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionary;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionaryExample;
import com.flc.coinmarket.dao.mysql.model.system.SysParameter;
import com.flc.coinmarket.dao.mysql.model.system.SysParameterExample;
import com.flc.coinmarket.dao.vo.ConsumerTeamVO;
import com.flc.coinmarket.dao.vo.EchartsPieVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticsService {
    @Autowired
    private CapitalTotalMapper capitalTotalMapper;
    @Autowired
    private CapitalDailyMapper capitalDailyMapper;
    @Autowired
    private ConsumerCountTotalMapper consumerCountTotalMapper;
    @Autowired
    private ConsumerCountDailyMapper consumerCountDailyMapper;
    @Autowired
    private ConsumerTeamMapper consumerTeamMapper;
    @Autowired
    private FeeTotalMapper feeTotalMapper;
    @Autowired
    private FeeDailyMapper feeDailyMapper;
    @Autowired
    private LockrepoDestroyTotalMapper lockrepoDestroyTotalMapper;
    @Autowired
    private LockrepoDestroyDailyMapper lockrepoDestroyDailyMapper;
    @Autowired
    private ProfitsTotalMapper profitsTotalMapper;
    @Autowired
    private ProfitsDailyMapper profitsDailyMapper;
    @Autowired
    private ConsumerCapitalAccountMapper consumerCapitalAccountMapper;
    @Autowired
    private ConsumerTranceDetailDAO consumerTranceDetailDAO;
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;
    @Autowired
    private SysParameterMapper sysParameterMapper;


    /**
     * 总资产总量的趋势
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<CapitalTotal>> capitalTotal(Date startDate, Date endDate) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");
        BaseResponse<List<CapitalTotal>> response = new BaseResponse<>();
        CapitalTotalExample example = new CapitalTotalExample();
        CapitalTotalExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        example.setOrderByClause("created_time asc");
        List<CapitalTotal> capitalTotals = capitalTotalMapper.selectByExample(example);
        response.setData(capitalTotals);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 日资产总量的趋势
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<CapitalDaily>> capitalDaily(Date startDate, Date endDate) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");

        BaseResponse<List<CapitalDaily>> response = new BaseResponse<>();
        CapitalDailyExample example = new CapitalDailyExample();
        CapitalDailyExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        example.setOrderByClause("created_time asc");
        List<CapitalDaily> capitalDailies = capitalDailyMapper.selectByExample(example);

        response.setData(capitalDailies);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 用户总览-总量趋势
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<ConsumerCountTotal>> consumerTotal(Date startDate, Date endDate) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");
        BaseResponse<List<ConsumerCountTotal>> response = new BaseResponse<>();
        ConsumerCountTotalExample example = new ConsumerCountTotalExample();
        ConsumerCountTotalExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        example.setOrderByClause("created_time asc");
        List<ConsumerCountTotal> consumerCountTotals = consumerCountTotalMapper.selectByExample(example);

        response.setData(consumerCountTotals);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 用户总览-日量趋势
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<ConsumerCountDaily>> consumerDaily(Date startDate, Date endDate) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");
        BaseResponse<List<ConsumerCountDaily>> response = new BaseResponse<>();
        ConsumerCountDailyExample example = new ConsumerCountDailyExample();
        ConsumerCountDailyExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        example.setOrderByClause("created_time asc");
        List<ConsumerCountDaily> consumerCountDailies = consumerCountDailyMapper.selectByExample(example);

        response.setData(consumerCountDailies);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 查询排行榜信息
     *
     * @return
     */
    public BaseResponse<List<ConsumerTeamVO>> rankingList() {
        BaseResponse<List<ConsumerTeamVO>> response = new BaseResponse<>();
        List<ConsumerTeamVO> consumerTeamVOS = consumerTeamMapper.selectTeamInfo();
        response.setData(consumerTeamVOS);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 总手续费趋势柱状图
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<FeeTotal>> feeTotal(Date startDate, Date endDate) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");
        BaseResponse<List<FeeTotal>> response = new BaseResponse<>();
        FeeTotalExample example = new FeeTotalExample();
        FeeTotalExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        example.setOrderByClause("created_time asc");
        List<FeeTotal> feeTotals = feeTotalMapper.selectByExample(example);
        response.setData(feeTotals);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 手续费增量趋势
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<FeeDaily>> feeDaily(Date startDate, Date endDate) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");

        BaseResponse<List<FeeDaily>> response = new BaseResponse<>();

        FeeDailyExample example = new FeeDailyExample();
        FeeDailyExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        example.setOrderByClause("created_time asc");
        List<FeeDaily> feeDailies = feeDailyMapper.selectByExample(example);

        response.setData(feeDailies);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 收益销账趋势
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<LockrepoDestroyTotal>> destroyTotal(Date startDate, Date endDate) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");

        BaseResponse<List<LockrepoDestroyTotal>> response = new BaseResponse<>();

        LockrepoDestroyTotalExample example = new LockrepoDestroyTotalExample();
        LockrepoDestroyTotalExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        example.setOrderByClause("created_time asc");
        List<LockrepoDestroyTotal> lockrepoDestroyTotalList = lockrepoDestroyTotalMapper.selectByExample(example);

        response.setData(lockrepoDestroyTotalList);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 日增销账趋势
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<LockrepoDestroyDaily>> destroyDaily(Date startDate, Date endDate) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");

        BaseResponse<List<LockrepoDestroyDaily>> response = new BaseResponse<>();

        LockrepoDestroyDailyExample example = new LockrepoDestroyDailyExample();
        LockrepoDestroyDailyExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        example.setOrderByClause("created_time asc");
        List<LockrepoDestroyDaily> lockrepoDestroyDailyList = lockrepoDestroyDailyMapper.selectByExample(example);

        response.setData(lockrepoDestroyDailyList);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;

    }

    /**
     * 今日统计-总量-饼状图
     *
     * @return
     */
    public BaseResponse<List<EchartsPieVO>> capitalTotalPie() {
        BaseResponse<List<EchartsPieVO>> response = new BaseResponse<>();
        List<EchartsPieVO> echartsPieVOS = new ArrayList<>();

//        List<CapitalTotal> capitalTotals = capitalTotalMapper.selectTodayCapitalTotal();
        CapitalTotal capitalTotal = consumerCapitalAccountMapper.selectTotalFunds();
        //当前资产资产用量
        if (capitalTotal != null) {
            EchartsPieVO echartsPieVO1 = new EchartsPieVO();
            echartsPieVO1.setName("消费资产");

            echartsPieVO1.setValue(capitalTotal.getFloatingFunds());
            echartsPieVOS.add(echartsPieVO1);

            EchartsPieVO echartsPieVO2 = new EchartsPieVO();
            echartsPieVO2.setName("锁仓资产");
            echartsPieVO2.setValue(capitalTotal.getLockrepoFunds());
            echartsPieVOS.add(echartsPieVO2);

            response.setData(echartsPieVOS);
        }
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 昨日资产饼状图
     *
     * @return
     */
    public BaseResponse<List<EchartsPieVO>> capitalDailyPie() {
        BaseResponse<List<EchartsPieVO>> response = new BaseResponse<>();
        List<EchartsPieVO> echartsPieVOS = new ArrayList<>();
        String yesterdayYMD = DateUtil.getYesterdayYMD();
        List<CapitalDaily> capitalDailies = capitalDailyMapper.selectTodayCapitalDaily(yesterdayYMD + "%");
        if (capitalDailies.size() != 0) {
            EchartsPieVO echartsPieVO1 = new EchartsPieVO();
            echartsPieVO1.setName("消费资产");

            echartsPieVO1.setValue(capitalDailies.get(0).getFloatingFunds());
            echartsPieVOS.add(echartsPieVO1);

            EchartsPieVO echartsPieVO2 = new EchartsPieVO();
            echartsPieVO2.setName("锁仓资产");
            echartsPieVO2.setValue(capitalDailies.get(0).getLockrepoFunds());
            echartsPieVOS.add(echartsPieVO2);

//            EchartsPieVO echartsPieVO3 = new EchartsPieVO();
//            echartsPieVO3.setName("收益资产");
//            echartsPieVO3.setValue(capitalDailies.get(0).getProfitsFunds());
//            echartsPieVOS.add(echartsPieVO3);

            response.setData(echartsPieVOS);
        }
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 收益总量-今日统计
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<ProfitsTotal>> profitsTotal(Date startDate, Date endDate) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");
        BaseResponse<List<ProfitsTotal>> response = new BaseResponse<>();
        ProfitsTotalExample example = new ProfitsTotalExample();
        ProfitsTotalExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        example.setOrderByClause("created_time asc");
        List<ProfitsTotal> profitsTotals = profitsTotalMapper.selectByExample(example);
        response.setData(profitsTotals);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 收益情况-饼状图
     *
     * @return
     */
    public BaseResponse<List<EchartsPieVO>> profitsTotalPie() {
        BaseResponse<List<EchartsPieVO>> response = new BaseResponse<>();
        List<EchartsPieVO> echartsPieVOS = new ArrayList<>();
        String yesterdayYMD = DateUtil.getYesterdayYMD();
        List<ProfitsTotal> profitsTotals = profitsTotalMapper.selectTodayProfitsTotal(yesterdayYMD + "%");
        if (profitsTotals.size() != 0) {
            EchartsPieVO echartsPieVO1 = new EchartsPieVO();
            echartsPieVO1.setName("锁仓收益");
            echartsPieVO1.setValue(profitsTotals.get(0).getProfitsLockrepo());
            echartsPieVOS.add(echartsPieVO1);

//            EchartsPieVO echartsPieVO2 = new EchartsPieVO();
//            echartsPieVO2.setName("推荐收益");
//            echartsPieVO2.setValue(profitsTotals.get(0).getProfitsReferee());
//            echartsPieVOS.add(echartsPieVO2);

            EchartsPieVO echartsPieVO3 = new EchartsPieVO();
            echartsPieVO3.setName("分享算力");
            echartsPieVO3.setValue(profitsTotals.get(0).getProfitsTeam());
            echartsPieVOS.add(echartsPieVO3);

            response.setData(echartsPieVOS);
        }
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 收益情况-每日增量趋势
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<ProfitsDaily>> profitsDaily(Date startDate, Date endDate) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");
        BaseResponse<List<ProfitsDaily>> response = new BaseResponse<>();
        ProfitsDailyExample example = new ProfitsDailyExample();
        ProfitsDailyExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        example.setOrderByClause("created_time asc");
        List<ProfitsDaily> profitsDailies = profitsDailyMapper.selectByExample(example);
        response.setData(profitsDailies);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 收益情况-每日增量饼状图
     *
     * @return
     */
    public BaseResponse<List<EchartsPieVO>> profitsDailyPie() {
        BaseResponse<List<EchartsPieVO>> response = new BaseResponse<>();
        List<EchartsPieVO> echartsPieVOS = new ArrayList<>();
        String yesterdayYMD = DateUtil.getYesterdayYMD();
        List<ProfitsDaily> profitsDailies = profitsDailyMapper.selectTodayProfitsDaily(yesterdayYMD + "%");

        if (profitsDailies.size() != 0) {
            EchartsPieVO echartsPieVO1 = new EchartsPieVO();
            echartsPieVO1.setName("锁仓收益");
            echartsPieVO1.setValue(profitsDailies.get(0).getProfitsLockrepo());
            echartsPieVOS.add(echartsPieVO1);
//
//            EchartsPieVO echartsPieVO2 = new EchartsPieVO();
//            echartsPieVO2.setName("推荐收益");
//            echartsPieVO2.setValue(profitsDailies.get(0).getProfitsReferee());
//            echartsPieVOS.add(echartsPieVO2);

            EchartsPieVO echartsPieVO3 = new EchartsPieVO();
            echartsPieVO3.setName("分享算力");
            echartsPieVO3.setValue(profitsDailies.get(0).getProfitsTeam());
            echartsPieVOS.add(echartsPieVO3);

            response.setData(echartsPieVOS);
        }
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    public List<ConsumerTranceDetail> exportXls() {
        BaseResponse<List<ConsumerTranceDetail>> baseResponse = new BaseResponse<>();
        BuguQuery<ConsumerTranceDetail> query = consumerTranceDetailDAO.query();
        List<ConsumerTranceDetail> results = query.results();
        return results;
    }

    public BaseResponse coinCurrent() {
        BaseResponse response = new BaseResponse<>();
        //现价默认为1
        BigDecimal coinPriceBig = BigDecimal.ONE;
        //比率值增量默认为0.01
        BigDecimal coinIncr=new BigDecimal(0.01);

        //获取现价
        SysParameterExample sysParameterExample=new SysParameterExample();
        sysParameterExample.createCriteria().andParamCodeEqualTo("current_price");
        List<SysParameter> sysParameters = sysParameterMapper.selectByExample(sysParameterExample);
        if(sysParameters.size()<=0||sysParameters.get(0)==null){
            //现价为空，获取比率值基数
            SysParameterExample coinPrice = new SysParameterExample();
            coinPrice.createCriteria().andParamCodeEqualTo("coin_price");
            List<SysParameter> coinPriceSysParams = sysParameterMapper.selectByExample(coinPrice);
            if (coinPriceSysParams.size() > 0 && coinPriceSysParams.get(0) != null) {
                coinPriceBig = coinPriceSysParams.get(0).getParamValue();
            }
        }else {
            coinPriceBig=sysParameters.get(0).getParamValue();
        }
//        // 获取比例值增量
//        SysParameterExample coinExample=new SysParameterExample();
//        coinExample.createCriteria().andParamCodeEqualTo("coin_incr");
//        List<SysParameter> coinIncrParams = sysParameterMapper.selectByExample(coinExample);
//        if(coinIncrParams.size()>0&&coinIncrParams.get(0)!=null){
//            coinIncr=coinIncrParams.get(0).getParamValue();
//        }
        BigDecimal currentPrice = coinPriceBig.setScale(2, BigDecimal.ROUND_HALF_UP);
        Map map = new HashMap<>();
        map.put("currentPrice", currentPrice);
        response.setData(map);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;

    }

    /**
     * 查询内部账地址
     *
     * @return
     */
    public String getInterAcoountAddress() {
        SysDictionaryExample dictionaryExample = new SysDictionaryExample();
        dictionaryExample.createCriteria().andDicCodeEqualTo("inter_Account");
        List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(dictionaryExample);
        if (sysDictionaries.size() == 0 || sysDictionaries.get(0) == null) {
            throw new RuntimeException("内部账地址为空！！！");
        }
        SysDictionary sysDictionary = sysDictionaries.get(0);
        return sysDictionary.getDicValue();
    }
}
