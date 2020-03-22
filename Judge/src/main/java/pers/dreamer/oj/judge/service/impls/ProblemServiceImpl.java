package pers.dreamer.oj.judge.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.oj.judge.dao.*;
import pers.dreamer.oj.judge.pojo.*;
import pers.dreamer.oj.judge.service.MessageService;
import pers.dreamer.oj.judge.service.ProblemService;
import pers.dreamer.oj.judge.service.UserService;
import pers.dreamer.oj.judge.dto.CodeDto;
import pers.dreamer.oj.judge.service.SubmitlistService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Resource
    CodeMapper codeMapper;

    @Resource
    SubmitlistMapper submitlistMapper;

    @Resource
    ProbleminfoMapper probleminfoMapper;

    @Resource
    UserService userService;

    @Resource
    ProblemMapper problemMapper;

    @Autowired
    MessageService messageService;

    @Autowired
    SubmitlistService submitlistService;

    @Resource
    ProblemcollectionMapper problemcollectionMapper;

    @Resource
    ProblemvoteMapper problemvoteMapper;

    @Resource
    ContestMapper contestMapper;

    @Resource
    ContestsubmitinfoMapper contestsubmitinfoMapper;

    @Resource
    ContestproblemsMapper contestproblemsMapper;

    @Override
    public Code findCodeBySid(Integer sid) {
        return codeMapper.selectByPrimaryKey(sid);
    }

    @Transactional
    @Override
    public void updateSubmitListAndProbleminfoByCodeDto(CodeDto codeDto) {
        //更新submitlist表
        Submitlist submitlist = submitlistMapper.selectByPrimaryKey(codeDto.getSid());
        submitlist.setMemoryUsed(codeDto.getMaxMemory());
        submitlist.setTimeUsed(codeDto.getMaxTime());
        submitlist.setScore(codeDto.getScore());
        submitlist.setResult(codeDto.getRes());
        submitlist.setPos(codeDto.getIndex());
        submitlist.setMsg(codeDto.getMsg());
        //更新probleminfo表
        Probleminfo probleminfo = probleminfoMapper.selectByPrimaryKey(submitlist.getPid());
        boolean f = true;
        if(probleminfo==null){
            f = false;
            probleminfo = new Probleminfo();
            probleminfo.setPid(submitlist.getPid());
        }

        if (submitlist.getCid() != -1) {
            int ac = submitlist.getResult()==0?1:0;
            contestproblemsMapper.updateByCidAndPid(submitlist.getCid(), submitlist.getPid(),ac);
            int type = contestMapper.findTypeByCid(submitlist.getCid());
            switch (type) {
                case 0: // ICPC
                    updateICPCSubmitInfo(submitlist);break;
                case 1: // OI
                    updateOISubmitInfo(submitlist);break;
                case 2: // IOI
                    updateIOISubmitInfo(submitlist);break;
                case 3: // SC
                    updateSCSubmitInfo(submitlist);break;
                case 4: // MT
                    updateMTSubmitInfo(submitlist);break;
            }
        }

        User u = userService.findUser(submitlist.getUid());
        if (submitlist.getResult() == 0 && submitlistMapper.isExistSubmitByUidAndPid(u.getUid(),submitlist.getPid())==null) {
            u.setCoins(u.getCoins()+5);
            Message message = new Message();
            message.setStatus(0);
            message.setDate(new Date());
            message.setTitle("首次AC 序号题:"+submitlist.getPid()+" +5 DC任务达成!");
            message.setContent("当前你的dc数量为:"+u.getCoins()+" DC");
            message.setSenduid(-1);
            message.setTargetuid(u.getUid());
            messageService.insertMessage(message);
            u.setLiveness(u.getLiveness()+20);
            u.setLevel(u.getLevel()+20);
        }
        switch (codeDto.getRes()){
            case 0:probleminfo.setAcCnt(probleminfo.getAcCnt()+1);break;
            case 1:probleminfo.setPeCnt(probleminfo.getPeCnt()+1);break;
            case 2:probleminfo.setTleCnt(probleminfo.getTleCnt()+1);break;
            case 3:probleminfo.setMleCnt(probleminfo.getMleCnt()+1);break;
            case 4:probleminfo.setWaCnt(probleminfo.getWaCnt()+1);break;
            case 5:probleminfo.setReCnt(probleminfo.getReCnt()+1);break;
            case 6:probleminfo.setOleCnt(probleminfo.getOleCnt()+1);break;
            case 8:probleminfo.setSeCnt(probleminfo.getSeCnt()+1);break;
            case 9:probleminfo.setCeCnt(probleminfo.getCeCnt()+1);break;
        }
        u.setLiveness(u.getLiveness()+10);
        u.setLevel(u.getLevel()+10);
        userService.updateUserInfo(u);
        submitlistMapper.updateByPrimaryKey(submitlist);
        if(!f) probleminfoMapper.insertSelective(probleminfo);
        else probleminfoMapper.updateByPrimaryKeySelective(probleminfo);
    }

    private void updateMTSubmitInfo(Submitlist submitlist) {
        Integer id;
        if ((id = contestsubmitinfoMapper.isExistSubmitInfoByCidAndUidAndPid(submitlist.getCid(),submitlist.getUid(),submitlist.getPid())) == null) {
            Contestsubmitinfo contestsubmitinfo = new Contestsubmitinfo();
            contestsubmitinfo.setCid(submitlist.getCid());
            contestsubmitinfo.setPid(submitlist.getPid());
            contestsubmitinfo.setUid(submitlist.getUid());
            contestsubmitinfo.setCnt(1);
            if (submitlist.getResult() == 0) {
                contestsubmitinfo.setScore(submitlist.getTimeUsed().doubleValue());
                contestsubmitinfo.setAcTime(submitlist.getDate());
                contestsubmitinfo.setResult(0);
            } else {
                contestsubmitinfo.setResult(1);
            }
            contestsubmitinfoMapper.insertSelective(contestsubmitinfo);
        } else if (submitlist.getResult() == 0){
            contestsubmitinfoMapper.updateSCACInfoById(id,submitlist.getTimeUsed().doubleValue(),submitlist.getDate());
        }
    }

    private void updateSCSubmitInfo(Submitlist submitlist) {
        Integer id;
        if ((id = contestsubmitinfoMapper.isExistSubmitInfoByCidAndUidAndPid(submitlist.getCid(),submitlist.getUid(),submitlist.getPid())) == null) {
            Contestsubmitinfo contestsubmitinfo = new Contestsubmitinfo();
            contestsubmitinfo.setCid(submitlist.getCid());
            contestsubmitinfo.setPid(submitlist.getPid());
            contestsubmitinfo.setUid(submitlist.getUid());
            contestsubmitinfo.setCnt(1);
            if (submitlist.getResult() == 0) {
                contestsubmitinfo.setScore(submitlist.getSize().doubleValue());
                contestsubmitinfo.setAcTime(submitlist.getDate());
                contestsubmitinfo.setResult(0);
            } else {
                contestsubmitinfo.setResult(1);
            }
            contestsubmitinfoMapper.insertSelective(contestsubmitinfo);
        } else if (submitlist.getResult() == 0){
            contestsubmitinfoMapper.updateSCACInfoById(id,submitlist.getSize().doubleValue(),submitlist.getDate());
        }
    }

    private void updateIOISubmitInfo(Submitlist submitlist) {
        updateOISubmitInfo(submitlist);
    }

    private void updateOISubmitInfo(Submitlist submitlist) {
        Integer id;
        if ((id = contestsubmitinfoMapper.isExistSubmitInfoByCidAndUidAndPid(submitlist.getCid(),submitlist.getUid(),submitlist.getPid())) == null) {
            Contestsubmitinfo contestsubmitinfo = new Contestsubmitinfo();
            contestsubmitinfo.setCid(submitlist.getCid());
            contestsubmitinfo.setPid(submitlist.getPid());
            contestsubmitinfo.setUid(submitlist.getUid());
            contestsubmitinfo.setScore(submitlist.getScore());
            contestsubmitinfo.setCnt(1);
            contestsubmitinfo.setAcTime(submitlist.getDate());
            contestsubmitinfoMapper.insertSelective(contestsubmitinfo);
        } else {
            contestsubmitinfoMapper.updateOIInfoById(id,submitlist.getScore(),submitlist.getDate());
        }
    }

    private void updateICPCSubmitInfo(Submitlist submitlist) {
        if (submitlist.getResult() == 9) {
            return;
        }
        Integer id;
        if ((id = contestsubmitinfoMapper.isExistSubmitInfoByCidAndUidAndPid(submitlist.getCid(),submitlist.getUid(),submitlist.getPid())) == null) {
            Contestsubmitinfo contestsubmitinfo = new Contestsubmitinfo();
            contestsubmitinfo.setCid(submitlist.getCid());
            contestsubmitinfo.setCnt(1);
            contestsubmitinfo.setPid(submitlist.getPid());
            contestsubmitinfo.setUid(submitlist.getUid());
            if (submitlist.getResult() == 0) {
                contestsubmitinfo.setResult(0);
                contestsubmitinfo.setAcTime(submitlist.getDate());
            } else {
                contestsubmitinfo.setResult(1);
            }
            contestsubmitinfoMapper.insertSelective(contestsubmitinfo);
        } else if (submitlist.getResult() == 0) {
            contestsubmitinfoMapper.updateAcInfoById(id,submitlist.getDate());
        } else {
            contestsubmitinfoMapper.updateInfoById(id);
        }
    }

    @Override
    public Problem findProblemByPid(Integer pid) {
        return problemMapper.selectByPrimaryKey(pid);
    }

    @Transactional
    @Override
    public void insertProblem(Problem problem) {
        problemMapper.insert(problem);
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
    public boolean delProblemByPid(Integer pid) {
        if(pid == null)return false;
        Problem problem = problemMapper.selectByPrimaryKey(pid);
        if(problem == null) return false;
        submitlistService.deleteSubmitlistByPid(pid);
        ProblemcollectionExample problemcollectionExample = new ProblemcollectionExample();
        problemcollectionExample.createCriteria().andPidEqualTo(pid);
        problemcollectionMapper.deleteByExample(problemcollectionExample);
        ProblemvoteExample problemvoteExample = new ProblemvoteExample();
        problemvoteExample.createCriteria().andPidEqualTo(pid);
        problemvoteMapper.deleteByExample(problemvoteExample);
        problemMapper.deleteByPrimaryKey(pid);
        return true;
    }
}
