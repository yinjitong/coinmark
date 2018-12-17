package com.flc.coinmarket.admin.service;

import com.flc.coinmarket.dao.mysql.model.consumer.ConsumerCapitalAccount;
import com.flc.coinmarket.dao.mysql.model.system.ProfitsRatio;

import java.math.BigDecimal;
import java.util.List;

public class SettlementService {

    /*
     ** 团队收益
     *
     */

    public BigDecimal teamProfit(ConsumerCapitalAccount account, List<ConsumerCapitalAccount> leftTeam, List<ConsumerCapitalAccount> rightTeam,
                                 List<ProfitsRatio> teamParamList, BigDecimal ratioLimit) {

        BigDecimal[] limitAndRatio = getRatio(account.getLockrepoFunds(), teamParamList);
        BigDecimal l = teamProfitCalculate(leftTeam, limitAndRatio[0]);
        BigDecimal r = teamProfitCalculate(rightTeam, limitAndRatio[0]);
        BigDecimal result = l.compareTo(r) > -1 ? r : l;
        return result.multiply(limitAndRatio[1]);
    }

    // 锁仓收益
    public BigDecimal lockProfit(ConsumerCapitalAccount account, List<ProfitsRatio> lockParamList, BigDecimal ratioLimit) {
        if (account == null)
            return BigDecimal.ZERO;
        BigDecimal[] d = getRatio(account.getLockrepoFunds(), lockParamList);
        return account.getLockrepoFunds().multiply(d[1]);
    }

    // 推荐收益
    public BigDecimal recommendProfit(BigDecimal refereedTeamProfitsTotal, BigDecimal refereeProfitsRatio) {

        if (refereedTeamProfitsTotal == null)
            return BigDecimal.ZERO;
        return refereedTeamProfitsTotal.multiply(refereeProfitsRatio);
    }

    // 收益销毁
    public BigDecimal destoryLock(ConsumerCapitalAccount account, BigDecimal destroyTimes, BigDecimal destroyLimit) {

        if (destroyTimes == null || account.getLockrepoFunds() == null || account.getAccumulatedProfits() == null)
            return BigDecimal.ZERO;
        BigDecimal lockrepoFunds = account.getLockrepoFunds();
        BigDecimal accumulatedProfits = account.getAccumulatedProfits();
        if (accumulatedProfits.compareTo(destroyTimes.multiply(lockrepoFunds.compareTo(destroyLimit) > -1 ? destroyLimit : lockrepoFunds)) > -1) {
            return lockrepoFunds.compareTo(destroyLimit) > -1 ? destroyLimit : lockrepoFunds;
        }
        return BigDecimal.ZERO;
    }

    // 自动复投
    public BigDecimal autoRein(ConsumerCapitalAccount consumer, BigDecimal reinvestLimit) {

        if (consumer == null || reinvestLimit == null)
            return BigDecimal.ZERO;

        BigDecimal c = consumer.getFloatingFunds();
        if (c.compareTo(reinvestLimit) > -1)
            c = reinvestLimit;
        return c;
    }

    // 锁仓释放
    public BigDecimal release(ConsumerCapitalAccount consumer, BigDecimal releaseRatio) {
        if (consumer == null || releaseRatio == null)
            return BigDecimal.ZERO;
        return consumer.getLockrepoFunds().multiply(releaseRatio);
    }

    // 计算团队收益
    private BigDecimal teamProfitCalculate(List<ConsumerCapitalAccount> team, BigDecimal limit) {
        if (team == null || team.size() == 0)
            return BigDecimal.ZERO;
        BigDecimal a = BigDecimal.ZERO, c;
        for (ConsumerCapitalAccount consumer : team) {
            c = consumer.getLockrepoFunds().compareTo(limit) > -1 ? limit : consumer.getLockrepoFunds();
            a = a.add(c);
        }
        return a;
    }

    // 获取收益比例和计算基数
    private BigDecimal[] getRatio(BigDecimal amount, List<ProfitsRatio> congigList) {
        BigDecimal[] result = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO};
        if (congigList == null || congigList.size() == 0)
            return result;
        for (ProfitsRatio r : congigList) {
            //如果最大值配置为0，则按Double最大值计算
            if (r.getUpperLimit().compareTo(new BigDecimal(0E-8)) == 0)
                r.setUpperLimit(new BigDecimal(Double.MAX_VALUE));

            if (amount.compareTo(r.getLowerLimit()) > -1 && amount.compareTo(r.getUpperLimit()) < 1) {
                result[0] = r.getCardinalNumber();
                result[1] = r.getRatio();
                break;
            }
        }
        return result;
    }
}
