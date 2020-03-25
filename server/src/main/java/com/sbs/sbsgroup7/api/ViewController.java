package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @Autowired
    RequestService requestService;

    @RequestMapping("/home")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", auth.getName());
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

    //Only viewable by Tier-2 employees (approving bank account requests)
    @RequestMapping("/approveRequests")
    public String approveRequests(Model model) {
        model.addAttribute("requests", requestService.findAll());

        return "approveRequests";
    }
}
