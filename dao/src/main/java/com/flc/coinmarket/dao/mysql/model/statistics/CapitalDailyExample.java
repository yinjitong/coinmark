package com.flc.coinmarket.dao.mysql.model.statistics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CapitalDailyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CapitalDailyExample() {
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

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(BigDecimal value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(BigDecimal value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(BigDecimal value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(BigDecimal value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<BigDecimal> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<BigDecimal> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total not between", value1, value2, "total");
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