package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.bean.*;
import pers.dreamer.dao.*;
import pers.dreamer.dto.CodeDto;
import pers.dreamer.dto.RankDto;
import pers.dreamer.dto.ResultDto;
import pers.dreamer.dto.SubmitDto;
import pers.dreamer.service.ContestService;
import pers.dreamer.service.ProbleminfoService;
import pers.dreamer.service.SubmitlistService;
import pers.dreamer.util.idworker.Sid;
import pers.dreamer.util.tools.MyUtil;

import java.util.Date;
import java.util.List;

import static java.lang.Math.max;

@Service
public class SubmitlistServiceImpl implements SubmitlistService {

    @Autowired
    SubmitlistMapper submitlistMapper;

    @Autowired
    CodeMapper codeMapper;

    @Autowired
    SubmitcollectionMapper submitcollectionMapper;

    @Autowired
    ContestService contestService;

    @Autowired
    ProbleminfoService probleminfoService;

    @Autowired
    Sid sid;

    @Autowired
    ContestsubmitinfoMapper contestsubmitinfoMapper;

    @Autowired
    ProbleminfoMapper probleminfoMapper;

    @Autowired
    ContestproblemsMapper contestproblemsMapper;

    @Autowired
    ContestMapper contestMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MessageMapper messageMapper;

    @Override
    public Integer findStatusOnProblemByUidAndPid(Integer uid, Integer pid) {
        if(uid == null || pid == null) return 0;
        if (submitlistMapper.isAcByUidAndPid(uid,pid) != null) {
            return 1;
        } else if (submitlistMapper.isExistSubmit(pid,uid) != null) {
            return 2;
        }
        return 0;
    }

    @Override
    public Integer findContestStatusOnPorblemByCidAndUidAndPid(Integer cid, Integer uid, Integer pid) {
        if (uid==null || pid==null || cid==null) return 0;
        if (submitlistMapper.isAcByPidAndUidAndCid(pid,uid,cid) != null) {
            return 1;
        } else if (submitlistMapper.isExistSubmitByCid(pid,uid,cid) != null) {
            return 2;
        }
        return 0;
    }

    @Transactional
    @Override
    public Integer insertCodeAndSubmit(CodeDto codeDto,Integer uid,Integer cid) {
        //插入submitlist表
        Submitlist submitlist = new Submitlist();
        submitlist.setDate(new Date());
        submitlist.setLang(codeDto.getLang());
        submitlist.setPid(codeDto.getPid());
        submitlist.setUid(uid);
        submitlist.setResult(7);
        submitlist.setSize(MyUtil.codeDecode(codeDto.getCode()).length());
        if (cid != null) submitlist.setCid(cid);
        System.out.println(submitlist.getSid());
        submitlistMapper.insertSelective(submitlist);
        System.out.println(submitlist.getSid());
        Code code = new Code();
        code.setText(codeDto.getCode());
        code.setSid(submitlist.getSid());
        codeMapper.insertSelective(code);
        return submitlist.getSid();
    }

    @Override
    public Long findTotalSizeByUidAndPidAndCid(Integer uid, Integer pid, Integer cid) {
        SubmitlistExample submitlistExample = new SubmitlistExample();
        submitlistExample.createCriteria().andUidEqualTo(uid).andPidEqualTo(pid).andCidEqualTo(cid);
        return submitlistMapper.countByExample(submitlistExample);
    }

    @Override
    public List<Submitlist> findSubmitlistByPageAndUidAndPidAndResAndLang(Integer page,Integer limitSize, Integer uid, Integer pid, Integer res, String lang) {
        SubmitlistExample submitlistExample = new SubmitlistExample();
        submitlistExample.setOrderByClause(String.format("date desc limit %d,%d",page*limitSize,limitSize));
        SubmitlistExample.Criteria criteria = submitlistExample.createCriteria();
        if (uid != null && uid != -1) criteria = criteria.andUidEqualTo(uid);
        if (pid != null && pid != -1) criteria = criteria.andPidEqualTo(pid);
        if (res != null && res != -1) criteria = criteria.andResultEqualTo(res);
        if (lang != null && lang.length() != 0) criteria = criteria.andLangEqualTo(lang);
        criteria.andCidEqualTo(-1);
        return submitlistMapper.selectByExample(submitlistExample);
    }

    @Override
    public Code findCodeByCodeid(Integer codeid) {
        return codeMapper.selectByPrimaryKey(codeid);
    }

    @Override
    public Long findSubmitlistSizeByPageAndUidAndPidAndResAndLang(Integer uid, Integer pid, Integer res, String lang) {
        SubmitlistExample submitlistExample = new SubmitlistExample();
        SubmitlistExample.Criteria criteria = submitlistExample.createCriteria();
        if (uid != null && uid != -1) criteria = criteria.andUidEqualTo(uid);
        if (pid != null && pid != -1) criteria = criteria.andPidEqualTo(pid);
        if (res != null && res != -1) criteria = criteria.andResultEqualTo(res);
        if (lang != null && lang.length() != 0) criteria = criteria.andLangEqualTo(lang);
        criteria.andCidEqualTo(-1);
        return submitlistMapper.countByExample(submitlistExample);
    }

    @Override
    public List<Submitlist> findSubmitlistsByCidAndUid(Integer cid, Integer uid) {
        if(cid == null || uid == null) return null;
        SubmitlistExample submitlistExample = new SubmitlistExample();
        submitlistExample.setOrderByClause("date asc");
        submitlistExample.createCriteria().andCidEqualTo(cid).andUidEqualTo(uid);
        return submitlistMapper.selectByExample(submitlistExample);
    }

    @Override
    public List<Submitlist> findSubmitlistsByUidAndPid(Integer uid, Integer pid) {
        if(uid == null || pid == null) return null;
        SubmitlistExample submitlistExample = new SubmitlistExample();
        submitlistExample.createCriteria().andUidEqualTo(uid).andPidEqualTo(pid);
        return submitlistMapper.selectByExample(submitlistExample);
    }

    @Override
    public Submitlist findSubmitlistBySid(Integer sid) {
        if(sid == null) return null;
        return submitlistMapper.selectByPrimaryKey(sid);
    }

    @Transactional
    @Override
    public boolean updateSubmitlistBySid(Submitlist submitlist) {
        if(submitlist.getSid()== null) return false;
        submitlistMapper.updateByPrimaryKeySelective(submitlist);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteSubmitlistBySid(Integer sid) {
        if(sid == null) return  false;
        Submitlist submitlist = findSubmitlistBySid(sid);
        if (submitlist == null) {
            return false;
        }
        SubmitcollectionExample submitcollectionExample = new SubmitcollectionExample();
//        submitcollectionExample.createCriteria().andSidEqualTo(sid);
        submitcollectionMapper.deleteByExample(submitcollectionExample);
        Probleminfo probleminfo = probleminfoService.findProblemInfoByPid(submitlist.getPid());
        switch (submitlist.getResult()){
            case 0:probleminfo.setAcCnt(max(probleminfo.getAcCnt()-1,0));break;
            case 1:probleminfo.setPeCnt(max(probleminfo.getPeCnt()-1,0));break;
            case 2:probleminfo.setTleCnt(max(probleminfo.getTleCnt()-1,0));break;
            case 3:probleminfo.setMleCnt(max(probleminfo.getMleCnt()-1,0));break;
            case 4:probleminfo.setWaCnt(max(probleminfo.getWaCnt()-1,0));break;
            case 5:probleminfo.setReCnt(max(probleminfo.getReCnt()-1,0));break;
            case 6:probleminfo.setOleCnt(max(probleminfo.getOleCnt()-1,0));break;
            case 8:probleminfo.setSeCnt(max(probleminfo.getSeCnt()-1,0));break;
            case 9:probleminfo.setCeCnt(max(probleminfo.getCeCnt()-1,0));break;
        }
        probleminfoService.updateProblemInfo(probleminfo);
        //codeMapper.deleteByPrimaryKey(submitlist.getCodeid());
        submitlistMapper.deleteByPrimaryKey(submitlist.getSid());
        return true;
    }

    @Transactional
    @Override
    public boolean insertSubmitCollection(Submitcollection submitcollection) {
//        Submitcollection submitcollections = findSubmitCollectionByUidAndSid(submitcollection.getUid(),submitcollection.getSid());
//        if(submitcollections != null) return false;
        submitcollectionMapper.insert(submitcollection);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteSubmitCollectionByUidAndSid(Integer uid,Integer sid) {
        Submitcollection submitcollections = findSubmitCollectionByUidAndSid(uid,sid);
        if(submitcollections != null) return false;
        SubmitcollectionExample submitcollectionExample = new SubmitcollectionExample();
//        submitcollectionExample.createCriteria().andUidEqualTo(uid).andSidEqualTo(sid);
        submitcollectionMapper.deleteByExample(submitcollectionExample);
        return true;
    }

    @Override
    public boolean findSubmitlistsOnExiststByCidAndUid(Integer cid, Integer uid) {
        if(cid == null || uid == null) return true;
        SubmitlistExample submitlistExample = new SubmitlistExample();
        submitlistExample.createCriteria().andCidEqualTo(cid).andUidEqualTo(uid);
        return submitlistMapper.countByExample(submitlistExample) > 0;
    }

    @Override
    public Submitcollection findSubmitCollectionByUidAndSid(Integer uid, Integer sid) {
        SubmitcollectionExample submitcollectionExample = new SubmitcollectionExample();
//        submitcollectionExample.createCriteria().andSidEqualTo(sid).andUidEqualTo(uid);
        List<Submitcollection> submitcollections = submitcollectionMapper.selectByExample(submitcollectionExample);
        return submitcollections.isEmpty()?null:submitcollections.get(0);
    }

    @Override
    public Submitlist findSubmitlistByCidAndUidAndPid(Integer cid, Integer uid, Integer pid) {
        SubmitlistExample example = new SubmitlistExample();
        example.createCriteria().andUidEqualTo(uid).andCidEqualTo(cid).andPidEqualTo(pid);
        List<Submitlist> submitlists = submitlistMapper.selectByExample(example);
        return submitlists.isEmpty()?null:submitlists.get(0);
    }

    @Override
    public List<Submitlist> findSubmitlistsByPid(Integer pid) {
        if(pid == null)  return null;
        SubmitlistExample submitlistExample = new SubmitlistExample();
        submitlistExample.createCriteria().andPidEqualTo(pid);
        return submitlistMapper.selectByExample(submitlistExample);
    }

    @Transactional
    @Override
    public void updateCode(Code code) {
        codeMapper.updateByPrimaryKey(code);
    }

    @Override
    public Long findSubmitlistSizeOnDateByResult(Date date1,Date date2, Integer result) {
        SubmitlistExample example = new SubmitlistExample();
        SubmitlistExample.Criteria criteria = example.createCriteria();
        if (result != null && result != -1) {
            criteria.andResultEqualTo(result);
        }
        criteria.andDateBetween(date1,date2);
        return submitlistMapper.countByExample(example);
    }

    @Override
    public Long findSubmitlistSizeOnDateByResultAndUid(Date date, Date tmp, Integer result, Integer uid) {
        SubmitlistExample example = new SubmitlistExample();
        SubmitlistExample.Criteria criteria = example.createCriteria();
        if (result != null && result != -1) {
            criteria.andResultEqualTo(result);
        }
        criteria.andDateBetween(date,tmp).andUidEqualTo(uid);
        return submitlistMapper.countByExample(example);
    }

    @Override
    public List<Date> findSubmitlistsDateByResultAndUidGroupByPid(Integer result, Integer uid) {
        return submitlistMapper.selectDateByResultAndUidGroupByPid(result,uid);
    }

    @Override
    public List<Integer> findSidsByUid(Integer uid) {
        return submitlistMapper.selectSidsByUid(uid);
    }

    @Override
    public List<Submitlist> findSubmitlistByCid(Integer cid) {
        SubmitlistExample example = new SubmitlistExample();
        example.createCriteria().andCidEqualTo(cid);
        return submitlistMapper.selectByExample(example);
    }

    @Override
    public List<Submitcollection> findSubmitCollectionByUid(Integer uid) {
        SubmitcollectionExample example = new SubmitcollectionExample();
        example.createCriteria().andUidEqualTo(uid);
        return submitcollectionMapper.selectByExample(example);
    }

    @Override
    public boolean isExistSubmitTodatByUidAndCid(Integer uid,Integer cid) {
        return submitlistMapper.isExistSubmitTodayByCid(uid,cid) != null;
    }

    @Override
    public Double findHighScoreSubmitlistByCidAndUidAndPid(Integer cid, Integer uid, Integer pid) {
        return submitlistMapper.findHighScoreOnContestByUidAndPidAndCid(uid,pid,cid);
    }

    @Override
    public Submitlist findShortCodeSubmitlistByCidAndUidAndPid(Integer cid, Integer uid, Integer pid) {
        return submitlistMapper.findShortCodeSubmitByCidAndUidAndPid(cid,uid,pid);
    }

    @Override
    public Submitlist findMinTimeSubmitlistByCidAndUidAndPid(Integer cid, Integer uid, Integer pid) {
        return submitlistMapper.findMinTimeSubmitlistByCidAndUidAndPid(cid,uid,pid);
    }

    @Override
    public boolean isSubmitCanBeViewedByUid(Integer uid, Integer pid) {
        return submitlistMapper.isAcByUidAndPid(uid,pid) != null;
    }

    @Override
    public boolean isCollectedByUid(Integer sid, Integer uid) {
        return submitcollectionMapper.isCollectByUid(sid,uid) != null;
    }

    @Override
    public boolean isExistSubmitByCid(Integer cid, Integer uid) {
        return submitlistMapper.isExistSubmitWithCid(cid,uid) != null;
    }

    @Override
    public Integer findUidBySid(Integer sid) {
        return submitlistMapper.findUidBySid(sid);
    }

    @Override
    public Integer findPidBySid(Integer sid) {
        return submitlistMapper.findPidBySid(sid);
    }

    @Override
    public List<SubmitDto> findAllSubmitlistLast30Days() {
        return submitlistMapper.findAllSubmitlistLast30Days();
    }

    @Override
    public List<SubmitDto> findAcSubmitlistLast30Days() {
        return submitlistMapper.findAcSubmitlistLast30Days();
    }

    @Override
    public List<SubmitDto> findAllSubmitlistLast30DaysByUid(Integer uid) {
        return submitlistMapper.findAllSubmitlistLast30DaysByUid(uid);
    }

    @Override
    public List<SubmitDto> findAcSubmitlistLast30DaysByUid(Integer uid) {
        return submitlistMapper.findAcSubmitlistLast30DaysByUid(uid);
    }

    @Override
    public List<Long> findAllPassDateOnProblemByUid(Integer uid) {
        return submitlistMapper.findAllPassDateOnProblemByUid(uid);
    }

    @Override
    public List<SubmitDto> findRegisterLast30Days() {
        return submitlistMapper.findRegisterLast30Days();
    }

    @Override
    public List<RankDto> findRankByCidAndUid(Integer cid, Integer uid) {
        return submitlistMapper.findRankByCidAndUid(cid,uid);
    }

    @Override
    public List<Submitlist> findRankSubmitlistByCid(Integer cid) {
        return submitlistMapper.findRankSubmitlistByCid(cid);
    }

    @Override
    public Integer findStatusOnProblemByCidAndUidAndPid(Integer cid, Integer uid, Integer pid) {
        if (cid == null || uid == null || pid == null) return 0;
        Integer res = contestsubmitinfoMapper.findResultByCidAndUidAndPid(cid,uid,pid);
        if (res == null) {
            return 0;
        } else if (res == 1) { //未ac
            return 2;
        } else {
            return 1;
        }
    }

    @Transactional
    @Override
    public void updateSubmitByResult(ResultDto resultDto) {
        //更新submitlist表
        Submitlist submitlist = submitlistMapper.selectByPrimaryKey(resultDto.getSid());
        submitlist.setMemoryUsed(resultDto.getMemoryUsed());
        submitlist.setTimeUsed(resultDto.getTimeUsed());
        submitlist.setScore(resultDto.getScore());
        submitlist.setResult(resultDto.getResult());
        submitlist.setPos(resultDto.getPos());
        submitlist.setMsg(resultDto.getMsg());
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

        int add = 10;
        User u = userMapper.findCoinsAndLiveAndLevelByUid(submitlist.getUid());
        if (submitlist.getResult() == 0 && submitlistMapper.isExistAcSubmit(submitlist.getPid(),u.getUid())==null) {
            u.setCoins(u.getCoins()+5);
            Message message = new Message();
            message.setStatus(0);
            message.setDate(new Date());
            message.setTitle("首次AC 序号题:"+submitlist.getPid()+" +5 DC任务达成!");
            message.setContent("当前你的dc数量为:"+u.getCoins()+" DC");
            message.setSenduid(-1);
            message.setTargetuid(submitlist.getUid());
            messageMapper.insertSelective(message);
            add += 20;
        }
        switch (resultDto.getResult()){
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
        u.setLevel(u.getLevel()+add);
        u.setLiveness(u.getLiveness()+add);
        userMapper.updateByPrimaryKeySelective(u);
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
            contestsubmitinfo.setResult(submitlist.getResult()==0?0:1);
            contestsubmitinfo.setAcTime(submitlist.getDate());
            contestsubmitinfoMapper.insertSelective(contestsubmitinfo);
        } else {
            contestsubmitinfoMapper.updateOIInfoById(id,submitlist.getScore(),submitlist.getDate(),submitlist.getResult()==0?0:1);
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
}
