package com.example.photoalbum.common.dto;


import com.example.photoalbum.common.po.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumPhotosDto {

    List<String> urls;
    List<Comment> comments;
}
