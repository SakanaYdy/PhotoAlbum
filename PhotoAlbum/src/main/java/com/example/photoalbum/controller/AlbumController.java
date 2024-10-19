package com.example.photoalbum.controller;


import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.po.Album;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
                                    String albumName,String owner) throws IOException {
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

}
