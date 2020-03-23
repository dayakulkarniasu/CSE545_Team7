package com.sbs.sbsgroup7.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/home")
    public String index(Model model) {
        model.addAttribute("name", "John");
        return "index";
    }

    @RequestMapping("/accounts")
    public String accounts() {
        return "accounts";
    }

    @RequestMapping("/createAccount")
    public String createAccount() {
        return "createAccount";
    }

    @RequestMapping("/requestTransfers")
    public String requestTransfers() {
        return "requestTransfers";
    }

    @RequestMapping("/approveTransfers")
    public String approveTransfers() {
        return "approveTransfers";
    }
}
