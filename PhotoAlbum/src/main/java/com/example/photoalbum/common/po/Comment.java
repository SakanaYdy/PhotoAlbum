package com.example.photoalbum.common.po;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {

    private String commenter;
    private String comment;
}
