package com.example.photoalbum.mapper;

import com.example.photoalbum.common.po.AlbumComment;
import com.example.photoalbum.common.po.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【notice】的数据库操作Mapper
* @createDate 2024-10-20 16:44:17
* @Entity com.example.photoalbum.common.po.Notice
*/
public interface NoticeMapper extends BaseMapper<Notice> {

    @Insert("insert into PhotoAlbum.notice (`from`, `to`, notice, time) VALUES (" +
            "#{from},#{to},#{notice},#{time})")
    void addNotice(Notice notice);

    @Select("select * from PhotoAlbum.notice where `to`= #{username}")
    List<Notice> getNotice(String username);

    @Delete("delete from PhotoAlbum.album_comment where album_name = #{album_name} " +
            "and commenter = #{commenter}")
    void deleteComment(AlbumComment albumComment);
}




