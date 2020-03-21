package pers.dreamer.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CommentExample() {
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

        public Criteria andCidIsNull() {
            addCriterion("cid is null");
            return (Criteria) this;
        }

        public Criteria andCidIsNotNull() {
            addCriterion("cid is not null");
            return (Criteria) this;
        }

        public Criteria andCidEqualTo(Integer value) {
            addCriterion("cid =", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotEqualTo(Integer value) {
            addCriterion("cid <>", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidGreaterThan(Integer value) {
            addCriterion("cid >", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidGreaterThanOrEqualTo(Integer value) {
            addCriterion("cid >=", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidLessThan(Integer value) {
            addCriterion("cid <", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidLessThanOrEqualTo(Integer value) {
            addCriterion("cid <=", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidIn(List<Integer> values) {
            addCriterion("cid in", values, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotIn(List<Integer> values) {
            addCriterion("cid not in", values, "cid");
            return (Criteria) this;
        }

        public Criteria andCidBetween(Integer value1, Integer value2) {
            addCriterion("cid between", value1, value2, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotBetween(Integer value1, Integer value2) {
            addCriterion("cid not between", value1, value2, "cid");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Integer value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Integer value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Integer value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Integer value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Integer value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Integer> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Integer> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Integer value1, Integer value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Integer value1, Integer value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Date value) {
            addCriterion("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterion("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterion("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterion("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterion("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterion("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Date> values) {
            addCriterion("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Date> values) {
            addCriterion("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Date value1, Date value2) {
            addCriterion("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Date value1, Date value2) {
            addCriterion("date not between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andPreuidIsNull() {
            addCriterion("preuid is null");
            return (Criteria) this;
        }

        public Criteria andPreuidIsNotNull() {
            addCriterion("preuid is not null");
            return (Criteria) this;
        }

        public Criteria andPreuidEqualTo(Integer value) {
            addCriterion("preuid =", value, "preuid");
            return (Criteria) this;
        }

        public Criteria andPreuidNotEqualTo(Integer value) {
            addCriterion("preuid <>", value, "preuid");
            return (Criteria) this;
        }

        public Criteria andPreuidGreaterThan(Integer value) {
            addCriterion("preuid >", value, "preuid");
            return (Criteria) this;
        }

        public Criteria andPreuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("preuid >=", value, "preuid");
            return (Criteria) this;
        }

        public Criteria andPreuidLessThan(Integer value) {
            addCriterion("preuid <", value, "preuid");
            return (Criteria) this;
        }

        public Criteria andPreuidLessThanOrEqualTo(Integer value) {
            addCriterion("preuid <=", value, "preuid");
            return (Criteria) this;
        }

        public Criteria andPreuidIn(List<Integer> values) {
            addCriterion("preuid in", values, "preuid");
            return (Criteria) this;
        }

        public Criteria andPreuidNotIn(List<Integer> values) {
            addCriterion("preuid not in", values, "preuid");
            return (Criteria) this;
        }

        public Criteria andPreuidBetween(Integer value1, Integer value2) {
            addCriterion("preuid between", value1, value2, "preuid");
            return (Criteria) this;
        }

        public Criteria andPreuidNotBetween(Integer value1, Integer value2) {
            addCriterion("preuid not between", value1, value2, "preuid");
            return (Criteria) this;
        }

        public Criteria andUpVoteIsNull() {
            addCriterion("up_vote is null");
            return (Criteria) this;
        }

        public Criteria andUpVoteIsNotNull() {
            addCriterion("up_vote is not null");
            return (Criteria) this;
        }

        public Criteria andUpVoteEqualTo(Integer value) {
            addCriterion("up_vote =", value, "upVote");
            return (Criteria) this;
        }

        public Criteria andUpVoteNotEqualTo(Integer value) {
            addCriterion("up_vote <>", value, "upVote");
            return (Criteria) this;
        }

        public Criteria andUpVoteGreaterThan(Integer value) {
            addCriterion("up_vote >", value, "upVote");
            return (Criteria) this;
        }

        public Criteria andUpVoteGreaterThanOrEqualTo(Integer value) {
            addCriterion("up_vote >=", value, "upVote");
            return (Criteria) this;
        }

        public Criteria andUpVoteLessThan(Integer value) {
            addCriterion("up_vote <", value, "upVote");
            return (Criteria) this;
        }

        public Criteria andUpVoteLessThanOrEqualTo(Integer value) {
            addCriterion("up_vote <=", value, "upVote");
            return (Criteria) this;
        }

        public Criteria andUpVoteIn(List<Integer> values) {
            addCriterion("up_vote in", values, "upVote");
            return (Criteria) this;
        }

        public Criteria andUpVoteNotIn(List<Integer> values) {
            addCriterion("up_vote not in", values, "upVote");
            return (Criteria) this;
        }

        public Criteria andUpVoteBetween(Integer value1, Integer value2) {
            addCriterion("up_vote between", value1, value2, "upVote");
            return (Criteria) this;
        }

        public Criteria andUpVoteNotBetween(Integer value1, Integer value2) {
            addCriterion("up_vote not between", value1, value2, "upVote");
            return (Criteria) this;
        }

        public Criteria andDownVoteIsNull() {
            addCriterion("down_vote is null");
            return (Criteria) this;
        }

        public Criteria andDownVoteIsNotNull() {
            addCriterion("down_vote is not null");
            return (Criteria) this;
        }

        public Criteria andDownVoteEqualTo(Integer value) {
            addCriterion("down_vote =", value, "downVote");
            return (Criteria) this;
        }

        public Criteria andDownVoteNotEqualTo(Integer value) {
            addCriterion("down_vote <>", value, "downVote");
            return (Criteria) this;
        }

        public Criteria andDownVoteGreaterThan(Integer value) {
            addCriterion("down_vote >", value, "downVote");
            return (Criteria) this;
        }

        public Criteria andDownVoteGreaterThanOrEqualTo(Integer value) {
            addCriterion("down_vote >=", value, "downVote");
            return (Criteria) this;
        }

        public Criteria andDownVoteLessThan(Integer value) {
            addCriterion("down_vote <", value, "downVote");
            return (Criteria) this;
        }

        public Criteria andDownVoteLessThanOrEqualTo(Integer value) {
            addCriterion("down_vote <=", value, "downVote");
            return (Criteria) this;
        }

        public Criteria andDownVoteIn(List<Integer> values) {
            addCriterion("down_vote in", values, "downVote");
            return (Criteria) this;
        }

        public Criteria andDownVoteNotIn(List<Integer> values) {
            addCriterion("down_vote not in", values, "downVote");
            return (Criteria) this;
        }

        public Criteria andDownVoteBetween(Integer value1, Integer value2) {
            addCriterion("down_vote between", value1, value2, "downVote");
            return (Criteria) this;
        }

        public Criteria andDownVoteNotBetween(Integer value1, Integer value2) {
            addCriterion("down_vote not between", value1, value2, "downVote");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Integer value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Integer value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Integer value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Integer value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Integer value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Integer> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Integer> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Integer value1, Integer value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Integer value1, Integer value2) {
            addCriterion("pid not between", value1, value2, "pid");
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