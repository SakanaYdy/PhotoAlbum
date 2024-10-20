package com.example.photoalbum.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.dto.AlbumCommentDto;
import com.example.photoalbum.common.po.AlbumComment;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumCommentService;
import com.example.photoalbum.mapper.AlbumCommentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author 杨大宇
* @description 针对表【album_comment】的数据库操作Service实现
* @createDate 2024-10-19 20:33:56
*/
@Service
public class AlbumCommentServiceImpl extends ServiceImpl<AlbumCommentMapper, AlbumComment>
    implements AlbumCommentService{

    @Resource
    private AlbumCommentMapper albumCommentMapper;

    @Override
    public Result<AlbumCommentDto> add(AlbumCommentDto albumCommentDto) {
        albumCommentMapper.add(albumCommentDto);
        return Result.success(albumCommentDto);
    }
}




