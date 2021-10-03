package com.travel.controller;

import com.travel.entity.Board;
import com.travel.entity.BoardFile;
import com.travel.entity.ChildDistrict;
import com.travel.reptositry.BoardFileRepository;
import com.travel.reptositry.ChildDistrictRepository;
import com.travel.reptositry.ParentsDistrictRepository;
import com.travel.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final ParentsDistrictRepository parentsDistrictRepository;
    private final ChildDistrictRepository childDistrictRepository;
    private final BoardService boardService;
    private final BoardFileRepository boardFileRepository;

    /**
     * 메인화면
     * @return
     */
    @GetMapping("/")
    public String main(HttpServletRequest req, Model model){

        String parentsIdx = req.getParameter("parentsIdx");

        if(parentsIdx == null){
            parentsIdx = "1";
        }

        String childIdx = req.getParameter("childIdx");

        if(childIdx == null){
            childIdx = "1";
        }

        List<BoardFile> galleryList = null;

        if(parentsIdx == null && childIdx == null){
            System.out.println("1번");
            galleryList = boardFileRepository.findAllByGalleryList();
        }

        else if(parentsIdx != null && childIdx == null){
            System.out.println("2번");
            galleryList = boardFileRepository.findAllByParentsIdx(parentsIdx);
        }

        else{
            System.out.println("3번");
            galleryList = boardFileRepository.findAllByParentsIdxAndChildIdx(parentsIdx, childIdx);
        }

        model.addAttribute("galleryList", galleryList);
        model.addAttribute("totalSize", galleryList.size());

        model.addAttribute("parentsIdx", Long.parseLong(parentsIdx));
        model.addAttribute("childIdx", Long.parseLong(childIdx));

        //부모 지역
        model.addAttribute("parentsDistrict", parentsDistrictRepository.findByUseYn("Y"));
        //자식 지역 (default로 서울 parentsIdx를 가진 자식항목 넣음)
        model.addAttribute("childDistrict", childDistrictRepository.findByParentsIdxAndUseYn(Long.parseLong(parentsIdx), "Y"));

        return "main";
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

        return "write";
    }

    @ResponseBody
    @PostMapping("/write")
    public Long writeSubmit(@RequestBody Board board){
        log.info("params={}", board);

        //board 게시판 테이블 insert
        Long boardIdx = boardService.savePost(board);
        board.setBoardIdx(boardIdx);

        //board 파일 테이블 insert
        boardService.insertBoardFile(board);


        return boardIdx;
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
    @GetMapping("/story")
    public String story(){

        return "story";
    }

}
