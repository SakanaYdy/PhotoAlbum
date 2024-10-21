package com.example.photoalbum.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.po.AlbumComment;
import com.example.photoalbum.common.po.Notice;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.NoticeService;
import com.example.photoalbum.mapper.NoticeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.*;

/**
* @author 杨大宇
* @description 针对表【notice】的数据库操作Service实现
* @createDate 2024-10-20 16:44:17
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService{

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public Result<Notice> addNotice(Notice notice) {
        noticeMapper.addNotice(notice);
        return Result.success(notice);
    }

    @Override
    public Result<List<Notice>> getNotice(String username) {
        List<Notice> notices = noticeMapper.getNotice(username);
        return Result.success(notices);
    }

}




