package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.model.User;
import com.sbs.sbsgroup7.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ViewController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserService userService;

    @Autowired
    RequestService requestService;

    @Autowired
    AccountService accountService;

   // @Autowired
   // private TransactionService transactionService;

    @RequestMapping("/home")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", auth.getName());
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        System.out.printf("in login");
        System.out.println();
        System.out.println();
        return "signin";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User userForm){
        try {
            userService.registerUser(userForm);
            return "signin";
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "dashboard";
    }

//    @GetMapping("/role")
//    public String user() {
//        return "role";
//    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping(value="/logout")
    public @ResponseBody String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            String username = auth.getName();
            //Remove the recently used OTP from server.
            otpService.clearOTP(username);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }




   // @RequestMapping("/EmailPhoneTransfer")
 //   public String EmailPhoneTransfer() {
    //    return "EmailPhoneTransfer";
    //}

    @RequestMapping("/appointment")
    public String appointment() {
        return "appointment";
    }

    /*@RequestMapping("/transactions")
    public String transaction(Model model) {
        model.addAttribute("requests", TransactionService.TIDVAL());

        return "transactions";
    }*/
}
