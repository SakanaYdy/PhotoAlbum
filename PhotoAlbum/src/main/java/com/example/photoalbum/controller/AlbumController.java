package com.example.photoalbum.controller;


import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.po.Album;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping("/create")
    public Result<Album> createAlbum(@RequestBody AlbumDto albumDto){
        return albumService.createAlbum(albumDto);
    }

    /**
     * 获取到所有相册
     * @return
     */
    @PostMapping("/getAll")
    public Result<List<Album>> getAllAlbum(){
        return albumService.getAllAlbum();
    }

    /**
     * 获取到用户所创建的相册
     * @return
     */
    @PostMapping("/getByUser")
    public Result<List<Album>> getUserAlbum(String username){
        return albumService.getUserAlbum(username);
    }

}
