package com.example.photoalbum.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName album_comment
 */
@TableName(value ="album_comment")
@Data
public class AlbumCommentDto implements Serializable {
    /**
     * 
     */
    private String album_name;

    /**
     * 
     */
    private String commenter;

    /**
     * 
     */
    private String comment;
}