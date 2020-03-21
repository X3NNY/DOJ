package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Contestregister;
import pers.dreamer.bean.ContestregisterExample;

public interface ContestregisterMapper {
    long countByExample(ContestregisterExample example);

    int deleteByExample(ContestregisterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Contestregister record);

    int insertSelective(Contestregister record);

    List<Contestregister> selectByExample(ContestregisterExample example);

    Contestregister selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Contestregister record, @Param("example") ContestregisterExample example);

    int updateByExample(@Param("record") Contestregister record, @Param("example") ContestregisterExample example);

    int updateByPrimaryKeySelective(Contestregister record);

    int updateByPrimaryKey(Contestregister record);

    @Select("select id from contestregister where cid=#{cid} and uid=#{uid} and role=1 limit 0,1")
    Integer isContestAdminByUid(@Param("cid")Integer cid,@Param("uid") Integer uid);

    @Select("select count(*) from contestregister where cid=#{cid} and rank is not null")
    Integer findValicTotalByCid(Integer cid);

    @Select("select id from contestregister where cid=#{cid} and uid=#{uid} and role=0 and status=0 order by id limit 0,1")
    Integer isValidUserByCidAndUid(@Param("cid") Integer cid,@Param("uid") Integer uid);

    @Select("select uid from contestregister where cid=#{cid} and status <> 0")
    List<Integer> findRestersUidByCid(Integer cid);

    @Select("select if(role,1,status-1) from contestregister where cid=#{cid} and uid=#{uid} order by id limit 0,1")
    Integer findStateByCidAndUid(@Param("cid") Integer cid,@Param("uid") Integer uid);

    @Select("update contestregister set rank=#{rank} where uid=#{uid} and cid=#{cid} limit 1")
    void updateRankByUid(@Param("cid") Integer cid,@Param("uid") Integer uid,@Param("rank") Integer rank);
}