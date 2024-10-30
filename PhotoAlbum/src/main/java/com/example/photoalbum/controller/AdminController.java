package com.example.photoalbum.controller;

import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.dto.DeleteAlbumDto;
import com.example.photoalbum.common.dto.DeleteComment;
import com.example.photoalbum.common.dto.ManageUser;
import com.example.photoalbum.common.po.*;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumCommentService;
import com.example.photoalbum.common.service.AlbumService;
import com.example.photoalbum.common.service.LabelService;
import com.example.photoalbum.common.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Resource
    private UserService userService;

    @Resource
    private LabelService labelService;

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

    /**
     * 管理员推荐
     */
    @PostMapping("/recommend")
    public Result<Album> recommend(@RequestParam String albumName,
                                   @RequestParam String username,
                                   @RequestParam String albumOwner){
        log.info("{}推荐{}的{}相册",username,albumOwner,albumName);
        return albumService.addRecommend(albumName,username,albumOwner);
    }

    /**
     * 管理员取消推荐
     * @param albumName
     * @return
     */
    @PostMapping("/disRecommend")
    public Result<Album> disRecommend(@RequestParam String albumName,
                                      @RequestParam String username,
                                      @RequestParam String albumOwner){
        return albumService.deleteRecommend(albumName,username,albumOwner);
    }

    /**
     * 用户管理相关接口
     */
    @GetMapping("/user")
    public Result<List<ManageUser>> getAllUser(){
        return userService.getAllUser();
    }

    /**
     *  修改用户账号状态
     */
    @PostMapping("/changeUser")
    public Result<String> changeUser(@RequestParam String username){
        return userService.changeUser(username);
    }

    /**
     * 类别管理相关接口
     */
    @GetMapping("/label")
    public Result<List<Label>> getAllLabel(){
        return labelService.getAllLabel();
    }

    /**
     * 修改类别状态
     * @param labelName
     * @return
     */
    @PostMapping("/changeLabel")
    public Result<String> changeLabel(@RequestParam String labelName){
        return labelService.changeLabel(labelName);
    }

    @PostMapping("/addLabel")
    public Result<Label> addLabel(@RequestParam String labelName){
        return labelService.addLabel(labelName);
    }


}
