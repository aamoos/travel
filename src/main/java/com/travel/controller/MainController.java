package com.travel.controller;

import com.travel.entity.ChildDistrict;
import com.travel.entity.ParentsDistrict;
import com.travel.reptositry.ChildDistrictRepository;
import com.travel.reptositry.ParentsDistrictRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final ParentsDistrictRepository parentsDistrictRepository;
    private final ChildDistrictRepository childDistrictRepository;

    /**
     * 메인화면
     * @return
     */
    @GetMapping("/")
    public String main(){
        return "single";
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

    @PostMapping("/getChildDistrict")
    public List<ChildDistrict> getChildDistrict(@RequestBody ChildDistrict childDistrict){
        log.info("parentsIdx={}", childDistrict.getParentsIdx());
        return childDistrictRepository.findByParentsIdxAndUseYn(childDistrict.getParentsIdx(), "Y");
    }

    @GetMapping("/story")
    public String bio(){
        return "story";
    }

}
