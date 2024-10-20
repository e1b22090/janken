package oit.is.z2680.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import oit.is.z2680.kaizi.janken.model.Match;

@Mapper
public interface MatchMapper {
  @Select("SELECT id, user1, user2, user1Hand, user2Hand FROM matches")
  ArrayList<Match> findAllMatches();

  // 追加7回目
  @Insert("INSERT INTO matches (user1, user2, user1Hand, user2Hand) " +
      "VALUES (#{user1}, #{user2}, #{user1Hand}, #{user2Hand})")
  void insertMatch(@Param("user1") int user1, @Param("user2") int user2,
      @Param("user1Hand") String user1Hand, @Param("user2Hand") String user2Hand);
}
