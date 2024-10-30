package com.example.photoalbum.mapper;

import com.example.photoalbum.common.po.Label;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【label】的数据库操作Mapper
* @createDate 2024-10-30 19:59:19
* @Entity com.example.photoalbum.common.po.Label
*/
public interface LabelMapper extends BaseMapper<Label> {

    @Select("select * from PhotoAlbum.label")
    List<Label> getAllLabel();

    @Update("update PhotoAlbum.label set isDelete = 1 - label.isDelete where labelName = #{labelName}")
    void changeLabel(String labelName);

    @Insert("insert into PhotoAlbum.label (labelName) values (#{labelName})")
    void add(String labelName);
}




