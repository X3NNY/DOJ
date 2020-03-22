package pers.dreamer.oj.judge.pojo;

import java.util.ArrayList;
import java.util.List;

public class ProbleminfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProbleminfoExample() {
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

        public Criteria andAcCntIsNull() {
            addCriterion("ac_cnt is null");
            return (Criteria) this;
        }

        public Criteria andAcCntIsNotNull() {
            addCriterion("ac_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andAcCntEqualTo(Integer value) {
            addCriterion("ac_cnt =", value, "acCnt");
            return (Criteria) this;
        }

        public Criteria andAcCntNotEqualTo(Integer value) {
            addCriterion("ac_cnt <>", value, "acCnt");
            return (Criteria) this;
        }

        public Criteria andAcCntGreaterThan(Integer value) {
            addCriterion("ac_cnt >", value, "acCnt");
            return (Criteria) this;
        }

        public Criteria andAcCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("ac_cnt >=", value, "acCnt");
            return (Criteria) this;
        }

        public Criteria andAcCntLessThan(Integer value) {
            addCriterion("ac_cnt <", value, "acCnt");
            return (Criteria) this;
        }

        public Criteria andAcCntLessThanOrEqualTo(Integer value) {
            addCriterion("ac_cnt <=", value, "acCnt");
            return (Criteria) this;
        }

        public Criteria andAcCntIn(List<Integer> values) {
            addCriterion("ac_cnt in", values, "acCnt");
            return (Criteria) this;
        }

        public Criteria andAcCntNotIn(List<Integer> values) {
            addCriterion("ac_cnt not in", values, "acCnt");
            return (Criteria) this;
        }

        public Criteria andAcCntBetween(Integer value1, Integer value2) {
            addCriterion("ac_cnt between", value1, value2, "acCnt");
            return (Criteria) this;
        }

        public Criteria andAcCntNotBetween(Integer value1, Integer value2) {
            addCriterion("ac_cnt not between", value1, value2, "acCnt");
            return (Criteria) this;
        }

        public Criteria andPeCntIsNull() {
            addCriterion("pe_cnt is null");
            return (Criteria) this;
        }

        public Criteria andPeCntIsNotNull() {
            addCriterion("pe_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andPeCntEqualTo(Integer value) {
            addCriterion("pe_cnt =", value, "peCnt");
            return (Criteria) this;
        }

        public Criteria andPeCntNotEqualTo(Integer value) {
            addCriterion("pe_cnt <>", value, "peCnt");
            return (Criteria) this;
        }

        public Criteria andPeCntGreaterThan(Integer value) {
            addCriterion("pe_cnt >", value, "peCnt");
            return (Criteria) this;
        }

        public Criteria andPeCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("pe_cnt >=", value, "peCnt");
            return (Criteria) this;
        }

        public Criteria andPeCntLessThan(Integer value) {
            addCriterion("pe_cnt <", value, "peCnt");
            return (Criteria) this;
        }

        public Criteria andPeCntLessThanOrEqualTo(Integer value) {
            addCriterion("pe_cnt <=", value, "peCnt");
            return (Criteria) this;
        }

        public Criteria andPeCntIn(List<Integer> values) {
            addCriterion("pe_cnt in", values, "peCnt");
            return (Criteria) this;
        }

        public Criteria andPeCntNotIn(List<Integer> values) {
            addCriterion("pe_cnt not in", values, "peCnt");
            return (Criteria) this;
        }

        public Criteria andPeCntBetween(Integer value1, Integer value2) {
            addCriterion("pe_cnt between", value1, value2, "peCnt");
            return (Criteria) this;
        }

        public Criteria andPeCntNotBetween(Integer value1, Integer value2) {
            addCriterion("pe_cnt not between", value1, value2, "peCnt");
            return (Criteria) this;
        }

        public Criteria andWaCntIsNull() {
            addCriterion("wa_cnt is null");
            return (Criteria) this;
        }

        public Criteria andWaCntIsNotNull() {
            addCriterion("wa_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andWaCntEqualTo(Integer value) {
            addCriterion("wa_cnt =", value, "waCnt");
            return (Criteria) this;
        }

        public Criteria andWaCntNotEqualTo(Integer value) {
            addCriterion("wa_cnt <>", value, "waCnt");
            return (Criteria) this;
        }

        public Criteria andWaCntGreaterThan(Integer value) {
            addCriterion("wa_cnt >", value, "waCnt");
            return (Criteria) this;
        }

        public Criteria andWaCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("wa_cnt >=", value, "waCnt");
            return (Criteria) this;
        }

        public Criteria andWaCntLessThan(Integer value) {
            addCriterion("wa_cnt <", value, "waCnt");
            return (Criteria) this;
        }

        public Criteria andWaCntLessThanOrEqualTo(Integer value) {
            addCriterion("wa_cnt <=", value, "waCnt");
            return (Criteria) this;
        }

        public Criteria andWaCntIn(List<Integer> values) {
            addCriterion("wa_cnt in", values, "waCnt");
            return (Criteria) this;
        }

        public Criteria andWaCntNotIn(List<Integer> values) {
            addCriterion("wa_cnt not in", values, "waCnt");
            return (Criteria) this;
        }

        public Criteria andWaCntBetween(Integer value1, Integer value2) {
            addCriterion("wa_cnt between", value1, value2, "waCnt");
            return (Criteria) this;
        }

        public Criteria andWaCntNotBetween(Integer value1, Integer value2) {
            addCriterion("wa_cnt not between", value1, value2, "waCnt");
            return (Criteria) this;
        }

        public Criteria andTleCntIsNull() {
            addCriterion("tle_cnt is null");
            return (Criteria) this;
        }

        public Criteria andTleCntIsNotNull() {
            addCriterion("tle_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andTleCntEqualTo(Integer value) {
            addCriterion("tle_cnt =", value, "tleCnt");
            return (Criteria) this;
        }

        public Criteria andTleCntNotEqualTo(Integer value) {
            addCriterion("tle_cnt <>", value, "tleCnt");
            return (Criteria) this;
        }

        public Criteria andTleCntGreaterThan(Integer value) {
            addCriterion("tle_cnt >", value, "tleCnt");
            return (Criteria) this;
        }

        public Criteria andTleCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("tle_cnt >=", value, "tleCnt");
            return (Criteria) this;
        }

        public Criteria andTleCntLessThan(Integer value) {
            addCriterion("tle_cnt <", value, "tleCnt");
            return (Criteria) this;
        }

        public Criteria andTleCntLessThanOrEqualTo(Integer value) {
            addCriterion("tle_cnt <=", value, "tleCnt");
            return (Criteria) this;
        }

        public Criteria andTleCntIn(List<Integer> values) {
            addCriterion("tle_cnt in", values, "tleCnt");
            return (Criteria) this;
        }

        public Criteria andTleCntNotIn(List<Integer> values) {
            addCriterion("tle_cnt not in", values, "tleCnt");
            return (Criteria) this;
        }

        public Criteria andTleCntBetween(Integer value1, Integer value2) {
            addCriterion("tle_cnt between", value1, value2, "tleCnt");
            return (Criteria) this;
        }

        public Criteria andTleCntNotBetween(Integer value1, Integer value2) {
            addCriterion("tle_cnt not between", value1, value2, "tleCnt");
            return (Criteria) this;
        }

        public Criteria andMleCntIsNull() {
            addCriterion("mle_cnt is null");
            return (Criteria) this;
        }

        public Criteria andMleCntIsNotNull() {
            addCriterion("mle_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andMleCntEqualTo(Integer value) {
            addCriterion("mle_cnt =", value, "mleCnt");
            return (Criteria) this;
        }

        public Criteria andMleCntNotEqualTo(Integer value) {
            addCriterion("mle_cnt <>", value, "mleCnt");
            return (Criteria) this;
        }

        public Criteria andMleCntGreaterThan(Integer value) {
            addCriterion("mle_cnt >", value, "mleCnt");
            return (Criteria) this;
        }

        public Criteria andMleCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("mle_cnt >=", value, "mleCnt");
            return (Criteria) this;
        }

        public Criteria andMleCntLessThan(Integer value) {
            addCriterion("mle_cnt <", value, "mleCnt");
            return (Criteria) this;
        }

        public Criteria andMleCntLessThanOrEqualTo(Integer value) {
            addCriterion("mle_cnt <=", value, "mleCnt");
            return (Criteria) this;
        }

        public Criteria andMleCntIn(List<Integer> values) {
            addCriterion("mle_cnt in", values, "mleCnt");
            return (Criteria) this;
        }

        public Criteria andMleCntNotIn(List<Integer> values) {
            addCriterion("mle_cnt not in", values, "mleCnt");
            return (Criteria) this;
        }

        public Criteria andMleCntBetween(Integer value1, Integer value2) {
            addCriterion("mle_cnt between", value1, value2, "mleCnt");
            return (Criteria) this;
        }

        public Criteria andMleCntNotBetween(Integer value1, Integer value2) {
            addCriterion("mle_cnt not between", value1, value2, "mleCnt");
            return (Criteria) this;
        }

        public Criteria andCeCntIsNull() {
            addCriterion("ce_cnt is null");
            return (Criteria) this;
        }

        public Criteria andCeCntIsNotNull() {
            addCriterion("ce_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andCeCntEqualTo(Integer value) {
            addCriterion("ce_cnt =", value, "ceCnt");
            return (Criteria) this;
        }

        public Criteria andCeCntNotEqualTo(Integer value) {
            addCriterion("ce_cnt <>", value, "ceCnt");
            return (Criteria) this;
        }

        public Criteria andCeCntGreaterThan(Integer value) {
            addCriterion("ce_cnt >", value, "ceCnt");
            return (Criteria) this;
        }

        public Criteria andCeCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("ce_cnt >=", value, "ceCnt");
            return (Criteria) this;
        }

        public Criteria andCeCntLessThan(Integer value) {
            addCriterion("ce_cnt <", value, "ceCnt");
            return (Criteria) this;
        }

        public Criteria andCeCntLessThanOrEqualTo(Integer value) {
            addCriterion("ce_cnt <=", value, "ceCnt");
            return (Criteria) this;
        }

        public Criteria andCeCntIn(List<Integer> values) {
            addCriterion("ce_cnt in", values, "ceCnt");
            return (Criteria) this;
        }

        public Criteria andCeCntNotIn(List<Integer> values) {
            addCriterion("ce_cnt not in", values, "ceCnt");
            return (Criteria) this;
        }

        public Criteria andCeCntBetween(Integer value1, Integer value2) {
            addCriterion("ce_cnt between", value1, value2, "ceCnt");
            return (Criteria) this;
        }

        public Criteria andCeCntNotBetween(Integer value1, Integer value2) {
            addCriterion("ce_cnt not between", value1, value2, "ceCnt");
            return (Criteria) this;
        }

        public Criteria andReCntIsNull() {
            addCriterion("re_cnt is null");
            return (Criteria) this;
        }

        public Criteria andReCntIsNotNull() {
            addCriterion("re_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andReCntEqualTo(Integer value) {
            addCriterion("re_cnt =", value, "reCnt");
            return (Criteria) this;
        }

        public Criteria andReCntNotEqualTo(Integer value) {
            addCriterion("re_cnt <>", value, "reCnt");
            return (Criteria) this;
        }

        public Criteria andReCntGreaterThan(Integer value) {
            addCriterion("re_cnt >", value, "reCnt");
            return (Criteria) this;
        }

        public Criteria andReCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("re_cnt >=", value, "reCnt");
            return (Criteria) this;
        }

        public Criteria andReCntLessThan(Integer value) {
            addCriterion("re_cnt <", value, "reCnt");
            return (Criteria) this;
        }

        public Criteria andReCntLessThanOrEqualTo(Integer value) {
            addCriterion("re_cnt <=", value, "reCnt");
            return (Criteria) this;
        }

        public Criteria andReCntIn(List<Integer> values) {
            addCriterion("re_cnt in", values, "reCnt");
            return (Criteria) this;
        }

        public Criteria andReCntNotIn(List<Integer> values) {
            addCriterion("re_cnt not in", values, "reCnt");
            return (Criteria) this;
        }

        public Criteria andReCntBetween(Integer value1, Integer value2) {
            addCriterion("re_cnt between", value1, value2, "reCnt");
            return (Criteria) this;
        }

        public Criteria andReCntNotBetween(Integer value1, Integer value2) {
            addCriterion("re_cnt not between", value1, value2, "reCnt");
            return (Criteria) this;
        }

        public Criteria andOleCntIsNull() {
            addCriterion("ole_cnt is null");
            return (Criteria) this;
        }

        public Criteria andOleCntIsNotNull() {
            addCriterion("ole_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andOleCntEqualTo(Integer value) {
            addCriterion("ole_cnt =", value, "oleCnt");
            return (Criteria) this;
        }

        public Criteria andOleCntNotEqualTo(Integer value) {
            addCriterion("ole_cnt <>", value, "oleCnt");
            return (Criteria) this;
        }

        public Criteria andOleCntGreaterThan(Integer value) {
            addCriterion("ole_cnt >", value, "oleCnt");
            return (Criteria) this;
        }

        public Criteria andOleCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("ole_cnt >=", value, "oleCnt");
            return (Criteria) this;
        }

        public Criteria andOleCntLessThan(Integer value) {
            addCriterion("ole_cnt <", value, "oleCnt");
            return (Criteria) this;
        }

        public Criteria andOleCntLessThanOrEqualTo(Integer value) {
            addCriterion("ole_cnt <=", value, "oleCnt");
            return (Criteria) this;
        }

        public Criteria andOleCntIn(List<Integer> values) {
            addCriterion("ole_cnt in", values, "oleCnt");
            return (Criteria) this;
        }

        public Criteria andOleCntNotIn(List<Integer> values) {
            addCriterion("ole_cnt not in", values, "oleCnt");
            return (Criteria) this;
        }

        public Criteria andOleCntBetween(Integer value1, Integer value2) {
            addCriterion("ole_cnt between", value1, value2, "oleCnt");
            return (Criteria) this;
        }

        public Criteria andOleCntNotBetween(Integer value1, Integer value2) {
            addCriterion("ole_cnt not between", value1, value2, "oleCnt");
            return (Criteria) this;
        }

        public Criteria andSeCntIsNull() {
            addCriterion("se_cnt is null");
            return (Criteria) this;
        }

        public Criteria andSeCntIsNotNull() {
            addCriterion("se_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andSeCntEqualTo(Integer value) {
            addCriterion("se_cnt =", value, "seCnt");
            return (Criteria) this;
        }

        public Criteria andSeCntNotEqualTo(Integer value) {
            addCriterion("se_cnt <>", value, "seCnt");
            return (Criteria) this;
        }

        public Criteria andSeCntGreaterThan(Integer value) {
            addCriterion("se_cnt >", value, "seCnt");
            return (Criteria) this;
        }

        public Criteria andSeCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("se_cnt >=", value, "seCnt");
            return (Criteria) this;
        }

        public Criteria andSeCntLessThan(Integer value) {
            addCriterion("se_cnt <", value, "seCnt");
            return (Criteria) this;
        }

        public Criteria andSeCntLessThanOrEqualTo(Integer value) {
            addCriterion("se_cnt <=", value, "seCnt");
            return (Criteria) this;
        }

        public Criteria andSeCntIn(List<Integer> values) {
            addCriterion("se_cnt in", values, "seCnt");
            return (Criteria) this;
        }

        public Criteria andSeCntNotIn(List<Integer> values) {
            addCriterion("se_cnt not in", values, "seCnt");
            return (Criteria) this;
        }

        public Criteria andSeCntBetween(Integer value1, Integer value2) {
            addCriterion("se_cnt between", value1, value2, "seCnt");
            return (Criteria) this;
        }

        public Criteria andSeCntNotBetween(Integer value1, Integer value2) {
            addCriterion("se_cnt not between", value1, value2, "seCnt");
            return (Criteria) this;
        }

        public Criteria andTagIsNull() {
            addCriterion("tag is null");
            return (Criteria) this;
        }

        public Criteria andTagIsNotNull() {
            addCriterion("tag is not null");
            return (Criteria) this;
        }

        public Criteria andTagEqualTo(String value) {
            addCriterion("tag =", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotEqualTo(String value) {
            addCriterion("tag <>", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThan(String value) {
            addCriterion("tag >", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThanOrEqualTo(String value) {
            addCriterion("tag >=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThan(String value) {
            addCriterion("tag <", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThanOrEqualTo(String value) {
            addCriterion("tag <=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLike(String value) {
            addCriterion("tag like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotLike(String value) {
            addCriterion("tag not like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagIn(List<String> values) {
            addCriterion("tag in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotIn(List<String> values) {
            addCriterion("tag not in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagBetween(String value1, String value2) {
            addCriterion("tag between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotBetween(String value1, String value2) {
            addCriterion("tag not between", value1, value2, "tag");
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