package pers.dreamer.bean;

import java.util.ArrayList;
import java.util.List;

public class ProblemExample {
    protected String orderByClause;

    protected boolean distinct;
    private Integer limitSize;

    public Integer getLimitSize() {
        return limitSize;
    }

    public void setLimitSize(Integer limitSize) {
        this.limitSize = limitSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    private Integer page;

    protected List<Criteria> oredCriteria;

    public ProblemExample() {
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andInputIsNull() {
            addCriterion("input is null");
            return (Criteria) this;
        }

        public Criteria andInputIsNotNull() {
            addCriterion("input is not null");
            return (Criteria) this;
        }

        public Criteria andInputEqualTo(String value) {
            addCriterion("input =", value, "input");
            return (Criteria) this;
        }

        public Criteria andInputNotEqualTo(String value) {
            addCriterion("input <>", value, "input");
            return (Criteria) this;
        }

        public Criteria andInputGreaterThan(String value) {
            addCriterion("input >", value, "input");
            return (Criteria) this;
        }

        public Criteria andInputGreaterThanOrEqualTo(String value) {
            addCriterion("input >=", value, "input");
            return (Criteria) this;
        }

        public Criteria andInputLessThan(String value) {
            addCriterion("input <", value, "input");
            return (Criteria) this;
        }

        public Criteria andInputLessThanOrEqualTo(String value) {
            addCriterion("input <=", value, "input");
            return (Criteria) this;
        }

        public Criteria andInputLike(String value) {
            addCriterion("input like", value, "input");
            return (Criteria) this;
        }

        public Criteria andInputNotLike(String value) {
            addCriterion("input not like", value, "input");
            return (Criteria) this;
        }

        public Criteria andInputIn(List<String> values) {
            addCriterion("input in", values, "input");
            return (Criteria) this;
        }

        public Criteria andInputNotIn(List<String> values) {
            addCriterion("input not in", values, "input");
            return (Criteria) this;
        }

        public Criteria andInputBetween(String value1, String value2) {
            addCriterion("input between", value1, value2, "input");
            return (Criteria) this;
        }

        public Criteria andInputNotBetween(String value1, String value2) {
            addCriterion("input not between", value1, value2, "input");
            return (Criteria) this;
        }

        public Criteria andOutputIsNull() {
            addCriterion("output is null");
            return (Criteria) this;
        }

        public Criteria andOutputIsNotNull() {
            addCriterion("output is not null");
            return (Criteria) this;
        }

        public Criteria andOutputEqualTo(String value) {
            addCriterion("output =", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputNotEqualTo(String value) {
            addCriterion("output <>", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputGreaterThan(String value) {
            addCriterion("output >", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputGreaterThanOrEqualTo(String value) {
            addCriterion("output >=", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputLessThan(String value) {
            addCriterion("output <", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputLessThanOrEqualTo(String value) {
            addCriterion("output <=", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputLike(String value) {
            addCriterion("output like", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputNotLike(String value) {
            addCriterion("output not like", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputIn(List<String> values) {
            addCriterion("output in", values, "output");
            return (Criteria) this;
        }

        public Criteria andOutputNotIn(List<String> values) {
            addCriterion("output not in", values, "output");
            return (Criteria) this;
        }

        public Criteria andOutputBetween(String value1, String value2) {
            addCriterion("output between", value1, value2, "output");
            return (Criteria) this;
        }

        public Criteria andOutputNotBetween(String value1, String value2) {
            addCriterion("output not between", value1, value2, "output");
            return (Criteria) this;
        }

        public Criteria andSampleinputIsNull() {
            addCriterion("sampleinput is null");
            return (Criteria) this;
        }

        public Criteria andSampleinputIsNotNull() {
            addCriterion("sampleinput is not null");
            return (Criteria) this;
        }

        public Criteria andSampleinputEqualTo(String value) {
            addCriterion("sampleinput =", value, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputNotEqualTo(String value) {
            addCriterion("sampleinput <>", value, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputGreaterThan(String value) {
            addCriterion("sampleinput >", value, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputGreaterThanOrEqualTo(String value) {
            addCriterion("sampleinput >=", value, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputLessThan(String value) {
            addCriterion("sampleinput <", value, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputLessThanOrEqualTo(String value) {
            addCriterion("sampleinput <=", value, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputLike(String value) {
            addCriterion("sampleinput like", value, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputNotLike(String value) {
            addCriterion("sampleinput not like", value, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputIn(List<String> values) {
            addCriterion("sampleinput in", values, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputNotIn(List<String> values) {
            addCriterion("sampleinput not in", values, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputBetween(String value1, String value2) {
            addCriterion("sampleinput between", value1, value2, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleinputNotBetween(String value1, String value2) {
            addCriterion("sampleinput not between", value1, value2, "sampleinput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputIsNull() {
            addCriterion("sampleoutput is null");
            return (Criteria) this;
        }

        public Criteria andSampleoutputIsNotNull() {
            addCriterion("sampleoutput is not null");
            return (Criteria) this;
        }

        public Criteria andSampleoutputEqualTo(String value) {
            addCriterion("sampleoutput =", value, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputNotEqualTo(String value) {
            addCriterion("sampleoutput <>", value, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputGreaterThan(String value) {
            addCriterion("sampleoutput >", value, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputGreaterThanOrEqualTo(String value) {
            addCriterion("sampleoutput >=", value, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputLessThan(String value) {
            addCriterion("sampleoutput <", value, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputLessThanOrEqualTo(String value) {
            addCriterion("sampleoutput <=", value, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputLike(String value) {
            addCriterion("sampleoutput like", value, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputNotLike(String value) {
            addCriterion("sampleoutput not like", value, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputIn(List<String> values) {
            addCriterion("sampleoutput in", values, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputNotIn(List<String> values) {
            addCriterion("sampleoutput not in", values, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputBetween(String value1, String value2) {
            addCriterion("sampleoutput between", value1, value2, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andSampleoutputNotBetween(String value1, String value2) {
            addCriterion("sampleoutput not between", value1, value2, "sampleoutput");
            return (Criteria) this;
        }

        public Criteria andTimelimitIsNull() {
            addCriterion("timelimit is null");
            return (Criteria) this;
        }

        public Criteria andTimelimitIsNotNull() {
            addCriterion("timelimit is not null");
            return (Criteria) this;
        }

        public Criteria andTimelimitEqualTo(Integer value) {
            addCriterion("timelimit =", value, "timelimit");
            return (Criteria) this;
        }

        public Criteria andTimelimitNotEqualTo(Integer value) {
            addCriterion("timelimit <>", value, "timelimit");
            return (Criteria) this;
        }

        public Criteria andTimelimitGreaterThan(Integer value) {
            addCriterion("timelimit >", value, "timelimit");
            return (Criteria) this;
        }

        public Criteria andTimelimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("timelimit >=", value, "timelimit");
            return (Criteria) this;
        }

        public Criteria andTimelimitLessThan(Integer value) {
            addCriterion("timelimit <", value, "timelimit");
            return (Criteria) this;
        }

        public Criteria andTimelimitLessThanOrEqualTo(Integer value) {
            addCriterion("timelimit <=", value, "timelimit");
            return (Criteria) this;
        }

        public Criteria andTimelimitIn(List<Integer> values) {
            addCriterion("timelimit in", values, "timelimit");
            return (Criteria) this;
        }

        public Criteria andTimelimitNotIn(List<Integer> values) {
            addCriterion("timelimit not in", values, "timelimit");
            return (Criteria) this;
        }

        public Criteria andTimelimitBetween(Integer value1, Integer value2) {
            addCriterion("timelimit between", value1, value2, "timelimit");
            return (Criteria) this;
        }

        public Criteria andTimelimitNotBetween(Integer value1, Integer value2) {
            addCriterion("timelimit not between", value1, value2, "timelimit");
            return (Criteria) this;
        }

        public Criteria andMemorylimitIsNull() {
            addCriterion("memorylimit is null");
            return (Criteria) this;
        }

        public Criteria andMemorylimitIsNotNull() {
            addCriterion("memorylimit is not null");
            return (Criteria) this;
        }

        public Criteria andMemorylimitEqualTo(Integer value) {
            addCriterion("memorylimit =", value, "memorylimit");
            return (Criteria) this;
        }

        public Criteria andMemorylimitNotEqualTo(Integer value) {
            addCriterion("memorylimit <>", value, "memorylimit");
            return (Criteria) this;
        }

        public Criteria andMemorylimitGreaterThan(Integer value) {
            addCriterion("memorylimit >", value, "memorylimit");
            return (Criteria) this;
        }

        public Criteria andMemorylimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("memorylimit >=", value, "memorylimit");
            return (Criteria) this;
        }

        public Criteria andMemorylimitLessThan(Integer value) {
            addCriterion("memorylimit <", value, "memorylimit");
            return (Criteria) this;
        }

        public Criteria andMemorylimitLessThanOrEqualTo(Integer value) {
            addCriterion("memorylimit <=", value, "memorylimit");
            return (Criteria) this;
        }

        public Criteria andMemorylimitIn(List<Integer> values) {
            addCriterion("memorylimit in", values, "memorylimit");
            return (Criteria) this;
        }

        public Criteria andMemorylimitNotIn(List<Integer> values) {
            addCriterion("memorylimit not in", values, "memorylimit");
            return (Criteria) this;
        }

        public Criteria andMemorylimitBetween(Integer value1, Integer value2) {
            addCriterion("memorylimit between", value1, value2, "memorylimit");
            return (Criteria) this;
        }

        public Criteria andMemorylimitNotBetween(Integer value1, Integer value2) {
            addCriterion("memorylimit not between", value1, value2, "memorylimit");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSpecialIsNull() {
            addCriterion("special is null");
            return (Criteria) this;
        }

        public Criteria andSpecialIsNotNull() {
            addCriterion("special is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialEqualTo(Integer value) {
            addCriterion("special =", value, "special");
            return (Criteria) this;
        }

        public Criteria andSpecialNotEqualTo(Integer value) {
            addCriterion("special <>", value, "special");
            return (Criteria) this;
        }

        public Criteria andSpecialGreaterThan(Integer value) {
            addCriterion("special >", value, "special");
            return (Criteria) this;
        }

        public Criteria andSpecialGreaterThanOrEqualTo(Integer value) {
            addCriterion("special >=", value, "special");
            return (Criteria) this;
        }

        public Criteria andSpecialLessThan(Integer value) {
            addCriterion("special <", value, "special");
            return (Criteria) this;
        }

        public Criteria andSpecialLessThanOrEqualTo(Integer value) {
            addCriterion("special <=", value, "special");
            return (Criteria) this;
        }

        public Criteria andSpecialIn(List<Integer> values) {
            addCriterion("special in", values, "special");
            return (Criteria) this;
        }

        public Criteria andSpecialNotIn(List<Integer> values) {
            addCriterion("special not in", values, "special");
            return (Criteria) this;
        }

        public Criteria andSpecialBetween(Integer value1, Integer value2) {
            addCriterion("special between", value1, value2, "special");
            return (Criteria) this;
        }

        public Criteria andSpecialNotBetween(Integer value1, Integer value2) {
            addCriterion("special not between", value1, value2, "special");
            return (Criteria) this;
        }

        public Criteria andHintIsNull() {
            addCriterion("hint is null");
            return (Criteria) this;
        }

        public Criteria andHintIsNotNull() {
            addCriterion("hint is not null");
            return (Criteria) this;
        }

        public Criteria andHintEqualTo(String value) {
            addCriterion("hint =", value, "hint");
            return (Criteria) this;
        }

        public Criteria andHintNotEqualTo(String value) {
            addCriterion("hint <>", value, "hint");
            return (Criteria) this;
        }

        public Criteria andHintGreaterThan(String value) {
            addCriterion("hint >", value, "hint");
            return (Criteria) this;
        }

        public Criteria andHintGreaterThanOrEqualTo(String value) {
            addCriterion("hint >=", value, "hint");
            return (Criteria) this;
        }

        public Criteria andHintLessThan(String value) {
            addCriterion("hint <", value, "hint");
            return (Criteria) this;
        }

        public Criteria andHintLessThanOrEqualTo(String value) {
            addCriterion("hint <=", value, "hint");
            return (Criteria) this;
        }

        public Criteria andHintLike(String value) {
            addCriterion("hint like", value, "hint");
            return (Criteria) this;
        }

        public Criteria andHintNotLike(String value) {
            addCriterion("hint not like", value, "hint");
            return (Criteria) this;
        }

        public Criteria andHintIn(List<String> values) {
            addCriterion("hint in", values, "hint");
            return (Criteria) this;
        }

        public Criteria andHintNotIn(List<String> values) {
            addCriterion("hint not in", values, "hint");
            return (Criteria) this;
        }

        public Criteria andHintBetween(String value1, String value2) {
            addCriterion("hint between", value1, value2, "hint");
            return (Criteria) this;
        }

        public Criteria andHintNotBetween(String value1, String value2) {
            addCriterion("hint not between", value1, value2, "hint");
            return (Criteria) this;
        }

        public Criteria andOr(String value) {
            addCriterion("("+value+")");
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