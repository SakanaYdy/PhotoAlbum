package com.example.photoalbum.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLikeAlbum {

    private String albumName;

    private String owner;


    private String photos;

    private String avatar_url;

    private Integer thumbs;


    private Integer favorites;


    private Integer recommends;

    private boolean like;

    private boolean favorite;

    private boolean recommend;

}
