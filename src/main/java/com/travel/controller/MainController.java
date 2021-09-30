package com.travel.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {

    /**
     * 메인화면
     * @return
     */
    @GetMapping("/")
    public String main(){
        return "single";
    }

    @GetMapping("/write")
    public String write(){
        return "write";
    }

    @GetMapping("/bio")
    public String bio(){
        return "bio";
    }

}
