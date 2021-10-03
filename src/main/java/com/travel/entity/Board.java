package com.travel.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;

    //지역 부모 idx
    private Long parentsIdx;

    //지역 자식 idx
    private Long childIdx;

    //제목
    @Column(columnDefinition = "varchar(45) not null comment '타이틀'")
    private String boardTitle;

    //내용
    @Column(columnDefinition = "TEXT not null comment '내용'")
    private String boardContent;

    //사용여부
    private String useYn;

    //해시태그
    private String hashTag;

    //첨부 파일 개수
    private int attachCount;

    //insert시에 현재시간을 읽어서 저장
    @CreationTimestamp
    private LocalDateTime regDate;

    //update시에 현재시간을 읽여서 저장
    @UpdateTimestamp
    private LocalDateTime uptDate;

    private String fileIdxs;

    @Transient
    private String deleteFileIdxs;

}
