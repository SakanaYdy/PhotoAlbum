package com.example.photoalbum.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.po.Label;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.LabelService;
import com.example.photoalbum.mapper.LabelMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 杨大宇
* @description 针对表【label】的数据库操作Service实现
* @createDate 2024-10-30 19:59:19
*/
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label>
    implements LabelService{

    @Resource
    private LabelMapper labelMapper;


    @Override
    public Result<List<Label>> getAllLabel() {
        List<Label> labels = labelMapper.getAllLabel();

        return Result.success(labels);
    }

    @Override
    public Result<String> changeLabel(String labelName) {
        labelMapper.changeLabel(labelName);
        return Result.success(labelName);
    }

    @Override
    public Result<Label> addLabel(String labelName) {
        labelMapper.add(labelName);
        Label label = new Label(labelName,0);
        return Result.success(label);
    }
}




