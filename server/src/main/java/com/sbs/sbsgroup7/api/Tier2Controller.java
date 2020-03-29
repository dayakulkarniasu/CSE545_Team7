package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.service.AccountService;
import com.sbs.sbsgroup7.service.RequestService;
import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/tier2")
//@PreAuthorize("TIER2")
public class Tier2Controller {

    @Autowired
    private RequestService requestService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String userHome(){
        return "tier2/home" ;
    }


    //Tier-2 employees can approve bank account requests
//    @GetMapping("/approveRequests")
//    public String approveRequests(Model model){
//        model.addAttribute("requests", requestService.findAll());
//        return "tier2/approveRequests";
//    }
//    @PostMapping("/approveRequests")
//    public String approveRequests(@RequestParam("requestId") String requestId){
//        try {
//            long reqId = Long.parseLong(requestId); //request ID from UI
//
//            //create user
//            Request r = requestService.findRequestById(reqId);
//            User requestedUser = userService.findByUserId(r.getRequestedUser());
//            Account a = new Account();
//            a.setAccountType(r.getRequestType());
//
//            //create account
//            accountService.createAccount(requestedUser, a);
//
//            //delete request entry
//            requestService.deleteByRequestId(reqId);
//
//            System.out.println(requestedUser.getUserId() + "'s account has been created");
//
//            return "redirect:/tier2/approveRequests";
//        } catch(Exception e) {
//            return e.getMessage();
//        }
//    }



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
