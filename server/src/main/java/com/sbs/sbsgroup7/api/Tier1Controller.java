package com.sbs.sbsgroup7.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tier1")
@PreAuthorize("TIER1")
public class Tier1Controller {

    @RequestMapping("/home")
    public String userHome(){
        return "tier1/home" ;
    }

}
