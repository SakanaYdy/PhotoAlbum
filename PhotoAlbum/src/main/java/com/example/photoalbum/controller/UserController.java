package com.example.photoalbum.controller;


import com.example.photoalbum.common.dto.UserDto;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.UserService;
import com.example.photoalbum.common.vo.UserVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 普通用户登录请求
     * @return
     */
    @PostMapping("/login")
    public Result<UserVo> loginUser(@RequestBody UserDto userDto, HttpSession session){
        return userService.login(userDto,session);
    }

    /**
     * 普通用户注册
     */
    @PostMapping("/register")
    public Result<UserVo> register(@RequestBody UserDto userDto,String conform){
        return userService.register(userDto,conform);
    }

}
