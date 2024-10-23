package com.example.photoalbum.mapper;

import com.example.photoalbum.common.po.Usertoalbum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【userToAlbum】的数据库操作Mapper
* @createDate 2024-10-23 11:52:14
* @Entity com.example.photoalbum.common.po.Usertoalbum
*/
public interface UsertoalbumMapper extends BaseMapper<Usertoalbum> {

    @Select("select * from PhotoAlbum.userToAlbum where username = #{username} and albumName = #{albumName}")
    Usertoalbum getLikes(String username, String albumName);

    @Update("update PhotoAlbum.userToAlbum set `like` = 1 - `like` " +
            "where albumName = #{albumName} and username = #{username}")
    void changeLike(String albumName, String username);
    @Update("update PhotoAlbum.userToAlbum set `favorite` = 1 - `favorite` " +
            "where albumName = #{albumName} and username = #{username}")
    void changeFav(String albumName, String username);

    @Update("update PhotoAlbum.userToAlbum set `recommend` = 1 - `recommend` " +
            "where albumName = #{albumName} and username = #{username}")
    void changeRecommend(String albumName, String username);

    @Insert("insert into PhotoAlbum.userToAlbum (username, albumName, `like`, favorite, recommend) VALUES " +
            "(#{username},#{albumName},#{like},#{favorite},#{recommend})")
    void insertLikes(Usertoalbum t);

    @Select("select albumName from PhotoAlbum.userToAlbum where username = #{username} and favorite > 0")
    List<String> getFavAlbum(String username);
}




