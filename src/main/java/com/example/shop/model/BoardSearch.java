package com.example.shop.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter @Setter
public class BoardSearch {
    private String type;
    private String keyword;
    private String pageIndex;
    private String pageRow;

    private int nPageIndex;
    private int nPageRow;
    private int start;
    private int end;

    private int total;

    public void getPaging() {
        nPageIndex = 0;
        nPageRow = 10;
        if (StringUtils.isEmpty(pageIndex) == false) {
            nPageIndex = Integer.parseInt(pageIndex)-1;
        }
        if (StringUtils.isEmpty(pageRow) == false) {
            nPageRow = Integer.parseInt(pageRow);
        }
        start = nPageIndex * nPageRow;
        end = nPageRow;
    }
}
