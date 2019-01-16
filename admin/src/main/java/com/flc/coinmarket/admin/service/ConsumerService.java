package com.flc.coinmarket.admin.service;

import com.bugull.mongo.BuguQuery;
import com.flc.coinmarket.admin.volidate.DateValidate;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.core.constant.Constants;
import com.flc.coinmarket.core.exception.MyException;
import com.flc.coinmarket.core.util.*;
import com.flc.coinmarket.dao.business.CommonUpdateInter;
import com.flc.coinmarket.dao.mongo.dao.ConsumerTranceDetailDAO;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;
import com.flc.coinmarket.dao.mysql.mapper.consumer.*;
import com.flc.coinmarket.dao.mysql.mapper.statistics.ConsumerCapitalDailyMapper;
import com.flc.coinmarket.dao.mysql.mapper.statistics.ConsumerCapitalTotalMapper;
import com.flc.coinmarket.dao.mysql.mapper.statistics.ConsumerProfitsTotalMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysDictionaryMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysParameterMapper;
import com.flc.coinmarket.dao.mysql.model.consumer.*;
import com.flc.coinmarket.dao.mysql.model.statistics.*;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionary;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionaryExample;
import com.flc.coinmarket.dao.mysql.model.system.SysParameter;
import com.flc.coinmarket.dao.mysql.model.system.SysParameterExample;
import com.flc.coinmarket.dao.pojo.ConsumerParam;
import com.flc.coinmarket.dao.pojo.ConsumerQuery;
import com.flc.coinmarket.dao.pojo.TransactionDetailQuery;
import com.flc.coinmarket.dao.vo.BatchTransferVO;
import com.flc.coinmarket.dao.vo.ConsumerInfoVO;
import com.flc.coinmarket.dao.vo.ConsumerTeamVO;
import com.flc.coinmarket.dao.vo.EchartsPieVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private CommonUpdateInter commonUpdateInter;

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
        Date registryTimeFrom = consumerQuery.getRegistryTimeFrom();
        if (registryTimeFrom != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(registryTimeFrom);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            consumerQuery.setRegistryTimeFrom(c.getTime());
        }

        Date registryTimeTo = consumerQuery.getRegistryTimeTo();
        if (registryTimeTo != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(registryTimeTo);
            c1.add(Calendar.DATE, 1);
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            consumerQuery.setRegistryTimeTo(c1.getTime());
        }


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
            addConsumer(consumerParam);
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

    /**
     * 模糊查询手机号
     *
     * @param phoneNo
     * @return
     */
    public BaseResponse<List<String>> phoneNoAssociate(String phoneNo) {
        BaseResponse<List<String>> response = new BaseResponse<>();

        List<String> phones = consumerMapper.phoneNoAssociate("%" + phoneNo + "%");

        response.setData(phones);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 查询转出方团队信息
     *
     * @param witchTeam
     * @return
     */
    public BaseResponse<List<String>> transferTeamInfo(String transferPhone, Integer witchTeam) {
        BaseResponse response = new BaseResponse();
        List<String> phones = new ArrayList<>();

        ConsumerExample consumerExample = new ConsumerExample();
        consumerExample.createCriteria().andPhoneNoEqualTo(transferPhone);
        List<Consumer> consumers = consumerMapper.selectByExample(consumerExample);
        Consumer consumer = consumers.get(0);
        if (consumer == null) {//用户不存在
            response.setResponseCode(ResponseCode.CONSUMER_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.CONSUMER_NOT_HAVE.getMessage());
            return response;
        }

        //查询左团队团队信息
        List<String> leftConsumerPhoneNo = new ArrayList<>();
        String codeLeft = consumer.getLeftCode();
        if (codeLeft != null) {
            ConsumerExample example = new ConsumerExample();
            example.createCriteria().andTeamPosCodeEqualTo(codeLeft);
            List<ConsumerWithBLOBs> consumersLeft = consumerMapper.selectByExampleWithBLOBs(example);
            if (consumersLeft.size() == 0 || consumersLeft.get(0) == null) {//id错误
                response.setResponseCode(ResponseCode.CONSUMER_TEAM_NOT_HAVE_MEMBER.getCode());
                response.setResponseMsg(ResponseCode.CONSUMER_TEAM_NOT_HAVE_MEMBER.getMessage());
                return response;
            }
            //查询左节点的全路径
            String fullPathLeft = consumersLeft.get(0).getFullPath();
            leftConsumerPhoneNo = consumerMapper.selectConsumerMemberPhoneNo(fullPathLeft + "%");
        }

        //查询右团队团队信息
        List<String> rightConsumerPhoneNo = new ArrayList<>();
        String codeRight = consumer.getRightCode();
        if (codeRight != null) {
            ConsumerExample example1 = new ConsumerExample();
            example1.createCriteria().andTeamPosCodeEqualTo(codeRight);
            List<ConsumerWithBLOBs> consumersRight = consumerMapper.selectByExampleWithBLOBs(example1);
            if (consumersRight.size() == 0 || consumersRight.get(0) == null) {//id错误
                response.setResponseCode(ResponseCode.CONSUMER_TEAM_NOT_HAVE_MEMBER.getCode());
                response.setResponseMsg(ResponseCode.CONSUMER_TEAM_NOT_HAVE_MEMBER.getMessage());
                return response;
            }
            //查询右节点的全路径
            String fullPathRight = consumersRight.get(0).getFullPath();
            rightConsumerPhoneNo = consumerMapper.selectConsumerMemberPhoneNo(fullPathRight + "%");
        }

        if (witchTeam == 0) {//左团队
            phones = leftConsumerPhoneNo;
        } else if (witchTeam == 1) {//右团队
            phones = rightConsumerPhoneNo;
        } else {//全团队
            phones.addAll(leftConsumerPhoneNo);
            phones.addAll(rightConsumerPhoneNo);
        }

        response.setData(phones);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 根据手机号查询余额
     *
     * @param phoneNo
     * @return
     */
    public BaseResponse queryBalance(String phoneNo) {
        BaseResponse response = new BaseResponse();

        //根据手机号查询余额
        BigDecimal balance = consumerMapper.queryBalanceByPhoneNo(phoneNo);
        if (balance == null) {
            balance = BigDecimal.ZERO.setScale(8);
        }

        Map map = new HashMap();
        map.put("balance", balance);
        response.setData(map);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 批量转出
     *
     * @param batchTransferVO
     * @return
     */
    @Transactional
    public BaseResponse batchTransfer(BatchTransferVO batchTransferVO) throws MyException {
        BaseResponse response = new BaseResponse();
        //本次交易流水号
        String tranNo = UUID.randomUUID().toString().replace("-", "").toLowerCase();

        //查询内部账地址
        SysDictionaryExample dictionaryExample = new SysDictionaryExample();
        dictionaryExample.createCriteria().andDicCodeEqualTo("inter_Account");
        List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(dictionaryExample);
        if (sysDictionaries.size() == 0 || sysDictionaries.get(0) == null) {
            throw new MyException("内部账地址不存在！！！");
        }
        SysDictionary sysDictionary = sysDictionaries.get(0);
        String inAddressValue = sysDictionary.getDicValue();

        //计算手续费比例值
        SysParameterExample sysParameterExample = new SysParameterExample();
        sysParameterExample.createCriteria().andParamCodeEqualTo("transaction_fee_rate");
        List<SysParameter> sysParameters = sysParameterMapper.selectByExample(sysParameterExample);
        if (sysParameters.size() == 0 || sysParameters.get(0) == null) {
            response.setResponseCode(ResponseCode.FEE_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.FEE_NOT_HAVE.getMessage());
            return response;
        }
        BigDecimal paramValue = sysParameters.get(0).getParamValue();//转账手续费百分比
        BigDecimal tranFee = batchTransferVO.getTransferAmt().multiply(paramValue);//每笔手续费
        BigDecimal tranFeeTotal = batchTransferVO.getTransferAmt().multiply(paramValue)
                .multiply(new BigDecimal(batchTransferVO.getTransfereePhones().size()));//总手续费

        BigDecimal payAccountBalance = BigDecimal.ZERO;

        //付款人相关信息
        Consumer payConsumer = new Consumer();
        ConsumerCapitalAccount payAccount = new ConsumerCapitalAccount();
        if (batchTransferVO.getTransferor().equals(Constants.Transferor.SYSTEM.getValue())) {//系统地址
            payAccount.setFloatingAddress(inAddressValue);
        } else {
            ConsumerExample consumerExample = new ConsumerExample();
            consumerExample.createCriteria().andPhoneNoEqualTo(batchTransferVO.getTransferorPhone()).andDeleteFlagEqualTo("0");
            List<Consumer> consumers = consumerMapper.selectByExample(consumerExample);
            if (consumers.size() == 0 || consumers.get(0) == null) {
                throw new MyException("手机号【" + batchTransferVO.getTransferorPhone() + "】对应的用户不存在！！！");
            }
            payConsumer = consumers.get(0);

            ConsumerCapitalAccountExample accountExample = new ConsumerCapitalAccountExample();
            accountExample.createCriteria().andConsumerIdEqualTo(payConsumer.getId());
            List<ConsumerCapitalAccount> accounts = consumerCapitalAccountMapper.selectByExample(accountExample);
            if (accounts.size() == 0 || accounts.get(0) == null) {
                response.setResponseCode(ResponseCode.ACCONUT_NOT_HAVE.getCode());
                response.setResponseMsg(ResponseCode.ACCONUT_NOT_HAVE.getMessage());
                return response;
            }
            payAccount = accounts.get(0);
            payAccountBalance = payAccount.getFloatingFunds();
        }
        //为个人时，更新账户表流动资产
        if (batchTransferVO.getTransferor().equals(Constants.Transferor.PERSONAL.getValue())) {//个人
            BigDecimal payFloatingFunds = payAccount.getFloatingFunds().subtract(batchTransferVO.getTransferAmt().multiply(new BigDecimal(batchTransferVO.getTransfereePhones().size()))).
                    subtract(tranFeeTotal);
            if(payFloatingFunds.compareTo(BigDecimal.ZERO)<0){
                throw new MyException("转出方余额不足！！！");
            }
            payAccount.setFloatingFunds(payFloatingFunds);
            consumerCapitalAccountMapper.updateByPrimaryKey(payAccount);
        }

        for (String phone : batchTransferVO.getTransfereePhones()) {
            //收款人相关信息
            ConsumerExample consumerExample = new ConsumerExample();
            consumerExample.createCriteria().andPhoneNoEqualTo(phone).andDeleteFlagEqualTo("0");
            List<Consumer> consumers = consumerMapper.selectByExample(consumerExample);
            if (consumers.size() == 0 || consumers.get(0) == null) {
                throw new MyException("手机号" + phone + "对应的收款人不存在！！！");
            }
            Consumer incomeConsumer = consumers.get(0);

            ConsumerCapitalAccountExample accountExample = new ConsumerCapitalAccountExample();
            accountExample.createCriteria().andConsumerIdEqualTo(incomeConsumer.getId());
            List<ConsumerCapitalAccount> accounts = consumerCapitalAccountMapper.selectByExample(accountExample);
            if (accounts.size() == 0 || accounts.get(0) == null) {
                response.setResponseCode(ResponseCode.ACCONUT_NOT_HAVE.getCode());
                response.setResponseMsg(ResponseCode.ACCONUT_NOT_HAVE.getMessage());
                return response;
            }
            ConsumerCapitalAccount incomeAccount = accounts.get(0);


            //1.付 交易转出
            BigDecimal balance;
            if (batchTransferVO.getTransferor().equals(Constants.Transferor.PERSONAL.getValue())) {//个人
                payAccountBalance = payAccountBalance.subtract(batchTransferVO.getTransferAmt());
                balance = payAccountBalance;
            } else {//系统
                //更新内部账
                balance = commonUpdateInter.updateInterBalance(batchTransferVO.getTransferAmt().negate());
            }

            String floatingAddress= incomeAccount.getFloatingAddress();//流动地址
            if(batchTransferVO.getAddressFlag().equals(Constants.AddressFlag.LOCKREPOADDRESS.getValue())){//锁仓地址
                incomeAccount.setFloatingAddress(incomeAccount.getLockrepoAddress());
            }
            ConsumerTranceDetail payDetail = createTranceDetail(tranNo, batchTransferVO.getTransferAmt(),
                    Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.TRANS_OUT.getValue(),
                    balance, payAccount, incomeAccount, payConsumer, incomeConsumer);
            consumerTranceDetailDAO.insert(payDetail);

            //2.收 交易转入
            ConsumerTranceDetail incomeDetail = createTranceDetail(tranNo, batchTransferVO.getTransferAmt(),
                    Constants.INCOME.VALUE, Constants.INCOME.SourceType.TRANS_IN.getValue(),
                    incomeAccount.getFloatingFunds().add(batchTransferVO.getTransferAmt()),
                    incomeAccount, payAccount, incomeConsumer, payConsumer);
            consumerTranceDetailDAO.insert(incomeDetail);

            //更改接收账户的流动资金或锁仓资金
            if(batchTransferVO.getAddressFlag().equals(Constants.AddressFlag.LOCKREPOADDRESS.getValue())){
                BigDecimal floatFunds = incomeAccount.getLockrepoFunds().add(batchTransferVO.getTransferAmt());
                incomeAccount.setLockrepoFunds(floatFunds);
            }else {
                BigDecimal floatFunds = incomeAccount.getFloatingFunds().add(batchTransferVO.getTransferAmt());
                incomeAccount.setFloatingFunds(floatFunds);
            }
            incomeAccount.setFloatingAddress(floatingAddress);
            consumerCapitalAccountMapper.updateByPrimaryKeySelective(incomeAccount);

            //3.手续费
            if (batchTransferVO.getTransferor().trim().equals(Constants.Transferor.PERSONAL.getValue())) {//个人
                //内部账信息
                Consumer interConsumer = new Consumer();
                ConsumerCapitalAccount interAccount = new ConsumerCapitalAccount();
                interAccount.setFloatingAddress(inAddressValue);

                payAccountBalance = payAccountBalance.subtract(tranFee);
                //付款方付手续费
                ConsumerTranceDetail payFeeDetail = createTranceDetail(tranNo, tranFee, Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.TRANS_FEE.getValue()
                        , payAccountBalance, payAccount, interAccount
                        , payConsumer, interConsumer);
                consumerTranceDetailDAO.insert(payFeeDetail);

                //更新内部账余额
                BigDecimal interBalance = commonUpdateInter.updateInterBalance(tranFee);

                //收  内部账手续费收
                ConsumerTranceDetail incomeFeeDetail = createTranceDetail(tranNo, tranFee.negate(),
                        Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.TRANS_FEE.getValue(),
                        interBalance, interAccount, payAccount, interConsumer, payConsumer);
                consumerTranceDetailDAO.insert(incomeFeeDetail);
            }
        }

        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }



    /**
     * 插入交易记录
     *
     * @param tranNo
     * @param funds
     * @param transType
     * @param sourceType
     * @param balance
     * @param fromAccount
     * @param toAccount
     * @param fromConsumer
     * @param toConsumer
     * @return
     */
    private ConsumerTranceDetail createTranceDetail(String tranNo, BigDecimal funds, String transType, String sourceType, BigDecimal balance,
                                                    ConsumerCapitalAccount fromAccount, ConsumerCapitalAccount toAccount, Consumer fromConsumer, Consumer toConsumer) {
        ConsumerTranceDetail tranceDetail = new ConsumerTranceDetail();
        tranceDetail.setAccountId(fromAccount == null ? null : fromAccount.getId());
        tranceDetail.setTranceNo(tranNo);
        tranceDetail.setFunds(funds);
        tranceDetail.setTranceType(transType);
        tranceDetail.setSourceType(sourceType);
        tranceDetail.setCreatedTime(new Date());
        tranceDetail.setUpdatedTime(new Date());
        tranceDetail.setTransferAddressFrom(fromAccount == null ? null : fromAccount.getFloatingAddress());
        tranceDetail.setTransferAddressTo(toAccount == null ? null : toAccount.getFloatingAddress());
        tranceDetail.setTransferConsumer(toConsumer == null ? null : toConsumer.getId());
        tranceDetail.setBalance(balance);
        tranceDetail.setPhoneNoFrom(fromConsumer == null ? null : fromConsumer.getPhoneNo());
        tranceDetail.setPhoneNoTo(toConsumer == null ? null : toConsumer.getPhoneNo());
        tranceDetail.setNickNameFrom(fromConsumer == null ? null : fromConsumer.getNickName());
        tranceDetail.setNickNameTo(toConsumer == null ? null : toConsumer.getNickName());
        return tranceDetail;
    }

    /**
     * 回滚账户数据（待删）
     * @throws ParseException
     */
    public void deleteScheduleDataOfToday() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = sdf.parse("2019-01-04 18:00:00");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse1 = sdf1.parse("2019-01-04 19:00:00");

        BuguQuery<ConsumerTranceDetail> query = consumerTranceDetailDAO.query();
        query.greaterThanEquals("createdTime", parse)
                .lessThan("createdTime", parse1)
                .notEquals("accountId", null)
                .in("sourceType",
                        Constants.INCOME.SourceType.TEAM_PROFITS.getValue(), Constants.INCOME.SourceType.LOCK_REPO_PROFITS.getValue()).results();
        List<ConsumerTranceDetail> details = query.results();
        if (details == null || details.isEmpty()) {
            return;
        }
        Map<Integer, ConsumerCapitalAccount> accounts = new HashMap<>();
        ConsumerCapitalAccount account;
        for (ConsumerTranceDetail detail : details) {
            account = accounts.containsKey(detail.getAccountId()) ? accounts.get(detail.getAccountId()) : consumerCapitalAccountMapper.selectByPrimaryKey(detail.getAccountId());
            if (account == null) continue;
            switch (detail.getSourceType()) {
                // 团队收益
                case "3":
                    account.setAccumulatedProfits(account.getAccumulatedProfits().subtract(detail.getFunds()));
                    account.setFloatingFunds(account.getFloatingFunds().subtract(detail.getFunds()));
                    break;
                // 锁仓收益
                case "2":
                    account.setAccumulatedProfits(account.getAccumulatedProfits().subtract(detail.getFunds()));
                    account.setFloatingFunds(account.getFloatingFunds().subtract(detail.getFunds()));
                    break;
                default:
                    break;
            }
            accounts.put(detail.getAccountId(), account);
        }

        for (ConsumerCapitalAccount a : accounts.values()) {
            consumerCapitalAccountMapper.updateByPrimaryKey(a);
        }
    }

    /**
     * 导入表格
     *
     * @param file
     * @return
     */
    @Transactional
    public BaseResponse importExcel(String fileName, MultipartFile file) throws  IOException,MyException {
        BaseResponse response = new BaseResponse();

        List<ConsumerParam> consumerList = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException("上传文件格式不正确！！！");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
            throw new MyException("表格无数据！！！");
        }
        ConsumerParam consumerParam;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            consumerParam = new ConsumerParam();
            String phone = getCellValue(row.getCell(0));
            if (phone == null || phone.isEmpty()) {
                throw new MyException("导入失败(第" + (r + 1) + "行,电话号未填写)");
            }
            String password = getCellValue(row.getCell(1));
            if (password == null || password.isEmpty()) {
                throw new MyException("导入失败(第" + (r + 1) + "行,密码未填写)");
            }
            String nickName = getCellValue(row.getCell(2));
            ;
            String email = getCellValue(row.getCell(3));
            ;
            String stringFunds = getCellValue(row.getCell(4));
            ;
            BigDecimal funds = (stringFunds == null || stringFunds.isEmpty() ? BigDecimal.ZERO : new BigDecimal(stringFunds));//消费资金
            String refereePhone = getCellValue(row.getCell(5)); //推荐人手机号
            String codeDirection = getCellValue(row.getCell(6)); //推荐码方向

            consumerParam.setPhoneNo(phone);
            consumerParam.setPassWord(password);
            consumerParam.setEmail(email);
            consumerParam.setNickName(nickName);
            consumerParam.setFloatingFunds(funds);
            consumerParam.setRefereePhone(refereePhone);
            consumerParam.setCodeDirection(codeDirection);

            consumerList.add(consumerParam);
        }

        for (ConsumerParam consumer : consumerList) {
            //调用新增用户方法
            addConsumer(consumer);
        }

        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 新增用户
     *
     * @param consumerParam
     * @return
     * @throws Exception
     */
    private  void addConsumer(ConsumerParam consumerParam) throws RuntimeException {
        if (StringUtils.isBlank(consumerParam.getPhoneNo())) {
           throw new RuntimeException("不能有手机号为空的数据！！！");
        }

        if(!RegularCheck.isPhoneNo(consumerParam.getPhoneNo())){
            throw new RuntimeException("手机号【"+consumerParam.getPhoneNo()+"】格式错误！！！");
        }

        if(consumerParam.getFloatingFunds().compareTo(BigDecimal.ZERO)<0){
                throw new RuntimeException("手机号为【"+consumerParam.getPhoneNo()+"】的消费资产为负数！！！");
        }

        if (StringUtils.isBlank(consumerParam.getPassWord())) {
           throw new RuntimeException("手机号为【"+consumerParam.getPhoneNo()+"】的密码不能为空！！！");
        }
        if(!RegularCheck.isNumAndChar(consumerParam.getPassWord())){
            throw new RuntimeException("请输入6-10位的数字或字母！！！");
        }

        String encodePwd = PasswordUtil.hashPassword(consumerParam.getPassWord());
        consumerParam.setPassWord(encodePwd);
        //判断手机号是否已被注册
        ConsumerExample example = new ConsumerExample();
        example.createCriteria().andAccountEqualTo(consumerParam.getPhoneNo()).andDeleteFlagEqualTo("0");
        List<ConsumerWithBLOBs> consumers = consumerMapper.selectByExampleWithBLOBs(example);
        if (consumers.size() != 0) {
            throw new RuntimeException("手机号【"+consumerParam.getPhoneNo()+"】已被注册！！！");
        }
        if ((StringUtils.isBlank(consumerParam.getRefereePhone()) && StringUtils.isNotBlank(consumerParam.getCodeDirection()))
                || (StringUtils.isNotBlank(consumerParam.getRefereePhone()) && StringUtils.isBlank(consumerParam.getCodeDirection()))) {
            throw new RuntimeException("请检查手机号为【" + consumerParam.getPhoneNo() + "】的推荐人手机号和推荐码方向！！！");
        }
        //如果推荐人手机号为空，或者左右点为空，则消费资金为零,不为零报错
        if (StringUtils.isNotBlank(consumerParam.getRefereePhone()) || StringUtils.isNotBlank(consumerParam.getCodeDirection())) {
            if (consumerParam.getFloatingFunds()==null||consumerParam.getFloatingFunds().compareTo(BigDecimal.ZERO) != 0) {
                throw new RuntimeException("请修改手机号为【" + consumerParam.getPhoneNo() + "】用户的消费资产为0.00！！！");
            }
            consumerParam.setFloatingFunds(BigDecimal.ZERO);
        }
        //登记客户记录
        ConsumerWithBLOBs consumerWithBLOBs = new ConsumerWithBLOBs();
        Integer tpc = getTeamPostCode();
        if (tpc == null) {
            throw new RuntimeException("生成用户节点码错误！！！");
        }
        String teamPosCode = ConvertUtil.convert10To62(tpc, 4);
        consumerWithBLOBs.setTeamPosCode(teamPosCode);

        String fullPath = "";//全路径
        String pathDirection = "";//路径方向
        String pathDirectionReferee = "";//节点码的路径方向
        String fullPathReferee = "";//节点码的全路径

        if (StringUtils.isBlank(consumerParam.getRefereePhone())&&StringUtils.isBlank(consumerParam.getCodeDirection())) {//无推荐码，顶级节点
            consumerParam.setFullPath(teamPosCode);
            consumerParam.setPathDirection("0");
            consumerParam.setIsleaf("0");
        } else {

            //获取推荐码
            ConsumerExample refereeConsumerExample = new ConsumerExample();
            refereeConsumerExample.createCriteria().andPhoneNoEqualTo(consumerParam.getRefereePhone()).andDeleteFlagEqualTo("0");
            List<Consumer> refereeConsumers = consumerMapper.selectByExample(refereeConsumerExample);
            if (refereeConsumers.size() == 0 || refereeConsumers.get(0) == null) {
                throw new RuntimeException("手机号为【" + consumerParam.getRefereePhone() + "】的推荐人不存在！！！");
            }
            Integer refereeConsumerId = refereeConsumers.get(0).getId();
            ConsumerTwoDimensionCodeExample codeExample = new ConsumerTwoDimensionCodeExample();
            codeExample.createCriteria().andConsumerIdEqualTo(refereeConsumerId);
            List<ConsumerTwoDimensionCode> refereeTwoCode = consumerTwoDimensionCodeMapper.selectByExample(codeExample);
            if (refereeTwoCode.size() == 0 || refereeTwoCode.get(0) == null) {
                throw new RuntimeException("手机号为【" + consumerParam.getRefereePhone() + "】的推荐人的二维码信息不存在！！！");
            }
            if (consumerParam.getCodeDirection().equals("0")) {//左
                String leftDimesionCode = refereeTwoCode.get(0).getLeftDimesionCode();
                consumerParam.setRefereeCode(leftDimesionCode);
            } else {
                String rightDimensionCode = refereeTwoCode.get(0).getRightDimensionCode();
                consumerParam.setRefereeCode(rightDimensionCode);
            }
            ConsumerTwoDimensionCodeExample exampleCode = null;
            if (consumerParam.getRefereeCode().startsWith(Constants.DimensionCode.LEFT.getValue())) {//左码
                exampleCode = new ConsumerTwoDimensionCodeExample();
                exampleCode.createCriteria().andLeftDimesionCodeEqualTo(consumerParam.getRefereeCode());
            } else if (consumerParam.getRefereeCode().startsWith(Constants.DimensionCode.RIGHT.getValue())) {
                exampleCode = new ConsumerTwoDimensionCodeExample();
                exampleCode.createCriteria().andRightDimensionCodeEqualTo(consumerParam.getRefereeCode());
            } else {
                throw new RuntimeException("推荐码错误");
            }
            List<ConsumerTwoDimensionCode> consumerTwoDimensionCodes = consumerTwoDimensionCodeMapper.selectByExample(exampleCode);
            if (consumerTwoDimensionCodes.size() == 0) {//二维码错误
                throw new RuntimeException("推荐码错误");
            }
            //获得推荐人的节点码
            ConsumerWithBLOBs consumerReferee = consumerMapper.selectByPrimaryKey(consumerTwoDimensionCodes.get(0).getConsumerId());
            if (consumerReferee == null) {
                throw new RuntimeException("获取推荐人信息错误");
            }
            consumerWithBLOBs.setRefereeCode(consumerParam.getRefereeCode());
            consumerWithBLOBs.setReferee(consumerReferee.getId());
            String leafCode = null;//推荐人节点码，扫左码为左节点码，扫右码为右节点码
            if (consumerParam.getRefereeCode().startsWith(Constants.DimensionCode.LEFT.getValue())) {
                leafCode = consumerReferee.getLeftCode();
            } else if (consumerParam.getRefereeCode().startsWith(Constants.DimensionCode.RIGHT.getValue())) {
                leafCode = consumerReferee.getRightCode();
            }
            pathDirectionReferee = ((ConsumerWithBLOBs) consumerReferee).getPathDirection();
            fullPathReferee = ((ConsumerWithBLOBs) consumerReferee).getFullPath();

            if (leafCode == null) { //无节点码
                if (consumerParam.getRefereeCode().startsWith(Constants.DimensionCode.LEFT.getValue())) {
                    pathDirection = pathDirectionReferee + Constants.DimensionCode.LEFT.getValue();
                } else {
                    pathDirection = pathDirectionReferee + Constants.DimensionCode.RIGHT.getValue();
                }
                //路径
                fullPath = fullPathReferee + teamPosCode;
                //设置当前节点为叶子节点
                consumerWithBLOBs.setIsleaf("1");
                //更新推荐人的左节点码为当前节点码
                if (consumerParam.getRefereeCode().startsWith(Constants.DimensionCode.LEFT.getValue())) {
                    consumerReferee.setLeftCode(teamPosCode);
                } else {
                    consumerReferee.setRightCode(teamPosCode);
                }
                consumerReferee.setIsleaf("0");
                consumerMapper.updateByPrimaryKeyWithBLOBs(consumerReferee);

            } else {
                //有节点，找到推荐人的节点的方向路径和全路径
                ConsumerExample consumerExample = new ConsumerExample();
                consumerExample.createCriteria().andTeamPosCodeEqualTo(leafCode);
                //推荐人的节点
                List<ConsumerWithBLOBs> refereeConsumer = consumerMapper.selectByExampleWithBLOBs(consumerExample);
                if (refereeConsumer.size() == 0) {
                    throw new RuntimeException("推荐人");
                }
                //根据推荐人路径方向找到推荐人子节点，
                // 即包含推荐人子节点的路径方向，截取完推荐人路径不包含R且叶子节点为1的人的全路径和路径方向
                ConsumerWithBLOBs consumerLeaf = consumerMapper.queryLeafCustomerByLeftCode(refereeConsumer.get(0).getPathDirection().length() + 1,
                        refereeConsumer.get(0).getPathDirection() + "%", refereeConsumer.get(0).getFullPath() + "%");

                if (consumerLeaf == null) {//节点即为叶子节点
                    consumerLeaf = refereeConsumer.get(0);
                }
                //更新叶子节点人的是否叶子节点为0
                consumerLeaf.setIsleaf("0");
                consumerLeaf.setLeftCode(teamPosCode);
                consumerMapper.updateByPrimaryKeyWithBLOBs(consumerLeaf);
                //设置当前节点为叶子节点
                consumerWithBLOBs.setIsleaf("1");
                fullPath = consumerLeaf.getFullPath() + teamPosCode;
                pathDirection = consumerLeaf.getPathDirection() + Constants.DimensionCode.LEFT.getValue();
            }

            consumerWithBLOBs.setFullPath(fullPath);
            consumerWithBLOBs.setPathDirection(pathDirection);
        }
        consumerWithBLOBs.setAccount(consumerParam.getPhoneNo());
        consumerWithBLOBs.setPhoneNo(consumerParam.getPhoneNo());
        String encodePasswords = PasswordUtil.hashPassword(consumerParam.getPassWord());
        consumerWithBLOBs.setPassWord(encodePasswords);
        consumerWithBLOBs.setCreateTime(new Date());
        consumerWithBLOBs.setDeleteFlag("0");
        consumerMapper.insertSelective(consumerWithBLOBs);

        //记录客户二维码
        ConsumerTwoDimensionCodeWithBLOBs consumerTwoDimensionCodeWithBLOBs = new ConsumerTwoDimensionCodeWithBLOBs();
        consumerTwoDimensionCodeWithBLOBs.setConsumerId(consumerWithBLOBs.getId());
        consumerTwoDimensionCodeWithBLOBs.setLeftDimesionCode(Constants.DimensionCode.LEFT.getValue() + RandomNumberUtil.getRandomValue(8));
        consumerTwoDimensionCodeWithBLOBs.setRightDimensionCode(Constants.DimensionCode.RIGHT.getValue() + RandomNumberUtil.getRandomValue(8));
        String transferCode = RandomNumberUtil.getRandomValue(40);
        consumerTwoDimensionCodeWithBLOBs.setTransferDimensionCode("0x" + transferCode);
//        consumerTwoDimensionCodeWithBLOBs.setLeftDimension();
//        consumerTwoDimensionCodeWithBLOBs.setRightDimension();
//        consumerTwoDimensionCodeWithBLOBs.setTrandferDimesion();
        consumerTwoDimensionCodeMapper.insertSelective(consumerTwoDimensionCodeWithBLOBs);

        //记录客户资产账户
        ConsumerCapitalAccount consumerCapitalAccount = new ConsumerCapitalAccount();
        consumerCapitalAccount.setConsumerId(consumerWithBLOBs.getId());
        String floatingAddress = "0x" + transferCode;
        consumerCapitalAccount.setFloatingAddress(floatingAddress);
        consumerCapitalAccount.setLockrepoAddress("0x" + RandomNumberUtil.getRandomValue(40));
        consumerCapitalAccount.setLockrepoFunds(BigDecimal.ZERO);
        consumerCapitalAccount.setProfitsFunds(BigDecimal.ZERO);
        consumerCapitalAccount.setAccumulatedProfits(BigDecimal.ZERO);
        consumerCapitalAccount.setProfitsAddress("0x" + RandomNumberUtil.getRandomValue(40));
        consumerCapitalAccount.setTeamPosCode(teamPosCode);
        consumerCapitalAccount.setReleaseFlag("0");
        consumerCapitalAccount.setReinvestFlag("0");
        consumerCapitalAccount.setProfitsToday(BigDecimal.ZERO);
        consumerCapitalAccount.setFloatingFunds(consumerParam.getFloatingFunds() == null ? BigDecimal.ZERO : consumerParam.getFloatingFunds());
        consumerCapitalAccountMapper.insertSelective(consumerCapitalAccount);

        //记录用户设置表
        ConsumerSettings consumerSettings = new ConsumerSettings();
        consumerSettings.setConsumerId(consumerWithBLOBs.getId());
        consumerSettings.setAutoTransfer("0");//否
        if (StringUtils.isNotBlank(consumerParam.getNickName())) {
            consumerSettings.setNickName(consumerParam.getNickName());
        } else {
            consumerSettings.setNickName(consumerParam.getPhoneNo());
        }
        consumerSettings.setHeadPortrait("/images/head.jpg");
        consumerSettingsMapper.insertSelective(consumerSettings);

        if (StringUtils.isBlank(consumerParam.getRefereePhone()) && StringUtils.isBlank(consumerParam.getCodeDirection())
                && consumerParam.getFloatingFunds().compareTo(BigDecimal.ZERO) > 0) {
            //付 内部账
            //查询内部账地址
            SysDictionaryExample sysDictionaryExample = new SysDictionaryExample();
            sysDictionaryExample.createCriteria().andDicCodeEqualTo("inter_Account");
            List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(sysDictionaryExample);
            if (sysDictionaries.size() == 0 || sysDictionaries.get(0) == null) {
                throw new RuntimeException("内部账地址不存在！！！");
            }
            String inAddressValue = sysDictionaries.get(0).getDicValue();

            String tranNo = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            //内部账账户信息
            ConsumerCapitalAccount payAccount = new ConsumerCapitalAccount();
            payAccount.setFloatingAddress(inAddressValue);
            //新用户账户信息
            ConsumerCapitalAccount incomeAccount = new ConsumerCapitalAccount();
            incomeAccount.setFloatingAddress(floatingAddress);
            //内部账
            Consumer payConusmer = new Consumer();
            //新用户信息
            Consumer incomeConusmer = new Consumer();
            incomeConusmer.setId(consumerWithBLOBs.getId());
            incomeConusmer.setPhoneNo(consumerParam.getPhoneNo());
            incomeConusmer.setNickName(consumerParam.getNickName() == null ? consumerParam.getPhoneNo() : consumerParam.getNickName());

            //更新内部账户
            BigDecimal balance = commonUpdateInter.updateInterBalance(consumerParam.getFloatingFunds().negate());
            ConsumerTranceDetail  payDetail= createTranceDetail(tranNo, consumerParam.getFloatingFunds(), Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.TRANS_OUT.getValue(),
                    balance, payAccount, incomeAccount, payConusmer, incomeConusmer);
            consumerTranceDetailDAO.insert(payDetail);

            //收 消费资产
            ConsumerTranceDetail incomeDetail = createTranceDetail(tranNo, consumerParam.getFloatingFunds(), Constants.INCOME.VALUE, Constants.INCOME.SourceType.TRANS_IN.getValue(), consumerParam.getFloatingFunds()
                    , incomeAccount, payAccount, incomeConusmer, payConusmer);
            consumerTranceDetailDAO.insert(incomeDetail);
        }
    }


    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                //short s = cell.getCellStyle().getDataFormat();
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    // 验证short值
                    if (cell.getCellStyle().getDataFormat() == 14) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    } else if (cell.getCellStyle().getDataFormat() == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (cell.getCellStyle().getDataFormat() == 22) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    } else {
                        throw new RuntimeException("日期格式错误!!!");
                    }
                    Date date = cell.getDateCellValue();
                    cellValue = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 0) {//处理数值格式
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = null;
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}
