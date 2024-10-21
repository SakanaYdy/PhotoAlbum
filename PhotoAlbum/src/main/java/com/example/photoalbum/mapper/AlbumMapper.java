package com.example.photoalbum.mapper;

import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.po.Album;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.photoalbum.common.po.AlbumPhoto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【album】的数据库操作Mapper
* @createDate 2024-10-18 14:24:38
* @Entity com.example.photoalbum.common.po.Album
*/
public interface AlbumMapper extends BaseMapper<Album> {

    /**
     * 新增相册操作
     */
    @Insert("insert into album (albumName, owner,avatar_url) values (#{albumName},#{owner},#{avatar_url})")
    void addAlbum(Album album);

    @Select("select * from album")
    List<Album> getAll();

    @Select("select * from album where owner = #{username}")
    List<Album> getAlbumByUser(String username);

    @Select("select album.photos from album where albumName = #{albumName}")
    String getPhotos(String albumName);

    @Update("UPDATE album SET photos = #{photos} WHERE albumName = #{albumName}")
    void addAlbumPhoto(Album album);

    @Delete("delete from album where albumName = #{albumName} and owner = #{owner}")
    void deleteAlbum(AlbumDto albumDto);
}




