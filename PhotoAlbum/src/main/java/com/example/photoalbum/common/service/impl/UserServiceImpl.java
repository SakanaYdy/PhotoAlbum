package com.example.photoalbum.common.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.dto.ManageUser;
import com.example.photoalbum.common.dto.UserDto;
import com.example.photoalbum.common.po.User;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.UserService;
import com.example.photoalbum.common.vo.UserVo;
import com.example.photoalbum.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.*;

/**
* @author 杨大宇
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-10-13 20:38:09
*/
@Service
@Component
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;


    @Override
    public Result<UserVo> login(UserDto userDto, HttpSession session) {

        String name = userDto.getName();
        String password = userDto.getPassword();

        User user = userMapper.getByName(name);

        if(user == null) return Result.error("用户不存在");

        // 将密码加密之后进行比较
        password = SecureUtil.md5(password);

        if(!password.equals(user.getPassword())) return Result.error("账号或密码错误");

        UserVo res = new UserVo();
        BeanUtils.copyProperties(user,res);
        session.setAttribute("user",user.getName());

        return Result.success(res);
    }

    @Override
    public Result<UserVo> register(UserDto userDto, String conform) {

        String name = userDto.getName();
        String password = userDto.getPassword();

        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        user.setRole("user");

        if(!password.equals(conform)) return Result.error("两次密码不一致");

        // 密码加密存储
        String s = SecureUtil.md5(password);
        user.setPassword(s);
        userMapper.addUser(user);

        UserVo res = new UserVo();
        res.setName(name);
        res.setRole("user");

        return Result.success(res);
    }

    @Override
    public Result<List<ManageUser>> getAllUser() {

        List<User> users = userMapper.getAllUser();

        // 脱敏
        List<ManageUser> res = new ArrayList<>();
        for(var user : users) {
            ManageUser manageUser = new ManageUser();
            BeanUtils.copyProperties(user, manageUser);
            res.add(manageUser);
        }
        return Result.success(res);
    }

    @Override
    public Result<String> changeUser(String username) {
        userMapper.changeUser(username);
        return Result.success(username);
    }
}




