package com.example.photoalbum.mapper;

import com.example.photoalbum.common.po.Photo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
* @author 杨大宇
* @description 针对表【photo】的数据库操作Mapper
* @createDate 2024-10-18 16:11:12
* @Entity com.example.photoalbum.common.po.Photo
*/
public interface PhotoMapper extends BaseMapper<Photo> {

    @Insert("insert into photo (url) values (#{url})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addPhoto(Photo photo);

    @Select("select url from photo where id = #{parseInt}")
    String getUrlById(int parseInt);
}




