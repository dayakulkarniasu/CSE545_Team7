package com.sbs.sbsgroup7.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/")
    public String index() {
        System.out.println("helloooo");
        return "index";
    }
}
