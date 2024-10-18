package com.example.photoalbum.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName album
 */
@TableName(value ="album")
@Data
public class AlbumDto implements Serializable {

    private String albumName;

    private String owner;

}