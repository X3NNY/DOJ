package pers.dreamer.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Submitlist;
import pers.dreamer.bean.SubmitlistExample;
import pers.dreamer.dto.RankDto;
import pers.dreamer.dto.SubmitDto;

public interface SubmitlistMapper {
    long countByExample(SubmitlistExample example);

    int deleteByExample(SubmitlistExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(Submitlist record);

    int insertSelective(Submitlist record);

    List<Submitlist> selectByExample(SubmitlistExample example);

    Submitlist selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") Submitlist record, @Param("example") SubmitlistExample example);

    int updateByExample(@Param("record") Submitlist record, @Param("example") SubmitlistExample example);

    int updateByPrimaryKeySelective(Submitlist record);

    int updateByPrimaryKey(Submitlist record);

    @Select("select sid from submitlist where pid=#{pid} and uid=#{uid} and result=0 limit 0,1")
    Integer isAcByUidAndPid(@Param("uid") Integer uid,@Param("pid") Integer pid);

    @Select("select result from submitlist where pid=#{pid} and uid=#{uid}")
    List<Integer> findResByPidAndUid(@Param("pid")Integer pid,@Param("uid") Integer uid);

    @Select("select sid from submitlist where pid=#{pid} and uid=#{uid} order by sid limit 0,1")
    Integer isExistSubmit(@Param("pid")Integer pid,@Param("uid")Integer uid);

    @Select("select sid from submitlist where cid=#{cid} and uid=#{uid} and pid=#{pid} order by sid limit 0,1")
    Integer isExistSubmitByCid(@Param("pid")Integer pid,@Param("uid")Integer uid,@Param("cid") Integer cid);

    @Select("select sid from submitlist where cid=#{cid} and uid=#{uid} and pid=#{pid} and result=0 order by sid limit 0,1")
    Integer isAcByPidAndUidAndCid(@Param("pid")Integer pid,@Param("uid") Integer uid,@Param("cid") Integer cid);

    @Select("select sid from submitlist where cid=#{cid} and uid=#{uid} order by sid limit 0,1")
    Integer isExistSubmitWithCid(@Param("cid")Integer cid,@Param("uid") Integer uid);

    @Select("select uid from submitlist where cid=#{cid} and pid=#{pid} and result=0 order by date limit 0,1")
    Integer findFBOnContestByCidAndPid(@Param("cid")Integer cid,@Param("pid") Integer pid);

    @Select("select count(DISTINCT pid) from submitlist where cid=#{cid} and uid=#{uid} and result=0")
    Integer findAcNumOnContestByUidAndCid(@Param("uid")Integer uid,@Param("cid") Integer cid);

    @Select("select sid,date from submitlist where cid=#{cid} and uid=#{uid} and pid=#{pid} and result=0 order by date limit 0,1")
    Submitlist findFirstlyAcTimeOnContestByCidAndUidAndPid(@Param("cid")Integer cid,@Param("uid") Integer uid,@Param("pid") Integer pid);

    @Select("select max(score) from submitlist where cid=#{cid} and uid=#{uid} and pid=#{pid}")
    Double findHighScoreOnContestByUidAndPidAndCid(@Param("uid")Integer uid,@Param("pid") Integer pid,@Param("cid") Integer cid);

    @Select("select sid,result,`size` from submitlist where cid=#{cid} and uid=#{uid} and pid=#{pid} order by sid limit 0,1")
    Submitlist findShortCodeSubmitByCidAndUidAndPid(@Param("cid")Integer cid,@Param("uid") Integer uid,@Param("pid") Integer pid);

    @Select("select sid,result,time_used from submitlist where cid=#{cid} and uid=#{uid} and pid=#{pid} order by sid limit 0,1")
    Submitlist findMinTimeSubmitlistByCidAndUidAndPid(@Param("cid")Integer cid,@Param("uid") Integer uid,@Param("pid") Integer pid);

    @Select("select sid from submitlist where uid=#{uid} and cid=-1 and result=0")
    List<Integer> selectSidsByUid(Integer uid);

    @Select("select uid from submitlist where sid=#{sid} order by sid limit 0,1")
    Integer findUidBySid(Integer sid);

    @Select("select pid from submitlist where sid=#{sid} order by sid limit 0,1")
    Integer findPidBySid(Integer sid);

    @Select("select count(DISTINCT pid) from submitlist where uid=#{uid} and result=0 order by sid")
    Integer findAcNumByUid(Integer uid);

    @Select("select min(date) from submitlist where uid=#{uid} and result=#{result} group by pid")
    List<Date> selectDateByResultAndUidGroupByPid(@Param("result") Integer result,@Param("uid") Integer uid);

    @Select("select count(sid) as num,date(date) as date from submitlist where date_sub(current_date(),interval 30 day)<=date group by date(date)")
    List<SubmitDto> findAllSubmitlistLast30Days();

    @Select("select count(sid) as num,date(date) as date from submitlist where result=0 and date_sub(current_date(),interval 30 day)<=date group by date(date)")
    List<SubmitDto> findAcSubmitlistLast30Days();

    @Select("select count(sid) as num,date(date) as date from submitlist where uid=#{uid} and date_sub(current_date(),interval 30 day)<=date group by date(date)")
    List<SubmitDto> findAllSubmitlistLast30DaysByUid(Integer uid);

    @Select("select count(sid) as num,date(date) as date from submitlist where uid=#{uid} and result=0 and date_sub(current_date(),interval 30 day)<=date group by date(date)")
    List<SubmitDto> findAcSubmitlistLast30DaysByUid(Integer uid);

    @Select("select UNIX_TIMESTAMP(min(date)) from submitlist where uid=#{uid} and result=0 group by pid")
    List<Long> findAllPassDateOnProblemByUid(Integer uid);

    @Select("select count(uid) as num,date(registerdate) as date from user where date_sub(current_date(),interval 30 day)<=registerdate group by date(registerdate)")
    List<SubmitDto> findRegisterLast30Days();

    @Select("select sid from submitlist where uid=#{uid} and cid=#{cid} and current_date()<=date limit 0,1")
    Integer isExistSubmitTodayByCid(@Param("uid") Integer uid,@Param("cid") Integer cid);

    @Select("select sum(val) from (select if(min(result),0,count(pid)*20-20) as val from submitlist where cid=#{cid} and uid=#{uid} group by pid)t")
    Long calcPenaltyOnContestByCidAndUid(@Param("cid") Integer cid,@Param("uid") Integer uid);

    @Select("select sum(val) from (select if(min(score),count(pid)*20+max(date),0) as val from submitlist where cid=#{cid} and uid=#{uid} group by pid)t")
    Long calcIOIPenaltyOnContestByCidAndUid(@Param("cid") Integer cid,@Param("uid") Integer uid);

    @Select("select sum(t.val) from (select if(min(w.result),0,count(w.pid)*20+-20+round((UNIX_TIMESTAMP((select min(s.date) from submitlist s where s.cid=#{cid} and s.uid=#{uid} and s.pid=w.pid and result=0))-UNIX_TIMESTAMP(#{date}))/60)) as val from submitlist w where w.cid=#{cid} and w.uid=#{uid} group by w.pid) t")
    Long calcAllPenaltyOnContestByCidAndUid(@Param("cid") Integer cid,@Param("uid") Integer uid,@Param("date") Date date);

    @Select("select pid,count(*) as cnt,min(result) as result from submitlist where cid=#{cid} and uid=#{uid} group by pid")
    List<RankDto> findRankByCidAndUid(@Param("cid") Integer cid,@Param("uid") Integer uid);

    //select uid,result,date from submitlist where cid=#{cid} and num>=0 order by uid,sid
    @Select("select sid,pid,uid,result,data from submitlist where cid=#{cid}")
    List<Submitlist> findRankSubmitlistByCid(Integer cid);

    @Select("select count(*) from (select uid from submitlist where pid=#{pid} and result=0 group by uid)t")
    Integer findPassNumByPid(Integer pid);

    @Select("select count(*) from (select uid from submitlist where pid=#{pid} group by uid)t")
    Integer findSubmitNumByPid(Integer pid);

    @Select("select sid from submitlist where pid=#{pid} and uid=#{uid} and result=0 order by sid limit 0,1")
    Integer isExistAcSubmit(@Param("pid") Integer pid,@Param("uid") Integer uid);
}