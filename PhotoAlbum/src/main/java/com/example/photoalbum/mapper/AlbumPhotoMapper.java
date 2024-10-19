package com.example.photoalbum.mapper;

import com.example.photoalbum.common.po.AlbumPhoto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

/**
* @author 杨大宇
* @description 针对表【album_photo】的数据库操作Mapper
* @createDate 2024-10-18 16:09:19
* @Entity com.example.photoalbum.common.po.AlbumPhoto
*/
public interface AlbumPhotoMapper extends BaseMapper<AlbumPhoto> {

    @Insert("insert into PhotoAlbum.album_photo (album_name, photo_id) " +
            "VALUES (#{album_name},#{photo_id})")
    void addAlbumPhoto(AlbumPhoto albumPhoto);
}




