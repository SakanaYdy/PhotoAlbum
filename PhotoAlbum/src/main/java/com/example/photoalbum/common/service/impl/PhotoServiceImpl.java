package com.example.photoalbum.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.po.Photo;
import com.example.photoalbum.common.service.PhotoService;
import com.example.photoalbum.mapper.PhotoMapper;
import org.springframework.stereotype.Service;

/**
* @author 杨大宇
* @description 针对表【photo】的数据库操作Service实现
* @createDate 2024-10-18 16:11:12
*/
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo>
    implements PhotoService{

}




