package com.example.shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class Board {
    private Long idx;
    @NotEmpty(message = "작성자를 입력해 주세요.")
    private String writer;
    @NotEmpty(message = "제목을 입력해 주세요.")
    private String title;
    @NotEmpty(message = "내용을 입력해 주세요.")
    private String content;
    private String regDate;
    private int hit;
}
