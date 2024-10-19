package com.example.photoalbum.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.po.AlbumPhoto;
import com.example.photoalbum.common.service.AlbumPhotoService;
import com.example.photoalbum.mapper.AlbumPhotoMapper;
import org.springframework.stereotype.Service;

/**
* @author 杨大宇
* @description 针对表【album_photo】的数据库操作Service实现
* @createDate 2024-10-18 16:09:20
*/
@Service
public class AlbumPhotoServiceImpl extends ServiceImpl<AlbumPhotoMapper, AlbumPhoto>
    implements AlbumPhotoService{

}




