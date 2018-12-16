package com.flc.coinmarket.app.service;

import com.bugull.mongo.BuguQuery;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.core.constant.Constants;
import com.flc.coinmarket.core.util.PasswordUtil;
import com.flc.coinmarket.dao.mongo.dao.ConsumerTranceDetailDAO;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerCapitalAccountMapper;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerSettingsMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysDictionaryMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysParameterMapper;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerCapitalAccount;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerCapitalAccountExample;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerSettings;
import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerSettingsExample;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        BigDecimal tranFee=paramValue.multiply(consumerTrance.getFunds());

        ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
        consumerSettingsExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerSettings> consumerSettings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
        if (consumerSettings.size() == 0) {
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
        //查询内部账地址
        SysDictionaryExample dictionaryExample = new SysDictionaryExample();
        dictionaryExample.createCriteria().andDicCodeEqualTo("inter_Account");
        List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(dictionaryExample);
        if (sysDictionaries.size() == 0 || sysDictionaries.get(0) == null) {
            throw new RuntimeException("内部账地址不存在！！！");
        }
        SysDictionary sysDictionary = sysDictionaries.get(0);
        String inAddressValue = sysDictionary.getDicValue();


        //付 交易转出
        ConsumerTranceDetail payDetail = new ConsumerTranceDetail();
        //根据用户id，查询账户信息
        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
        consumerCapitalAccountExample.createCriteria().andConsumerIdEqualTo(consumerId);
        List<ConsumerCapitalAccount> payAccounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
        if (payAccounts.size() == 0||payAccounts.get(0)==null) {
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
        payDetail.setTransferAddressFrom(payAccount.getFloatingAddress());
        payDetail.setTransferAddressTo(consumerTrance.getTransferAddressTo());
        payDetail.setFunds(consumerTrance.getFunds());
        payDetail.setAccountId(payAccount.getId());
        payDetail.setCreatedTime(new Date());
        payDetail.setSourceType(Constants.EXPENSE.SourceType.TRANS_OUT.getValue());
//      payDetail.setTranceNo();
        payDetail.setTranceType(Constants.EXPENSE.VALUE);
        //查询对方账户
        ConsumerCapitalAccountExample incomeAccountExample = new ConsumerCapitalAccountExample();
        incomeAccountExample.createCriteria().andFloatingAddressEqualTo(consumerTrance.getTransferAddressTo());
        List<ConsumerCapitalAccount> incomeAccounts = consumerCapitalAccountMapper.selectByExample(incomeAccountExample);
        if (incomeAccounts.size() == 0) {
            response.setResponseCode(ResponseCode.ADDRESS_NOT_INVALID.getCode());
            response.setResponseMsg(ResponseCode.ADDRESS_NOT_INVALID.getMessage());
            return response;
        }
        ConsumerCapitalAccount incomeAccount = incomeAccounts.get(0);
        //自转无效
        if (incomeAccount.getFloatingAddress().equals(payAccount.getFloatingAddress())) {
            response.setResponseCode(ResponseCode.ADDRESS_NOT_INVALID.getCode());
            response.setResponseMsg(ResponseCode.ADDRESS_NOT_INVALID.getMessage());
            return response;
        }
        payDetail.setTransferConsumer(incomeAccount.getConsumerId());
        consumerTranceDetailDAO.insert(payDetail);

        //付款方付手续费
        ConsumerTranceDetail payFeeDetail = new ConsumerTranceDetail();
        payFeeDetail.setTransferAddressFrom(payAccount.getFloatingAddress());
        payFeeDetail.setTransferAddressTo(inAddressValue);
        payFeeDetail.setFunds(tranFee);
        payFeeDetail.setAccountId(payAccount.getId());
        payFeeDetail.setCreatedTime(new Date());
        payFeeDetail.setSourceType(Constants.EXPENSE.SourceType.TRANS_FEE.getValue());
//      payDetail.setTranceNo();
        payFeeDetail.setTranceType(Constants.EXPENSE.VALUE);
//      payFeeDetail.setTransferConsumer();
        consumerTranceDetailDAO.insert(payFeeDetail);


        //收 交易转入
        ConsumerTranceDetail incomeDetail = new ConsumerTranceDetail();
        incomeDetail.setTransferAddressFrom(consumerTrance.getTransferAddressTo());
        incomeDetail.setTransferAddressTo(payAccount.getFloatingAddress());
        incomeDetail.setFunds(consumerTrance.getFunds());
        incomeDetail.setAccountId(incomeAccount.getId());
        incomeDetail.setCreatedTime(new Date());
        incomeDetail.setSourceType(Constants.INCOME.SourceType.TRANS_IN.getValue());
//      incomeDetail.setTranceNo();
        incomeDetail.setTranceType(Constants.INCOME.VALUE);
        incomeDetail.setTransferConsumer(consumerId);
        consumerTranceDetailDAO.insert(incomeDetail);

        //收  内部账手续费收
        ConsumerTranceDetail incomeFeeDetail = new ConsumerTranceDetail();
        incomeFeeDetail.setTransferAddressFrom(inAddressValue);
        incomeFeeDetail.setTransferAddressTo(payAccount.getFloatingAddress());
        incomeFeeDetail.setFunds(tranFee.negate());
//      incomeFeeDetail.setAccountId();
        incomeFeeDetail.setCreatedTime(new Date());
        incomeFeeDetail.setSourceType(Constants.EXPENSE.SourceType.TRANS_FEE.getValue());
//      incomeFeeDetail.setTranceNo();
        incomeFeeDetail.setTranceType(Constants.EXPENSE.VALUE);
      incomeFeeDetail.setTransferConsumer(consumerId);
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
        if (consumerSettings.size() == 0) {
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
        //登记交易明细表
        ConsumerTranceDetail detail = new ConsumerTranceDetail();
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

        //消费资产付
        detail.setTransferAddressFrom(account.getFloatingAddress());
        detail.setTransferAddressTo(account.getLockrepoAddress());
        detail.setFunds(consumerTrance.getFunds());
        detail.setAccountId(account.getId());
        detail.setCreatedTime(new Date());
        detail.setSourceType(Constants.EXPENSE.SourceType.FLOAT_LOCK.getValue());//流动转锁仓
//      payDetail.setTranceNo();
        detail.setTranceType(Constants.EXPENSE.VALUE);
//      detail.setTransferConsumer();
        consumerTranceDetailDAO.insert(detail);

        //锁仓资产收
        ConsumerTranceDetail detailIn = new ConsumerTranceDetail();
        detailIn.setTransferAddressFrom(account.getLockrepoAddress());
        detailIn.setTransferAddressTo(account.getFloatingAddress());
        detailIn.setFunds(consumerTrance.getFunds());
        detailIn.setAccountId(account.getId());
        detailIn.setCreatedTime(new Date());
        detailIn.setSourceType(Constants.INCOME.SourceType.LOCK_FLOAT.getValue());//流动转锁仓
//      payDetail.setTranceNo();
        detailIn.setTranceType(Constants.INCOME.VALUE);
//      detail.setTransferConsumer();
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
//        buguQuery.in("sourceType","1","6","7","9");
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
}
