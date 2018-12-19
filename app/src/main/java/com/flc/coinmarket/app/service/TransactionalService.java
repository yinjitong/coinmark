package com.flc.coinmarket.app.service;

import com.bugull.mongo.BuguQuery;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.core.constant.Constants;
import com.flc.coinmarket.core.util.DateUtil;
import com.flc.coinmarket.core.util.PasswordUtil;
import com.flc.coinmarket.dao.mongo.dao.ConsumerTranceDetailDAO;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerCapitalAccountMapper;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerMapper;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerSettingsMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysDictionaryMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysParameterMapper;
import com.flc.coinmarket.dao.mysql.model.consumer.*;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionary;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionaryExample;
import com.flc.coinmarket.dao.mysql.model.system.SysParameter;
import com.flc.coinmarket.dao.pojo.ConsumerTrance;
import com.flc.coinmarket.dao.pojo.TransactionDetailQuery;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionalService {
    @Autowired
    private ConsumerTranceDetailDAO consumerTranceDetailDAO;
    @Autowired
    private ConsumerCapitalAccountMapper consumerCapitalAccountMapper;
    @Autowired
    private ConsumerSettingsMapper consumerSettingsMapper;
    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;
    @Autowired
    private ConsumerMapper consumerMapper;

    /**
     * 对其他用户转账
     *
     * @param consumerTrance
     * @return
     */
    @Transactional
    public BaseResponse toothers(ConsumerTrance consumerTrance, Integer consumerId) {
        BaseResponse response = new BaseResponse();
        //查询转账手续费
        SysParameter sysParameter = sysParameterMapper.selectByPrimaryKey(8);
        BigDecimal paramValue = sysParameter.getParamValue();
        BigDecimal tranFee = paramValue.multiply(consumerTrance.getFunds());

        //查询收款方账户
        ConsumerCapitalAccountExample incomeAccountExample = new ConsumerCapitalAccountExample();
        incomeAccountExample.createCriteria().andFloatingAddressEqualTo(consumerTrance.getTransferAddressTo());
        List<ConsumerCapitalAccount> incomeAccounts = consumerCapitalAccountMapper.selectByExample(incomeAccountExample);
        if (incomeAccounts.size() == 0) {
            response.setResponseCode(ResponseCode.ADDRESS_NOT_INVALID.getCode());
            response.setResponseMsg(ResponseCode.ADDRESS_NOT_INVALID.getMessage());
            return response;
        }
        ConsumerCapitalAccount incomeAccount = incomeAccounts.get(0);

        //查询收款用户信息
        ConsumerWithBLOBs incomeConsumer = consumerMapper.selectByPrimaryKey(incomeAccount.getConsumerId());

        //查询付款方设置信息
        ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
        consumerSettingsExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerSettings> payConsumerSettings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
        if (payConsumerSettings.size() == 0 || payConsumerSettings.get(0) == null) {
            response.setResponseCode(ResponseCode.SETTING_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.SETTING_NOT_HAVE.getMessage());
            return response;
        }
        ConsumerSettings payConsumerSetting = payConsumerSettings.get(0);
        //检查付款方是否设置资金密码
        if (StringUtils.isBlank(payConsumerSetting.getCapitalPassword())) {
            response.setResponseCode(ResponseCode.NOT_HAVE_FUNDS_PWD.getCode());
            response.setResponseMsg(ResponseCode.NOT_HAVE_FUNDS_PWD.getMessage());
            return response;
        }
        //检验资金密码是否正确
        if (!PasswordUtil.checkPassword(consumerTrance.getPassWord(), payConsumerSetting.getCapitalPassword())) {
            response.setResponseCode(ResponseCode.PASSWORD_WRONG.getCode());
            response.setResponseMsg(ResponseCode.PASSWORD_WRONG.getMessage());
            return response;
        }

        //查询收款方设置信息
        ConsumerSettingsExample incomeSettingsExample = new ConsumerSettingsExample();
        incomeSettingsExample.createCriteria().andConsumerIdEqualTo(incomeAccount.getConsumerId());
        List<ConsumerSettings> incomeConsumerSettings = consumerSettingsMapper.selectByExample(incomeSettingsExample);
        if (incomeConsumerSettings.size() == 0 || incomeConsumerSettings.get(0) == null) {
            response.setResponseCode(ResponseCode.OPPO_SETTING_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.OPPO_SETTING_NOT_HAVE.getMessage());
            return response;
        }
        ConsumerSettings incomeConsumerSetting = incomeConsumerSettings.get(0);

        //查询付款方用户信息
        ConsumerWithBLOBs payConsumer = consumerMapper.selectByPrimaryKey(consumerId);

        //查询内部账地址
        SysDictionaryExample dictionaryExample = new SysDictionaryExample();
        dictionaryExample.createCriteria().andDicCodeEqualTo("inter_Account");
        List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(dictionaryExample);
        if (sysDictionaries.size() == 0 || sysDictionaries.get(0) == null) {
            throw new RuntimeException("内部账地址不存在！！！");
        }
        SysDictionary sysDictionary = sysDictionaries.get(0);
        String inAddressValue = sysDictionary.getDicValue();

        //本次交易流水号
        String tranNo = UUID.randomUUID().toString().replace("-", "").toLowerCase();

        //根据用户id，查询账户信息
        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
        consumerCapitalAccountExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerCapitalAccount> payAccounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
        if (payAccounts.size() == 0 || payAccounts.get(0) == null) {
            response.setResponseCode(ResponseCode.ACCONUT_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.ACCONUT_NOT_HAVE.getMessage());
            return response;
        }
        ConsumerCapitalAccount payAccount = payAccounts.get(0);
        //判断用户资金是否足够
        if (payAccount.getFloatingFunds().compareTo(consumerTrance.getFunds().add(tranFee)) == -1) {
            response.setResponseCode(ResponseCode.FLOATFUNDS_NOT_ENOUGH.getCode());
            response.setResponseMsg(ResponseCode.FLOATFUNDS_NOT_ENOUGH.getMessage());
            return response;
        }
        //自转无效
        if (incomeAccount.getFloatingAddress().equals(payAccount.getFloatingAddress())) {
            response.setResponseCode(ResponseCode.ADDRESS_NOT_INVALID.getCode());
            response.setResponseMsg(ResponseCode.ADDRESS_NOT_INVALID.getMessage());
            return response;
        }

        //查询内部账号余额
        BuguQuery<ConsumerTranceDetail> query = consumerTranceDetailDAO.query();
        List<ConsumerTranceDetail> results = query.in("transferAddressFrom", inAddressValue).sortDesc("createdTime").results();

        //付 交易转出
        payAccount.setFloatingFunds(payAccount.getFloatingFunds().subtract(consumerTrance.getFunds()));
        ConsumerTranceDetail payDetail = createTranceDetail(tranNo, payAccount.getId(), consumerTrance.getFunds(), Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.TRANS_OUT.getValue()
                ,payAccount.getFloatingAddress(),consumerTrance.getTransferAddressTo(), incomeAccount.getId(), payAccount.getFloatingFunds()
                , payConsumer.getPhoneNo(),incomeConsumer.getPhoneNo(),  payConsumerSetting.getNickName() == null ? payConsumer.getPhoneNo() : payConsumerSetting.getNickName(),
                incomeConsumerSetting.getNickName() == null ? incomeConsumer.getPhoneNo() : incomeConsumerSetting.getNickName());

        consumerTranceDetailDAO.insert(payDetail);

        //付款方付手续费
        ConsumerTranceDetail payFeeDetail = createTranceDetail(tranNo, payAccount.getId(), tranFee, Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.TRANS_FEE.getValue()
                ,payAccount.getFloatingAddress(),inAddressValue, null,payAccount.getFloatingFunds().subtract(tranFee)
                , payConsumer.getPhoneNo(), null,payConsumerSetting.getNickName() == null ? payConsumer.getPhoneNo() : payConsumerSetting.getNickName(),
                null);
        consumerTranceDetailDAO.insert(payFeeDetail);

        //收 交易转入
        ConsumerTranceDetail incomeDetail = createTranceDetail(tranNo, incomeAccount.getId(), consumerTrance.getFunds(), Constants.INCOME.VALUE, Constants.INCOME.SourceType.TRANS_IN.getValue()
                ,consumerTrance.getTransferAddressTo(),payAccount.getFloatingAddress(), consumerId,incomeAccount.getFloatingFunds().add(consumerTrance.getFunds())
                , incomeConsumer.getPhoneNo(),payConsumer.getPhoneNo(),incomeConsumerSetting.getNickName() == null ? incomeConsumer.getPhoneNo() : incomeConsumerSetting.getNickName() ,
                payConsumerSetting.getNickName() == null ? payConsumer.getPhoneNo() : payConsumerSetting.getNickName());
        consumerTranceDetailDAO.insert(incomeDetail);

        //收  内部账手续费收
        ConsumerTranceDetail incomeFeeDetail = createTranceDetail(tranNo,null,tranFee.negate(), Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.TRANS_FEE.getValue()
                ,inAddressValue,payAccount.getFloatingAddress(), consumerId,results.size() == 0 || results.get(0) == null ? BigDecimal.ZERO.add(tranFee) : results.get(0).getBalance().add(tranFee)
                , null,payConsumer.getPhoneNo(),null ,
                payConsumerSetting.getNickName() == null ? payConsumer.getPhoneNo() : payConsumerSetting.getNickName());
        consumerTranceDetailDAO.insert(incomeFeeDetail);

        //更新账户表
        //更改付出账户的流动资金
        BigDecimal payFloatingFunds = payAccount.getFloatingFunds().subtract(consumerTrance.getFunds()).subtract(tranFee);
        payAccount.setFloatingFunds(payFloatingFunds);
        consumerCapitalAccountMapper.updateByPrimaryKey(payAccount);
        //更改接收账户的流动资金
        BigDecimal floatFunds = incomeAccount.getFloatingFunds().add(consumerTrance.getFunds());
        incomeAccount.setFloatingFunds(floatFunds);
        consumerCapitalAccountMapper.updateByPrimaryKeySelective(incomeAccount);

        Map<String, BigDecimal> map = new HashMap<>();
        map.put("floatingFunds", payFloatingFunds);
        response.setData(map);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 流动转锁仓
     *
     * @param consumerTrance
     * @param consumerId
     * @return
     */
    @Transactional
    public BaseResponse tolockrepo(ConsumerTrance consumerTrance, int consumerId) {
        BaseResponse response = new BaseResponse();
        //记录交易明细
        ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
        consumerSettingsExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerSettings> consumerSettings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
        if (consumerSettings.size() == 0 || consumerSettings.get(0) == null) {
            response.setResponseCode(ResponseCode.SETTING_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.SETTING_NOT_HAVE.getMessage());
            return response;
        }
        //检查是否设置资金密码
        if (StringUtils.isBlank(consumerSettings.get(0).getCapitalPassword())) {
            response.setResponseCode(ResponseCode.NOT_HAVE_FUNDS_PWD.getCode());
            response.setResponseMsg(ResponseCode.NOT_HAVE_FUNDS_PWD.getMessage());
            return response;
        }
        //检验资金密码是否正确
        if (!PasswordUtil.checkPassword(consumerTrance.getPassWord(), consumerSettings.get(0).getCapitalPassword())) {
            response.setResponseCode(ResponseCode.PASSWORD_WRONG.getCode());
            response.setResponseMsg(ResponseCode.PASSWORD_WRONG.getMessage());
            return response;
        }
        ConsumerSettings consumerSetting = consumerSettings.get(0);
        //根基用户id，查询当前用户转账地址
        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
        consumerCapitalAccountExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerCapitalAccount> accounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
        if (accounts.size() == 0) {
            response.setResponseCode(ResponseCode.ACCONUT_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.ACCONUT_NOT_HAVE.getMessage());
            return response;
        }
        ConsumerCapitalAccount account = accounts.get(0);

        //检验转账数量是否足够
        if (account.getFloatingFunds().compareTo(consumerTrance.getFunds()) == -1) {
            response.setResponseCode(ResponseCode.FLOATFUNDS_NOT_ENOUGH.getCode());
            response.setResponseMsg(ResponseCode.FLOATFUNDS_NOT_ENOUGH.getMessage());
            return response;
        }
        //获取用户信息
        ConsumerWithBLOBs consumer = consumerMapper.selectByPrimaryKey(consumerId);
        //本次交易流水号
        String tranNo = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //消费资产付
        ConsumerTranceDetail detailPay = createTranceDetail(tranNo, account.getId(), consumerTrance.getFunds(), Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.FLOAT_LOCK.getValue()
                , account.getFloatingAddress(), account.getLockrepoAddress(), null, account.getFloatingFunds().subtract(consumerTrance.getFunds())
                , consumer.getPhoneNo(), consumer.getPhoneNo(), consumerSetting.getNickName() == null ? consumer.getPhoneNo() : consumerSetting.getNickName(),
                consumerSetting.getNickName() == null ? consumer.getPhoneNo() : consumerSetting.getNickName());

        consumerTranceDetailDAO.insert(detailPay);

        //锁仓资产收
        ConsumerTranceDetail detailIn = createTranceDetail(tranNo, account.getId(), consumerTrance.getFunds(), Constants.INCOME.VALUE, Constants.INCOME.SourceType.LOCK_FLOAT.getValue()
                ,account.getLockrepoAddress(), account.getFloatingAddress(), null,account.getLockrepoFunds().add(consumerTrance.getFunds())
                , consumer.getPhoneNo(), consumer.getPhoneNo(), consumerSetting.getNickName() == null ? consumer.getPhoneNo() : consumerSetting.getNickName(),
                consumerSetting.getNickName() == null ? consumer.getPhoneNo() : consumerSetting.getNickName());
        consumerTranceDetailDAO.insert(detailIn);


        //更新账户表
        BigDecimal floatFunds = account.getFloatingFunds().subtract(consumerTrance.getFunds());
        account.setFloatingFunds(floatFunds);
        account.setLockrepoFunds(account.getLockrepoFunds().add(consumerTrance.getFunds()));
        consumerCapitalAccountMapper.updateByPrimaryKey(account);

        Map<String, BigDecimal> map = new HashMap<>();
        map.put("floatingFunds", floatFunds);
        response.setData(map);
        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;
    }

    /**
     * 用户交易流水
     *
     * @param consumerId
     * @param query
     * @return
     */
    public BaseResponse<List<ConsumerTranceDetail>> details(Integer consumerId, TransactionDetailQuery query) {
        BaseResponse response = new BaseResponse();
        Integer pageSie = 10;
        String transType = query.getTransType();
        if (transType == null) {
            transType = Constants.EXPENSE.VALUE;
        }
        if (query.getPageNo() == null) {
            query.setPageNo(1);
        }
        //通过consumerId确定账户id
        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
        consumerCapitalAccountExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerCapitalAccount> accounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
        if (accounts.size() == 0) {
            response.setResponseCode(ResponseCode.ACCONUT_NOT_HAVE.getCode());
            response.setResponseMsg(ResponseCode.ACCONUT_NOT_HAVE.getMessage());
            return response;
        }
        ConsumerCapitalAccount account = accounts.get(0);

        BuguQuery<ConsumerTranceDetail> buguQuery = consumerTranceDetailDAO.query();
        buguQuery.is("tranceType", transType);
        buguQuery.is("accountId", account.getId());
        buguQuery.pageSize(pageSie).pageNumber(query.getPageNo());
        List<ConsumerTranceDetail> results = buguQuery.results();
        int size = results.size();
        PageInfo<ConsumerTranceDetail> pageInfo = new PageInfo<>();
        pageInfo.setTotal(size);
        pageInfo.setList(results);
        pageInfo.setPageNum(query.getPageNo());
        pageInfo.setPageSize(pageSie);
        response.setData(pageInfo);

        response.setResponseCode(ResponseCode.OK.getCode());
        response.setResponseMsg(ResponseCode.OK.getMessage());
        return response;

    }

    private ConsumerTranceDetail createTranceDetail(String tranNo,Integer accountId, BigDecimal funds, String transType, String sourceType, String addressFrom, String addressTo,
                                                    Integer transferConsumerId,BigDecimal balance,String phoneFrom,String phoneTo,String nickNameFrom,String nickNameTo ) {
        ConsumerTranceDetail tranceDetail = new ConsumerTranceDetail();
        tranceDetail.setAccountId(accountId);
        tranceDetail.setTranceNo(tranNo);
        tranceDetail.setFunds(funds);
        tranceDetail.setTranceType(transType);
        tranceDetail.setSourceType(sourceType);
        tranceDetail.setCreatedTime(new Date());
        tranceDetail.setUpdatedTime(new Date());
        tranceDetail.setTransferAddressFrom(addressFrom);
        tranceDetail.setTransferAddressTo(addressTo);
        tranceDetail.setTransferConsumer(transferConsumerId);
        tranceDetail.setBalance(balance);
        tranceDetail.setPhoneNoFrom(phoneFrom);
        tranceDetail.setPhoneNoTo(phoneTo);
        tranceDetail.setNickNameFrom(nickNameFrom);
        tranceDetail.setNickNameTo(nickNameTo);
        return tranceDetail;
    }
}
