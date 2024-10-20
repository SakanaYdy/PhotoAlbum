package com.example.photoalbum.controller;

import com.example.photoalbum.common.dto.AlbumCommentDto;
import com.example.photoalbum.common.po.AlbumComment;
import com.example.photoalbum.common.po.Comment;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumCommentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 评论操作接口
 */
@RestController
@RequestMapping("/comment")
@Slf4j
@CrossOrigin("*")
public class CommentController {

    @Resource
    private AlbumCommentService albumCommentService;

    @PostMapping("/add")
    public Result<AlbumCommentDto> add(@RequestBody AlbumCommentDto albumCommentDto){
        System.out.println("新增评论" + albumCommentDto.getCommenter());
        return albumCommentService.add(albumCommentDto);
    }

}
