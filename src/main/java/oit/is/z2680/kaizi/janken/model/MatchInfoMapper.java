package oit.is.z2680.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) " +
      "VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  void insertMatchInfo(
      @Param("user1") int user1,
      @Param("user2") int user2,
      @Param("user1Hand") String user1Hand,
      @Param("isActive") boolean isActive);

  @Select("SELECT * FROM matchinfo WHERE isActive = true")
  ArrayList<MatchInfo> findActiveMatches();

  @Select("SELECT * FROM matchinfo WHERE isActive = true AND (user1 = #{userId} OR user2 = #{userId})")
  ArrayList<MatchInfo> findActiveMatchInfoByUserId(int userId);

  @Update("UPDATE matchinfo SET isActive = false WHERE id = #{id}")
  void deactivateMatchInfo(int id);
}
