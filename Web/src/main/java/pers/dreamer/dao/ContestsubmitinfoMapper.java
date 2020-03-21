package pers.dreamer.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Contestsubmitinfo;
import pers.dreamer.bean.ContestsubmitinfoExample;

public interface ContestsubmitinfoMapper {
    long countByExample(ContestsubmitinfoExample example);

    int deleteByExample(ContestsubmitinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Contestsubmitinfo record);

    int insertSelective(Contestsubmitinfo record);

    List<Contestsubmitinfo> selectByExample(ContestsubmitinfoExample example);

    Contestsubmitinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Contestsubmitinfo record, @Param("example") ContestsubmitinfoExample example);

    int updateByExample(@Param("record") Contestsubmitinfo record, @Param("example") ContestsubmitinfoExample example);

    int updateByPrimaryKeySelective(Contestsubmitinfo record);

    int updateByPrimaryKey(Contestsubmitinfo record);

    @Select("select * from contestsubmitinfo where cid=#{cid} order by uid,id")
    List<Contestsubmitinfo> findByCid(Integer cid);

    @Select("select result from contestsubmitinfo where cid=#{cid} and uid=#{uid} and pid=#{pid} order by id limit 0,1")
    Integer findResultByCidAndUidAndPid(@Param("cid") Integer cid,@Param("uid") Integer uid,@Param("pid") Integer pid);

    @Select("select id from contestsubmitinfo where cid=#{cid} and uid=#{uid} and pid=#{pid} order by id limit 0,1")
    Integer isExistSubmitInfoByCidAndUidAndPid(@Param("cid") Integer cid,@Param("uid") Integer uid,@Param("pid") Integer pid);

    @Select("update contestsubmitinfo set cnt=cnt+1,ac_time=#{date},result=0 where id=#{id} and result=1 limit 1")
    void updateAcInfoById(@Param("id") Integer id,@Param("date") Date date);

    @Select("update contestsubmitinfo set cnt=cnt+1 where id=#{id} limit 1")
    void updateInfoById(Integer id);

    @Select("update contestsubmitinfo set ac_time=#{date},score=#{score},result=#{res} where id=#{id} and score<#{score} limit 1")
    void updateOIInfoById(@Param("id") Integer id,@Param("score") Double score,@Param("date") Date date,@Param("res") Integer res);

    @Select("update contestsubmitinfo set ac_time=#{date},score=#{score} where id=#{id} limit 1")
    void updateSCACInfoById(@Param("id") Integer id,@Param("score") Double score,@Param("date") Date date);
}