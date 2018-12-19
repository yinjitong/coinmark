package com.flc.coinmarket.admin.service;

import com.bugull.mongo.BuguQuery;
import com.flc.coinmarket.admin.volidate.DateValidate;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.core.constant.Constants;
import com.flc.coinmarket.core.util.ConvertUtil;
import com.flc.coinmarket.core.util.DateUtil;
import com.flc.coinmarket.core.util.PasswordUtil;
import com.flc.coinmarket.core.util.RandomNumberUtil;
import com.flc.coinmarket.dao.mongo.dao.ConsumerTranceDetailDAO;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;
import com.flc.coinmarket.dao.mysql.mapper.consumer.*;
import com.flc.coinmarket.dao.mysql.mapper.statistics.ConsumerCapitalDailyMapper;
import com.flc.coinmarket.dao.mysql.mapper.statistics.ConsumerCapitalTotalMapper;
import com.flc.coinmarket.dao.mysql.mapper.statistics.ConsumerProfitsTotalMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysDictionaryMapper;
import com.flc.coinmarket.dao.mysql.model.consumer.*;
import com.flc.coinmarket.dao.mysql.model.statistics.*;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionary;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionaryExample;
import com.flc.coinmarket.dao.pojo.ConsumerParam;
import com.flc.coinmarket.dao.pojo.ConsumerQuery;
import com.flc.coinmarket.dao.pojo.TransactionDetailQuery;
import com.flc.coinmarket.dao.vo.ConsumerInfoVO;
import com.flc.coinmarket.dao.vo.ConsumerTeamVO;
import com.flc.coinmarket.dao.vo.EchartsPieVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;
    @Autowired
    private ConsumerCapitalDailyMapper consumerCapitalDailyMapper;
    @Autowired
    private ConsumerCapitalTotalMapper consumerCapitalTotalMapper;
    @Autowired
    private ConsumerProfitsTotalMapper consumerProfitsTotalMapper;
    @Autowired
    private ConsumerTranceDetailDAO consumerTranceDetailDAO;
    @Autowired
    private ConsumerCapitalAccountMapper consumerCapitalAccountMapper;
    @Autowired
    private ConsumerTeamMapper consumerTeamMapper;
    @Autowired
    private ConsumerLoginHistoryMapper consumerLoginHistoryMapper;
    @Autowired
    private ConsumerSettingsMapper consumerSettingsMapper;
    @Autowired
    private ConsumerTwoDimensionCodeMapper consumerTwoDimensionCodeMapper;
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    /**
     * 查询所有用户信息
     *
     * @param consumerQuery
     * @return
     */
    public BaseResponse<PageInfo<ConsumerInfoVO>> consumers(ConsumerQuery consumerQuery) {
        BaseResponse<PageInfo<ConsumerInfoVO>> response = new BaseResponse<>();
        if (consumerQuery.getPageNo() == null) {
            consumerQuery.setPageNo(1);
        }
        if (consumerQuery.getPageSize() == null) {
            consumerQuery.setPageSize(10);
        }
        PageHelper.startPage(consumerQuery.getPageNo(), consumerQuery.getPageSize());

        List<ConsumerInfoVO> consumerInfoVOS = consumerMapper.selectConsumerInfo(consumerQuery);
        PageInfo<ConsumerInfoVO> appsPageInfo = new PageInfo<>(consumerInfoVOS);
        response.setData(appsPageInfo);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 用户资产趋势情况查看
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<ConsumerCapitalDaily>> capitalTotal(Integer id, Date startDate, Date endDate) {
        BaseResponse<List<ConsumerCapitalDaily>> response = new BaseResponse<>();
        //求输入时间段的柱状图
        ConsumerCapitalDailyExample example = new ConsumerCapitalDailyExample();
        example.createCriteria().andCreateTimeBetween(startDate, endDate);
        List<ConsumerCapitalDaily> consumerCapitalDailies = consumerCapitalDailyMapper.selectByExample(example);
        response.setData(consumerCapitalDailies);

        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());

        return response;
    }

    private synchronized Integer getTeamPostCode() {
        Integer postCodeInt;
        //查找最大id的team_post_code
        String postCode = consumerMapper.selectMaxTeamPostCode();
        //如果为null设置为0;
        if (postCode == null) {
            postCodeInt = 0;
        } else {
            String spostCode = ConvertUtil.convertBase62ToDecimal(postCode + "");
            postCodeInt = Integer.parseInt(spostCode) + 1;
        }
        return postCodeInt;
    }

    /**
     * 新增或修改用户信息
     *
     * @param consumerParam
     * @return
     */
    @Transactional
    public BaseResponse<ConsumerWithBLOBs> add(ConsumerParam consumerParam) {
        BaseResponse<ConsumerWithBLOBs> response = new BaseResponse<>();
        if (StringUtils.isBlank(consumerParam.getPhoneNo())) {
            response.setResponseCode(ResponseCode.PHONE_CANT_BE_NULL.getCode());
            response.setResponseMsg(ResponseCode.PHONE_CANT_BE_NULL.getMessage());
            return response;
        }

        if (consumerParam.getId() != null) {//修改
            ConsumerWithBLOBs consumer = consumerMapper.selectByPrimaryKey(consumerParam.getId());
            if (StringUtils.isNotBlank(consumerParam.getPassWord())) {
                String encodePasswords = PasswordUtil.hashPassword(consumerParam.getPassWord());
                consumer.setPassWord(encodePasswords);//密码
            }
            consumer.setUpdateTime(new Date());
            consumer.setEmail(consumerParam.getEmail());
            consumer.setPhoneNo(consumerParam.getPhoneNo());
            consumerMapper.updateByPrimaryKeySelective(consumer);

            //更改昵称
            ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
            consumerSettingsExample.createCriteria().andConsumerIdEqualTo(consumerParam.getId());
            List<ConsumerSettings> consumerSettings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
            if (consumerSettings.size() != 0) {
                ConsumerSettings consumerSettings1 = consumerSettings.get(0);
                consumerSettings1.setNickName(consumerParam.getNickName());
                consumerSettingsMapper.updateByPrimaryKey(consumerSettings1);
            }
            response.setData(consumer);
        } else {

            if (StringUtils.isBlank(consumerParam.getPassWord())) {
                response.setResponseCode(ResponseCode.PWD_CANT_NULL.getCode());
                response.setResponseMsg(ResponseCode.PWD_CANT_NULL.getMessage());
                return response;
            }

            Integer tpc = getTeamPostCode();
            String teamPosCode = ConvertUtil.convert10To62(tpc, 4);//TODO 节点编码
            consumerParam.setTeamPosCode(teamPosCode);
            consumerParam.setFullPath(teamPosCode);
            consumerParam.setPathDirection("0");
            consumerParam.setIsleaf("0");
            consumerParam.setAccount(consumerParam.getPhoneNo());
            String encodePasswords = PasswordUtil.hashPassword(consumerParam.getPassWord());
            consumerParam.setPassWord(encodePasswords);//密码
            consumerParam.setCreateTime(new Date());
            consumerParam.setDeleteFlag("0");//默认有效
            consumerMapper.insertSelective(consumerParam);

            //记录用户设置表
            ConsumerSettings consumerSettings = new ConsumerSettings();
            consumerSettings.setConsumerId(consumerParam.getId());
            consumerSettings.setAutoTransfer("0");//否
            if (StringUtils.isNotBlank(consumerParam.getNickName())) {
                consumerSettings.setNickName(consumerParam.getNickName());
            }
            consumerSettings.setHeadPortrait("/images/head.jpg");
            consumerSettingsMapper.insertSelective(consumerSettings);


            //TODO 记录客户二维码
            ConsumerTwoDimensionCodeWithBLOBs consumerTwoDimensionCodeWithBLOBs = new ConsumerTwoDimensionCodeWithBLOBs();
            consumerTwoDimensionCodeWithBLOBs.setConsumerId(consumerParam.getId());
            consumerTwoDimensionCodeWithBLOBs.setLeftDimesionCode("L" + RandomNumberUtil.getRandomValue(8));
            consumerTwoDimensionCodeWithBLOBs.setRightDimensionCode("R" + RandomNumberUtil.getRandomValue(8));
            String transferCode = RandomNumberUtil.getRandomValue(40);
            consumerTwoDimensionCodeWithBLOBs.setTransferDimensionCode("0x" + transferCode);

//        consumerTwoDimensionCodeWithBLOBs.setLeftDimension();
//        consumerTwoDimensionCodeWithBLOBs.setRightDimension();
//        consumerTwoDimensionCodeWithBLOBs.setTrandferDimesion();
            consumerTwoDimensionCodeMapper.insertSelective(consumerTwoDimensionCodeWithBLOBs);

            //记录客户资产账户
            ConsumerCapitalAccount consumerCapitalAccount = new ConsumerCapitalAccount();
            consumerCapitalAccount.setConsumerId(consumerParam.getId());
            String floatingAddress="0x" + transferCode;
            consumerCapitalAccount.setFloatingAddress(floatingAddress);
            consumerCapitalAccount.setFloatingFunds(BigDecimal.ZERO);
            consumerCapitalAccount.setLockrepoAddress("0x" + RandomNumberUtil.getRandomValue(40));
            consumerCapitalAccount.setLockrepoFunds(BigDecimal.ZERO);
            consumerCapitalAccount.setProfitsFunds(BigDecimal.ZERO);
            consumerCapitalAccount.setAccumulatedProfits(BigDecimal.ZERO);
            consumerCapitalAccount.setProfitsAddress("0x" + RandomNumberUtil.getRandomValue(40));
            consumerCapitalAccount.setTeamPosCode(teamPosCode);
            consumerCapitalAccount.setProfitsToday(BigDecimal.ZERO);
            consumerCapitalAccount.setReinvestFlag("0");//不自动复投
            consumerCapitalAccount.setReleaseFlag("0");//不释放
            consumerCapitalAccount.setFloatingFunds(consumerParam.getFloatingFunds() == null ? BigDecimal.ZERO : consumerParam.getFloatingFunds());
            consumerCapitalAccountMapper.insertSelective(consumerCapitalAccount);

            //记账
            ConsumerTranceDetail detail = new ConsumerTranceDetail();
            //付 内部账
            //查询内部账地址
            SysDictionaryExample sysDictionaryExample=new SysDictionaryExample();
            sysDictionaryExample.createCriteria().andDicCodeEqualTo("inter_Account");
            List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(sysDictionaryExample);
            if(sysDictionaries.size()==0||sysDictionaries.get(0)==null){
                throw new RuntimeException("内部账地址不存在！！！");
            }
            String inAddressValue = sysDictionaries.get(0).getDicValue();
            //查询内部账号余额
            BuguQuery<ConsumerTranceDetail> query = consumerTranceDetailDAO.query();
            List<ConsumerTranceDetail> results = query.in("transferAddressFrom", inAddressValue).sortDesc("createdTime").results();


            String tranNo= UUID.randomUUID().toString().replace("-", "").toLowerCase();

            detail.setTranceNo(tranNo);
            detail.setTransferAddressFrom(inAddressValue);
            detail.setTransferAddressTo(floatingAddress);
            detail.setFunds(consumerParam.getFloatingFunds()==null?BigDecimal.ZERO:consumerParam.getFloatingFunds());
//            detail.setAccountId();
            detail.setCreatedTime(new Date());
            detail.setSourceType(Constants.EXPENSE.SourceType.TRANS_OUT.getValue());//
            detail.setTranceType(Constants.EXPENSE.VALUE);
            detail.setTransferConsumer(consumerParam.getId());
            detail.setBalance(results.size()==0||results.get(0)==null ?
                    BigDecimal.ZERO.add(consumerParam.getFloatingFunds().negate()) : results.get(0).getBalance().add(consumerParam.getFloatingFunds().negate()));//余额
//            detail.setNickNameFrom();
            detail.setNickNameTo(consumerParam.getPhoneNo());
//            detail.setPhoneNoFrom();
            detail.setPhoneNoTo(consumerParam.getPhoneNo());
            consumerTranceDetailDAO.insert(detail);

            //收 消费资产
            ConsumerTranceDetail detailIn = new ConsumerTranceDetail();
            detailIn.setTranceNo(tranNo);
            detailIn.setTransferAddressFrom(floatingAddress);
            detailIn.setTransferAddressTo(inAddressValue);
            detailIn.setFunds(consumerParam.getFloatingFunds()==null?BigDecimal.ZERO:consumerParam.getFloatingFunds());
            detailIn.setAccountId(consumerCapitalAccount.getId());
            detailIn.setCreatedTime(new Date());
            detailIn.setSourceType(Constants.INCOME.SourceType.TRANS_IN.getValue());
            detailIn.setTranceType(Constants.INCOME.VALUE);
//          detailIn.setTransferConsumer();
//          detailIn.setPhoneNoTo();
            detailIn.setPhoneNoFrom(consumerParam.getPhoneNo());
//          detailIn.setNickNameTo();
            detailIn.setNickNameFrom(consumerParam.getPhoneNo());
            detailIn.setBalance(consumerParam.getFloatingFunds()==null?BigDecimal.ZERO:consumerParam.getFloatingFunds());
            consumerTranceDetailDAO.insert(detailIn);

            response.setData(consumerParam);
        }

        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    public BaseResponse<Consumer> delete(Integer id) throws RuntimeException {
        BaseResponse<Consumer> response = new BaseResponse<>();
        ConsumerWithBLOBs consumerWithBLOBs = consumerMapper.selectByPrimaryKey(id);
        //更新为已删除
        consumerWithBLOBs.setDeleteFlag("1");//已删除
        consumerMapper.updateByPrimaryKey(consumerWithBLOBs);

        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    public BaseResponse<Consumer> deleteBatch(List<Integer> ids) {
        BaseResponse<Consumer> response = new BaseResponse<>();
        for (Integer id : ids) {
            ConsumerWithBLOBs consumerWithBLOBs = consumerMapper.selectByPrimaryKey(id);
            //更新为已删除
            consumerWithBLOBs.setDeleteFlag("1");//已删除
            consumerMapper.updateByPrimaryKey(consumerWithBLOBs);
        }
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 客戶-资产状态
     *
     * @param id
     * @return
     */
    public BaseResponse<List<EchartsPieVO>> capitalTotalPie(Integer id) {
        BaseResponse<List<EchartsPieVO>> response = new BaseResponse<>();
        if (id == null) {
            response.setResponseCode(ResponseCode.CONSUMER_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.CONSUMER_NOT_HAVE.getMessage());
            return response;
        }
        List<EchartsPieVO> echartsPieVOS = new ArrayList<>();
//        ConsumerCapitalTotal consumerCapitalTotal = consumerCapitalTotalMapper.selectTodayConsumerCapitalTotal(id);
        ConsumerCapitalAccountExample accountExample = new ConsumerCapitalAccountExample();
        accountExample.createCriteria().andConsumerIdEqualTo(id);
        List<ConsumerCapitalAccount> accounts = consumerCapitalAccountMapper.selectByExample(accountExample);
        if (accounts.size() == 0) {
            response.setResponseCode(ResponseCode.ACCONUT_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.ACCONUT_NOT_HAVE.getMessage());
            return response;
        }
        ConsumerCapitalAccount account = accounts.get(0);
        if (account != null) {
            EchartsPieVO echartsPieVO1 = new EchartsPieVO();
            echartsPieVO1.setName("消费资产");

            echartsPieVO1.setValue(account.getFloatingFunds());
            echartsPieVOS.add(echartsPieVO1);

            EchartsPieVO echartsPieVO2 = new EchartsPieVO();
            echartsPieVO2.setName("锁仓资产");
            echartsPieVO2.setValue(account.getLockrepoFunds());
            echartsPieVOS.add(echartsPieVO2);

            response.setData(echartsPieVOS);
        }
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 客戶-资产状态-趋势
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<ConsumerCapitalTotal>> consumerCapitalTotal(Integer id, Date startDate, Date endDate) {
        BaseResponse<List<ConsumerCapitalTotal>> response = new BaseResponse<>();
        if (id == null) {
            response.setResponseCode(ResponseCode.CONSUMER_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.CONSUMER_NOT_HAVE.getMessage());
            return response;
        }

        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");

        ConsumerCapitalTotalExample example = new ConsumerCapitalTotalExample();
        ConsumerCapitalTotalExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        criteria.andConsumerIdEqualTo(id);
        example.setOrderByClause("created_time asc");
        List<ConsumerCapitalTotal> consumerCapitalTotals = consumerCapitalTotalMapper.selectByExample(example);
        response.setData(consumerCapitalTotals);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 用户收益趋势
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<ConsumerProfitsTotal>> profitsTotal(Integer id, Date startDate, Date endDate) {
        BaseResponse<List<ConsumerProfitsTotal>> response = new BaseResponse<>();
        if (id == null) {
            response.setResponseCode(ResponseCode.CONSUMER_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.CONSUMER_NOT_HAVE.getMessage());
            return response;
        }

        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");

        ConsumerProfitsTotalExample example = new ConsumerProfitsTotalExample();
        ConsumerProfitsTotalExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeLessThanOrEqualTo(endDate);
        criteria.andCreatedTimeGreaterThanOrEqualTo(startDate);
        criteria.andConsumerIdEqualTo(id);
        example.setOrderByClause("created_time asc");
        List<ConsumerProfitsTotal> consumerProfitsTotals = consumerProfitsTotalMapper.selectByExample(example);
        response.setData(consumerProfitsTotals);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 用户收益
     *
     * @param id
     * @return
     */
    public BaseResponse<List<EchartsPieVO>> profitsTotalPie(Integer id) {
        BaseResponse<List<EchartsPieVO>> response = new BaseResponse<>();
        if (id == null) {
            response.setResponseCode(ResponseCode.CONSUMER_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.CONSUMER_NOT_HAVE.getMessage());
            return response;
        }
        List<EchartsPieVO> echartsPieVOS = new ArrayList<>();
        ConsumerProfitsTotal consumerProfitsTotal = consumerProfitsTotalMapper.selectConsumerProfitsTotal(id);
        if (consumerProfitsTotal != null) {
            EchartsPieVO echartsPieVO1 = new EchartsPieVO();
            echartsPieVO1.setName("锁仓收益");

            echartsPieVO1.setValue(consumerProfitsTotal.getProfitsLockrepo());
            echartsPieVOS.add(echartsPieVO1);

//            EchartsPieVO echartsPieVO2 = new EchartsPieVO();
//            echartsPieVO2.setName("推荐收益");
//            echartsPieVO2.setValue(consumerProfitsTotal.getProfitsReferee());
//            echartsPieVOS.add(echartsPieVO2);

            EchartsPieVO echartsPieVO3 = new EchartsPieVO();
            echartsPieVO3.setName("分享算力");
            echartsPieVO3.setValue(consumerProfitsTotal.getProfitsTeam());
            echartsPieVOS.add(echartsPieVO3);

            response.setData(echartsPieVOS);
        }
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 交易明细
     *
     * @param query
     * @return
     */
    public BaseResponse<PageInfo<ConsumerTranceDetail>> transactionDetail(TransactionDetailQuery query) {
        BaseResponse<PageInfo<ConsumerTranceDetail>> response = new BaseResponse<>();
        if (query == null || query.getConsumerId() == null) {
            response.setResponseCode(ResponseCode.CONSUMER_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.CONSUMER_NOT_HAVE.getMessage());
            return response;
        }
        //根据用户id，获取账户id
        ConsumerCapitalAccountExample example = new ConsumerCapitalAccountExample();
        example.createCriteria().andConsumerIdEqualTo(query.getConsumerId());
        List<ConsumerCapitalAccount> consumerCapitalAccounts = consumerCapitalAccountMapper.selectByExample(example);
        if (consumerCapitalAccounts.size() == 0) {
            response.setResponseCode(ResponseCode.ACCONUT_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.ACCONUT_NOT_HAVE.getMessage());
            return response;
        }
        Integer accountId = consumerCapitalAccounts.get(0).getId();//账号id

        if (query.getTransType() == null) {
            query.setTransType(Constants.INCOME.VALUE);
        }
        if (query.getPageNo() == null) {
            query.setPageNo(1);
        }
        if (query.getPageSize() == null) {
            query.setPageSize(10);
        }
        BuguQuery<ConsumerTranceDetail> buguQuery = consumerTranceDetailDAO.query();

        buguQuery.is("tranceType", query.getTransType());
        buguQuery.is("accountId", accountId);

        //数据内容
        List<ConsumerTranceDetail> results = buguQuery.results();
        //总条数
        Integer total = buguQuery.results().size();

        buguQuery.pageSize(query.getPageSize()).pageNumber(query.getPageNo());
        List<ConsumerTranceDetail> results1 = buguQuery.results();

        //总页数
        Integer totalPage = ((Double) Math.ceil(total * 1.0 / query.getPageSize())).intValue();

        PageInfo<ConsumerTranceDetail> pageInfo = new PageInfo<>();
        pageInfo.setList(results1);
        pageInfo.setTotal(total);
        pageInfo.setPageNum(query.getPageNo());
        pageInfo.setPages(totalPage);
        pageInfo.setPageSize(query.getPageSize());
        response.setData(pageInfo);


        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 团队总览
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    public BaseResponse<List<ConsumerTeamVO>> team(Integer id, Date startDate, Date endDate) {
        BaseResponse response = new BaseResponse();
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(startDate, endDate);
        startDate = stringDateHashMap.get("startDate");
        endDate = stringDateHashMap.get("endDate");
        List<ConsumerTeamVO> consumerTeamVOS = consumerTeamMapper.selectConsumerTeamTrend(id, startDate, endDate);
        response.setData(consumerTeamVOS);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    public BaseResponse<ConsumerInfoVO> consumer(Integer id) {
        ConsumerInfoVO consumerInfoVO = new ConsumerInfoVO();
        BaseResponse<ConsumerInfoVO> response = new BaseResponse();
        ConsumerWithBLOBs consumerWithBLOBs = consumerMapper.selectByPrimaryKey(id);
        consumerInfoVO.setCreateTime(consumerWithBLOBs.getCreateTime());
        consumerInfoVO.setEmail(consumerWithBLOBs.getEmail());
        consumerInfoVO.setPhoneNo(consumerWithBLOBs.getPhoneNo());
        consumerInfoVO.setPassWord(consumerWithBLOBs.getPassWord());

        Date lastLoginTime = consumerLoginHistoryMapper.selectLastLoginTime(id);
        if (lastLoginTime != null) {
            consumerInfoVO.setLastLoginTime(lastLoginTime);
        }

        ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
        consumerSettingsExample.createCriteria().andConsumerIdEqualTo(id);
        List<ConsumerSettings> consumerSettings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
        if (consumerSettings.size() != 0) {
            consumerInfoVO.setNickName(consumerSettings.get(0).getNickName());
        }

        response.setData(consumerInfoVO);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 返回左/右团队人员明细
     *
     * @param id
     * @param witchteam
     * @return
     */
    public BaseResponse<List<ConsumerInfoVO>> teamMember(Integer id, Integer witchteam) {
        BaseResponse<List<ConsumerInfoVO>> response = new BaseResponse<>();
        ConsumerWithBLOBs consumerWithBLOBs = consumerMapper.selectByPrimaryKey(id);
        if (consumerWithBLOBs == null) {//用户不存在
            response.setResponseCode(ResponseCode.CONSUMER_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.CONSUMER_NOT_HAVE.getMessage());
            return response;
        }
        List<ConsumerInfoVO> consumerInfoVOS = new ArrayList<>();
        String code = null;
        if (witchteam == 0) {//左团队
            //左码
            code = consumerWithBLOBs.getLeftCode();
        } else {//右团队
            //右码
            code = consumerWithBLOBs.getRightCode();
        }
        if (code == null) {
            response.setData(consumerInfoVOS);
            response.setResponseCode(ResponseCode.OK.getCode());
            response.setResponseMsg(ResponseCode.OK.getMessage());
            return response;
        }
        //左节点的全路径
        ConsumerExample example = new ConsumerExample();
        example.createCriteria().andTeamPosCodeEqualTo(code);
        List<ConsumerWithBLOBs> consumers = consumerMapper.selectByExampleWithBLOBs(example);
        if (consumers.size() == 0 || consumers.get(0) == null) {//id错误
            response.setResponseCode(ResponseCode.CONSUMER_TEAM_NOT_HAVE_MEMBER.getCode());
            response.setResponseMsg(ResponseCode.CONSUMER_TEAM_NOT_HAVE_MEMBER.getMessage());
            return response;
        }
        String fullPath = consumers.get(0).getFullPath();
        consumerInfoVOS = consumerMapper.selectConsumerMember(fullPath + "%");
        response.setData(consumerInfoVOS);

        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }
}
