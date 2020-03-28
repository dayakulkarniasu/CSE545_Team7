package com.sbs.sbsgroup7.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/home")
    public String userHome(){
        return "admin/home" ;
    }

}
