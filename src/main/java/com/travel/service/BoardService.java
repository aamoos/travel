package com.travel.service;

import com.travel.entity.Board;
import com.travel.entity.BoardFile;
import com.travel.reptositry.BoardFileRepository;
import com.travel.reptositry.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;


    /**
     *  게시판 저장
     */
    public Long savePost(Board board){
        return boardRepository.save(board).getBoardIdx();
    }

    //게시판 파일 등록
    @Transactional
    public void insertBoardFile(Board board) {
        //파일 등록할게 있을경우만
        if(board.getFileIdxs() != null) {
            //파일 등록
            String fileIdxs = ((String) board.getFileIdxs()).replace("[", "").replace("]", "");
            String[] fileIdxArray = fileIdxs.split(",");

            for (int i=0; i<fileIdxArray.length; i++) {
                String fileIdx = fileIdxArray[i].trim();
                BoardFile boardFile = new BoardFile(board.getBoardIdx(), Long.parseLong(fileIdx),"Y") ;
                boardFileRepository.save(boardFile);
            }
        }
    }


}
