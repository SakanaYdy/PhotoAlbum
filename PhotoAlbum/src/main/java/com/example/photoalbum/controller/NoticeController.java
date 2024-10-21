package com.example.photoalbum.controller;


import cn.hutool.core.date.DateTime;
import com.example.photoalbum.common.dto.NoticeDto;
import com.example.photoalbum.common.po.Notice;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.NoticeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;

@RestController
@RequestMapping("/notice")
@CrossOrigin("*")
@Slf4j
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * 给相关用户发送通知
     */
    @PostMapping("/add")
    public Result<Notice> addNotice(@RequestBody NoticeDto noticeDto){
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDto,notice);
        notice.setTime(LocalDateTime.now());
//        notice.setTime(LocalTime.now());
        return noticeService.addNotice(notice);
    }

    /**
     * 获取每一个用户的通知信息
     * @param username
     * @return
     */
    @PostMapping("/getNotice")
    public Result<List<Notice>> getNotice(@RequestParam String username){
        log.info("获取{}的通知",username);
        return noticeService.getNotice(username);
    }

}
