package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.bean.*;
import pers.dreamer.dao.*;
import pers.dreamer.dto.ContestDto;
import pers.dreamer.service.UserService;
import pers.dreamer.util.tools.EncodeUtil;
import pers.dreamer.util.tools.MyUtil;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SubmitlistMapper submitlistMapper;

    @Autowired
    ContestregisterMapper contestregisterMapper;

    @Autowired
    ContestMapper contestMapper;

    @Autowired
    ContestproblemsMapper contestproblemsMapper;

    @Override
    public User findUserByPwdAndName(String username, String password) throws Exception {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(EncodeUtil.sha1Encode(password));
        List<User> users = userMapper.selectByExample(userExample);
        return  users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public User findUserByName(String username) {
        if(username ==null||username.equals("")) return null;
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        return  users.size() > 0 ? users.get(0) : null;
    }

    @Transactional
    @Override
    public boolean insertUser(String email, String username, String password, String school, String invitcode) {
        User exists = findUserByName(username);
        if(exists == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setSchool(school);
            user.setInvitecode(invitcode);
            userMapper.insertSelective(user);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean updateUserInfo(User user) {
        return userMapper.updateByPrimaryKeySelective(user) > 0;
    }

    @Override
    public User findUserByUid(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public Long findRankByRating(Integer rating) {
        return userMapper.selectRankByRating(rating);
    }

    @Override
    public Long findAc_cntByUid(Integer uid) {
        SubmitlistExample submitlistExample = new SubmitlistExample();
        submitlistExample.createCriteria().andUidEqualTo(uid).andResultEqualTo(0);
        return submitlistMapper.countByExample(submitlistExample);
    }

    @Override
    public Long findAll_cntByUid(Integer uid) {
        SubmitlistExample submitlistExample = new SubmitlistExample();
        submitlistExample.createCriteria().andUidEqualTo(uid);
        return submitlistMapper.countByExample(submitlistExample);
    }

    @Override
    public Integer findAc_numByUid(Integer uid) {
        return submitlistMapper.findAcNumByUid(uid);
    }

    @Override
    public List<ContestDto> findContestByUid(Integer uid) {
        List<ContestDto> lists = new ArrayList<ContestDto>();
        ContestregisterExample contestregisterExample = new ContestregisterExample();
        contestregisterExample.createCriteria().andUidEqualTo(uid).andStatusEqualTo(1).andRankIsNotNull();
        List<Contestregister> contestregisters = contestregisterMapper.selectByExample(contestregisterExample);
        for(int i = 0; i < contestregisters.size(); i++){
            Contest contest = contestMapper.findTitleAndStimeByCid(contestregisters.get(i).getCid());
            if (contest == null) {
                continue;
            }
            ContestDto contestDto = new ContestDto();
            contestDto.setCid(contest.getCid());
            contestDto.setStarttime(contest.getStarttime());
            contestDto.setTitle(contest.getTitle());
            if (i+1 < contestregisters.size()) {
                contestDto.setScore(contestregisters.get(i + 1).getLastrating());
                contestDto.setAddscore(contestregisters.get(i+1).getLastrating() - contestregisters.get(i).getLastrating());
            } else {
                int rating = userMapper.findRatingByUid(uid);
                contestDto.setScore(rating);
                contestDto.setAddscore(rating - contestregisters.get(i).getLastrating());
            }
            contestDto.setSum(contestregisterMapper.findValicTotalByCid(contestregisters.get(i).getCid()));
            contestDto.setRank(contestregisters.get(i).getRank());
            lists.add(contestDto);
        }
        return lists;
    }

    @Override
    public List<User> findRanksByPageAndName(Integer page, Integer limitSize, String name) {
        UserExample userExample = new UserExample();
        userExample.setPage(page*limitSize);
        userExample.setLimitSize(limitSize);
        userExample.setOrderByClause("rating desc");
        UserExample.Criteria criteria = userExample.createCriteria();
        if(name != null && name.length() != 0) criteria.andUsernameEqualTo(name);
        return userMapper.selectByExample(userExample);
    }

    @Override
    public Long findUserTotal() {
        return userMapper.countByExample(null);
    }

    @Override
    public List<User> findAllUsersByLiveness(Integer limit) {
        if(limit == null) return null;
        return userMapper.findAllUsersByLiveness(limit);
    }

    @Override
    public int findRankOnContestByUid(Integer cid, Integer uid) {
        ContestregisterExample example = new ContestregisterExample();
        example.createCriteria().andCidEqualTo(cid).andUidEqualTo(uid);
        List<Contestregister> contestregisters = contestregisterMapper.selectByExample(example);
        return contestregisters.isEmpty()?-1:contestregisters.get(0).getRank();
    }

    @Override
    public Long findRegisterNumOnDate(Date date, Date tmp) {
        UserExample example = new UserExample();
        example.createCriteria().andRegisterdateBetween(date,tmp);
        return userMapper.countByExample(example);
    }

    @Override
    public Long findUserTotalByNameOrId(String name) {
        UserExample example = new UserExample();
        if (name != null && name.length() != 0) {
            example.or().andUsernameLike("%"+name+"%");
        }
        if (MyUtil.isNumber(name)) {
            example.or().andUidEqualTo(Integer.parseInt(name));
        }
        return userMapper.countByExample(example);
    }

    @Override
    public List<User> findUserByPageAndName(Integer page, String name) {
        UserExample example = new UserExample();
        if (name != null && name.length() != 0) {
            example.or().andUsernameLike("%"+name+"%");
        }
        if (MyUtil.isNumber(name)) {
            example.or().andUidEqualTo(Integer.parseInt(name));
        }
        example.setOrderByClause("uid limit "+(page*20)+",20");
        return userMapper.selectByExample(example);
    }

    @Override
    public User findUserStyleNameByUid(Integer uid) {
        return userMapper.findUserStyleNameByUid(uid);
    }

    @Transactional
    @Override
    public void addLivenessAndLevelByUid(Integer uid, int i) {
        userMapper.addLivenessAndLevelByUid(uid,i);
    }

    @Transactional
    @Override
    public void addLivenessByUid(Integer uid, int i) {
        userMapper.addLivenessByUid(uid,i);
    }

    @Override
    public Integer findUidByUsername(String username) {
        return userMapper.findUidByUsername(username);
    }

    @Override
    public String findUsernameByUid(Integer uid) {
        return userMapper.findUsernameByUid(uid);
    }

    @Override
    public boolean isExistUserByUid(Integer uid) {
        return userMapper.isExistByUid(uid) != 0;
    }

    @Override
    public Integer findRatingByUid(Integer uid) {
        return userMapper.findRatingByUid(uid);
    }

    @Transactional
    @Override
    public void updateUserInfoNew(User user) {
        userMapper.updateByUser(user);
    }

    @Override
    public List<User> findEmailAndName() {
        return userMapper.findEmailAndName();
    }
}