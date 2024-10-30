package com.example.photoalbum.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageUser {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;


    private String role;

    private String phone;


    private String email;

    private int status;

}
