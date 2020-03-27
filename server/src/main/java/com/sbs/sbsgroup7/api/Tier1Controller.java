package com.sbs.sbsgroup7.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tier1")
public class Tier1Controller {

    @RequestMapping("/home")
    public String userHome(){
        return "tier1Home" ;
    }

}
