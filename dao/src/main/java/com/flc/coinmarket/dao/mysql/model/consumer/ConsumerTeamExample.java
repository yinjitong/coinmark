package com.flc.coinmarket.dao.mysql.model.consumer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsumerTeamExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConsumerTeamExample() {
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

        public Criteria andLeftTotalMemberIsNull() {
            addCriterion("left_total_member is null");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberIsNotNull() {
            addCriterion("left_total_member is not null");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberEqualTo(Integer value) {
            addCriterion("left_total_member =", value, "leftTotalMember");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberNotEqualTo(Integer value) {
            addCriterion("left_total_member <>", value, "leftTotalMember");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberGreaterThan(Integer value) {
            addCriterion("left_total_member >", value, "leftTotalMember");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberGreaterThanOrEqualTo(Integer value) {
            addCriterion("left_total_member >=", value, "leftTotalMember");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberLessThan(Integer value) {
            addCriterion("left_total_member <", value, "leftTotalMember");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberLessThanOrEqualTo(Integer value) {
            addCriterion("left_total_member <=", value, "leftTotalMember");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberIn(List<Integer> values) {
            addCriterion("left_total_member in", values, "leftTotalMember");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberNotIn(List<Integer> values) {
            addCriterion("left_total_member not in", values, "leftTotalMember");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberBetween(Integer value1, Integer value2) {
            addCriterion("left_total_member between", value1, value2, "leftTotalMember");
            return (Criteria) this;
        }

        public Criteria andLeftTotalMemberNotBetween(Integer value1, Integer value2) {
            addCriterion("left_total_member not between", value1, value2, "leftTotalMember");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberIsNull() {
            addCriterion("right_total_member is null");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberIsNotNull() {
            addCriterion("right_total_member is not null");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberEqualTo(Integer value) {
            addCriterion("right_total_member =", value, "rightTotalMember");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberNotEqualTo(Integer value) {
            addCriterion("right_total_member <>", value, "rightTotalMember");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberGreaterThan(Integer value) {
            addCriterion("right_total_member >", value, "rightTotalMember");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberGreaterThanOrEqualTo(Integer value) {
            addCriterion("right_total_member >=", value, "rightTotalMember");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberLessThan(Integer value) {
            addCriterion("right_total_member <", value, "rightTotalMember");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberLessThanOrEqualTo(Integer value) {
            addCriterion("right_total_member <=", value, "rightTotalMember");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberIn(List<Integer> values) {
            addCriterion("right_total_member in", values, "rightTotalMember");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberNotIn(List<Integer> values) {
            addCriterion("right_total_member not in", values, "rightTotalMember");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberBetween(Integer value1, Integer value2) {
            addCriterion("right_total_member between", value1, value2, "rightTotalMember");
            return (Criteria) this;
        }

        public Criteria andRightTotalMemberNotBetween(Integer value1, Integer value2) {
            addCriterion("right_total_member not between", value1, value2, "rightTotalMember");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalIsNull() {
            addCriterion("left_profits_total is null");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalIsNotNull() {
            addCriterion("left_profits_total is not null");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalEqualTo(BigDecimal value) {
            addCriterion("left_profits_total =", value, "leftProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalNotEqualTo(BigDecimal value) {
            addCriterion("left_profits_total <>", value, "leftProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalGreaterThan(BigDecimal value) {
            addCriterion("left_profits_total >", value, "leftProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("left_profits_total >=", value, "leftProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalLessThan(BigDecimal value) {
            addCriterion("left_profits_total <", value, "leftProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("left_profits_total <=", value, "leftProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalIn(List<BigDecimal> values) {
            addCriterion("left_profits_total in", values, "leftProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalNotIn(List<BigDecimal> values) {
            addCriterion("left_profits_total not in", values, "leftProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("left_profits_total between", value1, value2, "leftProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andLeftProfitsTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("left_profits_total not between", value1, value2, "leftProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalIsNull() {
            addCriterion("left_lockrepo_total is null");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalIsNotNull() {
            addCriterion("left_lockrepo_total is not null");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalEqualTo(BigDecimal value) {
            addCriterion("left_lockrepo_total =", value, "leftLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalNotEqualTo(BigDecimal value) {
            addCriterion("left_lockrepo_total <>", value, "leftLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalGreaterThan(BigDecimal value) {
            addCriterion("left_lockrepo_total >", value, "leftLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("left_lockrepo_total >=", value, "leftLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalLessThan(BigDecimal value) {
            addCriterion("left_lockrepo_total <", value, "leftLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("left_lockrepo_total <=", value, "leftLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalIn(List<BigDecimal> values) {
            addCriterion("left_lockrepo_total in", values, "leftLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalNotIn(List<BigDecimal> values) {
            addCriterion("left_lockrepo_total not in", values, "leftLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("left_lockrepo_total between", value1, value2, "leftLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andLeftLockrepoTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("left_lockrepo_total not between", value1, value2, "leftLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalIsNull() {
            addCriterion("left_floating_total is null");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalIsNotNull() {
            addCriterion("left_floating_total is not null");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalEqualTo(BigDecimal value) {
            addCriterion("left_floating_total =", value, "leftFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalNotEqualTo(BigDecimal value) {
            addCriterion("left_floating_total <>", value, "leftFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalGreaterThan(BigDecimal value) {
            addCriterion("left_floating_total >", value, "leftFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("left_floating_total >=", value, "leftFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalLessThan(BigDecimal value) {
            addCriterion("left_floating_total <", value, "leftFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("left_floating_total <=", value, "leftFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalIn(List<BigDecimal> values) {
            addCriterion("left_floating_total in", values, "leftFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalNotIn(List<BigDecimal> values) {
            addCriterion("left_floating_total not in", values, "leftFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("left_floating_total between", value1, value2, "leftFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andLeftFloatingTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("left_floating_total not between", value1, value2, "leftFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalIsNull() {
            addCriterion("right_profits_total is null");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalIsNotNull() {
            addCriterion("right_profits_total is not null");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalEqualTo(BigDecimal value) {
            addCriterion("right_profits_total =", value, "rightProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalNotEqualTo(BigDecimal value) {
            addCriterion("right_profits_total <>", value, "rightProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalGreaterThan(BigDecimal value) {
            addCriterion("right_profits_total >", value, "rightProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("right_profits_total >=", value, "rightProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalLessThan(BigDecimal value) {
            addCriterion("right_profits_total <", value, "rightProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("right_profits_total <=", value, "rightProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalIn(List<BigDecimal> values) {
            addCriterion("right_profits_total in", values, "rightProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalNotIn(List<BigDecimal> values) {
            addCriterion("right_profits_total not in", values, "rightProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("right_profits_total between", value1, value2, "rightProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andRightProfitsTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("right_profits_total not between", value1, value2, "rightProfitsTotal");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalIsNull() {
            addCriterion("right_lockrepo_total is null");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalIsNotNull() {
            addCriterion("right_lockrepo_total is not null");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalEqualTo(BigDecimal value) {
            addCriterion("right_lockrepo_total =", value, "rightLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalNotEqualTo(BigDecimal value) {
            addCriterion("right_lockrepo_total <>", value, "rightLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalGreaterThan(BigDecimal value) {
            addCriterion("right_lockrepo_total >", value, "rightLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("right_lockrepo_total >=", value, "rightLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalLessThan(BigDecimal value) {
            addCriterion("right_lockrepo_total <", value, "rightLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("right_lockrepo_total <=", value, "rightLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalIn(List<BigDecimal> values) {
            addCriterion("right_lockrepo_total in", values, "rightLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalNotIn(List<BigDecimal> values) {
            addCriterion("right_lockrepo_total not in", values, "rightLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("right_lockrepo_total between", value1, value2, "rightLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andRightLockrepoTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("right_lockrepo_total not between", value1, value2, "rightLockrepoTotal");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalIsNull() {
            addCriterion("right_floating_total is null");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalIsNotNull() {
            addCriterion("right_floating_total is not null");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalEqualTo(BigDecimal value) {
            addCriterion("right_floating_total =", value, "rightFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalNotEqualTo(BigDecimal value) {
            addCriterion("right_floating_total <>", value, "rightFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalGreaterThan(BigDecimal value) {
            addCriterion("right_floating_total >", value, "rightFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("right_floating_total >=", value, "rightFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalLessThan(BigDecimal value) {
            addCriterion("right_floating_total <", value, "rightFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("right_floating_total <=", value, "rightFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalIn(List<BigDecimal> values) {
            addCriterion("right_floating_total in", values, "rightFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalNotIn(List<BigDecimal> values) {
            addCriterion("right_floating_total not in", values, "rightFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("right_floating_total between", value1, value2, "rightFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andRightFloatingTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("right_floating_total not between", value1, value2, "rightFloatingTotal");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("created_time is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("created_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(Date value) {
            addCriterion("created_time =", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(Date value) {
            addCriterion("created_time <>", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(Date value) {
            addCriterion("created_time >", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("created_time >=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(Date value) {
            addCriterion("created_time <", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("created_time <=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<Date> values) {
            addCriterion("created_time in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<Date> values) {
            addCriterion("created_time not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(Date value1, Date value2) {
            addCriterion("created_time between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("created_time not between", value1, value2, "createdTime");
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