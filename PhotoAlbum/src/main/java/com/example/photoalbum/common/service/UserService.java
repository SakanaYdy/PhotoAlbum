package com.example.photoalbum.common.service;

import com.example.photoalbum.common.dto.ManageUser;
import com.example.photoalbum.common.dto.UserDto;
import com.example.photoalbum.common.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.vo.UserVo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【user】的数据库操作Service
* @createDate 2024-10-13 20:38:09
*/

public interface UserService extends IService<User> {


    Result<UserVo> login(UserDto userDto, HttpSession session);

    Result<UserVo> register(UserDto userDto, String conform);

    Result<List<ManageUser>> getAllUser();


    Result<String> changeUser(String username);
}
