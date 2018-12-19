package com.flc.coinmarket.admin.service;

import com.bugull.mongo.BuguQuery;
import com.flc.coinmarket.core.constant.Constants;
import com.flc.coinmarket.core.util.DateUtil;
import com.flc.coinmarket.dao.mongo.dao.ConsumerTranceDetailDAO;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerCapitalAccountMapper;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerMapper;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerSettingsMapper;
import com.flc.coinmarket.dao.mysql.mapper.consumer.ConsumerTeamMapper;
import com.flc.coinmarket.dao.mysql.mapper.statistics.*;
import com.flc.coinmarket.dao.mysql.mapper.system.ProfitsLockrepoRatioMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.ProfitsTeamRatioMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysDictionaryMapper;
import com.flc.coinmarket.dao.mysql.mapper.system.SysParameterMapper;
import com.flc.coinmarket.dao.mysql.model.consumer.*;
import com.flc.coinmarket.dao.mysql.model.statistics.*;
import com.flc.coinmarket.dao.mysql.model.system.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ScheduledTaskService {
    private static Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);

    @Autowired
    ConsumerMapper consumerMapper;

    @Autowired
    ConsumerCapitalAccountMapper consumerCapitalAccountMapper;

    @Autowired
    ProfitsLockrepoRatioMapper profitsLockrepoRatioMapper;

    @Autowired
    ProfitsTeamRatioMapper profitsTeamRatioMapper;

    @Autowired
    ConsumerTranceDetailDAO consumerTranceDetailDAO;

    @Autowired
    ConsumerProfitsDailyMapper consumerProfitsDailyMapper;

    @Autowired
    ConsumerProfitsTotalMapper consumerProfitsTotalMapper;

    @Autowired
    ProfitsDailyMapper profitsDailyMapper;

    @Autowired
    ProfitsTotalMapper profitsTotalMapper;

    @Autowired
    ConsumerCountDailyMapper consumerCountDailyMapper;

    @Autowired
    ConsumerCountTotalMapper consumerCountTotalMapper;

    @Autowired
    ConsumerCapitalDailyMapper consumerCapitalDailyMapper;

    @Autowired
    ConsumerCapitalTotalMapper consumerCapitalTotalMapper;

    @Autowired
    CapitalDailyMapper capitalDailyMapper;
    @Autowired
    CapitalTotalMapper capitalTotalMapper;

    @Autowired
    ConsumerTeamMapper consumerTeamMapper;

    @Autowired
    FeeDailyMapper feeDailyMapper;

    @Autowired
    FeeTotalMapper feeTotalMapper;

    @Autowired
    LockrepoDestroyDailyMapper lockrepoDestroyDailyMapper;

    @Autowired
    LockrepoDestroyTotalMapper lockrepoDestroyTotalMapper;

    @Autowired
    SysParameterMapper sysParameterMapper;
    @Autowired
    SysDictionaryMapper sysDictionaryMapper;
    @Autowired
    ConsumerSettingsMapper consumerSettingsMapper;

    SettlementService settlementService = new SettlementService();

    public void lockrepoProfits() {
        //每日收益上限
        BigDecimal limit = getSysParameter(Constants.SYSTEM_PARAMETER.DAILY_PROFITS_PARAM.DAILY_PROFITS_LIMIT);
        BigDecimal ratioLimit = getSysParameter(Constants.SYSTEM_PARAMETER.DESTROY_LOCKREPO_PARAM.DESTROY_LOCKREPO_LIMIT);
        //如果没有配置，则每日收益上限为1000
        limit = limit == null ? Constants.SYSTEM_PARAMETER.DEFAULT_LIMIT.DEFAULT_LIMIT : limit;
        ratioLimit = ratioLimit == null ? Constants.SYSTEM_PARAMETER.DEFAULT_LIMIT.DEFAULT_LIMIT : ratioLimit;
        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
        //超过每日收益上限的用户不计算锁仓收益
        consumerCapitalAccountExample.createCriteria().andProfitsTodayLessThan(limit);

        List<ConsumerCapitalAccount> capitalAccounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
        if (capitalAccounts == null || capitalAccounts.isEmpty()) {
            return;
        }
        ProfitsLockrepoRatioExample profitsLockrepoRatioExample = new ProfitsLockrepoRatioExample();
        List<ProfitsRatio> lockParamList = profitsLockrepoRatioMapper.selectByExample(profitsLockrepoRatioExample);

        List<ConsumerTranceDetail> tranceDetails = new ArrayList<>();

        for (ConsumerCapitalAccount account : capitalAccounts) {
            BigDecimal lockProfits = settlementService.lockProfit(account, lockParamList, ratioLimit);
            //与每日收益上限比较
            lockProfits = limit.compareTo(lockProfits.add(account.getProfitsToday())) > 0 ? lockProfits : limit.subtract(account.getProfitsToday());
            if (lockProfits.compareTo(new BigDecimal(0)) < 1) {
                continue;
            }

            //查询当前账户用户信息
            ConsumerWithBLOBs consumer = consumerMapper.selectByPrimaryKey(account.getConsumerId());

            //查询当前用户设置信息
            ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
            consumerSettingsExample.createCriteria().andConsumerIdEqualTo(account.getConsumerId());
            List<ConsumerSettings> settings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
            if (settings.size() == 0 || settings.get(0) == null) {
                throw new RuntimeException("当前用户设置信息不存在");
            }
            ConsumerSettings setting= settings.get(0);
            //本次交易流水号
            String tranNo = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            //查询内部账号余额
            String interAcoountAddress = getInterAcoountAddress();
            BuguQuery<ConsumerTranceDetail> query = consumerTranceDetailDAO.query();
            List<ConsumerTranceDetail> results = query.in("transferAddressFrom", interAcoountAddress).sortDesc("createdTime").results();

            //内部账 付
            tranceDetails.add(createTranceDetail(tranNo, null,lockProfits.negate(), Constants.INCOME.VALUE,
                    Constants.INCOME.SourceType.LOCK_REPO_PROFITS.getValue(), interAcoountAddress, account.getFloatingAddress(),account.getId()
                    ,results.size() == 0 || results.get(0) == null ? BigDecimal.ZERO.subtract(lockProfits) : results.get(0).getBalance().subtract(lockProfits)
            ,null,consumer.getPhoneNo(),null,setting.getNickName()==null?consumer.getPhoneNo():setting.getNickName()));
            //收
            tranceDetails.add(createTranceDetail(tranNo,account.getId(), lockProfits, Constants.INCOME.VALUE,
                    Constants.INCOME.SourceType.LOCK_REPO_PROFITS.getValue(), account.getFloatingAddress(), interAcoountAddress, null
                    ,account.getFloatingFunds().add(lockProfits),consumer.getPhoneNo(),null,setting.getNickName()==null?consumer.getPhoneNo():setting.getNickName(),null));

            account.setFloatingFunds(account.getFloatingFunds().add(lockProfits));
            account.setProfitsToday(account.getProfitsToday().add(lockProfits));
            account.setAccumulatedProfits(account.getAccumulatedProfits().add(lockProfits));
        }
        if (!tranceDetails.isEmpty()) {
            consumerTranceDetailDAO.insert(tranceDetails);
        }
        for (ConsumerCapitalAccount account : capitalAccounts) {
            consumerCapitalAccountMapper.updateByPrimaryKey(account);
        }
    }

//    public void refereeProfits() {
//        BigDecimal limit = getSysParameter(Constants.SYSTEM_PARAMETER.DAILY_PROFITS_PARAM.DAILY_PROFITS_LIMIT);
//        limit = limit == null ? Constants.SYSTEM_PARAMETER.DEFAULT_LIMIT.DEFAULT_LIMIT : limit;
//        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
//        consumerCapitalAccountExample.createCriteria().andProfitsTodayLessThan(limit);
//        List<ConsumerCapitalAccount> capitalAccounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
//        BigDecimal ratio = getSysParameter(Constants.SYSTEM_PARAMETER.REFEREE_PROFITS_PARAM.REFEREE_PROFITS_RATIO);
//        List<ConsumerTranceDetail> tranceDetails = new ArrayList<>();
//        Calendar today = getCalendarToday();
//        Calendar tomorrow = getCalendarToday();
//        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
//        for (ConsumerCapitalAccount account : capitalAccounts) {
//            List<Integer> refereedAccountIds = consumerCapitalAccountMapper.selectRefereeTeamAccountId(account.getConsumerId());
//            Double refereedTeamProfitsTotal = consumerTranceDetailDAO.sum("funds", consumerTranceDetailDAO.query()
//                    .greaterThan("createdTime", today.getTime())
//                    .lessThan("createdTime", tomorrow.getTime())
//                    .is("tranceType", Constants.INCOME.VALUE)
//                    .is("sourceType", Constants.INCOME.SourceType.TEAM_PROFITS.getValue())
//                    .in("accountId", refereedAccountIds));
//            BigDecimal refereeProfits = settlementService.recommendProfit(new BigDecimal(refereedTeamProfitsTotal), ratio);
//            refereeProfits = limit.compareTo(refereeProfits.add(account.getProfitsToday())) > 0 ? refereeProfits : limit.subtract(account.getProfitsToday());
//            String interAcoountAddress = getInterAcoountAddress();
//            tranceDetails.add(createTranceDetail(account.getId(), refereeProfits, Constants.INCOME.VALUE, Constants.INCOME.SourceType.REFEREE_PROFITS.getValue(),interAcoountAddress,account.getProfitsAddress()));
//            account.setProfitsToday(account.getProfitsToday().add(refereeProfits));
//            account.setAccumulatedProfits(account.getAccumulatedProfits().add(refereeProfits));
//        }
//        if (!tranceDetails.isEmpty()) {
//            consumerTranceDetailDAO.insert(tranceDetails);
//        }
//        for (ConsumerCapitalAccount account : capitalAccounts) {
//            consumerCapitalAccountMapper.updateByPrimaryKey(account);
//        }
//    }

    private void deleteScheduleDataOfToday() {
        Calendar today = getCalendarToday();
        Calendar tomorrow = getCalendarToday();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        BuguQuery<ConsumerTranceDetail> query = consumerTranceDetailDAO.query()
                .greaterThan("createdTime", today.getTime())
                .lessThan("createdTime", tomorrow.getTime())
                .notEquals("accountId", null)
                .in("sourceType", Constants.INCOME.SourceType.RELEASE_LOCK_REPO.getValue(), Constants.EXPENSE.SourceType.DESTROY.getValue(), Constants.EXPENSE.SourceType.REINVEST.getValue(), Constants.INCOME.SourceType.TEAM_PROFITS.getValue(), Constants.INCOME.SourceType.LOCK_REPO_PROFITS.getValue(), Constants.INCOME.SourceType.REFEREE_PROFITS.getValue());
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
                    break;
                // 锁仓收益
                case "2":
                    account.setAccumulatedProfits(account.getAccumulatedProfits().subtract(detail.getFunds()));
                    break;
                // 推荐收益
                case "4":
                    account.setAccumulatedProfits(account.getAccumulatedProfits().subtract(detail.getFunds()));
                    break;
                // 锁仓资产释放
                case "5":
                    account.setFloatingFunds(account.getFloatingFunds().subtract(detail.getFunds()));
                    account.setLockrepoFunds(account.getLockrepoFunds().add(detail.getFunds()));
                    break;
                // 复投锁仓
                case "7":
                    account.setFloatingFunds(account.getFloatingFunds().add(detail.getFunds()));
                    account.setLockrepoFunds(account.getLockrepoFunds().subtract(detail.getFunds()));
                    break;
                // 锁仓资金销毁
                case "8":
                    account.setLockrepoFunds(account.getLockrepoFunds().add(detail.getFunds()));
                    account.setAccumulatedProfits(account.getAccumulatedProfits().add(detail.getFunds()));
                    break;
                default:
                    break;
            }
            accounts.put(detail.getAccountId(), account);
        }

        ConsumerProfitsDailyExample consumerProfitsDailyExample = new ConsumerProfitsDailyExample();
        consumerProfitsDailyExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        consumerProfitsDailyMapper.deleteByExample(consumerProfitsDailyExample);

        ConsumerProfitsTotalExample consumerProfitsTotalExample = new ConsumerProfitsTotalExample();
        consumerProfitsTotalExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        consumerProfitsTotalMapper.deleteByExample(consumerProfitsTotalExample);

        ProfitsDailyExample profitsDailyExample = new ProfitsDailyExample();
        profitsDailyExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        profitsDailyMapper.deleteByExample(profitsDailyExample);

        ProfitsTotalExample profitsTotalExample = new ProfitsTotalExample();
        profitsTotalExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        profitsTotalMapper.deleteByExample(profitsTotalExample);

        ConsumerCountDailyExample consumerCountDailyExample = new ConsumerCountDailyExample();
        consumerCountDailyExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        consumerCountDailyMapper.deleteByExample(consumerCountDailyExample);

        ConsumerCountTotalExample consumerCountTotalExample = new ConsumerCountTotalExample();
        consumerCountTotalExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        consumerCountTotalMapper.deleteByExample(consumerCountTotalExample);

        ConsumerCapitalDailyExample consumerCapitalDaily = new ConsumerCapitalDailyExample();
        consumerCapitalDaily.createCriteria().andCreateTimeBetween(today.getTime(), tomorrow.getTime());
        consumerCapitalDailyMapper.deleteByExample(consumerCapitalDaily);

        ConsumerCapitalTotalExample consumerCapitalTotalExample = new ConsumerCapitalTotalExample();
        consumerCapitalTotalExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        consumerCapitalTotalMapper.deleteByExample(consumerCapitalTotalExample);

        CapitalDailyExample capitalDailyExample = new CapitalDailyExample();
        capitalDailyExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        capitalDailyMapper.deleteByExample(capitalDailyExample);

        CapitalTotalExample capitalTotalExample = new CapitalTotalExample();
        capitalTotalExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        capitalTotalMapper.deleteByExample(capitalTotalExample);

        ConsumerTeamExample consumerTeamExample = new ConsumerTeamExample();
        consumerTeamExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        consumerTeamMapper.deleteByExample(consumerTeamExample);

        FeeDailyExample feeDailyExample = new FeeDailyExample();
        feeDailyExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        feeDailyMapper.deleteByExample(feeDailyExample);

        FeeTotalExample feeTotalExample = new FeeTotalExample();
        feeTotalExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        feeTotalMapper.deleteByExample(feeTotalExample);

        LockrepoDestroyDailyExample lockrepoDestroyDailyExample = new LockrepoDestroyDailyExample();
        lockrepoDestroyDailyExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        lockrepoDestroyDailyMapper.deleteByExample(lockrepoDestroyDailyExample);

        LockrepoDestroyTotalExample lockrepoDestroyTotalExample = new LockrepoDestroyTotalExample();
        lockrepoDestroyTotalExample.createCriteria().andCreatedTimeBetween(today.getTime(), tomorrow.getTime());
        lockrepoDestroyTotalMapper.deleteByExample(lockrepoDestroyTotalExample);

        for (ConsumerCapitalAccount a : accounts.values()) {
            consumerCapitalAccountMapper.updateByPrimaryKey(a);
        }
        consumerTranceDetailDAO.remove(query);
    }

    public void teamProfits() {
        // deleteScheduleDataOfToday();

        BigDecimal limit = getSysParameter(Constants.SYSTEM_PARAMETER.DAILY_PROFITS_PARAM.DAILY_PROFITS_LIMIT);
        BigDecimal ratioLimit = getSysParameter(Constants.SYSTEM_PARAMETER.DESTROY_LOCKREPO_PARAM.DESTROY_LOCKREPO_LIMIT);
        limit = limit == null ? Constants.SYSTEM_PARAMETER.DEFAULT_LIMIT.DEFAULT_LIMIT : limit;
        ratioLimit = ratioLimit == null ? Constants.SYSTEM_PARAMETER.DEFAULT_LIMIT.DEFAULT_LIMIT : ratioLimit;
        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
        consumerCapitalAccountExample.createCriteria().andProfitsTodayLessThan(limit);
        List<ConsumerCapitalAccount> capitalAccounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
        if (capitalAccounts == null || capitalAccounts.isEmpty()) {
            return;
        }
        ProfitsTeamRatioExample profitsTeamRatioExample = new ProfitsTeamRatioExample();
        List<ProfitsRatio> teamParamList = profitsTeamRatioMapper.selectByExample(profitsTeamRatioExample);
        List<ConsumerTranceDetail> tranceDetails = new ArrayList<>();

        for (ConsumerCapitalAccount account : capitalAccounts) {
            List<ConsumerCapitalAccount> leftTeam = consumerCapitalAccountMapper.selectLeftTeam(account.getConsumerId());
            List<ConsumerCapitalAccount> rightTeam = consumerCapitalAccountMapper.selectRightTeam(account.getConsumerId());
            BigDecimal teamProfits = settlementService.teamProfit(account, leftTeam, rightTeam, teamParamList, ratioLimit);

            teamProfits = limit.compareTo(teamProfits.add(account.getProfitsToday())) > 0 ? teamProfits : limit.subtract(account.getProfitsToday());

            if (teamProfits.compareTo(new BigDecimal(0)) < 1) {
                continue;
            }
            //查询当前账户用户信息
            ConsumerWithBLOBs consumer = consumerMapper.selectByPrimaryKey(account.getConsumerId());

            //查询当前用户设置信息
            ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
            consumerSettingsExample.createCriteria().andConsumerIdEqualTo(account.getConsumerId());
            List<ConsumerSettings> settings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
            if (settings.size() == 0 || settings.get(0) == null) {
                throw new RuntimeException("当前用户设置信息不存在");
            }
            ConsumerSettings setting= settings.get(0);

            String interAcoountAddress = getInterAcoountAddress();
            //本次交易流水号
            String tranNo = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            //查询内部账号余额
            BuguQuery<ConsumerTranceDetail> query = consumerTranceDetailDAO.query();
            List<ConsumerTranceDetail> results = query.in("transferAddressFrom", interAcoountAddress).sortDesc("createdTime").results();


            //付 内部账
            tranceDetails.add(createTranceDetail(tranNo, null,teamProfits.negate(), Constants.INCOME.VALUE, Constants.INCOME.SourceType.TEAM_PROFITS.getValue(),
                    interAcoountAddress, account.getFloatingAddress(), account.getId(),results.size() == 0 || results.get(0) == null ? BigDecimal.ZERO.subtract(teamProfits) : results.get(0).getBalance().subtract(teamProfits)
                    ,null,consumer.getPhoneNo(),null,setting.getNickName()==null?consumer.getPhoneNo():setting.getNickName()));
            //收 profitsAddress
            tranceDetails.add(createTranceDetail(tranNo,account.getId(), teamProfits, Constants.INCOME.VALUE, Constants.INCOME.SourceType.TEAM_PROFITS.getValue(), account.getFloatingAddress(), interAcoountAddress, null
                    ,account.getFloatingFunds().add(teamProfits),consumer.getPhoneNo(),null,setting.getNickName()==null?consumer.getPhoneNo():setting.getNickName(),null));
            account.setFloatingFunds(account.getFloatingFunds().add(teamProfits));
            account.setProfitsToday(teamProfits);
            account.setAccumulatedProfits(account.getAccumulatedProfits().add(teamProfits));
        }
        if (!tranceDetails.isEmpty()) {
            consumerTranceDetailDAO.insert(tranceDetails);
        }
        for (ConsumerCapitalAccount account : capitalAccounts) {
            consumerCapitalAccountMapper.updateByPrimaryKey(account);
        }
    }

    private ConsumerTranceDetail createTranceDetail(String tranNo,Integer accountId, BigDecimal funds, String transType, String sourceType, String addressFrom, String addressTo,
                                                    Integer transferConsumerId,BigDecimal balance,String phoneFrom,String phoneTo,String nickNameFrom,String nickNameTo ) {
        ConsumerTranceDetail tranceDetail = new ConsumerTranceDetail();
        tranceDetail.setAccountId(accountId);
        tranceDetail.setTranceNo(tranNo);
        tranceDetail.setFunds(funds);
        tranceDetail.setTranceType(transType);
        tranceDetail.setSourceType(sourceType);
        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        tranceDetail.setCreatedTime(yestrtdayDate);
        tranceDetail.setUpdatedTime(yestrtdayDate);
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

    private BigDecimal getSysParameter(String parameterCode) {
        SysParameterExample sysParameterExample = new SysParameterExample();
        sysParameterExample.createCriteria().andParamCodeEqualTo(parameterCode);
        List<SysParameter> referee = sysParameterMapper.selectByExample(sysParameterExample);
        BigDecimal ratio = new BigDecimal(0);
        if (referee != null && !referee.isEmpty()) {
            ratio = referee.get(0).getParamValue();
        }
        return ratio;
    }

    private CapitalTotal createCapitalTotal(CapitalTotal total, CapitalDaily capitalDaily) {
        CapitalTotal capitalTotal = new CapitalTotal();
        capitalTotal.setFloatingFunds(total.getFloatingFunds().add(capitalDaily.getFloatingFunds()));
        capitalTotal.setLockrepoFunds(total.getLockrepoFunds().add(capitalDaily.getLockrepoFunds()));
        capitalTotal.setProfitsFunds(total.getProfitsFunds().add(capitalDaily.getProfitsFunds()));
        capitalTotal.setTotal(total.getTotal().add(capitalDaily.getTotal()));
        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        capitalTotal.setCreatedTime(yestrtdayDate);
        return capitalTotal;
    }

    private CapitalDaily createCapitalDaily(BigDecimal floatingFunds, BigDecimal lockrepoFunds, BigDecimal profitsFunds) {
        CapitalDaily capitalDaily = new CapitalDaily();
        capitalDaily.setFloatingFunds(floatingFunds.add(profitsFunds));
        capitalDaily.setLockrepoFunds(lockrepoFunds);
        capitalDaily.setProfitsFunds(new BigDecimal(0));
        capitalDaily.setTotal(floatingFunds.add(lockrepoFunds).add(profitsFunds));
        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        capitalDaily.setCreatedTime(yestrtdayDate);
        return capitalDaily;
    }

    private ConsumerCapitalTotal createConsumerCapitalTotal(ConsumerCapitalTotal total, ConsumerCapitalDaily consumerCapitalDaily) {
        ConsumerCapitalTotal consumerCapitalTotal = new ConsumerCapitalTotal();
        consumerCapitalTotal.setAccountId(consumerCapitalDaily.getAccountId());
        consumerCapitalTotal.setConsumerId(consumerCapitalDaily.getConsumerId());
        consumerCapitalTotal.setFloatingFunds(total.getFloatingFunds().add(consumerCapitalDaily.getFloatingFunds()));
        consumerCapitalTotal.setLockrepoFunds(total.getLockrepoFunds().add(consumerCapitalDaily.getLockrepoFunds()));
        consumerCapitalTotal.setProfitsFunds(total.getProfitsFunds().add(consumerCapitalDaily.getProfitsFunds()));
        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        consumerCapitalTotal.setCreatedTime(yestrtdayDate);
        consumerCapitalTotal.setUpdatedTime(yestrtdayDate);
        return consumerCapitalTotal;
    }

    private ConsumerCapitalDaily createConsumerCapitalDaily(Integer accountId, Integer consumerId, BigDecimal floatingFunds, BigDecimal lockrepoFunds, BigDecimal profitsFunds) {
        ConsumerCapitalDaily consumerCapitalDaily = new ConsumerCapitalDaily();
        consumerCapitalDaily.setAccountId(accountId);
        consumerCapitalDaily.setConsumerId(consumerId);
        consumerCapitalDaily.setFloatingFunds(floatingFunds.add(profitsFunds));
        consumerCapitalDaily.setLockrepoFunds(lockrepoFunds);
        consumerCapitalDaily.setProfitsFunds(new BigDecimal(0));
        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        consumerCapitalDaily.setCreateTime(yestrtdayDate);
        consumerCapitalDaily.setUpdatedTime(yestrtdayDate);
        return consumerCapitalDaily;
    }

    private BigDecimal[] getCapitalAndProfitsDaily(BigDecimal[] capitalAndProfitsDaily, BigDecimal[] consumerCapitalAndProfitsDaily) {
        capitalAndProfitsDaily[0] = capitalAndProfitsDaily[0].add(consumerCapitalAndProfitsDaily[0]);
        capitalAndProfitsDaily[1] = capitalAndProfitsDaily[1].add(consumerCapitalAndProfitsDaily[1]);
        capitalAndProfitsDaily[2] = capitalAndProfitsDaily[2].add(consumerCapitalAndProfitsDaily[2]);
        capitalAndProfitsDaily[3] = capitalAndProfitsDaily[3].add(consumerCapitalAndProfitsDaily[3]);
        capitalAndProfitsDaily[4] = capitalAndProfitsDaily[4].add(consumerCapitalAndProfitsDaily[4]);
        capitalAndProfitsDaily[5] = capitalAndProfitsDaily[5].add(consumerCapitalAndProfitsDaily[5]);
        capitalAndProfitsDaily[6] = capitalAndProfitsDaily[6].add(consumerCapitalAndProfitsDaily[6]);
        capitalAndProfitsDaily[7] = capitalAndProfitsDaily[7].add(consumerCapitalAndProfitsDaily[7]);
        return capitalAndProfitsDaily;
    }

    private BigDecimal[] getConsumerCapitalAndProfitsDaily(Integer accountId, Map<String, BigDecimal> transDetailMap) {
        BigDecimal trans_in = transDetailMap.get(accountId + "_" + Constants.INCOME.VALUE + "_" + Constants.INCOME.SourceType.TRANS_IN.getValue());
        BigDecimal lockrepo_profits = transDetailMap.get(accountId + "_" + Constants.INCOME.VALUE + "_" + Constants.INCOME.SourceType.LOCK_REPO_PROFITS.getValue());
        BigDecimal referee_profits = transDetailMap.get(accountId + "_" + Constants.INCOME.VALUE + "_" + Constants.INCOME.SourceType.REFEREE_PROFITS.getValue());
        BigDecimal team_profits = transDetailMap.get(accountId + "_" + Constants.INCOME.VALUE + "_" + Constants.INCOME.SourceType.TEAM_PROFITS.getValue());
        BigDecimal release = transDetailMap.get(accountId + "_" + Constants.INCOME.VALUE + "_" + Constants.INCOME.SourceType.RELEASE_LOCK_REPO.getValue());
        BigDecimal lock_float = transDetailMap.get(accountId + "_" + Constants.INCOME.VALUE + "_" + Constants.INCOME.SourceType.LOCK_FLOAT.getValue());

        BigDecimal trans_out = transDetailMap.get(accountId + "_" + Constants.EXPENSE.VALUE + "_" + Constants.EXPENSE.SourceType.TRANS_OUT.getValue());
        BigDecimal reinvest = transDetailMap.get(accountId + "_" + Constants.EXPENSE.VALUE + "_" + Constants.EXPENSE.SourceType.REINVEST.getValue());
        BigDecimal lockrepo_destroy = transDetailMap.get(accountId + "_" + Constants.EXPENSE.VALUE + "_" + Constants.EXPENSE.SourceType.DESTROY.getValue());
        BigDecimal fee = transDetailMap.get(accountId + "_" + Constants.EXPENSE.VALUE + "_" + Constants.EXPENSE.SourceType.TRANS_FEE.getValue());
        BigDecimal float_lock = transDetailMap.get(accountId + "_" + Constants.EXPENSE.VALUE + "_" + Constants.EXPENSE.SourceType.FLOAT_LOCK.getValue());

        trans_in = trans_in == null ? new BigDecimal(0) : trans_in;
        lockrepo_profits = lockrepo_profits == null ? new BigDecimal(0) : lockrepo_profits;
        referee_profits = referee_profits == null ? new BigDecimal(0) : referee_profits;
        team_profits = team_profits == null ? new BigDecimal(0) : team_profits;
        release = release == null ? new BigDecimal(0) : release;
        lock_float = lock_float == null ? new BigDecimal(0) : lock_float;

        trans_out = trans_out == null ? new BigDecimal(0) : trans_out;
        reinvest = reinvest == null ? new BigDecimal(0) : reinvest;
        lockrepo_destroy = lockrepo_destroy == null ? new BigDecimal(0) : lockrepo_destroy;
        fee = fee == null ? new BigDecimal(0) : fee;
        float_lock = float_lock == null ? new BigDecimal(0) : float_lock;


        BigDecimal[] result = new BigDecimal[8];

        result[0] = trans_in.add(release).subtract(trans_out).subtract(reinvest).subtract(fee).subtract(float_lock);
        result[1] = reinvest.subtract(release).subtract(lockrepo_destroy).add(lock_float);
        result[2] = lockrepo_profits.add(referee_profits).add(team_profits);
        result[3] = lockrepo_profits;
        result[4] = referee_profits;
        result[5] = team_profits;
        result[6] = lockrepo_destroy;
        result[7] = fee;
        return result;
    }

    private ConsumerProfitsDaily createConsumerProfitsDaily(Integer consumerId, Integer accountId, BigDecimal lockrepo, BigDecimal referee, BigDecimal team) {
        ConsumerProfitsDaily consumerProfitsDaily = new ConsumerProfitsDaily();
        consumerProfitsDaily.setAccountId(accountId);
        consumerProfitsDaily.setConsumerId(consumerId);
        consumerProfitsDaily.setProfitsLockrepo(lockrepo);
        consumerProfitsDaily.setProfitsReferee(referee);
        consumerProfitsDaily.setProfitsTeam(team);
        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        consumerProfitsDaily.setCreatedTime(yestrtdayDate);
        return consumerProfitsDaily;
    }

    private ConsumerProfitsTotal createConsumerProfitsTotal(ConsumerProfitsTotal total, ConsumerProfitsDaily consumerProfitsDaily) {
        ConsumerProfitsTotal consumerProfitsTotal = new ConsumerProfitsTotal();
        consumerProfitsTotal.setAccountId(consumerProfitsDaily.getAccountId());
        consumerProfitsTotal.setConsumerId(consumerProfitsDaily.getConsumerId());
        consumerProfitsTotal.setProfitsLockrepo(total.getProfitsLockrepo().add(consumerProfitsDaily.getProfitsLockrepo()));
        consumerProfitsTotal.setProfitsReferee(total.getProfitsReferee().add(consumerProfitsDaily.getProfitsReferee()));
        consumerProfitsTotal.setProfitsTeam(total.getProfitsTeam().add(consumerProfitsDaily.getProfitsTeam()));
        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        consumerProfitsTotal.setCreatedTime(yestrtdayDate);
        return consumerProfitsTotal;
    }

    private ProfitsDaily createProfitsDaily(BigDecimal lockrepoTotal, BigDecimal refereeTotal, BigDecimal teamTotal) {
        ProfitsDaily profitsDaily = new ProfitsDaily();
        profitsDaily.setProfitsLockrepo(lockrepoTotal);
        profitsDaily.setProfitsReferee(refereeTotal);
        profitsDaily.setProfitsTeam(teamTotal);
        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        profitsDaily.setCreatedTime(yestrtdayDate);
        return profitsDaily;
    }

    private ProfitsTotal createProfitsTotal(ProfitsTotal total, ProfitsDaily daily) {
        ProfitsTotal profitsTotal = new ProfitsTotal();
        profitsTotal.setProfitsLockrepo(total.getProfitsLockrepo().add(daily.getProfitsLockrepo()));
        profitsTotal.setProfitsReferee(total.getProfitsReferee().add(daily.getProfitsReferee()));
        profitsTotal.setProfitsTeam(total.getProfitsTeam().add(daily.getProfitsTeam()));
        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        profitsTotal.setCreatedTime(yestrtdayDate);
        return profitsTotal;
    }

    private Calendar getCalendarToday() {
        Calendar c = Calendar.getInstance();
        try {
            DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
            Date date = d.parse(d.format(new Date()));
            c.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void releaseLockrepo() {
        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
        consumerCapitalAccountExample.createCriteria().andReleaseFlagEqualTo("1");
        List<ConsumerCapitalAccount> capitalAccounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
        if (capitalAccounts == null || capitalAccounts.isEmpty()) {
            return;
        }
        BigDecimal releaseRatio = getSysParameter(Constants.SYSTEM_PARAMETER.RELEASE_RATIO_PARAM.RELEASE_RATIO);
        List<ConsumerTranceDetail> tranceDetails = new ArrayList<>();
        for (ConsumerCapitalAccount account : capitalAccounts) {
            BigDecimal releaseLockrepo = settlementService.release(account, releaseRatio);
            if (releaseLockrepo.compareTo(new BigDecimal(0)) < 1) {
                continue;
            }
            account.setFloatingFunds(account.getFloatingFunds().add(releaseLockrepo));
            account.setLockrepoFunds(account.getLockrepoFunds().subtract(releaseLockrepo));

            //查询当前账户用户信息
            ConsumerWithBLOBs consumer = consumerMapper.selectByPrimaryKey(account.getConsumerId());

            //查询当前用户设置信息
            ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
            consumerSettingsExample.createCriteria().andConsumerIdEqualTo(account.getConsumerId());
            List<ConsumerSettings> settings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
            if (settings.size() == 0 || settings.get(0) == null) {
                throw new RuntimeException("当前用户设置信息不存在");
            }
            ConsumerSettings setting= settings.get(0);

            //本次交易流水号
            String tranNo = UUID.randomUUID().toString().replace("-", "").toLowerCase();

            //消费地址 收
            tranceDetails.add(createTranceDetail(tranNo,account.getId(), releaseLockrepo, Constants.INCOME.VALUE, Constants.INCOME.SourceType.RELEASE_LOCK_REPO.getValue(), account.getFloatingAddress(), account.getLockrepoAddress(), null
            ,account.getFloatingFunds().add(releaseLockrepo),consumer.getPhoneNo(),consumer.getPhoneNo(),setting.getNickName()==null?consumer.getPhoneNo():setting.getNickName(),setting.getNickName()==null?consumer.getPhoneNo():setting.getNickName()));
            //锁仓地址 付
            tranceDetails.add(createTranceDetail(tranNo,account.getId(), releaseLockrepo, Constants.INCOME.VALUE, Constants.EXPENSE.SourceType.LOCKREPO_RELESE.getValue(), account.getLockrepoAddress(), account.getFloatingAddress(), null
            ,account.getLockrepoFunds().subtract(releaseLockrepo),consumer.getPhoneNo(),consumer.getPhoneNo(),setting.getNickName()==null?consumer.getPhoneNo():setting.getNickName(),setting.getNickName()==null?consumer.getPhoneNo():setting.getNickName()));
        }
        if (!tranceDetails.isEmpty()) {
            consumerTranceDetailDAO.insert(tranceDetails);
        }
        for (ConsumerCapitalAccount account : capitalAccounts) {
            consumerCapitalAccountMapper.updateByPrimaryKey(account);
        }
    }

    public void destroyLockrepo() {
        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
        List<ConsumerCapitalAccount> capitalAccounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
        if (capitalAccounts == null || capitalAccounts.isEmpty()) {
            return;
        }
        BigDecimal destroyTimes = getSysParameter(Constants.SYSTEM_PARAMETER.DESTROY_LOCKREPO_PARAM.DESTROY_LOCKREPO_TIMES);
        BigDecimal destroyLimit = getSysParameter(Constants.SYSTEM_PARAMETER.DESTROY_LOCKREPO_PARAM.DESTROY_LOCKREPO_LIMIT);
        BigDecimal reinvestLimit = getSysParameter(Constants.SYSTEM_PARAMETER.REINVEST_PARAM.REINVEST_LIMIT);
        List<ConsumerTranceDetail> tranceDetails = new ArrayList<>();
        for (ConsumerCapitalAccount account : capitalAccounts) {
            BigDecimal destroyLockrepo = settlementService.destoryLock(account, destroyTimes, destroyLimit);
            if (destroyLockrepo.compareTo(new BigDecimal(0)) < 1) {
                continue;
            }
            account.setLockrepoFunds(account.getLockrepoFunds().subtract(destroyLockrepo));
            account.setAccumulatedProfits(new BigDecimal(0));

            //获取内部账地址
            String interAcoountAddress = getInterAcoountAddress();

            //查询当前账户用户信息
            ConsumerWithBLOBs consumer = consumerMapper.selectByPrimaryKey(account.getConsumerId());

            //查询当前用户设置信息
            ConsumerSettingsExample consumerSettingsExample = new ConsumerSettingsExample();
            consumerSettingsExample.createCriteria().andConsumerIdEqualTo(account.getConsumerId());
            List<ConsumerSettings> settings = consumerSettingsMapper.selectByExample(consumerSettingsExample);
            if (settings.size() == 0 || settings.get(0) == null) {
                throw new RuntimeException("当前用户设置信息不存在");
            }
            ConsumerSettings setting= settings.get(0);
            //本次交易流水号
            String tranNo = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            //查询内部账号余额
            BuguQuery<ConsumerTranceDetail> query = consumerTranceDetailDAO.query();
            List<ConsumerTranceDetail> results = query.in("transferAddressFrom", interAcoountAddress).sortDesc("createdTime").results();


            //锁仓  付
            tranceDetails.add(createTranceDetail(tranNo,account.getId(), destroyLockrepo, Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.DESTROY.getValue(), account.getLockrepoAddress(), interAcoountAddress, null
            ,account.getLockrepoFunds().subtract(destroyLockrepo),consumer.getPhoneNo(),null,setting.getNickName()==null?consumer.getPhoneNo():setting.getNickName(),null));
            //内部账 收
            tranceDetails.add(createTranceDetail(tranNo,null, destroyLockrepo.negate(), Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.DESTROY.getValue(), interAcoountAddress, account.getLockrepoAddress(), account.getId()
                   , results.size() == 0 || results.get(0) == null ? BigDecimal.ZERO.subtract(destroyLockrepo) : results.get(0).getBalance().add(destroyLockrepo)
            ,null,consumer.getPhoneNo(),null,setting.getNickName()==null?consumer.getPhoneNo():setting.getNickName()));

            //自动复投
            if ("1".equals(account.getReinvestFlag())) {
                BigDecimal autoReinvest = settlementService.autoRein(account, reinvestLimit);
                if (autoReinvest.compareTo(new BigDecimal(0)) == -1) {
                    autoReinvest = BigDecimal.ZERO;
                }
                account.setFloatingFunds(account.getFloatingFunds().subtract(autoReinvest));
                account.setLockrepoFunds(account.getLockrepoFunds().add(autoReinvest));
                //流动 付
                tranceDetails.add(createTranceDetail(tranNo,account.getId(), autoReinvest, Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.REINVEST.getValue(), account.getFloatingAddress(), account.getLockrepoAddress(), null,
                        account.getFloatingFunds().subtract(autoReinvest),null,null,null,null));
                //锁仓  收
                tranceDetails.add(createTranceDetail(tranNo,account.getId(), autoReinvest.negate(), Constants.EXPENSE.VALUE, Constants.EXPENSE.SourceType.REINVEST.getValue(), account.getLockrepoAddress(), account.getFloatingAddress(), null,
                        account.getLockrepoFunds().add(autoReinvest),null,null,null,null
                        ));
            }
        }
        if (!tranceDetails.isEmpty()) {
            consumerTranceDetailDAO.insert(tranceDetails);
        }
        for (ConsumerCapitalAccount account : capitalAccounts) {
            consumerCapitalAccountMapper.updateByPrimaryKey(account);
        }
    }

    public void transCollect() {
        ConsumerCapitalAccountExample consumerCapitalAccountExample = new ConsumerCapitalAccountExample();
        List<ConsumerCapitalAccount> accounts = consumerCapitalAccountMapper.selectByExample(consumerCapitalAccountExample);
        if (accounts == null || accounts.isEmpty()) {
            return;
        }
//        Calendar today = getCalendarToday();
//        Calendar tomorrow = getCalendarToday();
//        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        //获取昨日凌晨到今日凌晨的时间
        Date[] dawnTime = DateUtil.getDawnTime();

        BuguQuery<ConsumerTranceDetail> query = consumerTranceDetailDAO.query();
        List<ConsumerTranceDetail> transDetails;
        Map<String, BigDecimal> consumerProfitsMap = new HashMap<>();

        transDetails = query.greaterThanEquals("createdTime", dawnTime[0])
                .lessThan("createdTime", dawnTime[1])
                .notEquals("accountId", null)
                .results();
        if (transDetails == null || transDetails.isEmpty()) {
            return;
        }
        // transDetails.forEach(detail -> consumerProfitsMap.put(detail.getAccountId() + "_" + detail.getTranceType() + "_" + detail.getSourceType(), detail.getFunds().abs()));
        String key;
        for (ConsumerTranceDetail detail : transDetails) {
            key = detail.getAccountId() + "_" + detail.getTranceType() + "_" + detail.getSourceType();
            logger.info(key+"============="+detail.getFunds());
            if (consumerProfitsMap.containsKey(key))
                consumerProfitsMap.put(key, consumerProfitsMap.get(key).add(detail.getFunds().abs()));
            else
                consumerProfitsMap.put(key, detail.getFunds().abs());
        }
        //打印数据
        for (String s :consumerProfitsMap.keySet() ) {
            logger.info("key : " + s + " value : " + consumerProfitsMap.get(s));
        }
        BigDecimal[] capitalAndProfitsDaily = new BigDecimal[]{new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)};
        List<ConsumerProfitsDaily> consumerProfitsDailies = new ArrayList<>();
        List<ConsumerProfitsTotal> consumerProfitsTotals = new ArrayList<>();
        List<ConsumerCapitalDaily> consumerCapitalDailies = new ArrayList<>();
        List<ConsumerCapitalTotal> consumerCapitalTotals = new ArrayList<>();
        List<ConsumerTeam> consumerTeams = new ArrayList<>();
        ProfitsDaily profitsDaily;
        ProfitsTotal profitsTotal;
        CapitalDaily capitalDaily;
        CapitalTotal capitalTotal;
        for (ConsumerCapitalAccount account : accounts) {
            BigDecimal[] consumerCapitalAndProfitsDaily = getConsumerCapitalAndProfitsDaily(account.getId(), consumerProfitsMap);
            ConsumerProfitsDaily consumerProfitsDaily = createConsumerProfitsDaily(account.getConsumerId(), account.getId(), consumerCapitalAndProfitsDaily[3], consumerCapitalAndProfitsDaily[4], consumerCapitalAndProfitsDaily[5]);
            consumerProfitsDailies.add(consumerProfitsDaily);
            List<ConsumerProfitsTotal> consumerProfitsTotalList = consumerProfitsTotalMapper.selectLatest(account.getConsumerId());
            ConsumerProfitsTotal consumerProfitsTotal = consumerProfitsTotalList == null || consumerProfitsTotalList.isEmpty() ? new ConsumerProfitsTotal() : consumerProfitsTotalList.get(0);
            consumerProfitsTotals.add(createConsumerProfitsTotal(consumerProfitsTotal, consumerProfitsDaily));
            getCapitalAndProfitsDaily(capitalAndProfitsDaily, consumerCapitalAndProfitsDaily);
            ConsumerCapitalDaily consumerCapitalDaily = createConsumerCapitalDaily(account.getId(), account.getConsumerId(), consumerCapitalAndProfitsDaily[0], consumerCapitalAndProfitsDaily[1], consumerCapitalAndProfitsDaily[2]);
            consumerCapitalDailies.add(consumerCapitalDaily);
            List<ConsumerCapitalTotal> consumerCapitalTotalList = consumerCapitalTotalMapper.selectLatest(account.getConsumerId());
            ConsumerCapitalTotal consumerCapitalTotal = consumerCapitalTotalList == null || consumerCapitalTotalList.isEmpty() ? new ConsumerCapitalTotal() : consumerCapitalTotalList.get(0);
            consumerCapitalTotals.add(createConsumerCapitalTotal(consumerCapitalTotal, consumerCapitalDaily));
            ConsumerTeam leftTeam = consumerTeamMapper.selectLeftTeam(account.getConsumerId());
            ConsumerTeam rightTeam = consumerTeamMapper.selectRightTeam(account.getConsumerId());
            consumerTeams.add(createConsumerTeam(leftTeam == null ? new ConsumerTeam(account.getConsumerId(), account.getTeamPosCode()) : leftTeam, rightTeam == null ? new ConsumerTeam() : rightTeam));
        }
        profitsDaily = createProfitsDaily(capitalAndProfitsDaily[3], capitalAndProfitsDaily[4], capitalAndProfitsDaily[5]);
        List<ProfitsTotal> profitsTotals = profitsTotalMapper.selectLatest();
        profitsTotal = profitsTotals == null || profitsTotals.isEmpty() ? new ProfitsTotal() : profitsTotals.get(0);
        profitsTotal = createProfitsTotal(profitsTotal, profitsDaily);

        capitalDaily = createCapitalDaily(capitalAndProfitsDaily[0], capitalAndProfitsDaily[1], capitalAndProfitsDaily[2]);
        List<CapitalTotal> capitalTotals = capitalTotalMapper.selectLatest();
        capitalTotal = capitalTotals == null || capitalTotals.isEmpty() ? new CapitalTotal() : capitalTotals.get(0);
        capitalTotal = createCapitalTotal(capitalTotal, capitalDaily);
        if (!consumerProfitsDailies.isEmpty()) {
            consumerProfitsDailyMapper.batchInsert(consumerProfitsDailies);
        }
        if (!consumerProfitsTotals.isEmpty()) {
            consumerProfitsTotalMapper.batchInsert(consumerProfitsTotals);
        }
        profitsDailyMapper.insert(profitsDaily);
        profitsTotalMapper.insert(profitsTotal);
        capitalDailyMapper.insert(capitalDaily);
        capitalTotalMapper.insert(capitalTotal);


        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        FeeDaily feeDaily = new FeeDaily(capitalAndProfitsDaily[7], yestrtdayDate);
        LockrepoDestroyDaily lockrepoDestroyDaily = new LockrepoDestroyDaily(capitalAndProfitsDaily[6],yestrtdayDate);
        feeDailyMapper.insert(feeDaily);
        feeTotalMapper.insertIncrease(capitalAndProfitsDaily[7], yestrtdayDate);
        lockrepoDestroyDailyMapper.insert(lockrepoDestroyDaily);
        lockrepoDestroyTotalMapper.insertIncrease(capitalAndProfitsDaily[6], yestrtdayDate);


        ConsumerExample consumerExample = new ConsumerExample();
        consumerExample.createCriteria().andCreateTimeBetween(dawnTime[0], dawnTime[1]);
        Long consumerCount = consumerMapper.countByExample(consumerExample);
        ConsumerCountDaily consumerCountDaily = new ConsumerCountDaily();
        consumerCountDaily.setCount(consumerCount.intValue());
        consumerCountDaily.setCreatedTime(yestrtdayDate);

        consumerCountDailyMapper.insert(consumerCountDaily);
        consumerCountTotalMapper.insertIncrease(consumerCount.intValue(),yestrtdayDate);
        if (!consumerCapitalDailies.isEmpty()) {
            consumerCapitalDailyMapper.batchInsert(consumerCapitalDailies);
        }
        if (!consumerCapitalTotals.isEmpty()) {
            consumerCapitalTotalMapper.batchInsert(consumerCapitalTotals);
        }
        if (!consumerTeams.isEmpty()) {
            consumerTeamMapper.batchInsert(consumerTeams);
        }

        //更新现价
        //比率值增量默认为0.01
        BigDecimal coinIncr=new BigDecimal(0.01);
        // 获取比例值增量
        SysParameterExample coinExample=new SysParameterExample();
        coinExample.createCriteria().andParamCodeEqualTo("coin_incr");
        List<SysParameter> coinIncrParams = sysParameterMapper.selectByExample(coinExample);
        if(coinIncrParams.size()>0&&coinIncrParams.get(0)!=null){
            coinIncr=coinIncrParams.get(0).getParamValue();
        }
        //现价默认为1
        BigDecimal coinPriceBig = BigDecimal.ONE;

        SysParameterExample sysParameterExample=new SysParameterExample();
        sysParameterExample.createCriteria().andParamCodeEqualTo("current_price");
        List<SysParameter> sysParameters = sysParameterMapper.selectByExample(sysParameterExample);
        if(sysParameters.size()<=0||sysParameters.get(0)==null){
            SysParameterExample coinPrice = new SysParameterExample();
            coinPrice.createCriteria().andParamCodeEqualTo("coin_price");
            List<SysParameter> coinPriceSysParams = sysParameterMapper.selectByExample(coinPrice);
            if (coinPriceSysParams.size() > 0 && coinPriceSysParams.get(0) != null) {
                coinPriceBig = coinPriceSysParams.get(0).getParamValue();
            }
            BigDecimal currentPrice = coinPriceBig.add(coinIncr).setScale(2, BigDecimal.ROUND_HALF_UP);
            SysParameter sysParameter=new SysParameter(null,"current_price","现价",currentPrice,null,null,null,null);
            //插入现价
            sysParameterMapper.insertSelective(sysParameter);

        }else {
            coinPriceBig=sysParameters.get(0).getParamValue();
            BigDecimal currentPrice = coinPriceBig.add(coinIncr).setScale(2, BigDecimal.ROUND_HALF_UP);
            sysParameters.get(0).setParamValue(currentPrice);
            //更新现价
            sysParameterMapper.updateByPrimaryKey(sysParameters.get(0));
        }
    }

    private ConsumerTeam createConsumerTeam(ConsumerTeam leftTeam, ConsumerTeam rightTeam) {
        leftTeam.setRightFloatingTotal(rightTeam.getRightFloatingTotal());
        leftTeam.setRightLockrepoTotal(rightTeam.getRightLockrepoTotal());
        leftTeam.setRightProfitsTotal(rightTeam.getRightProfitsTotal());
        leftTeam.setRightTotalMember(rightTeam.getRightTotalMember());
        Date yestrtdayDate = DateUtil.getYestrtdayDate();
        leftTeam.setCreatedTime(yestrtdayDate);
        return leftTeam;
    }

    /**
     * 查询内部账地址
     *
     * @return
     */
    private String getInterAcoountAddress() {
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
