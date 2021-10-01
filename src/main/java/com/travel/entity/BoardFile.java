package com.travel.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BoardFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    private Long boardIdx;
    private String useYn;

    public BoardFile(Long boardIdx, Long fileId, String useYn) {
        this.boardIdx = boardIdx;
        this.fileId = fileId;
        this.useYn = useYn;
    }
}
