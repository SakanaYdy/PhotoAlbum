package com.example.photoalbum.controller;


import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.dto.AlbumPhotosDto;
import com.example.photoalbum.common.dto.UserLikeAlbum;
import com.example.photoalbum.common.po.Album;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/photo")
@CrossOrigin("*")  // 解决跨域问题
@Slf4j
public class AlbumController {

    @Resource
    private AlbumService albumService;

    /**
     * 创建相册
     */
//    @PostMapping("/create")
//    public Result<Album> createAlbum(MultipartFile avatar,AlbumDto albumDto) throws IOException {
//        return albumService.createAlbum(albumDto,avatar);
////        return null;
//    }

    @PostMapping("/create")
    public Result<Album> createAlbum(@RequestParam("avatar") MultipartFile avatar,
                                     String albumName, String owner) throws IOException {
        AlbumDto albumDto = new AlbumDto(albumName,owner);
        return albumService.createAlbum(albumDto, avatar);
    }

    /**
     * 获取到所有相册
     * @return
     */
    @GetMapping("/getAll")
    public Result<List<Album>> getAllAlbum(){
        return albumService.getAllAlbum();
    }


    @PostMapping("/getAll_user")
    public Result<List<UserLikeAlbum>> getAllWithUser(@RequestParam String username){
        return albumService.getAllAlbumWithUser(username);
    }

    /**
     * 获取到用户所创建的相册
     * @return
     */
    @PostMapping("/getByUser")
    public Result<List<Album>> getUserAlbum(@RequestParam String username){
        System.out.println("用户名:" + username);
        return albumService.getUserAlbum(username);
    }

    /**
     * 获取到相册内的图片数据
     */
    @PostMapping("/getPhotos")
    public Result<List<String>> getPhotos(@RequestParam  String albumName){
        System.out.println("相册名：{}" + albumName);
        return albumService.getPhotos(albumName);
    }

    /**
     * 获取的相册内容以及评论信息
     * @param albumName
     * @return
     */
    @PostMapping("/getPhotos_comment")
    public Result<AlbumPhotosDto> getPhotosWithComments(@RequestParam String albumName){
        System.out.println("相册名：{}" + albumName);
        return albumService.getPhotosWithComments(albumName);
    }

    /**
     *
     * @param albumName 相册名称
     * @param username 点赞的用户
     * @param albumOwner 被点赞相册的拥有者
     */
    @PostMapping("/likeAlbum")
    public Result<Album> likeAlbum(@RequestParam String albumName,
                                   @RequestParam String username,
                                   @RequestParam String albumOwner){
        log.info("被点赞相册名： {}",albumName);
        log.info("{}点赞{}",username,albumName);
        log.info("相册所有者{}",albumOwner);
        return albumService.likeAlbum(albumName,username,albumOwner);
    }

    /**
     * 取消点赞相册
     */
    @PostMapping("/dislikeAlbum")
    public Result<Album> dislikeAlbum(@RequestParam String albumName,
                                      @RequestParam String username,
                                      @RequestParam String albumOwner){
        return albumService.dislikeAlbum(albumName,username,albumOwner);
    }

    /**
     * 收藏相册

     */
    @PostMapping("/favAlbum")
    public Result<Album> favAlbum(@RequestParam String albumName,
                                  @RequestParam String username,
                                  @RequestParam String albumOwner){
        return albumService.favAlbum(albumName,username,albumOwner);
    }

    /**
     * 取消收藏相册
     */
    @PostMapping("/disfavAlbum")
    public Result<Album> disfavAlbum(@RequestParam String albumName,
                                     @RequestParam String username,
                                     @RequestParam String albumOwner){
        return albumService.disfavAlbum(albumName,username,albumOwner);
    }

    /**
     * 获取到用户收藏相册信息
     */
    @PostMapping("/favorite")
    Result<List<UserLikeAlbum>>  getFavoriteAlbum(@RequestParam String username){
        System.out.println("用户名:" + username);
        return albumService.getFavoriteAlbum(username);
    }

}
