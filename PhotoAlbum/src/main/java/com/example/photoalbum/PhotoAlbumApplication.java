package com.example.photoalbum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.photoalbum.mapper")
public class PhotoAlbumApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoAlbumApplication.class, args);
    }

}
