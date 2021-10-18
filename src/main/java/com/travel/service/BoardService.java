package com.travel.service;

import com.travel.entity.Board;
import com.travel.entity.BoardFile;
import com.travel.reptositry.BoardFileRepository;
import com.travel.reptositry.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

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
            String[] fileIdxArray = board.getFileIdxs().split(",");

            for (int i=0; i<fileIdxArray.length; i++) {
                String fileIdx = fileIdxArray[i];
                BoardFile boardFile = new BoardFile(board.getBoardIdx(), Long.parseLong(fileIdx),"Y") ;
                boardFileRepository.save(boardFile);
            }
        }
    }

    /**
     * 게시판 삭제
     * @param board
     */
    public void deleteBoard(Board board){

        Optional<Board> optional = boardRepository.findById(board.getBoardIdx());
        if(optional.isPresent()){
            board = optional.get();
            board.setUseYn("N");
            boardRepository.save(board);
        }
        else{
            throw new NullPointerException();
        }
    }

    //boardIdx로 해당 게시물 파일리스트 조회
    public List<BoardFile> selectBoardFile(Long boardIdx){
        return boardFileRepository.findByBoardIdxAndUseYn(boardIdx, "Y");
    }

    @Transactional
    public void deleteBoardFile(Long fileId){
        boardFileRepository.deleteById(fileId);
    }

}
