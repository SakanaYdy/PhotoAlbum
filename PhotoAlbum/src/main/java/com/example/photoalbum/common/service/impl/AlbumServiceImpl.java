package com.example.photoalbum.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.po.Album;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumService;
import com.example.photoalbum.mapper.AlbumMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【album】的数据库操作Service实现
* @createDate 2024-10-18 14:24:38
*/
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album>
    implements AlbumService{


    @Resource
    private AlbumMapper albumMapper;

    @Override
    public Result<Album> createAlbum(AlbumDto albumDto) {

        // 新增相册
        albumMapper.addAlbum(albumDto);

        Album album = new Album();
        BeanUtils.copyProperties(albumDto,album);

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
}




