package com.example.photoalbum.common.service;

import com.example.photoalbum.common.po.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photoalbum.common.res.Result;

/**
* @author 杨大宇
* @description 针对表【notice】的数据库操作Service
* @createDate 2024-10-20 16:44:17
*/
public interface NoticeService extends IService<Notice> {

    Result<Notice> addNotice(Notice notice);
}
