package com.flc.coinmarket.dao.mysql.model.consumer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConsumerCapitalAccountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConsumerCapitalAccountExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andConsumerIdIsNull() {
            addCriterion("consumer_id is null");
            return (Criteria) this;
        }

        public Criteria andConsumerIdIsNotNull() {
            addCriterion("consumer_id is not null");
            return (Criteria) this;
        }

        public Criteria andConsumerIdEqualTo(Integer value) {
            addCriterion("consumer_id =", value, "consumerId");
            return (Criteria) this;
        }

        public Criteria andConsumerIdNotEqualTo(Integer value) {
            addCriterion("consumer_id <>", value, "consumerId");
            return (Criteria) this;
        }

        public Criteria andConsumerIdGreaterThan(Integer value) {
            addCriterion("consumer_id >", value, "consumerId");
            return (Criteria) this;
        }

        public Criteria andConsumerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("consumer_id >=", value, "consumerId");
            return (Criteria) this;
        }

        public Criteria andConsumerIdLessThan(Integer value) {
            addCriterion("consumer_id <", value, "consumerId");
            return (Criteria) this;
        }

        public Criteria andConsumerIdLessThanOrEqualTo(Integer value) {
            addCriterion("consumer_id <=", value, "consumerId");
            return (Criteria) this;
        }

        public Criteria andConsumerIdIn(List<Integer> values) {
            addCriterion("consumer_id in", values, "consumerId");
            return (Criteria) this;
        }

        public Criteria andConsumerIdNotIn(List<Integer> values) {
            addCriterion("consumer_id not in", values, "consumerId");
            return (Criteria) this;
        }

        public Criteria andConsumerIdBetween(Integer value1, Integer value2) {
            addCriterion("consumer_id between", value1, value2, "consumerId");
            return (Criteria) this;
        }

        public Criteria andConsumerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("consumer_id not between", value1, value2, "consumerId");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeIsNull() {
            addCriterion("team_pos_code is null");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeIsNotNull() {
            addCriterion("team_pos_code is not null");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeEqualTo(String value) {
            addCriterion("team_pos_code =", value, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeNotEqualTo(String value) {
            addCriterion("team_pos_code <>", value, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeGreaterThan(String value) {
            addCriterion("team_pos_code >", value, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeGreaterThanOrEqualTo(String value) {
            addCriterion("team_pos_code >=", value, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeLessThan(String value) {
            addCriterion("team_pos_code <", value, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeLessThanOrEqualTo(String value) {
            addCriterion("team_pos_code <=", value, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeLike(String value) {
            addCriterion("team_pos_code like", value, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeNotLike(String value) {
            addCriterion("team_pos_code not like", value, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeIn(List<String> values) {
            addCriterion("team_pos_code in", values, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeNotIn(List<String> values) {
            addCriterion("team_pos_code not in", values, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeBetween(String value1, String value2) {
            addCriterion("team_pos_code between", value1, value2, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andTeamPosCodeNotBetween(String value1, String value2) {
            addCriterion("team_pos_code not between", value1, value2, "teamPosCode");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsIsNull() {
            addCriterion("floating_funds is null");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsIsNotNull() {
            addCriterion("floating_funds is not null");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsEqualTo(BigDecimal value) {
            addCriterion("floating_funds =", value, "floatingFunds");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsNotEqualTo(BigDecimal value) {
            addCriterion("floating_funds <>", value, "floatingFunds");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsGreaterThan(BigDecimal value) {
            addCriterion("floating_funds >", value, "floatingFunds");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("floating_funds >=", value, "floatingFunds");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsLessThan(BigDecimal value) {
            addCriterion("floating_funds <", value, "floatingFunds");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("floating_funds <=", value, "floatingFunds");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsIn(List<BigDecimal> values) {
            addCriterion("floating_funds in", values, "floatingFunds");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsNotIn(List<BigDecimal> values) {
            addCriterion("floating_funds not in", values, "floatingFunds");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("floating_funds between", value1, value2, "floatingFunds");
            return (Criteria) this;
        }

        public Criteria andFloatingFundsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("floating_funds not between", value1, value2, "floatingFunds");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsIsNull() {
            addCriterion("lockrepo_funds is null");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsIsNotNull() {
            addCriterion("lockrepo_funds is not null");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsEqualTo(BigDecimal value) {
            addCriterion("lockrepo_funds =", value, "lockrepoFunds");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsNotEqualTo(BigDecimal value) {
            addCriterion("lockrepo_funds <>", value, "lockrepoFunds");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsGreaterThan(BigDecimal value) {
            addCriterion("lockrepo_funds >", value, "lockrepoFunds");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lockrepo_funds >=", value, "lockrepoFunds");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsLessThan(BigDecimal value) {
            addCriterion("lockrepo_funds <", value, "lockrepoFunds");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lockrepo_funds <=", value, "lockrepoFunds");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsIn(List<BigDecimal> values) {
            addCriterion("lockrepo_funds in", values, "lockrepoFunds");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsNotIn(List<BigDecimal> values) {
            addCriterion("lockrepo_funds not in", values, "lockrepoFunds");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lockrepo_funds between", value1, value2, "lockrepoFunds");
            return (Criteria) this;
        }

        public Criteria andLockrepoFundsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lockrepo_funds not between", value1, value2, "lockrepoFunds");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsIsNull() {
            addCriterion("profits_funds is null");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsIsNotNull() {
            addCriterion("profits_funds is not null");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsEqualTo(BigDecimal value) {
            addCriterion("profits_funds =", value, "profitsFunds");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsNotEqualTo(BigDecimal value) {
            addCriterion("profits_funds <>", value, "profitsFunds");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsGreaterThan(BigDecimal value) {
            addCriterion("profits_funds >", value, "profitsFunds");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profits_funds >=", value, "profitsFunds");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsLessThan(BigDecimal value) {
            addCriterion("profits_funds <", value, "profitsFunds");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profits_funds <=", value, "profitsFunds");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsIn(List<BigDecimal> values) {
            addCriterion("profits_funds in", values, "profitsFunds");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsNotIn(List<BigDecimal> values) {
            addCriterion("profits_funds not in", values, "profitsFunds");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profits_funds between", value1, value2, "profitsFunds");
            return (Criteria) this;
        }

        public Criteria andProfitsFundsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profits_funds not between", value1, value2, "profitsFunds");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsIsNull() {
            addCriterion("accumulated_profits is null");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsIsNotNull() {
            addCriterion("accumulated_profits is not null");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsEqualTo(BigDecimal value) {
            addCriterion("accumulated_profits =", value, "accumulatedProfits");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsNotEqualTo(BigDecimal value) {
            addCriterion("accumulated_profits <>", value, "accumulatedProfits");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsGreaterThan(BigDecimal value) {
            addCriterion("accumulated_profits >", value, "accumulatedProfits");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accumulated_profits >=", value, "accumulatedProfits");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsLessThan(BigDecimal value) {
            addCriterion("accumulated_profits <", value, "accumulatedProfits");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accumulated_profits <=", value, "accumulatedProfits");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsIn(List<BigDecimal> values) {
            addCriterion("accumulated_profits in", values, "accumulatedProfits");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsNotIn(List<BigDecimal> values) {
            addCriterion("accumulated_profits not in", values, "accumulatedProfits");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accumulated_profits between", value1, value2, "accumulatedProfits");
            return (Criteria) this;
        }

        public Criteria andAccumulatedProfitsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accumulated_profits not between", value1, value2, "accumulatedProfits");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayIsNull() {
            addCriterion("profits_today is null");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayIsNotNull() {
            addCriterion("profits_today is not null");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayEqualTo(BigDecimal value) {
            addCriterion("profits_today =", value, "profitsToday");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayNotEqualTo(BigDecimal value) {
            addCriterion("profits_today <>", value, "profitsToday");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayGreaterThan(BigDecimal value) {
            addCriterion("profits_today >", value, "profitsToday");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profits_today >=", value, "profitsToday");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayLessThan(BigDecimal value) {
            addCriterion("profits_today <", value, "profitsToday");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profits_today <=", value, "profitsToday");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayIn(List<BigDecimal> values) {
            addCriterion("profits_today in", values, "profitsToday");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayNotIn(List<BigDecimal> values) {
            addCriterion("profits_today not in", values, "profitsToday");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profits_today between", value1, value2, "profitsToday");
            return (Criteria) this;
        }

        public Criteria andProfitsTodayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profits_today not between", value1, value2, "profitsToday");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressIsNull() {
            addCriterion("floating_address is null");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressIsNotNull() {
            addCriterion("floating_address is not null");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressEqualTo(String value) {
            addCriterion("floating_address =", value, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressNotEqualTo(String value) {
            addCriterion("floating_address <>", value, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressGreaterThan(String value) {
            addCriterion("floating_address >", value, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressGreaterThanOrEqualTo(String value) {
            addCriterion("floating_address >=", value, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressLessThan(String value) {
            addCriterion("floating_address <", value, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressLessThanOrEqualTo(String value) {
            addCriterion("floating_address <=", value, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressLike(String value) {
            addCriterion("floating_address like", value, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressNotLike(String value) {
            addCriterion("floating_address not like", value, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressIn(List<String> values) {
            addCriterion("floating_address in", values, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressNotIn(List<String> values) {
            addCriterion("floating_address not in", values, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressBetween(String value1, String value2) {
            addCriterion("floating_address between", value1, value2, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andFloatingAddressNotBetween(String value1, String value2) {
            addCriterion("floating_address not between", value1, value2, "floatingAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressIsNull() {
            addCriterion("lockrepo_address is null");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressIsNotNull() {
            addCriterion("lockrepo_address is not null");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressEqualTo(String value) {
            addCriterion("lockrepo_address =", value, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressNotEqualTo(String value) {
            addCriterion("lockrepo_address <>", value, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressGreaterThan(String value) {
            addCriterion("lockrepo_address >", value, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressGreaterThanOrEqualTo(String value) {
            addCriterion("lockrepo_address >=", value, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressLessThan(String value) {
            addCriterion("lockrepo_address <", value, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressLessThanOrEqualTo(String value) {
            addCriterion("lockrepo_address <=", value, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressLike(String value) {
            addCriterion("lockrepo_address like", value, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressNotLike(String value) {
            addCriterion("lockrepo_address not like", value, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressIn(List<String> values) {
            addCriterion("lockrepo_address in", values, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressNotIn(List<String> values) {
            addCriterion("lockrepo_address not in", values, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressBetween(String value1, String value2) {
            addCriterion("lockrepo_address between", value1, value2, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andLockrepoAddressNotBetween(String value1, String value2) {
            addCriterion("lockrepo_address not between", value1, value2, "lockrepoAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressIsNull() {
            addCriterion("profits_address is null");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressIsNotNull() {
            addCriterion("profits_address is not null");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressEqualTo(String value) {
            addCriterion("profits_address =", value, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressNotEqualTo(String value) {
            addCriterion("profits_address <>", value, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressGreaterThan(String value) {
            addCriterion("profits_address >", value, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressGreaterThanOrEqualTo(String value) {
            addCriterion("profits_address >=", value, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressLessThan(String value) {
            addCriterion("profits_address <", value, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressLessThanOrEqualTo(String value) {
            addCriterion("profits_address <=", value, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressLike(String value) {
            addCriterion("profits_address like", value, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressNotLike(String value) {
            addCriterion("profits_address not like", value, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressIn(List<String> values) {
            addCriterion("profits_address in", values, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressNotIn(List<String> values) {
            addCriterion("profits_address not in", values, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressBetween(String value1, String value2) {
            addCriterion("profits_address between", value1, value2, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andProfitsAddressNotBetween(String value1, String value2) {
            addCriterion("profits_address not between", value1, value2, "profitsAddress");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagIsNull() {
            addCriterion("release_flag is null");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagIsNotNull() {
            addCriterion("release_flag is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagEqualTo(String value) {
            addCriterion("release_flag =", value, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagNotEqualTo(String value) {
            addCriterion("release_flag <>", value, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagGreaterThan(String value) {
            addCriterion("release_flag >", value, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagGreaterThanOrEqualTo(String value) {
            addCriterion("release_flag >=", value, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagLessThan(String value) {
            addCriterion("release_flag <", value, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagLessThanOrEqualTo(String value) {
            addCriterion("release_flag <=", value, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagLike(String value) {
            addCriterion("release_flag like", value, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagNotLike(String value) {
            addCriterion("release_flag not like", value, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagIn(List<String> values) {
            addCriterion("release_flag in", values, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagNotIn(List<String> values) {
            addCriterion("release_flag not in", values, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagBetween(String value1, String value2) {
            addCriterion("release_flag between", value1, value2, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReleaseFlagNotBetween(String value1, String value2) {
            addCriterion("release_flag not between", value1, value2, "releaseFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagIsNull() {
            addCriterion("reinvest_flag is null");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagIsNotNull() {
            addCriterion("reinvest_flag is not null");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagEqualTo(String value) {
            addCriterion("reinvest_flag =", value, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagNotEqualTo(String value) {
            addCriterion("reinvest_flag <>", value, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagGreaterThan(String value) {
            addCriterion("reinvest_flag >", value, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagGreaterThanOrEqualTo(String value) {
            addCriterion("reinvest_flag >=", value, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagLessThan(String value) {
            addCriterion("reinvest_flag <", value, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagLessThanOrEqualTo(String value) {
            addCriterion("reinvest_flag <=", value, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagLike(String value) {
            addCriterion("reinvest_flag like", value, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagNotLike(String value) {
            addCriterion("reinvest_flag not like", value, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagIn(List<String> values) {
            addCriterion("reinvest_flag in", values, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagNotIn(List<String> values) {
            addCriterion("reinvest_flag not in", values, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagBetween(String value1, String value2) {
            addCriterion("reinvest_flag between", value1, value2, "reinvestFlag");
            return (Criteria) this;
        }

        public Criteria andReinvestFlagNotBetween(String value1, String value2) {
            addCriterion("reinvest_flag not between", value1, value2, "reinvestFlag");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}