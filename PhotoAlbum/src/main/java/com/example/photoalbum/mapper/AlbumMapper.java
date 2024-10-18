package com.example.photoalbum.mapper;

import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.po.Album;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

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
     * @param albumDto
     */
    @Insert("insert into album (album_name, owner) values (#{albumName},#{owner})")
    void addAlbum(AlbumDto albumDto);

    @Select("select * from album;")
    List<Album> getAll();

    @Select("select * from album where owner = #{username}")
    List<Album> getAlbumByUser(String username);
}




