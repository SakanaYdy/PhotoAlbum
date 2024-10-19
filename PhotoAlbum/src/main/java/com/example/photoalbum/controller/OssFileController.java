package com.example.photoalbum.controller;


import com.example.photoalbum.common.po.Album;
import com.example.photoalbum.common.po.AlbumPhoto;
import com.example.photoalbum.common.po.Photo;
import com.example.photoalbum.common.res.Result;
import com.example.photoalbum.mapper.AlbumMapper;
import com.example.photoalbum.mapper.AlbumPhotoMapper;
import com.example.photoalbum.mapper.FileMapper;
import com.example.photoalbum.mapper.PhotoMapper;
import com.example.photoalbum.utils.AliOssUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/file/oss")
@CrossOrigin("*")
public class OssFileController {

    private final AliOssUtil aliOssUtil = new AliOssUtil();

    @Resource
    private FileMapper fileMapper;

    @Resource
    private PhotoMapper photoMapper;

    @Resource
    private AlbumPhotoMapper albumPhotoMapper;

    @Resource
    private AlbumMapper albumMapper;

    /**
     * 相册封面上传
     */
    @PostMapping("/upload/albumAvatar")
    public Result<String> uploadAlbumAvatar(MultipartFile file) throws IOException {

        log.info("文件上传------------");

        // 获取原文件名以及后缀
        String originalFilename = file.getOriginalFilename();
        String back = originalFilename.substring(originalFilename.lastIndexOf('.'));
        // 创建新的文件名
        String fileName =  UUID.randomUUID().toString()+back;
        String upload = aliOssUtil.upload(file.getBytes(), fileName);
        log.info("文件上传到{}",upload);
        System.out.println(originalFilename + " " + fileName);
        fileMapper.InsertOss(originalFilename,fileName);

        return Result.success(upload);

        // https://photo-album-ydy.oss-cn-beijing.aliyuncs.com/089c5468-caa2-4cad-a6b2-a09a9e50989d.png
        // https://photo-album-ydy.oss-cn-beijing.aliyuncs.com/f27f4076-68ec-4933-b60c-dcb902acf5cb.png

    }

    /**
     * 相册图片上传接口
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/albumPhoto")
    public Result<List<String>> uploadAlbumPhoto(String albumName,List<MultipartFile> files) throws IOException {
        log.info("文件上传------------");
        List<String> uploadUrl = new ArrayList<>();
        List<Integer> photoList = new ArrayList<>();
       for(MultipartFile file : files){
           // 获取原文件名以及后缀
           String originalFilename = file.getOriginalFilename();
           String back = originalFilename.substring(originalFilename.lastIndexOf('.'));
           // 创建新的文件名
           String fileName =  UUID.randomUUID().toString()+back;
           String upload = aliOssUtil.upload(file.getBytes(), fileName);
           log.info("文件上传到{}",upload);
           System.out.println(originalFilename + " " + fileName);
           fileMapper.InsertOss(originalFilename,fileName);
           uploadUrl.add(upload);

           Photo photo = new Photo();
           photo.setUrl(upload);
           photoMapper.addPhoto(photo);

           photoList.add(photo.getId());
       }

        String photos = albumMapper.getPhotos(albumName);
        StringBuilder sb;
        if(photos == null){
            sb = new StringBuilder();
        }else sb = new StringBuilder(photos);

//        AlbumPhoto albumPhoto = new AlbumPhoto();
//        albumPhoto.setAlbum_name(albumName);
//        albumPhoto.setPhoto_id(Arrays.toString(photoList.toArray()));
        for(var id : photoList){
           if(sb.length() > 0) sb.append("-").append(id);
           else sb.append(id);
        }
        Album album = new Album();
        album.setAlbumName(albumName);
        album.setPhotos(sb.toString());
        albumMapper.addAlbumPhoto(album);
        System.out.println(Arrays.toString(photoList.toArray()));
        return Result.success(uploadUrl);
    }

    /**
     * 文件删除
     */
    @PostMapping("/delete")
    public Result<String> delete(MultipartFile file){
        String FromName = file.getOriginalFilename();
        String ToName = fileMapper.SelectToNameOss(FromName);
        fileMapper.DeleteFileOss(FromName);
        System.out.println(FromName + " " + ToName);
        return aliOssUtil.delete(ToName);
    }
}
