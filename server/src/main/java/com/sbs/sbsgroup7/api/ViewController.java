package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.DataSource.SystemLogRepository;
import com.sbs.sbsgroup7.model.SystemLog;
import com.sbs.sbsgroup7.model.User;
import com.sbs.sbsgroup7.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

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

    @Autowired
    SystemLogRepository systemLogRepository;

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
    public String register(@Valid @ModelAttribute("user") User userForm, BindingResult result){
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.registerUser(userForm);

            SystemLog systemLog=new SystemLog();
            systemLog.setMessage(userForm.getEmail() + " successfully registered");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);

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

            SystemLog systemLog=new SystemLog();
            systemLog.setMessage(username + " logged out");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping("/appointment")
    public String appointment() {
        return "appointment";
    }
}
