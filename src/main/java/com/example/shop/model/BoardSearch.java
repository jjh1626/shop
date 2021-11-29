package com.example.shop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardSearch {
    private String type;
    private String keyword;

    private String pageIndex;
    private String pageRow;
    private int nPageIndex;
    private int nPageRow;

    private int total;
}
