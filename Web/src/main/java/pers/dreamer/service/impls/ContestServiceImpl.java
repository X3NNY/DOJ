package pers.dreamer.service.impls;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.bean.*;
import pers.dreamer.dao.*;
import pers.dreamer.service.ContestService;
import pers.dreamer.service.SubmitlistService;
import pers.dreamer.service.UserService;
import pers.dreamer.util.ContestOnUtil;
import pers.dreamer.util.tools.MyUtil;

import java.text.DateFormat;
import java.util.*;

@Service
@EnableRabbit
public class ContestServiceImpl implements ContestService {

    @Autowired
    ContestproblemsMapper contestproblemsMapper;

    @Autowired
    ContestregisterMapper contestregisterMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    ProbleminfoMapper probleminfoMapper;

    @Autowired
    SubmitlistMapper submitlistMapper;

    @Autowired
    CodeMapper codeMapper;

    @Autowired
    ContestMapper contestMapper;

    @Autowired
    ContestnoticeMapper contestnoticeMapper;

    @Autowired
    ContestvoteMapper contestvoteMapper;

    @Autowired
    UserService userService;

    @Autowired
    SubmitlistService submitlistService;

    @Autowired
    DateFormat dateFormat;

    @Autowired
    ContestsubmitinfoMapper contestsubmitinfoMapper;

    @Override
    public Contestproblems findProblemIsExistsOnContestByPid(Integer pid) {
        if(pid == null) return null;
        ContestproblemsExample contestproblemsExample = new ContestproblemsExample();
        contestproblemsExample.createCriteria().andPidEqualTo(pid).andCidEqualTo(ContestOnUtil.cid);
        List<Contestproblems> contestproblems = contestproblemsMapper.selectByExample(contestproblemsExample);
        return contestproblems.size() >1 ?contestproblems.get(0):null;
    }

    @Override
    public boolean findUserRegisterOnTheContest(Integer uid,Integer cid) {
        if(cid == null || uid == null) return false;
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andUidEqualTo(uid).andCidEqualTo(cid);
        return contestregisterMapper.countByExample(contestregisterExample) > 0;
    }

    @Override
    public List<Contest> findContestByPageAndNameAndUid(Integer page,Integer limitSize,String name,Integer uid) {
        ContestExample contestExample = new ContestExample();
        if(page!=null)contestExample.setPage(page*limitSize);
        contestExample.setLimitSize(limitSize);
        ContestExample.Criteria criteria = contestExample.createCriteria();
        contestExample.setOrderByClause("cid desc");
        if (uid == null) {
            criteria.andUidNotEqualTo(-1);
        } else {
            criteria.andUidEqualTo(uid);
        }
        if(name != null && name.length() != 0) {
            name = name.trim();
            StringBuffer s = new StringBuffer("%");
            for (int i = 0; i < name.length(); i++) {
                s.append(name.charAt(i));
                s.append('%');
            }
            criteria.andTitleLike(s.toString());
        }
        return contestMapper.selectByExample(contestExample);
    }

    @Override
    public List<User> findContestForAuthorsByCid(Integer cid) {
        if(cid == null) return new ArrayList<>();
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andCidEqualTo(cid).andRoleEqualTo(1);
        List<Contestregister> contestregisters = contestregisterMapper.selectByExample(contestregisterExample);
        List<User> users = new ArrayList<>();
        for (Contestregister contestregister : contestregisters) {
            User user = userService.findUserStyleNameByUid(contestregister.getUid());
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public Integer findMyVoteByUidAndCid(Integer uid, Integer cid) {
        if(uid == null || cid == null) return 0;
        ContestvoteExample contestvoteExample = new ContestvoteExample();
        contestvoteExample.createCriteria().andCidEqualTo(cid).andUidEqualTo(uid);
        List<Contestvote> contestvotes = contestvoteMapper.selectByExample(contestvoteExample);
        if(contestvotes.size() > 0) return contestvotes.get(0).getVote();
        return 0;
    }

    @Override
    public Long findRegisterTotalOnContestByCid(Integer cid) {
        if(cid == null) return Long.valueOf(0);
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andCidEqualTo(cid);
        return contestregisterMapper.countByExample(contestregisterExample);
    }

    @Override
    public Long findContestSizeByPageAndName(String name) {
        ContestExample contestExample = new ContestExample();
        if(name != null) {
            name = name.trim();
            StringBuffer s = new StringBuffer("%");
            for (int i = 0; i < name.length(); i++) {
                s.append(name.charAt(i));
                s.append('%');
            }
            contestExample.createCriteria().andTitleLike(s.toString());
        }
        return contestMapper.countByExample(contestExample);
    }

    @Override
    public Integer findStateOnContestByUidAndCid(Integer uid, Integer cid) {
        if(uid == null || cid == null) return  0;
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andCidEqualTo(cid).andUidEqualTo(uid);
        List<Contestregister> contestregisters = contestregisterMapper.selectByExample(contestregisterExample);
        if(!contestregisters.isEmpty()){
            Contestregister contestregister = contestregisters.get(0);
            if(contestregister.getRole() == 1) return 2;
            return contestregister.getStatus();
        }
        return 0;
    }

    @Override
    public Contest findContestInfoByCid(Integer cid) {
        if(cid == null) return null;
        return contestMapper.selectByPrimaryKey(cid);
    }

    @Override
    public List<Problem> findProblemsByCid(Integer cid) {
        if(cid == null) return  null;
        ContestproblemsExample contestproblemsExample = new ContestproblemsExample();
        contestproblemsExample.createCriteria().andCidEqualTo(cid);
        List<Problem> problems = new ArrayList<>();
        List<Contestproblems> contestproblems = contestproblemsMapper.selectByExample(contestproblemsExample);
        for(Contestproblems contestproblem : contestproblems) {
            Problem problem = problemMapper.selectByPrimaryKey(contestproblem.getPid());
            if(problem != null) problems.add(problem);
        }
        return problems;
    }

    @Override
    public List<Contestproblems> findOrderProblemsByCid(Integer cid) {
        ContestproblemsExample contestproblemsExample = new ContestproblemsExample();
        contestproblemsExample.createCriteria().andCidEqualTo(cid);
        return contestproblemsMapper.selectByExample(contestproblemsExample);
    }

    @Override
    public Problem findProblemsByCidAndNum(Integer cid, Integer num) {
        if(cid == null || num == null) return null;
        ContestproblemsExample contestproblemsExample = new ContestproblemsExample();
        contestproblemsExample.createCriteria().andCidEqualTo(cid).andNumEqualTo(num);
        List<Contestproblems> contestproblems = contestproblemsMapper.selectByExample(contestproblemsExample);
        if(contestproblems.size() > 0) return problemMapper.selectByPrimaryKey(contestproblems.get(0).getPid());
        return null;
    }

    @Override
    public Long findStatusSizeOnContestByCidAndPageAndUidAndPidAndResAndLang(Integer cid, Integer uid, Integer pid, Integer res, String lang) {
        SubmitlistExample submitlistExample = new SubmitlistExample();
        SubmitlistExample.Criteria criteria = submitlistExample.createCriteria();
        criteria.andCidEqualTo(cid);
        if (uid != null && uid != -1) criteria = criteria.andUidEqualTo(uid);
        if (pid != null && pid != -1) criteria = criteria.andPidEqualTo(pid);
        if (res != null && res != -1) criteria = criteria.andResultEqualTo(res);
        if (lang != null && lang.length() != 0) criteria = criteria.andLangEqualTo(lang);
        return submitlistMapper.countByExample(submitlistExample);
    }

    @Override
    public List<Contestregister> findRestersOnContestByCidAndPage(Integer cid, Integer page) {
        ContestregisterExample example = new ContestregisterExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id limit "+page*50+",50");
        return contestregisterMapper.selectByExample(example);
    }

    @Override
    public Contestregister findContestReigsterByUidAndCid(Integer uid, Integer cid) {
        if(cid == null || uid == null) return null;
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andCidEqualTo(cid).andUidEqualTo(uid);
        List<Contestregister> contestregisters = contestregisterMapper.selectByExample(contestregisterExample);
        return contestregisters.size() > 0 ? contestregisters.get(0):null;
    }

    @Transactional
    @Override
    public void insertContestRegister(Contestregister contestregister) {
        if(contestregister.getUid() == null || contestregister.getCid() == null) return;
        contestregisterMapper.insertSelective(contestregister);
    }

    @Transactional
    @Override
    public void delContestRegister(Contestregister contestregister) {
        if(contestregister.getUid() == null || contestregister.getCid() == null) return;
        contestregisterMapper.deleteByPrimaryKey(contestregister.getId());
    }

    @Override
    public List<Submitlist> findStatusOnContestByCidAndPageAndUidAndPidAndResAndLang(Integer cid, Integer page, Integer limitSize,Integer uid, Integer pid, Integer res, String lang) {
        SubmitlistExample submitlistExample = new SubmitlistExample();
        submitlistExample.setOrderByClause(String.format("date desc limit %d,%d",page*limitSize,limitSize));
        SubmitlistExample.Criteria criteria = submitlistExample.createCriteria();
        criteria.andCidEqualTo(cid);
        if (uid != null && uid != -1) criteria = criteria.andUidEqualTo(uid);
        if (pid != null && pid != -1) criteria = criteria.andPidEqualTo(pid);
        if (res != null && res != -1) criteria = criteria.andResultEqualTo(res);
        if (lang != null && lang.length() != 0) criteria = criteria.andLangEqualTo(lang);
        return submitlistMapper.selectByExample(submitlistExample);
    }

    @Override
    public Integer findNumOnContestByPid(Integer pid) {
        if(pid == null) return null;
        ContestproblemsExample contestproblemsExample = new ContestproblemsExample();
        contestproblemsExample.createCriteria().andPidEqualTo(pid);
        List<Contestproblems> contestproblems = contestproblemsMapper.selectByExample(contestproblemsExample);
        if(contestproblems.size() > 0) return contestproblems.get(0).getNum();
        return null;
    }

    @Override
    public List<Contestnotice> findNoticesOnContestByCid(Integer cid) {
        ContestnoticeExample contestnoticeExample = new ContestnoticeExample();
        contestnoticeExample.createCriteria().andCidEqualTo(cid);
        return contestnoticeMapper.selectByExample(contestnoticeExample);
    }

    @Transactional
    @Override
    public void insertNotice(Integer cid, Integer pid,Integer uid, String question) {
        if(cid == null || pid == null) return;
        Contestnotice contestnotice = new Contestnotice();
        contestnotice.setQuestion(question);
        contestnotice.setCid(cid);
        contestnotice.setUid(uid);
        contestnotice.setPid(pid);
        contestnotice.setQdate(new Date());
        contestnoticeMapper.insertSelective(contestnotice);
    }

    @Override
    public Integer findPidByCidAndNum(Integer cid, Integer num) {
        if(cid == null || num == null) return null;
        return contestproblemsMapper.findPidByCidAndNum(cid,num);
    }

    @Override
    public Integer findVaildRegistersByCid(Integer cid) {
        if(cid == null) return null;
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andCidEqualTo(cid).andStatusNotEqualTo(0);
        List<Contestregister> contestregisters = contestregisterMapper.selectByExample(contestregisterExample);
        return contestregisters.size();
    }

    @Override
    public List<Contestregister> findRestersOnContestByCid(Integer cid) {
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andCidEqualTo(cid).andStatusNotEqualTo(0);
        return contestregisterMapper.selectByExample(contestregisterExample);
    }

    @Override
    public Integer findAc_numByUidAndCid(Integer uid, Integer cid) {
        if(cid == null || uid == null) return null;
        Integer num = submitlistMapper.findAcNumOnContestByUidAndCid(uid,cid);
        return num == null ? 0 : num;
    }

    @Override
    public Long findPenaltyOnContestByCidAndUid(Integer cid, Integer uid,Integer flag) {
        Long pt;
        if (flag == 0) {
            pt = submitlistMapper.calcPenaltyOnContestByCidAndUid(cid, uid);
        } else {
            pt = submitlistMapper.calcIOIPenaltyOnContestByCidAndUid(cid,uid);
        }
        return pt == null ? 0 : pt;
    }

    @Override
    public Submitlist findFirstlyAcProblemTimeOnContestByUidAndPid(Integer cid,Integer uid, Integer pid) {
        if(uid == null || pid == null || cid == -1) return null;
        return submitlistMapper.findFirstlyAcTimeOnContestByCidAndUidAndPid(cid,uid,pid);
    }

    @Override
    public Integer findStatusOnContestByUidAndCid(Integer uid, Integer cid) {
        if(uid == null || cid == null) return null;
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andCidEqualTo(cid).andUidEqualTo(uid);
        List<Contestregister> contestregisters = contestregisterMapper.selectByExample(contestregisterExample);
        if (contestregisters.isEmpty()) return null;
        return contestregisters.get(0).getRole() == 1?1:contestregisters.get(0).getStatus()-1;
    }

    @Transactional
    @Override
    public void updateRegisterByUidAndCid(Integer uid, Integer cid) {
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andCidEqualTo(cid).andUidEqualTo(uid);
        Contestregister contestregister = new Contestregister();
        contestregister.setStatus(1);
        contestregisterMapper.updateByExampleSelective(contestregister,contestregisterExample);
    }

    @Override
    public Long findContestsTotalByUid(Integer uid) {
        if(uid == null) return null;
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andUidEqualTo(uid).andRankIsNotNull();
        return contestregisterMapper.countByExample(contestregisterExample);
    }

    @Transactional
    @Override
    public void insertContest(Contest contest) {
        contestMapper.insertSelective(contest);
    }

    @Transactional
    @Override
    public void insertContestProblem(Contestproblems contestproblems) {
        contestproblemsMapper.insertSelective(contestproblems);
    }

    @Transactional
    @Override
    public void insertMyVoteForContest(Integer cid, Integer uid, boolean up) {
        if(cid == null || uid == null) return;
        Contestvote contestvote = new Contestvote();
        contestvote.setVotetime(new Date());
        contestvote.setVote(up?1:-1);
        contestvote.setCid(cid);
        contestvote.setUid(uid);

        Contest contest = contestMapper.selectByPrimaryKey(cid);
        if (up) {
            contest.setUpVote(contest.getUpVote()+1);
        } else {
            contest.setDownVote(contest.getDownVote()+1);
        }
        contestMapper.updateByPrimaryKeySelective(contest);
        contestvoteMapper.insertSelective(contestvote);
    }

    @Override
    public List<Contestregister> findRestersOnContestByUid(Integer uid) {
        ContestregisterExample example = new ContestregisterExample();
        example.createCriteria().andUidEqualTo(uid).andStatusEqualTo(1).andRoleEqualTo(0).andRankIsNotNull();
        return contestregisterMapper.selectByExample(example);
    }

    @Override
    public Long findFormalContestRegisterBy(Integer cid) {
        ContestregisterExample example = new ContestregisterExample();
        example.createCriteria().andCidEqualTo(cid).andStatusEqualTo(1).andRoleEqualTo(0);
        return contestregisterMapper.countByExample(example);
    }

    @Override
    public Contestregister findFormalContestRegisterById(Integer id) {
        return contestregisterMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void updateRegister(Contestregister contestregister) {
        contestregisterMapper.updateByPrimaryKeySelective(contestregister);
    }

    @Override
    public Contest findContestByNow() {
        return contestMapper.findContestByNow();
    }

    @Override
    public Integer findFirstBloodOnContestByCidAndPid(Integer cid, Integer pid) {
        return submitlistMapper.findFBOnContestByCidAndPid(cid,pid);
    }

    @Override
    public List<Contest> findAllContestByPage(Integer page, Integer limitSize) {
        ContestExample example = new ContestExample();
        example.setPage(page*limitSize);
        example.setLimitSize(limitSize);
        example.createCriteria().andUidEqualTo(-1);
        return contestMapper.selectByExample(example);
    }

    @Override
    public Contest findContestByTitleAndUidAndLevel(String title, Integer uid,Integer level) {
        ContestExample example = new ContestExample();
        example.createCriteria().andTitleEqualTo(title).andUidEqualTo(uid).andLevelEqualTo(level);
        example.setOrderByClause("starttime desc");
        List<Contest> contests = contestMapper.selectByExample(example);
        return contests.isEmpty()?null:contests.get(0);
    }

    @Transactional
    @Override
    public void deleteAllContestproblemByCid(Integer cid) {
        ContestproblemsExample example = new ContestproblemsExample();
        example.createCriteria().andCidEqualTo(cid);
        contestproblemsMapper.deleteByExample(example);
    }

    @Transactional
    @Override
    public void updateContest(Contest contest) {
        contestMapper.updateByPrimaryKeySelective(contest);
    }

    @Override
    public User findAuthorByCid(Integer cid) {
        if(cid == null || cid <= 0) return null;
        Integer uid = contestMapper.findUidByCid(cid);
        return userService.findUserByUid(uid);
    }

    @Override
    public Contestnotice findNoticeByNid(Integer nid) {
        return contestnoticeMapper.selectByPrimaryKey(nid);
    }

    @Transactional
    @Override
    public void updateContestnoticeByNid(Contestnotice contestnotice) {
        contestnoticeMapper.updateByPrimaryKeySelective(contestnotice);
    }

    @Override
    public boolean isContestEnds(Integer cid) {
        return contestMapper.isContestEnds(cid) != null;
    }

    @Override
    public boolean isContestAdminByUid(Integer cid,Integer uid) {
        return contestregisterMapper.isContestAdminByUid(cid,uid) != null;
    }

    @Override
    public Long findAllPenaltyOnContestByCidAndUid(Integer cid, Integer uid, Date date) {
        return submitlistMapper.calcAllPenaltyOnContestByCidAndUid(cid,uid,date);
    }

    @Override
    public boolean isValidUser(Integer cid, Integer uid) {
        return contestregisterMapper.isValidUserByCidAndUid(cid,uid) != null;
    }

    @Override
    public Contest findContestTimeAndTypeInfoByCid(Integer cid) {
        return contestMapper.findTimeAndTypeByCid(cid);
    }

    @Override
    public Date findFirstlyAcTimeOnContestByUidAndPid(Integer cid, Integer uid, Integer pid) {
        return submitlistMapper.findFirstlyAcTimeOnContestByCidAndUidAndPid(cid,uid,pid).getDate();
    }

    @Override
    public List<Integer> findRestersUidOnContestByCid(Integer cid) {
        return contestregisterMapper.findRestersUidByCid(cid);
    }

    @Override
    public List<Contestsubmitinfo> findContestsubmitinfoByCid(Integer cid) {
        return contestsubmitinfoMapper.findByCid(cid);
    }

    @Override
    public Integer findProblemsTotalByCid(Integer cid) {
        return contestproblemsMapper.countProblemsByCid(cid);
    }

    @Override
    public Integer findRegisterStateByCidAndUid(Integer cid, Integer uid) {
        return contestregisterMapper.findStateByCidAndUid(cid,uid);
    }

    @Override
    public List<Problem> findProblemsTitleByCid(Integer cid) {
        return contestproblemsMapper.findProblemTitleByCid(cid);
    }

    @Override
    public Integer findProblemAcCntByPid(Integer cid, Integer pid) {
        return contestproblemsMapper.findAcCntByCidAndPid(cid,pid);
    }

    @Override
    public Integer findProblemAllCntByPid(Integer cid, Integer pid) {
        return contestproblemsMapper.findAllCntByCidAndPid(cid,pid);
    }

    @Override
    public List<Contestproblems> findProblemsNumByCid(Integer cid) {
        return contestproblemsMapper.findProblemsByCid(cid);
    }

    @Override
    public void updateRegisterRankByUid(Integer cid,Integer uid, Integer rank) {
        contestregisterMapper.updateRankByUid(cid,uid,rank);
    }

    @Override
    public List<Contestregister> findRestersAndRankNotNullOnContestByCid(Integer cid) {
        ContestregisterExample example = new ContestregisterExample();
        example.createCriteria().andRankIsNotNull().andCidEqualTo(cid).andRoleEqualTo(0);
        return contestregisterMapper.selectByExample(example);
    }
}
