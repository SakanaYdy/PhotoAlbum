package com.example.photoalbum.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName notice
 */
@TableName(value ="notice")
@Data
public class NoticeDto implements Serializable {
    /**
     * 
     */
    private String from;

    /**
     * 
     */
    private String to;

    /**
     * 
     */
    private String notice;

}