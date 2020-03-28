package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.service.AccountService;
import com.sbs.sbsgroup7.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/tier2")
//@PreAuthorize("TIER2")
public class Tier2Controller {

    @Autowired
    private RequestService requestService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/home")
    public String userHome(){
        return "tier2/home" ;
    }

    //Tier-2 employees can approve bank account requests
    @RequestMapping("/approveRequests")
    public String approveRequests(Model model) {
        model.addAttribute("requests", requestService.findAll());

        return "tier2/approveRequests";
    }

    //Tier-2 employees can view accounts to edit, delete
    @RequestMapping("/viewAccounts")
    public String viewAccounts(Model model) {
        model.addAttribute("accounts", accountService.findAll());

        return "tier2/viewAccounts";
    }

    //Tier-2 employees can view accounts to edit, delete
    @RequestMapping("/approveTransfers")
    public String viewAccounts() {

        return "tier2/approveTransfers";
    }

}
