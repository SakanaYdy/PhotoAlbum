package com.example.photoalbum.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.po.Favorites;
import com.example.photoalbum.common.service.FavoritesService;
import com.example.photoalbum.mapper.FavoritesMapper;
import org.springframework.stereotype.Service;

/**
* @author 杨大宇
* @description 针对表【favorites】的数据库操作Service实现
* @createDate 2024-10-21 17:19:29
*/
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites>
    implements FavoritesService{

}




