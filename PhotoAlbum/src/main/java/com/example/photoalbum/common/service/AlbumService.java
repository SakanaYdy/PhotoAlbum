package com.example.photoalbum.common.service;

import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.dto.AlbumPhotosDto;
import com.example.photoalbum.common.po.Album;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photoalbum.common.res.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
* @author 杨大宇
* @description 针对表【album】的数据库操作Service
* @createDate 2024-10-18 14:24:38
*/
public interface AlbumService extends IService<Album> {

    Result<Album> createAlbum(AlbumDto albumDto, MultipartFile avatar) throws IOException;

    Result<List<Album>> getAllAlbum();

    Result<List<Album>> getUserAlbum(String username);

    Result<List<String>> getPhotos(String albumName);

    Result<AlbumPhotosDto> getPhotosWithComments(String albumName);
}
