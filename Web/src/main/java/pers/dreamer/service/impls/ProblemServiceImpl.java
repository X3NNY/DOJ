package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.bean.*;
import pers.dreamer.dao.*;
import pers.dreamer.service.ProblemService;
import pers.dreamer.service.ProbleminfoService;
import pers.dreamer.service.SubmitlistService;
import pers.dreamer.service.UserService;
import pers.dreamer.util.tools.MyUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    ProblemvoteMapper problemvoteMapper;

    @Autowired
    ProbleminfoMapper probleminfoMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ProblemtagMapper problemtagMapper;

    @Autowired
    ProblemcollectionMapper problemcollectionMapper;

    @Autowired
    ProbleminfoService probleminfoService;

    @Autowired
    SubmitlistService submitlistService;

    @Autowired
    TagsMapper tagsMapper;

    @Override
    public Problem findProblemByPid(Integer pid,User user) {
        ProblemExample problemExample = new ProblemExample();
        problemExample.or().andPidEqualTo(pid).andStatusEqualTo(0);
        if(user!=null){
            ProblemExample.Criteria criteria1 = problemExample.or().andPidEqualTo(pid).andStatusEqualTo(1);
            if(user.getRole() < 6) criteria1.andUidEqualTo(user.getUid());
        }
        List<Problem> problems = problemMapper.selectByExample(problemExample);
        return problems.isEmpty()?null:problems.get(0);
    }

    @Override
    public List<Problem> findProblemsByPageAndNameAndTagAndStar(Integer page, Integer limitSize, String name, String tag, Integer star,User user) {
        ProblemExample problemExample = pattern1(name,tag,star,user);
        if (problemExample == null) return null;
        if(page!=null) problemExample.setPage(page*limitSize);
        problemExample.setLimitSize(limitSize);
        return problemMapper.selectByExample(problemExample);
    }

    @Override
    public List<Problem> findProblemsToIndex() {
        ProblemExample problemExample = new ProblemExample();
        problemExample.setPage(0);
        problemExample.setLimitSize(5);
        problemExample.setOrderByClause("pid desc");
        problemExample.createCriteria().andStatusEqualTo(0);
        return problemMapper.selectByExample(problemExample);
    }

    @Override
    public Integer findProblemVoteByUidAndPid(Integer uid, Integer pid) {
        if (uid == null || pid == null) return 0;
        Integer vote = problemvoteMapper.findVoteByPidAndUid(pid,uid);
        return vote == null ? 0 : vote;
    }

    @Override
    public void insertProblemVoteyByUidAndPid(Integer uid, Integer pid, int i) {
        Problemvote problemvote = new Problemvote();
        problemvote.setCreatetime(new Date());
        problemvote.setPid(pid);
        problemvote.setVote(i);
        problemvote.setUid(uid);
        problemvoteMapper.insertSelective(problemvote);
    }

    @Override
    public User findAuthorByPid(Integer pid) {
        Integer uid = problemMapper.findUidByPid(pid);
        if(uid == null) return null;
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public Long findProblemsSizeByPageAndNameAndTagAndStar(String name, String tag, Integer star,User user) {
        return problemMapper.countByExample(pattern1(name,tag,star,user));
    }

    private ProblemExample pattern1(String name, String tag, Integer star,User user){
        ProblemExample problemExample = new ProblemExample();
        ProblemExample.Criteria criteria = problemExample.createCriteria();

        if(star != null && star == 1 && user != null){
            List<Integer> ids = problemcollectionMapper.findPidByUid(user.getUid());
            if (ids.isEmpty()) return null;
            criteria.andPidIn(ids);
        }
        if(tag != null&& !tag.equals("")){
            Integer tid = tagsMapper.findTidByName(tag);
            if (tid == null) return null;
            List<Integer> ids = problemtagMapper.findPidByTagName(tid);
            if (ids.isEmpty()) return null;
            criteria.andPidIn(ids);
        }

        if (name != null && name.length()!=0) {
            String or = "`title` like \"%"+name+"%\"";

            Integer uid = userMapper.findUidByUsername(name);
            if (uid != null) {
                or += " or uid=\""+uid+"\"";
            }
            if (MyUtil.isNumber(name)) {
                int pid = Integer.parseInt(name);
                or += " or pid=\""+pid+"\"";
            }
            criteria.andOr(or);
        }

        if (user == null || user.getRole() < 6) {
            String or = "status=0 or uid=\""+(user==null?"":user.getUid())+"\"";
            criteria.andOr(or);
        }
        return problemExample;
    }

    @Override
    public List<Problemtag> findProblemtagsUidByPid(Integer pid) {
        if(pid == null) return null;
        return problemtagMapper.findProblemtagsByPid(pid);
    }

    @Transactional
    @Override
    public Boolean insertProblem(Problem problem) {
        Problem title = findProblemByTitle(problem.getTitle());
        if(title == null) problemMapper.insertSelective(problem);
        return title == null;
    }

    @Override
    public Problem findProblemByTitle(String title) {
        ProblemExample problemExample = new ProblemExample();
        problemExample.createCriteria().andTitleEqualTo(title);
        List<Problem> problems = problemMapper.selectByExample(problemExample);
        return problems.size() > 0 ? problems.get(0) : null;
    }

    @Transactional
    @Override
    public boolean insertProblemTag(Problemtag problemtag) {
        Problemtag problemtags = findProblemtagsByUidAndPid(problemtag.getUid(), problemtag.getPid());
        if(problemtags == null) problemtagMapper.insertSelective(problemtag);
        return problemtags == null;
    }

    @Override
    public Problemtag findProblemtagsByUidAndPid(Integer uid, Integer pid) {
        if(pid == null || uid == null) return null;
        ProblemtagExample problemtagExample = new ProblemtagExample();
        problemtagExample.createCriteria().andPidEqualTo(pid).andUidEqualTo(uid);
        List<Problemtag> problemtags = problemtagMapper.selectByExample(problemtagExample);
        return problemtags.size() > 0 ? problemtags.get(0) : null;
    }

    @Transactional
    @Override
    public void changeProblemCollectionByPidAndUid(Integer pid, Integer uid) {
        if(uid==null||pid==null) return;
        ProblemcollectionExample problemcollectionExample = new ProblemcollectionExample();
        problemcollectionExample.createCriteria().andUidEqualTo(uid).andPidEqualTo(pid);
        List<Problemcollection> problemcollections = problemcollectionMapper.selectByExample(problemcollectionExample);
        if(problemcollections.size() > 0) problemcollectionMapper.deleteByPrimaryKey(problemcollections.get(0).getId());
        else{
            Problemcollection problemcollection = new Problemcollection();
            problemcollection.setDate(new Date());
            problemcollection.setPid(pid);
            problemcollection.setUid(uid);
            problemcollectionMapper.insertSelective(problemcollection);
        }
    }

    @Transactional
    @Override
    public boolean delTagsByUidAndPidAndTag(Integer uid, Integer pid, String tag) {
        if(uid == null||pid==null) return false;
        ProblemtagExample problemtagExample = new ProblemtagExample();
        //problemtagExample.createCriteria().andUidEqualTo(uid).andPidEqualTo(pid).andTagEqualTo(tag);
        List<Problemtag> problemtags = problemtagMapper.selectByExample(problemtagExample);
        if(problemtags.size() == 0) return false;
        problemtagMapper.deleteByExample(problemtagExample);
        return true;
    }

    @Transactional
    @Override
    public boolean editProblem(Problem problem) {
        if(problem.getPid() == null) return false;
        Problem problem1 = problemMapper.selectByPrimaryKey(problem.getPid());
        if(problem1 == null) return false;
        problemMapper.updateByPrimaryKeySelective(problem);
        return true;
    }

    @Transactional
    @Override
    public boolean updateProblemStatusByPidAndStatus(Integer pid, Integer status) {
        if(pid == null || status == null) return false;
        Problem problem = problemMapper.selectByPrimaryKey(pid);
        if(problem == null) return false;
        problem.setStatus(status);
        problemMapper.updateByPrimaryKey(problem);
        return true;
    }

    @Transactional
    @Override
    public void updateProblem(Problem problem) {
        problemMapper.updateByPrimaryKeySelective(problem);
    }

    @Override
    public Problem findProblemByPidWithoutUid(Integer pid) {
        return problemMapper.selectByPrimaryKey(pid);
    }

    @Override
    public boolean isVoteExist(Integer uid, Integer pid) {
        return problemvoteMapper.findVoteByPidAndUid(pid,uid) != null;
    }

    @Override
    public Integer findAuthorIdByPid(Integer pid) {
        return problemMapper.findUidByPid(pid);
    }

    @Override
    public boolean isProblemCanBeViewed(Integer pid, User user) {
        if (user.getRole() >= 6) return true;
        return problemMapper.isProblemCanBeViewed(pid,user.getUid()) != null;
    }

    @Override
    public String findProblemTitleByPid(Integer pid) {
        return problemMapper.findTitleByPid(pid);
    }

    @Override
    public boolean isExistProblem(Integer pid) {
        return problemMapper.isExistByPid(pid) != null;
    }

    @Override
    public Integer findTidByTagname(String tag) {
        return tagsMapper.findTidByName(tag);
    }

    @Override
    public String findTagnameByTid(Integer tid) {
        return tagsMapper.findNameByTid(tid);
    }

    @Override
    public List<Tags> findTags() {
        return tagsMapper.findAll();
    }

    @Override
    public boolean isExistTag(Integer tid) {
        return tagsMapper.selectByPrimaryKey(tid) != null;
    }
}
