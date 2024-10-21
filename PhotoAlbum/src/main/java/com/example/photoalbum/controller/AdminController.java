package com.example.photoalbum.controller;

import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.dto.DeleteAlbumDto;
import com.example.photoalbum.common.dto.DeleteComment;
import com.example.photoalbum.common.po.Comment;
import com.example.photoalbum.common.po.Notice;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumCommentService;
import com.example.photoalbum.common.service.AlbumService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员相关操作
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
@Slf4j
public class AdminController {

    @Resource
    private AlbumService albumService;

    @Resource
    private AlbumCommentService albumCommentService;

    /**
     * 管理员删除违规相册
     * @return
     */

    @PostMapping("/deleteAlbum")
    public Result<DeleteAlbumDto> deleteAlbum(@RequestBody DeleteAlbumDto deleteAlbumDto){
        log.info("删除相册:{}",deleteAlbumDto.getAlbumName());
        return albumService.deleteAlbum(deleteAlbumDto);
    }

    /**
     * 管理员删除违规评论
     */
    @PostMapping("/deleteComment")
    public Result<DeleteComment> deleteComment(@RequestBody DeleteComment deleteComment){
        log.info("删除评论:{}",deleteComment.getAlbum_name());
        return albumCommentService.deleteComment(deleteComment);
    }

}
