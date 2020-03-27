package com.sbs.sbsgroup7.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/tier2")
public class Tier2Controller {

    @RequestMapping("/home")
    public String userHome(){
        return "tier2Home" ;
    }

}
