package com.example.photoalbum.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteComment {
    private String album_name;
    private String commenter;
//    private String comment;
    private String maker;
    private String reason;
}
