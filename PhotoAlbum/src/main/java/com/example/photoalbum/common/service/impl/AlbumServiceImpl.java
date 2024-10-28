package com.example.photoalbum.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photoalbum.common.dto.AlbumDto;
import com.example.photoalbum.common.dto.AlbumPhotosDto;
import com.example.photoalbum.common.dto.DeleteAlbumDto;
import com.example.photoalbum.common.dto.UserLikeAlbum;
import com.example.photoalbum.common.po.Album;
import com.example.photoalbum.common.po.Comment;
import com.example.photoalbum.common.po.Notice;
import com.example.photoalbum.common.po.Usertoalbum;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.common.service.AlbumService;
import com.example.photoalbum.common.service.NoticeService;
import com.example.photoalbum.mapper.AlbumCommentMapper;
import com.example.photoalbum.mapper.AlbumMapper;
import com.example.photoalbum.mapper.PhotoMapper;
import com.example.photoalbum.mapper.UsertoalbumMapper;
import com.example.photoalbum.utils.AliOssUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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

    @Resource
    private AlbumCommentMapper albumCommentMapper;

    @Resource
    private NoticeService noticeService;

    @Resource
    private UsertoalbumMapper usertoalbumMapper;

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

    @Override
    public Result<AlbumPhotosDto> getPhotosWithComments(String albumName) {
        // 先获取到所有的图片链接
        String photos = albumMapper.getPhotos(albumName);

        if(photos == null) return Result.success(null);

        String[] split = photos.split("-");
        if(split.length == 0) return Result.success(null);
        List<String> ans = new ArrayList<>();
        for(var s : split){
            String url = photoMapper.getUrlById(Integer.parseInt(s));
            ans.add(url);
        }

        AlbumPhotosDto res = new AlbumPhotosDto();
        res.setUrls(ans);

        List<Comment> comments = albumCommentMapper.getComments(albumName);
        res.setComments(comments);

        return Result.success(res);
    }

    @Override
    public Result<DeleteAlbumDto> deleteAlbum(DeleteAlbumDto deleteAlbumDto) {

        AlbumDto albumDto = new AlbumDto();
        BeanUtils.copyProperties(deleteAlbumDto,albumDto);

        albumMapper.deleteAlbum(albumDto);

        // 触发通知
        Notice notice = new Notice();
        notice.setFrom(deleteAlbumDto.getMaker());
        notice.setTo(deleteAlbumDto.getOwner());
        notice.setTime(LocalDateTime.now());
        notice.setNotice(deleteAlbumDto.getReason());
        noticeService.addNotice(notice);

        return Result.success(deleteAlbumDto);
    }

    @Override
    public Result<Album> likeAlbum(String albumName, String username, String albumOwner) {

        check(albumName,username);
        albumMapper.addLike(albumName);
        usertoalbumMapper.changeLike(albumName,username);
        Album album = new Album();
        album.setAlbumName(albumName);

        // 点赞之后通知用户
        makeNotice(albumName,username,albumOwner,"点赞");

        return Result.success(album);
    }

    @Override
    public Result<Album> dislikeAlbum(String albumName,String username,String albumOwner) {

        check(albumName,username);

        albumMapper.deleteLike(albumName);
        usertoalbumMapper.changeLike(albumName,username);
        Album album = new Album();
        album.setAlbumName(albumName);

        makeNotice(albumName,username,albumOwner,"取消点赞");

        return Result.success(album);
    }

    @Override
    public Result<Album> disfavAlbum(String albumName,String username,String albumOwner) {

        check(albumName,username);

        albumMapper.deleteFav(albumName);
        usertoalbumMapper.changeFav(albumName,username);
        Album album = new Album();
        album.setAlbumName(albumName);
        makeNotice(albumName,username,albumOwner,"取消收藏");
        return Result.success(album);
    }

    @Override
    public Result<Album> favAlbum(String albumName,String username,String albumOwner) {
        check(albumName,username);

        albumMapper.addFav(albumName);
        usertoalbumMapper.changeFav(albumName,username);
        Album album = new Album();
        album.setAlbumName(albumName);
        makeNotice(albumName,username,albumOwner,"收藏");
        return Result.success(album);
    }

    @Override
    public Result<Album> addRecommend(String albumName,String username,String albumOwner) {

        check(albumName,username);

        albumMapper.addRecommend(albumName);
        usertoalbumMapper.changeRecommend(albumName,username);
        Album album = new Album();
        album.setAlbumName(albumName);
        makeNotice(albumName,username,albumOwner,"推荐");
        return Result.success(album);
    }

    @Override
    public Result<Album> deleteRecommend(String albumName,String username,String albumOwner) {
        check(albumName,username);

        albumMapper.deleteRecommend(albumName);
        usertoalbumMapper.changeRecommend(albumName,username);
        Album album = new Album();
        album.setAlbumName(albumName);
        makeNotice(albumName,username,albumOwner,"取消推荐");
        return Result.success(album);
    }

    @Override
    public Result<List<UserLikeAlbum>> getAllAlbumWithUser(String username) {

        List<Album> albums = albumMapper.getAll();
        List<UserLikeAlbum> ans = new ArrayList<>();
        for(var album : albums){
            String albumName = album.getAlbumName();
            Usertoalbum usertoalbum =  usertoalbumMapper.getLikes(username,albumName);
            UserLikeAlbum userLikeAlbum = new UserLikeAlbum();
            BeanUtil.copyProperties(album,userLikeAlbum);
            BeanUtil.copyProperties(usertoalbum,userLikeAlbum);
            ans.add(userLikeAlbum);
        }
        return Result.success(ans);
    }

    @Override
    public Result<List<UserLikeAlbum>>  getFavoriteAlbum(String username) {
        List<String> albumNames = usertoalbumMapper.getFavAlbum(username);
        List<Album> res = new ArrayList<>();
        for(var albumName : albumNames){
            Album album = albumMapper.getByAlbumName(albumName);
            res.add(album);
        }

        List<UserLikeAlbum> ans = new ArrayList<>();
        if(res.size() == 0)  return Result.success(null);

        for(var album : res){
            if(album != null){
                String albumName = album.getAlbumName();
                Usertoalbum usertoalbum =  usertoalbumMapper.getLikes(username,albumName);
                UserLikeAlbum userLikeAlbum = new UserLikeAlbum();
                BeanUtil.copyProperties(album,userLikeAlbum);
                BeanUtil.copyProperties(usertoalbum,userLikeAlbum);
                ans.add(userLikeAlbum);
            }
        }
        return Result.success(ans);

    }

    public void makeNotice(String albumName,String username,String albumOwner,String label){
        log.info("消息to{}",albumOwner);
        // 点赞之后通知用户
        Notice notice = new Notice();
        notice.setTime(LocalDateTime.now());
        notice.setFrom(username);
        notice.setTo(albumOwner);
        notice.setNotice(username + label + "了你的 " + albumName + " 相册");

        noticeService.addNotice(notice);
    }

    public void check(String albumName,String username){
        log.info("检查是否有记录 ------------");
        // 如果原来没有记录，需要先添加记录
        Usertoalbum likes = usertoalbumMapper.getLikes(username,albumName);
        if(likes == null){
            Usertoalbum t = new Usertoalbum(username,albumName,0,0,0);
            usertoalbumMapper.insertLikes(t);
        }
    }
}




