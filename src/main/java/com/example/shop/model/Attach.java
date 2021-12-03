package com.example.shop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Attach {
    private Long idx;
    private Long boardIdx;
    private String originalFileName;
    private String storedFileName;
    private Long fileSize;
    private String regDate;
}
