package com.example.photoalbum.common.service;

import com.example.photoalbum.common.dto.AlbumCommentDto;
import com.example.photoalbum.common.po.AlbumComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photoalbum.common.res.Result;

/**
* @author 杨大宇
* @description 针对表【album_comment】的数据库操作Service
* @createDate 2024-10-19 20:33:56
*/
public interface AlbumCommentService extends IService<AlbumComment> {

    Result<AlbumCommentDto> add(AlbumCommentDto albumCommentDto);
}
