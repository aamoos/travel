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
    private Long fileIdx;
    private Long boardIdx;
    private String useYn;

//    @OneToOne
//    @JoinColumn(name="fileIdx")
//    private File file;

    public BoardFile(Long boardIdx, Long fileIdx, String useYn) {
        this.boardIdx = boardIdx;
        this.fileIdx = fileIdx;
        this.useYn = useYn;
    }
}
