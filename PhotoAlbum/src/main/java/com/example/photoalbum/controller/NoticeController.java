package com.example.photoalbum.controller;


import cn.hutool.core.date.DateTime;
import com.example.photoalbum.common.dto.NoticeDto;
import com.example.photoalbum.common.po.Notice;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.NoticeService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
@CrossOrigin("*")
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
        notice.setTime(DateTime.now());
        return noticeService.addNotice(notice);
    }

}
