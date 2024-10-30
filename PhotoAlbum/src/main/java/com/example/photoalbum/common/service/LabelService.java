package com.example.photoalbum.common.service;

import com.example.photoalbum.common.po.Label;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photoalbum.common.res.Result;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【label】的数据库操作Service
* @createDate 2024-10-30 19:59:19
*/
public interface LabelService extends IService<Label> {

    Result<List<Label>> getAllLabel();

    Result<String> changeLabel(String labelName);

    Result<Label> addLabel(String labelName);
}
