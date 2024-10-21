package com.example.photoalbum.mapper;

import com.example.photoalbum.common.dto.AlbumCommentDto;
import com.example.photoalbum.common.po.AlbumComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.photoalbum.common.po.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【album_comment】的数据库操作Mapper
* @createDate 2024-10-19 20:33:56
* @Entity com.example.photoalbum.common.po.AlbumComment
*/
public interface AlbumCommentMapper extends BaseMapper<AlbumComment> {

    @Select("select commenter,comment from PhotoAlbum.album_comment where album_name = #{albumName}")
    List<Comment> getComments(String albumName);

    @Insert("insert into PhotoAlbum.album_comment (album_name, commenter, comment) VALUES " +
            "(#{album_name},#{commenter},#{comment})")
    void add(AlbumCommentDto albumCommentDto);

    @Delete("delete from PhotoAlbum.album_comment where album_name = #{album_name} " +
            "and commenter = #{commenter}")
    void deleteComment(AlbumComment albumComment);
}




