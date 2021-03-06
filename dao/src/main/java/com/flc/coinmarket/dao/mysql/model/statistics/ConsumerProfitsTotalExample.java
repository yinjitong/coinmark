package com.flc.coinmarket.dao.mysql.model.statistics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsumerProfitsTotalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConsumerProfitsTotalExample() {
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

        public Criteria andProfitsTeamIsNull() {
            addCriterion("profits_team is null");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamIsNotNull() {
            addCriterion("profits_team is not null");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamEqualTo(BigDecimal value) {
            addCriterion("profits_team =", value, "profitsTeam");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamNotEqualTo(BigDecimal value) {
            addCriterion("profits_team <>", value, "profitsTeam");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamGreaterThan(BigDecimal value) {
            addCriterion("profits_team >", value, "profitsTeam");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profits_team >=", value, "profitsTeam");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamLessThan(BigDecimal value) {
            addCriterion("profits_team <", value, "profitsTeam");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profits_team <=", value, "profitsTeam");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamIn(List<BigDecimal> values) {
            addCriterion("profits_team in", values, "profitsTeam");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamNotIn(List<BigDecimal> values) {
            addCriterion("profits_team not in", values, "profitsTeam");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profits_team between", value1, value2, "profitsTeam");
            return (Criteria) this;
        }

        public Criteria andProfitsTeamNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profits_team not between", value1, value2, "profitsTeam");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeIsNull() {
            addCriterion("profits_referee is null");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeIsNotNull() {
            addCriterion("profits_referee is not null");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeEqualTo(BigDecimal value) {
            addCriterion("profits_referee =", value, "profitsReferee");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeNotEqualTo(BigDecimal value) {
            addCriterion("profits_referee <>", value, "profitsReferee");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeGreaterThan(BigDecimal value) {
            addCriterion("profits_referee >", value, "profitsReferee");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profits_referee >=", value, "profitsReferee");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeLessThan(BigDecimal value) {
            addCriterion("profits_referee <", value, "profitsReferee");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profits_referee <=", value, "profitsReferee");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeIn(List<BigDecimal> values) {
            addCriterion("profits_referee in", values, "profitsReferee");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeNotIn(List<BigDecimal> values) {
            addCriterion("profits_referee not in", values, "profitsReferee");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profits_referee between", value1, value2, "profitsReferee");
            return (Criteria) this;
        }

        public Criteria andProfitsRefereeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profits_referee not between", value1, value2, "profitsReferee");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoIsNull() {
            addCriterion("profits_lockrepo is null");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoIsNotNull() {
            addCriterion("profits_lockrepo is not null");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoEqualTo(BigDecimal value) {
            addCriterion("profits_lockrepo =", value, "profitsLockrepo");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoNotEqualTo(BigDecimal value) {
            addCriterion("profits_lockrepo <>", value, "profitsLockrepo");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoGreaterThan(BigDecimal value) {
            addCriterion("profits_lockrepo >", value, "profitsLockrepo");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profits_lockrepo >=", value, "profitsLockrepo");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoLessThan(BigDecimal value) {
            addCriterion("profits_lockrepo <", value, "profitsLockrepo");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profits_lockrepo <=", value, "profitsLockrepo");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoIn(List<BigDecimal> values) {
            addCriterion("profits_lockrepo in", values, "profitsLockrepo");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoNotIn(List<BigDecimal> values) {
            addCriterion("profits_lockrepo not in", values, "profitsLockrepo");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profits_lockrepo between", value1, value2, "profitsLockrepo");
            return (Criteria) this;
        }

        public Criteria andProfitsLockrepoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profits_lockrepo not between", value1, value2, "profitsLockrepo");
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

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(Integer value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(Integer value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(Integer value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(Integer value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<Integer> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<Integer> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
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