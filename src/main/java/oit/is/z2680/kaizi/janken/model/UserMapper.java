package oit.is.z2680.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import oit.is.z2680.kaizi.janken.model.User;

@Mapper
public interface UserMapper {

  @Select("SELECT id, name FROM users")
  ArrayList<User> findAllUsers();

  @Select("SELECT * FROM users WHERE id = #{id}")
  User findUserById(@Param("id") int id);

  @Select("SELECT * FROM users WHERE name = #{name}")
  User findUserByName(@Param("name") String name);
}
