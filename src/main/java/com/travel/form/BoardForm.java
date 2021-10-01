package com.travel.form;

import javax.persistence.*;

public class BoardForm {

    private Long boardIdx;

    @Column(columnDefinition = "varchar(45) not null comment '타이틀'")
    private String boardTitle;

    @Column(columnDefinition = "TEXT not null comment '내용'")
    private String boardContent;

    //사용여부
    private String useYn;

    //첨부 파일 개수
    private int attachCount;

    //첨부파일 file idx
    private String fileIdxs;

    //첨부파일 삭제 파일 idx
    private String deleteFileIdxs;
}
