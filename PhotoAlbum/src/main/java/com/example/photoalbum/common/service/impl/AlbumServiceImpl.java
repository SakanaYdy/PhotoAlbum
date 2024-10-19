package com.example.photoalbum.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.po.Album;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumService;
import com.example.photoalbum.mapper.AlbumMapper;
import com.example.photoalbum.mapper.PhotoMapper;
import com.example.photoalbum.utils.AliOssUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
* @author 杨大宇
* @description 针对表【album】的数据库操作Service实现
* @createDate 2024-10-18 14:24:38
*/
@Service
@Slf4j
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album>
    implements AlbumService{

    private final AliOssUtil aliOssUtil = new AliOssUtil();
    @Resource
    private AlbumMapper albumMapper;

    @Resource
    private PhotoMapper photoMapper;

    @Override
    public Result<Album> createAlbum(AlbumDto albumDto, MultipartFile avatar) throws IOException {

        String albumName = albumDto.getAlbumName();
        String owner = albumDto.getOwner();
        System.out.println(albumName + owner);
        // 首先将图片上传获取到url
        // 获取原文件名以及后缀
        String originalFilename = avatar.getOriginalFilename();
        String back = originalFilename.substring(originalFilename.lastIndexOf('.'));
        // 创建新的文件名
        String fileName =  UUID.randomUUID().toString()+back;
        String upload = aliOssUtil.upload(avatar.getBytes(), fileName);
        log.info("文件上传到{}",upload);

        Album album = new Album();
        BeanUtils.copyProperties(albumDto,album);
        album.setAvatar_url(upload);

        // 新增相册
        albumMapper.addAlbum(album);

        return Result.success(album);
    }

    @Override
    public Result<List<Album>> getAllAlbum() {
        List<Album> ans = albumMapper.getAll();
        return Result.success(ans);
    }

    @Override
    public Result<List<Album>> getUserAlbum(String username) {
        List<Album> ans =  albumMapper.getAlbumByUser(username);
        return Result.success(ans);
    }

    @Override
    public Result<List<String>> getPhotos(String albumName) {

        String photos = albumMapper.getPhotos(albumName);

        if(photos == null) return Result.success(null);

        String[] split = photos.split("-");
        if(split.length == 0) return Result.success(null);
        List<String> ans = new ArrayList<>();
        for(var s : split){
            String url = photoMapper.getUrlById(Integer.parseInt(s));
            ans.add(url);
        }
        return Result.success(ans);
    }
}




