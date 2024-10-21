package com.example.photoalbum.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.dto.AlbumCommentDto;
import com.example.photoalbum.common.dto.DeleteComment;
import com.example.photoalbum.common.po.AlbumComment;
import com.example.photoalbum.common.po.Notice;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumCommentService;
import com.example.photoalbum.common.service.NoticeService;
import com.example.photoalbum.mapper.AlbumCommentMapper;
import com.example.photoalbum.mapper.NoticeMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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


    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public Result<AlbumCommentDto> add(AlbumCommentDto albumCommentDto) {
        albumCommentMapper.add(albumCommentDto);
        return Result.success(albumCommentDto);
    }

    @Override
    public Result<DeleteComment> deleteComment(DeleteComment deleteComment) {
        AlbumComment albumComment = new AlbumComment();
        BeanUtils.copyProperties(deleteComment,albumComment);

        // 删除相册评论
        albumCommentMapper.deleteComment(albumComment);

        // 通知页面
        Notice notice = new Notice();
        notice.setFrom(deleteComment.getMaker());
        notice.setTo(deleteComment.getCommenter());
        notice.setNotice(deleteComment.getReason());
        notice.setTime(LocalDateTime.now());

        noticeMapper.addNotice(notice);

        return Result.success(deleteComment);
    }
}




