package com.travel.controller;

import com.travel.entity.Board;
import com.travel.entity.BoardFile;
import com.travel.entity.ChildDistrict;
import com.travel.reptositry.BoardFileRepository;
import com.travel.reptositry.BoardRepository;
import com.travel.reptositry.ChildDistrictRepository;
import com.travel.reptositry.ParentsDistrictRepository;
import com.travel.service.BoardService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final ParentsDistrictRepository parentsDistrictRepository;
    private final ChildDistrictRepository childDistrictRepository;
    private final BoardService boardService;
    private final BoardFileRepository boardFileRepository;
    private final BoardRepository boardRepository;

    /**
     * 메인화면
     * @return
     */
    @GetMapping("/main")
    public String main(HttpServletRequest req, Model model){

        String parentsIdx = req.getParameter("parentsIdx");

        //전체 선택일경우
        if(!isStringEmpty(parentsIdx)){
            if(parentsIdx.equals("0")){
                parentsIdx = null;
            }
        }

        String childIdx = req.getParameter("childIdx");

        List<BoardFile> galleryList = null;

        if(isStringEmpty(parentsIdx) && isStringEmpty(childIdx)){
            System.out.println("1번");
            galleryList = boardFileRepository.findAllByGalleryList();
        }

        else if(!isStringEmpty(parentsIdx) && isStringEmpty(childIdx)){
            System.out.println("2번");
            galleryList = boardFileRepository.findAllByParentsIdx(parentsIdx);
        }

        else{
            System.out.println("3번");
            galleryList = boardFileRepository.findAllByParentsIdxAndChildIdx(parentsIdx, childIdx);
        }

        model.addAttribute("galleryList", galleryList);
        model.addAttribute("totalSize", galleryList.size());

        setParentsChildModel(model, parentsIdx, childIdx);

        return "board/main";
    }

    /**
     *  글쓰기
     * @return
     */
    @GetMapping("/write")
    public String write(Model model){

        //부모 지역
        model.addAttribute("parentsDistrict", parentsDistrictRepository.findByUseYn("Y"));

        //자식 지역 (default로 서울 parentsIdx를 가진 자식항목 넣음)
        model.addAttribute("childDistrict", childDistrictRepository.findByParentsIdxAndUseYn(1L, "Y"));

        return "board/write";
    }

    /**
     * 다운로드 홈페이지
     * @param model
     * @return
     */
    @GetMapping("/download")
    public String download(Model model){

        return "board/download";
    }

    /**
     * 글수정
     * @param model
     * @return
     */
    @GetMapping("/update/{boardIdx}")
    public String update(@PathVariable Long boardIdx, Model model){
        log.info("boardIdx={}", boardIdx);

        Board boardDetail = boardRepository.findByBoardIdx(boardIdx);

        model.addAttribute("boardDetail", boardDetail);

        //부모 지역
        model.addAttribute("parentsDistrict", parentsDistrictRepository.findByUseYn("Y"));

        //자식 지역 (default로 서울 parentsIdx를 가진 자식항목 넣음)
        model.addAttribute("childDistrict", childDistrictRepository.findByParentsIdxAndUseYn(boardDetail.getParentsIdx(), "Y"));

        //게시판 파일 리스트
        model.addAttribute("boardFileInfo", boardService.selectBoardFile(boardIdx));

        return "board/update";
    }

    @ResponseBody
    @PostMapping("/write")
    public Long writeSubmit(@RequestBody Board board){
        log.info("params={}", board);

        //fileIdxs 가공

        if(!isStringEmpty(board.getFileIdxs())){
            board.setFileIdxs(((String) board.getFileIdxs()).replace("[", "").replace("]", "").replaceAll(" ",""));
        }

        if(!isStringEmpty(board.getHashTag())){
            board.setHashTag(board.getHashTag().replaceAll(" ", ""));
        }

        //board 게시판 테이블 insert
        Long boardIdx = boardService.savePost(board);
        board.setBoardIdx(boardIdx);

        //board 파일 테이블 insert
        boardService.insertBoardFile(board);


        return boardIdx;
    };

    /**
     * 수정
     * @param board
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public Long updateSubmit(@RequestBody Board board){
        log.info("params={}", board);

        if(!isStringEmpty(board.getFileIdxs())){
            board.setFileIdxs(((String) board.getFileIdxs()).replace("[", "").replace("]", "").replaceAll(" ",""));
        }

        if(!isStringEmpty(board.getHashTag())){
            board.setHashTag(board.getHashTag().replaceAll(" ", ""));
        }

        Long boardIdx = boardService.savePost(board);

        //board 파일 테이블 insert
        boardService.insertBoardFile(board);

        System.out.println("deleteFileIdxs : " + board.getDeleteFileIdxs());

        //넘어온 파일 삭제 시퀀스 삭제처리
        if(!board.getDeleteFileIdxs().isEmpty()){
            String deleteFileIdxs = (String) board.getDeleteFileIdxs();
            String[] fileIdxsArray = deleteFileIdxs.split(",");
            System.out.println("fileIdxsArray : " + fileIdxsArray);
            //해당 시퀀스 삭제처리
            for(int i=0; i<fileIdxsArray.length; i++){
                String fileId = fileIdxsArray[i];
                System.out.println("fileId : " + fileId);
                boardService.deleteBoardFile(Long.parseLong(fileId));
            }
        }

        return boardIdx;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Long deleteSubmit(@RequestBody Board board){
        log.info("params={}", board);

        System.out.println("머야 : " + board.getBoardIdx());
        //board 게시판 테이블 insert
         boardService.deleteBoard(board);


        return board.getBoardIdx();
    };

    /**
     *  자식 지역 리스트 가져오기
     * @param parentsIdx
     * @return
     */
    @ResponseBody
    @PostMapping("/getChildDistrict")
    public List<ChildDistrict> getChildDistrict(@RequestBody Long parentsIdx){
        log.info("parentsIdx={}", parentsIdx);
        return childDistrictRepository.findByParentsIdxAndUseYn(parentsIdx, "Y");
    }

    /**
     * story 페이지
     * @return
     */
    @GetMapping("/")
    public String story(HttpServletRequest req, Model model){

        String parentsIdx = req.getParameter("parentsIdx");

        //전체 선택일경우
        if(!isStringEmpty(parentsIdx)){
            if(parentsIdx.equals("0")){
                parentsIdx = null;
            }
        }

        String childIdx = req.getParameter("childIdx");
        setParentsChildModel(model, parentsIdx, childIdx);

        List<Board> boardDetail = null;

        if(isStringEmpty(parentsIdx) && isStringEmpty(childIdx)){
            boardDetail = boardRepository.findAllByStoryList();
        }

        else if(!isStringEmpty(parentsIdx) && isStringEmpty(childIdx)){
            System.out.println("2번");
            boardDetail = boardRepository.findAllByParentsIdx(parentsIdx);
        }

        else{
            System.out.println("3번");
            boardDetail = boardRepository.findAllByParentsIdxAndChildIdx(parentsIdx, childIdx);
        }

        model.addAttribute("boardDetail", boardDetail);
        model.addAttribute("totalSize", boardDetail.size());
        System.out.println(boardDetail.size());
        return "board/story";
    }

    private void setParentsChildModel(Model model, String parentsIdx, String childIdx){

        if(!isStringEmpty(parentsIdx)){
            model.addAttribute("parentsIdx", Long.parseLong(parentsIdx));
        }

        if(!isStringEmpty(childIdx)){
            model.addAttribute("childIdx", Long.parseLong(childIdx));
        }

        //부모 지역
        model.addAttribute("parentsDistrict", parentsDistrictRepository.findByUseYn("Y"));

        if(!isStringEmpty(parentsIdx)){
            //자식 지역 (default로 서울 parentsIdx를 가진 자식항목 넣음)
            model.addAttribute("childDistrict", childDistrictRepository.findByParentsIdxAndUseYn(Long.parseLong(parentsIdx), "Y"));
        }
    }

    //널 빈값 체크
    static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
