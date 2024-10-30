package com.example.photoalbum.mapper;

import com.example.photoalbum.common.dto.UserDto;
import com.example.photoalbum.common.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-10-13 20:38:09
* @Entity com.example.photoalbum.common.po.User
*/
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where name = #{name}")
    User getByName(String name);

    @Insert("insert into user (name,password,role) values (#{name},#{password},#{role})")
    void addUser(User user);

    @Select("select * from user")
    List<User> getAllUser();

    @Update("update PhotoAlbum.user set status = 1  - user.status where name = #{username}")
    void changeUser(String username);
}




