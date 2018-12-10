package com.flc.coinmarket.app.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.flc.coinmarket.app.volidate.DateValidate;
import com.flc.coinmarket.core.constant.Constants;
import com.flc.coinmarket.core.util.*;
import com.flc.coinmarket.dao.mysql.mapper.consumer.*;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.dao.mysql.mapper.statistics.ConsumerProfitsDailyMapper;
import com.flc.coinmarket.dao.mysql.mapper.statistics.ConsumerProfitsTotalMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysDictionaryMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysParameterMapper;
import com.flc.coinmarket.dao.mysql.model.consumer.*;
import com.flc.coinmarket.dao.mysql.model.statistics.*;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionary;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionaryExample;
import com.flc.coinmarket.dao.mysql.model.system.SysParameter;
import com.flc.coinmarket.dao.pojo.*;
import com.flc.coinmarket.dao.vo.ConsumerAppVO;
import com.flc.coinmarket.dao.vo.ConsumerWalletVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;
    @Autowired
    private ConsumerLoginHistoryMapper consumerLoginHistoryMapper;
    @Autowired
    private ConsumerTwoDimensionCodeMapper consumerTwoDimensionCodeMapper;
    @Autowired
    private ConsumerCapitalAccountMapper consumerCapitalAccountMapper;
    @Autowired
    private ConsumerTeamMapper consumerTeamMapper;
    @Autowired
    private ConsumerProfitsTotalMapper consumerProfitsTotalMapper;
    @Autowired
    private ConsumerProfitsDailyMapper consumerProfitsDailyMapper;
    @Autowired
    private ConsumerSettingsMapper consumerSettingsMapper;
    @Autowired
    private ConsumerCheckCodeMapper consumerCheckCodeMapper;
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;
    @Autowired
    private SysParameterMapper sysParameterMapper;

    private static Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    /**
     * 通过短信发送验证码给用户
     *
     * @param sendMessageQuery
     * @return
     */

    @Transactional
    public synchronized BaseResponse sendSms(SendMessageQuery sendMessageQuery) {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isBlank(sendMessageQuery.getPhoneNo())) {
            response.setResponseCode(ResponseCode.PHONE_CANTNULL.getCode());
            response.setResponseMsg(ResponseCode.PHONE_CANTNULL.getMessage());
            return response;
        }
        if (StringUtils.isBlank(sendMessageQuery.getSource())) {
            response.setResponseCode(ResponseCode.SMS_SOURCE_NOT_KNOW.getCode());
            response.setResponseMsg(ResponseCode.SMS_SOURCE_NOT_KNOW.getMessage());
            return response;
        }

        // 生成验证码
        String checkCode = RandomStringUtils.randomNumeric(4);
        // 把验证码通过短信发给用户
        boolean b = SmsUtils.sendSms(sendMessageQuery.getPhoneNo(), checkCode);

        if (b) {//发送成功
            // 将验证码插入数据库中
            ConsumerCheckCode consumerCheckCode = new ConsumerCheckCode();
            consumerCheckCode.setCheckCode(checkCode);
            consumerCheckCode.setCreatedTime(new Date());
            consumerCheckCode.setPhoneNo(sendMessageQuery.getPhoneNo());
            consumerCheckCode.setInvalidFlag("0");
            consumerCheckCode.setSource(sendMessageQuery.getSource());
            consumerCheckCodeMapper.insertSelective(consumerCheckCode);

            response.setResponseCode(ResponseCode.OK.getCode());
            response.setResponseMsg(ResponseCode.OK.getMessage());
        } else {
            response.setResponseCode(ResponseCode.MESSAGE_SEND_WRONG.getCode());
            response.setResponseMsg(ResponseCode.MESSAGE_SEND_WRONG.getMessage());
        }
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
     * 用户注册
     *
     * @param consumerRegist
     * @return
     */
    @Transactional
    public BaseResponse regist(ConsumerRegist consumerRegist) throws Exception {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isBlank(consumerRegist.getPhoneNo())) {
            response.setResponseCode(ResponseCode.PHONE_CANTNULL.getCode());
            response.setResponseMsg(ResponseCode.PHONE_CANTNULL.getMessage());
            return response;
        }
        if (StringUtils.isBlank(consumerRegist.getRefereeCode())) {
            response.setResponseCode(ResponseCode.REFEREE_CANTNULL.getCode());
            response.setResponseMsg(ResponseCode.REFEREE_CANTNULL.getMessage());
            return response;
        }

        if (StringUtils.isBlank(consumerRegist.getPassWord())) {
            response.setResponseCode(ResponseCode.PASSWORD_WRONG.getCode());
            response.setResponseMsg(ResponseCode.PASSWORD_WRONG.getMessage());
            return response;
        }
        //判断手机号是否已被注册
        ConsumerExample example = new ConsumerExample();
        example.createCriteria().andAccountEqualTo(consumerRegist.getPhoneNo()).andDeleteFlagNotLike("1");
        List<ConsumerWithBLOBs> consumers = consumerMapper.selectByExampleWithBLOBs(example);
        if (consumers.size() != 0) {
            response.setResponseCode(ResponseCode.PHONE_HAS_REGIST.getCode());
            response.setResponseMsg(ResponseCode.PHONE_HAS_REGIST.getMessage());
            return response;
        }
        //查询最新时间的验证码，时间不超过24小时，标志位有效
        ConsumerCheckCode consumerCheckCode = consumerCheckCodeMapper.selectCheckCode(consumerRegist.getPhoneNo(), "regist");
        if (consumerCheckCode == null) {
            response.setResponseCode(ResponseCode.CHCEK_CODE_WRONG.getCode());
            response.setResponseMsg(ResponseCode.CHCEK_CODE_WRONG.getMessage());
            return response;
        }
        String checkcodesession = consumerCheckCode.getCheckCode();
        //判断验证码是否输入正确
        if (StringUtils.isBlank(consumerRegist.getCheckcode()) || StringUtils.isBlank(
                checkcodesession) || !consumerRegist.getCheckcode().equals(checkcodesession)) {
            response.setResponseCode(ResponseCode.CHCEK_CODE_WRONG.getCode());
            response.setResponseMsg(ResponseCode.CHCEK_CODE_WRONG.getMessage());
            return response;
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Date time = c.getTime();
        Date createdTime = consumerCheckCode.getCreatedTime();
        //判断验证码是否已过期
        if (createdTime.before(time)) {
            response.setResponseCode(ResponseCode.CHECK_CODE_TIME_OUT.getCode());
            response.setResponseMsg(ResponseCode.CHECK_CODE_TIME_OUT.getMessage());
            return response;
        }
        //验证码更新为已使用
        consumerCheckCode.setInvalidFlag("1");
        consumerCheckCodeMapper.updateByPrimaryKey(consumerCheckCode);

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
        ConsumerTwoDimensionCodeExample exampleCode = null;
        if (consumerRegist.getRefereeCode().startsWith(Constants.DimensionCode.LEFT.getValue())) {//左码
            exampleCode = new ConsumerTwoDimensionCodeExample();
            exampleCode.createCriteria().andLeftDimesionCodeEqualTo(consumerRegist.getRefereeCode());
        } else if (consumerRegist.getRefereeCode().startsWith(Constants.DimensionCode.RIGHT.getValue())) {
            exampleCode = new ConsumerTwoDimensionCodeExample();
            exampleCode.createCriteria().andRightDimensionCodeEqualTo(consumerRegist.getRefereeCode());
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
        consumerWithBLOBs.setRefereeCode(consumerRegist.getRefereeCode());
        consumerWithBLOBs.setReferee(consumerReferee.getId());
        String leafCode = null;//推荐人节点码，扫左码为左节点码，扫右码为右节点码
        if (consumerRegist.getRefereeCode().startsWith(Constants.DimensionCode.LEFT.getValue())) {
            leafCode = consumerReferee.getLeftCode();
        } else if (consumerRegist.getRefereeCode().startsWith(Constants.DimensionCode.RIGHT.getValue())) {
            leafCode = consumerReferee.getRightCode();
        }
        pathDirectionReferee = ((ConsumerWithBLOBs) consumerReferee).getPathDirection();
        fullPathReferee = ((ConsumerWithBLOBs) consumerReferee).getFullPath();

        if (leafCode == null) { //无节点码
            if (consumerRegist.getRefereeCode().startsWith(Constants.DimensionCode.LEFT.getValue())) {
                pathDirection = pathDirectionReferee + Constants.DimensionCode.LEFT.getValue();
            } else {
                pathDirection = pathDirectionReferee + Constants.DimensionCode.RIGHT.getValue();
            }
            //路径
            fullPath = fullPathReferee + teamPosCode;
            //设置当前节点为叶子节点
            consumerWithBLOBs.setIsleaf("1");
            //更新推荐人的左节点码为当前节点码
            if (consumerRegist.getRefereeCode().startsWith(Constants.DimensionCode.LEFT.getValue())) {
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

            logger.info(consumerLeaf.getId()+"..."+consumerLeaf.getDeleteFlag());
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
        consumerWithBLOBs.setAccount(consumerRegist.getPhoneNo());
        consumerWithBLOBs.setPhoneNo(consumerRegist.getPhoneNo());
        String encodePasswords = PasswordUtil.hashPassword(consumerRegist.getPassWord());
        consumerWithBLOBs.setPassWord(encodePasswords);
        consumerWithBLOBs.setCreateTime(new Date());
        consumerWithBLOBs.setDeleteFlag("0");
        consumerMapper.insertSelective(consumerWithBLOBs);

        //TODO 记录客户二维码
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
        consumerCapitalAccount.setFloatingAddress("0x" + transferCode);
        consumerCapitalAccount.setFloatingFunds(BigDecimal.ZERO);
        consumerCapitalAccount.setLockrepoAddress("0x" + RandomNumberUtil.getRandomValue(40));
        consumerCapitalAccount.setLockrepoFunds(BigDecimal.ZERO);
        consumerCapitalAccount.setProfitsFunds(BigDecimal.ZERO);
        consumerCapitalAccount.setAccumulatedProfits(BigDecimal.ZERO);
        consumerCapitalAccount.setProfitsAddress("0x" + RandomNumberUtil.getRandomValue(40));
        consumerCapitalAccount.setTeamPosCode(teamPosCode);
        consumerCapitalAccount.setReleaseFlag("0");
        consumerCapitalAccount.setReinvestFlag("0");
        consumerCapitalAccount.setProfitsToday(BigDecimal.ZERO);
        consumerCapitalAccountMapper.insertSelective(consumerCapitalAccount);

        //记录用户设置表
        ConsumerSettings consumerSettings = new ConsumerSettings();
        consumerSettings.setConsumerId(consumerWithBLOBs.getId());
        consumerSettings.setAutoTransfer("0");//否
        consumerSettings.setNickName(consumerRegist.getPhoneNo());
        consumerSettings.setHeadPortrait("/images/head.jpg");
        consumerSettingsMapper.insertSelective(consumerSettings);

        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;

    }

    /**
     * 用户登录
     *
     * @param consumerLogin
     * @return
     */
    public BaseResponse login(ConsumerLogin consumerLogin) throws Exception {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isBlank(consumerLogin.getPhoneNo())) {
            response.setResponseCode(ResponseCode.PHONE_CANTNULL.getCode());
            response.setResponseMsg(ResponseCode.PHONE_CANTNULL.getMessage());
            return response;
        }

        ConsumerExample example = new ConsumerExample();
        example.createCriteria().andAccountEqualTo(consumerLogin.getPhoneNo());
        List<Consumer> consumers = consumerMapper.selectByExample(example);
        if (consumers.isEmpty()) {
            response.setResponseCode(ResponseCode.PLEASE_REGISTE.getCode());
            response.setResponseMsg(ResponseCode.PLEASE_REGISTE.getMessage());
            return response;
        }
        boolean flag = false;
        for (Consumer c : consumers) {
            if (c.getDeleteFlag().equals("0")) {
                flag = true;
            }
        }
        if (!flag) {
            response.setResponseCode(ResponseCode.PLEASE_REGISTE.getCode());
            response.setResponseMsg(ResponseCode.PLEASE_REGISTE.getMessage());
            return response;
        }

        if (StringUtils.isBlank(consumerLogin.getPassword())) {
            response.setResponseCode(ResponseCode.PASSWORD_WRONG.getCode());
            response.setResponseMsg(ResponseCode.PASSWORD_WRONG.getMessage());
            return response;
        }

        if (consumerLogin.getPassword().isEmpty() || !PasswordUtil.checkPassword(consumerLogin.getPassword(), consumers.get(0).getPassWord())) {
            response.setResponseCode(ResponseCode.PASSWORD_WRONG.getCode());
            response.setResponseMsg(ResponseCode.PASSWORD_WRONG.getMessage());
            return response;
        }

        //生成token
        String token = JWT.create().withAudience(consumers.get(0).getId() + "")
                .sign(Algorithm.HMAC256(consumers.get(0).getPassWord()));

        //记录客户登录历史
        ConsumerLoginHistory consumerLoginHistory = new ConsumerLoginHistory();
        consumerLoginHistory.setConsumerId(consumers.get(0).getId());
        consumerLoginHistory.setCreatedTime(new Date());
        consumerLoginHistory.setIpAddress(consumerLogin.getIpAddress());
        consumerLoginHistoryMapper.insertSelective(consumerLoginHistory);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        response.setData(map);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    public ConsumerWithBLOBs findConsumerById(String id) {
        ConsumerWithBLOBs consumerWithBLOBs = consumerMapper.selectByPrimaryKey(Integer.parseInt(id));
        return consumerWithBLOBs;
    }

    /**
     * 用户钱包首页信息查询
     *
     * @param id
     * @return
     */
    public BaseResponse<ConsumerWalletVO> overview(Integer id) {
        BaseResponse<ConsumerWalletVO> response = new BaseResponse();
        ConsumerWalletVO consumerWalletVO = new ConsumerWalletVO(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
                , BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                "", "", "0", BigDecimal.ZERO,BigDecimal.ZERO);
        //查询用户总资产,锁仓资产，流动资产，收益资产
        ConsumerCapitalAccountExample example = new ConsumerCapitalAccountExample();
        example.createCriteria().andConsumerIdEqualTo(id);
        List<ConsumerCapitalAccount> consumerCapitalAccounts = consumerCapitalAccountMapper.selectByExample(example);
        if (consumerCapitalAccounts.size() != 0 || consumerCapitalAccounts.get(0) != null) {
            BigDecimal totals = consumerCapitalAccounts.get(0).getFloatingFunds().add(consumerCapitalAccounts.get(0).getLockrepoFunds()).add(consumerCapitalAccounts.get(0).getProfitsFunds());
            consumerWalletVO.setTotalFunds(totals);
            consumerWalletVO.setFloatingFunds(consumerCapitalAccounts.get(0).getFloatingFunds());
            consumerWalletVO.setLockrepoFunds(consumerCapitalAccounts.get(0).getLockrepoFunds());
            consumerWalletVO.setProfitsFunds(consumerCapitalAccounts.get(0).getProfitsFunds());
            consumerWalletVO.setLockReleseFlag(consumerCapitalAccounts.get(0).getReleaseFlag());
        }
        String todayYMD= DateUtil.getTodayYMD();
        //查询用户锁仓收益，用户推荐收益，用户团队收益
        ConsumerProfitsTotal consumerProfitsTotal = consumerProfitsTotalMapper.selectConsumerProfitsTotal(id);
        if (consumerProfitsTotal != null) {
            consumerWalletVO.setProfitsReferee(consumerProfitsTotal.getProfitsReferee());
            consumerWalletVO.setProfitsTeam(consumerProfitsTotal.getProfitsTeam());
            consumerWalletVO.setProfitsLockrepo(consumerProfitsTotal.getProfitsLockrepo());
        }
        //查询用户昨日锁仓收益，用户昨日推荐收益，用户昨日团队收益
        ConsumerProfitsDaily consumerProfitsDaily = consumerProfitsDailyMapper.selectYestodayConsumerProfitsDaily(id,todayYMD+"%");
        if (consumerProfitsDaily != null) {
            consumerWalletVO.setProfitsLockrepoDaily(consumerProfitsDaily.getProfitsLockrepo());
            consumerWalletVO.setProfitsRefereeDaily(consumerProfitsDaily.getProfitsReferee());
            consumerWalletVO.setProfitsTeamDaily(consumerProfitsDaily.getProfitsTeam());
        }
        //团队一资产，团队二资产
        ConsumerTeam consumerTeam = consumerTeamMapper.selectConsumerTeam(id);
        if (consumerTeam!=null) {
            consumerWalletVO.setLeftTotalFunds(consumerTeam.getLeftLockrepoTotal());
            consumerWalletVO.setRightTotalFunds(consumerTeam.getRightLockrepoTotal());
        }
        //团队一推荐码，团队二推荐码
        ConsumerTwoDimensionCodeExample consumerTwoDimensionCodeExample = new ConsumerTwoDimensionCodeExample();
        consumerTwoDimensionCodeExample.createCriteria().andConsumerIdEqualTo(id);
        List<ConsumerTwoDimensionCode> consumerTwoDimensionCodes = consumerTwoDimensionCodeMapper.selectByExample(consumerTwoDimensionCodeExample);
        if (consumerTwoDimensionCodes.size() != 0) {
            consumerWalletVO.setLeftDimesionCode(consumerTwoDimensionCodes.get(0).getLeftDimesionCode());
            consumerWalletVO.setRightDimesionCode(consumerTwoDimensionCodes.get(0).getRightDimensionCode());
        }
        //手续费
        SysParameter sysParameter = sysParameterMapper.selectByPrimaryKey(8);
        if (sysParameter != null) {
            BigDecimal paramValue = sysParameter.getParamValue();
            consumerWalletVO.setTranceFee(paramValue);
        }
        //锁仓资产每日释放比例
        SysParameter sysParameter1= sysParameterMapper.selectByPrimaryKey(2);
        if (sysParameter1 != null) {
            BigDecimal paramValue = sysParameter1.getParamValue();
            consumerWalletVO.setReleaseLockrepoRatio(paramValue);
        }

        response.setData(consumerWalletVO);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 修改资金密码
     *
     * @param consumerPwdQuery
     * @return
     */
    @Transactional
    public BaseResponse changeFundspswd(ConsumerPwdQuery consumerPwdQuery, Integer consumerId) {
        BaseResponse response = new BaseResponse();
        if (consumerPwdQuery.getPassWord() == null) {
            response.setResponseCode(ResponseCode.PWD_CANT_BE_NULL.getCode());
            response.setResponseMsg(ResponseCode.PWD_CANT_BE_NULL.getMessage());
            return response;
        }

//        查询最新时间的验证码，标志位有效
        ConsumerCheckCode consumerCheckCode = consumerCheckCodeMapper.selectCheckCode(consumerPwdQuery.getPhoneNo(), "updateFundsPwd");
        if (consumerCheckCode == null) {
            response.setResponseCode(ResponseCode.CHECK_CODE_TIME_OUT.getCode());
            response.setResponseMsg(ResponseCode.CHECK_CODE_TIME_OUT.getMessage());
            return response;
        }
        String checkcodesession = consumerCheckCode.getCheckCode();
        //判断验证码是否输入正确
        if (StringUtils.isBlank(consumerPwdQuery.getCheckcode()) || StringUtils.isBlank(
                checkcodesession) || !consumerPwdQuery.getCheckcode().equals(checkcodesession)) {
            response.setResponseCode(ResponseCode.CHCEK_CODE_WRONG.getCode());
            response.setResponseMsg(ResponseCode.CHCEK_CODE_WRONG.getMessage());
            return response;
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Date time = c.getTime();
        Date createdTime = consumerCheckCode.getCreatedTime();
        //判断验证码是否已过期
        if (createdTime.before(time)) {
            response.setResponseCode(ResponseCode.CHECK_CODE_TIME_OUT.getCode());
            response.setResponseMsg(ResponseCode.CHECK_CODE_TIME_OUT.getMessage());
            return response;
        }
        //验证码更新为已使用
        consumerCheckCode.setInvalidFlag("1");
        consumerCheckCodeMapper.updateByPrimaryKey(consumerCheckCode);

        //更改用户设置表的资金密码
        ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
        consumerSettingsExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerSettings> consumerSettings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
        if (consumerSettings.size() == 0) {
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }
        ConsumerSettings settings = consumerSettings.get(0);
        //密码加密
        String encodepassword = PasswordUtil.hashPassword(consumerPwdQuery.getPassWord());
        settings.setCapitalPassword(encodepassword);
        consumerSettingsMapper.updateByPrimaryKey(settings);

        response.setData(new ArrayList<>());
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 查询用户每日收益
     *
     * @param id
     * @param dateQuery
     * @return
     */
    public BaseResponse<List<ConsumerProfitsDaily>> profitsDaily(Integer id, DateQuery dateQuery) throws ParseException {
        BaseResponse<List<ConsumerProfitsDaily>> response = new BaseResponse<>();
        if (id == null) {
            response.setResponseCode(ResponseCode.CONSUMER_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.CONSUMER_NOT_HAVE.getMessage());
            return response;
        }

        HashMap<String, String> stringDateHashMap = DateValidate.dateValidate(dateQuery.getStartDate(), dateQuery.getEndDate());
        String startDate = stringDateHashMap.get("startDate");
        String endDate = stringDateHashMap.get("endDate");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sStartDate = sdf.parse(startDate);
        Date sEndDate = sdf.parse(endDate);
        Calendar c=Calendar.getInstance();
        c.setTime(sEndDate);
        c.add(Calendar.DAY_OF_YEAR, 1);

        ConsumerProfitsDailyExample example = new ConsumerProfitsDailyExample();
        ConsumerProfitsDailyExample.Criteria criteria = example.createCriteria();
        criteria.andCreatedTimeBetween(sStartDate,c.getTime());

        criteria.andConsumerIdEqualTo(id);
        example.setOrderByClause("created_time asc");
        List<ConsumerProfitsDaily> consumerProfitsDailies = consumerProfitsDailyMapper.selectByExample(example);

        response.setData(consumerProfitsDailies);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 查询用户收益明细
     *
     * @param consumerId
     * @param pageNo
     * @return
     */
    public BaseResponse<PageInfo<List<ConsumerProfitsDaily>>> profitsdetail(Integer consumerId, Integer pageNo) {
        BaseResponse<PageInfo<List<ConsumerProfitsDaily>>> response = new BaseResponse();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(10);
        ConsumerProfitsDailyExample example = new ConsumerProfitsDailyExample();
        example.createCriteria().andConsumerIdEqualTo(consumerId);
        example.setOrderByClause("created_time desc");
        PageHelper.startPage(pageNo, pageInfo.getPageSize());
        List<ConsumerProfitsDaily> consumerProfitsDailies = consumerProfitsDailyMapper.selectByExample(example);
        pageInfo.setPageNum(pageNo);
        pageInfo.setList(consumerProfitsDailies);
        response.setData(pageInfo);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 获取用户信息
     *
     * @param consumerId
     * @return
     */
    public BaseResponse<ConsumerAppVO> consumer(Integer consumerId) throws ParseException {
        BaseResponse<ConsumerAppVO> response = new BaseResponse<>();
        ConsumerAppVO consumerAppVO =
                new ConsumerAppVO("", "", "", "", "", "", "", BigDecimal.ZERO, "");
        //获取推荐码，转账码
        ConsumerTwoDimensionCodeExample codeExample = new ConsumerTwoDimensionCodeExample();
        codeExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerTwoDimensionCode> consumerTwoDimensionCodes = consumerTwoDimensionCodeMapper.selectByExample(codeExample);
        if (consumerTwoDimensionCodes.size() != 0) {
            ConsumerTwoDimensionCode consumerTwoDimensionCode = consumerTwoDimensionCodes.get(0);
            consumerAppVO.setLeftDimesionCode(consumerTwoDimensionCode.getLeftDimesionCode());
            consumerAppVO.setRightDimensionCode(consumerTwoDimensionCode.getRightDimensionCode());
            consumerAppVO.setTransferDimensionCode(consumerTwoDimensionCode.getTransferDimensionCode());
        }

        //获取电话号
        ConsumerWithBLOBs consumerWithBLOBs = consumerMapper.selectByPrimaryKey(consumerId);
        if (consumerWithBLOBs != null) {
            consumerAppVO.setPhoneNo(consumerWithBLOBs.getPhoneNo());
            consumerAppVO.setAccount(consumerWithBLOBs.getAccount());
        }
        ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
        consumerSettingsExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerSettings> consumerSettings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
        if (consumerSettings.size() != 0) {
            ConsumerSettings consumerSetting = consumerSettings.get(0);
            consumerAppVO.setHeadPortrait(consumerSetting.getHeadPortrait());
            consumerAppVO.setNickName(consumerSetting.getNickName());
        }
        //获取是否自定复投标志
        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
        consumerCapitalAccountExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerCapitalAccount> accounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
        if (accounts.size() != 0 && accounts.get(0) != null) {
            consumerAppVO.setAutoReinvestFlag(accounts.get(0).getReinvestFlag());
        }

        //计算现价
        SysDictionaryExample sysDictionaryExample = new SysDictionaryExample();
        sysDictionaryExample.createCriteria().andGroupCodeEqualTo("publishDate");
        List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(sysDictionaryExample);
        if (sysDictionaries.size() == 0) {
            response.setResponseMsg(ResponseCode.NOT_HAVE_PUBDATE.getMessage());
            response.setResponseCode(ResponseCode.NOT_HAVE_PUBDATE.getCode());
            return response;
        }
        String dicValue = sysDictionaries.get(0).getDicValue();//上线日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date publicDate = sdf.parse(dicValue);
        Date today = new Date();
        int dayCount = DateUtil.differentDays(publicDate, today);
        //TODO 获取比例值增量
//        sysParameterMapper.selectByExample();

        //TODO 获取比例值基数
//        sysParameterMapper.selectByExample();


        BigDecimal currentPrice = BigDecimal.ONE.add(new BigDecimal(0.01).multiply(new BigDecimal(dayCount)));
        BigDecimal currentPriceB = currentPrice.setScale(2, RoundingMode.HALF_UP);
        consumerAppVO.setCurrentPrice(currentPriceB);//现价

        response.setData(consumerAppVO);
        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }

    /**
     * 忘记密码
     *
     * @param consumerPwdQuery
     * @return
     */
    public BaseResponse forgetPwd(ConsumerPwdQuery consumerPwdQuery) {
        BaseResponse response = new BaseResponse();
        //更据账号（即手机号）查询用户信息consumer
        ConsumerExample consumerExample = new ConsumerExample();
        consumerExample.createCriteria().andAccountEqualTo(consumerPwdQuery.getPhoneNo());
        List<Consumer> consumers = consumerMapper.selectByExample(consumerExample);
        if (consumers.size() == 0) {
            response.setResponseMsg(ResponseCode.PHONE_NOT_REGIST.getMessage());
            response.setResponseCode(ResponseCode.PHONE_NOT_REGIST.getCode());
            return response;
        }
        Consumer consumer = consumers.get(0);
        if (consumer == null) {
            response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
            response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
            return response;
        }

        //查询最新时间的验证码，标志位有效
        ConsumerCheckCode consumerCheckCode = consumerCheckCodeMapper.selectCheckCode(consumerPwdQuery.getPhoneNo(), "forgetPwd");
        if (consumerCheckCode == null) {
            response.setResponseCode(ResponseCode.CHECK_CODE_TIME_OUT.getCode());
            response.setResponseMsg(ResponseCode.CHECK_CODE_TIME_OUT.getMessage());
            return response;
        }
        String checkcodesession = consumerCheckCode.getCheckCode();
        //判断验证码是否输入正确
        if (StringUtils.isBlank(consumerPwdQuery.getCheckcode()) || StringUtils.isBlank(
                checkcodesession) || !consumerPwdQuery.getCheckcode().equals(checkcodesession)) {
            response.setResponseCode(ResponseCode.CHCEK_CODE_WRONG.getCode());
            response.setResponseMsg(ResponseCode.CHCEK_CODE_WRONG.getMessage());
            return response;
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Date time = c.getTime();
        Date createdTime = consumerCheckCode.getCreatedTime();
        //判断验证码是否已过期
        if (createdTime.before(time)) {
            response.setResponseCode(ResponseCode.CHECK_CODE_TIME_OUT.getCode());
            response.setResponseMsg(ResponseCode.CHECK_CODE_TIME_OUT.getMessage());
            return response;
        }
        //验证码更新为已使用
        consumerCheckCode.setInvalidFlag("1");
        consumerCheckCodeMapper.updateByPrimaryKey(consumerCheckCode);


        //更改密码
        String encodepassword = PasswordUtil.hashPassword(consumerPwdQuery.getPassWord());
        consumer.setPassWord(encodepassword);
        consumerMapper.updateByPrimaryKey(consumer);

        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 修改登录密码
     *
     * @param consumerPwdQuery
     * @param consumerId
     * @return
     */
    public BaseResponse updatePwd(ConsumerPwdQuery consumerPwdQuery, Integer consumerId) {
        BaseResponse response = new BaseResponse();
        ConsumerWithBLOBs consumer = consumerMapper.selectByPrimaryKey(consumerId);
        if (consumer == null) {
            response.setResponseMsg(ResponseCode.CONSUMER_DONOT_HAVE.getMessage());
            response.setResponseCode(ResponseCode.CONSUMER_DONOT_HAVE.getCode());
            return response;
        }
        //判断旧密码是否输入正确
        if (!PasswordUtil.checkPassword(consumerPwdQuery.getOldPassWord(), consumer.getPassWord())) {
            response.setResponseCode(ResponseCode.OLD_PWD_WRONG.getCode());
            response.setResponseMsg(ResponseCode.OLD_PWD_WRONG.getMessage());
            return response;
        }
        //新密码不能与旧密码相同
        if (consumerPwdQuery.getOldPassWord().equals(consumerPwdQuery.getPassWord())) {
            response.setResponseCode(ResponseCode.NEW_PASSWORD_WRONG.getCode());
            response.setResponseMsg(ResponseCode.NEW_PASSWORD_WRONG.getMessage());
            return response;
        }
        //检验验证码
        ConsumerCheckCode consumerCheckCode = consumerCheckCodeMapper.selectCheckCode(consumerPwdQuery.getPhoneNo(), "updatePwd");
        if (consumerCheckCode == null) {
            response.setResponseCode(ResponseCode.CHECK_CODE_TIME_OUT.getCode());
            response.setResponseMsg(ResponseCode.CHECK_CODE_TIME_OUT.getMessage());
            return response;
        }
        String checkcodesession = consumerCheckCode.getCheckCode();
        //判断验证码是否输入正确
        if (StringUtils.isBlank(consumerPwdQuery.getCheckcode()) || StringUtils.isBlank(
                checkcodesession) || !consumerPwdQuery.getCheckcode().equals(checkcodesession)) {
            response.setResponseCode(ResponseCode.CHCEK_CODE_WRONG.getCode());
            response.setResponseMsg(ResponseCode.CHCEK_CODE_WRONG.getMessage());
            return response;
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Date time = c.getTime();
        Date createdTime = consumerCheckCode.getCreatedTime();
        //判断验证码是否已过期
        if (createdTime.before(time)) {
            response.setResponseCode(ResponseCode.CHECK_CODE_TIME_OUT.getCode());
            response.setResponseMsg(ResponseCode.CHECK_CODE_TIME_OUT.getMessage());
            return response;
        }
        //生成token
        String token = JWT.create().withAudience(consumer.getId() + "")
                .sign(Algorithm.HMAC256(consumer.getPassWord()));
        if (StringUtils.isBlank(token)) {
            response.setResponseCode(ResponseCode.TOKEN_CREATE_WRONG.getCode());
            response.setResponseMsg(ResponseCode.TOKEN_CREATE_WRONG.getMessage());
            return response;
        }

        //验证码更新为已使用
        consumerCheckCode.setInvalidFlag("1");
        consumerCheckCodeMapper.updateByPrimaryKey(consumerCheckCode);
        //更改密码
        String encodepassword = PasswordUtil.hashPassword(consumerPwdQuery.getPassWord());
        consumer.setPassWord(encodepassword);
        consumerMapper.updateByPrimaryKey(consumer);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        response.setData(map);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }


    /**
     * 更新用户昵称,头像等信息
     *
     * @param consumerId
     * @return
     */
    public BaseResponse update(Integer consumerId, ConsumerAppVO consumerAppVO) {
        BaseResponse response = new BaseResponse();
        //根据consumerId查询用户设置信息
        ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
        consumerSettingsExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerSettings> consumerSettings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
        if (consumerSettings.size() != 0) {
            ConsumerSettings consumerSetting = consumerSettings.get(0);
            consumerSetting.setNickName(consumerAppVO.getNickName());
            consumerSettingsMapper.updateByPrimaryKey(consumerSetting);
            response.setData(new ArrayList<>());
            response.setResponseCode(ResponseCode.OK.getCode());
            response.setResponseMsg(ResponseCode.OK.getMessage());
            return response;
        }
        response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        return response;
    }


    /**
     * 自动锁仓释放 0-不释放1-释放
     *
     * @param consumerId
     * @param witchFlag
     * @return
     */
    public BaseResponse lockRelease(Integer consumerId, String witchFlag) {
        BaseResponse response = new BaseResponse();
        ConsumerCapitalAccountExample example = new ConsumerCapitalAccountExample();
        example.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerCapitalAccount> accounts = consumerCapitalAccountMapper.selectByExample(example);
        if (accounts.size() != 0 || accounts.get(0) != null) {
            ConsumerCapitalAccount consumerCapitalAccount = accounts.get(0);
            if (consumerCapitalAccount.getReleaseFlag().equals(witchFlag) && "1".equals(witchFlag)) {
                response.setResponseCode(ResponseCode.LOCKRELESE_HAS_OPEN.getCode());
                response.setResponseMsg(ResponseCode.LOCKRELESE_HAS_OPEN.getMessage());
                return response;
            }
            if (consumerCapitalAccount.getReleaseFlag().equals(witchFlag) && "0".equals(witchFlag)) {
                response.setResponseCode(ResponseCode.LOCKRELESE_HAS_CLOSE.getCode());
                response.setResponseMsg(ResponseCode.LOCKRELESE_HAS_CLOSE.getMessage());
                return response;
            }
            consumerCapitalAccount.setReleaseFlag(witchFlag);
            consumerCapitalAccountMapper.updateByPrimaryKeySelective(consumerCapitalAccount);
            response.setData(new ArrayList<>());
            response.setResponseCode(ResponseCode.OK.getCode());
            response.setResponseMsg(ResponseCode.OK.getMessage());
            return response;
        }

        response.setResponseCode(ResponseCode.SERVER_FAILED.getCode());
        response.setResponseMsg(ResponseCode.SERVER_FAILED.getMessage());
        return response;
    }


    /**
     * 自动复投
     *
     * @param consumerId
     * @param whichFlag
     * @return
     */
    public BaseResponse autoReinvest(Integer consumerId, String whichFlag) {
        BaseResponse response = new BaseResponse();
        ConsumerCapitalAccountExample example = new ConsumerCapitalAccountExample();
        example.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerCapitalAccount> accounts = consumerCapitalAccountMapper.selectByExample(example);
        if (accounts.size() != 0 || accounts.get(0) != null) {
            ConsumerCapitalAccount consumerCapitalAccount = accounts.get(0);
            if (consumerCapitalAccount.getReinvestFlag().equals(whichFlag) && "1".equals(whichFlag)) {
                response.setResponseCode(ResponseCode.REINVEST_HAS_OPEN.getCode());
                response.setResponseMsg(ResponseCode.REINVEST_HAS_OPEN.getMessage());
                return response;
            }
            if (consumerCapitalAccount.getReinvestFlag().equals(whichFlag) && "0".equals(whichFlag)) {
                response.setResponseCode(ResponseCode.REINVEST_HAS_CLOSE.getCode());
                response.setResponseMsg(ResponseCode.REINVEST_HAS_CLOSE.getMessage());
                return response;
            }
            consumerCapitalAccount.setReinvestFlag(whichFlag);
            consumerCapitalAccountMapper.updateByPrimaryKeySelective(consumerCapitalAccount);

            response.setResponseCode(ResponseCode.OK.getCode());
            response.setResponseMsg(ResponseCode.OK.getMessage());
            return response;
        } else {
            response.setResponseCode(ResponseCode.ACCONUT_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.ACCONUT_NOT_HAVE.getMessage());
            return response;
        }
    }
}
