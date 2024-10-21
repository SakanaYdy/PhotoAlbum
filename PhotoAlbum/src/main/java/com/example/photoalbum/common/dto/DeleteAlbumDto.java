package com.example.photoalbum.common.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName album
 */
@TableName(value ="album")
@Data
@AllArgsConstructor
public class DeleteAlbumDto implements Serializable {

    private String albumName;

    private String owner;

    private String reason;

    private String maker;
}