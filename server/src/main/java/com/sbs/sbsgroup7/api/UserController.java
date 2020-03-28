package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.model.Request;
import com.sbs.sbsgroup7.model.User;
import com.sbs.sbsgroup7.service.AccountService;
import com.sbs.sbsgroup7.service.RequestService;
import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestService requestService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService=userService;
    }
    @RequestMapping("/home")
    public String userHome(){
        return "user/home" ;
    }

    @PostMapping("/add")
    public void addUser(@NotNull @Validated @RequestBody User user){
        userService.add(user);
    }

    @PutMapping ("/update")
    public void update(@NotNull @Validated @RequestBody User user){
        userService.update(user);
    }

    @DeleteMapping(path="/remove/{id}")
    public void deleteUserById(@PathVariable("id") String id){
        userService.delete(id);
    }

    @DeleteMapping(path="/removeAll")
    public void deleteAll(){
        userService.deleteAll();
    }



    @RequestMapping("/accounts")
    public String approveRequests(Model model) {
        model.addAttribute("accounts", accountService.findAll());

        return "user/accounts";
    }

    //requesting new bank account creations
    @GetMapping("/createAccount")
    public String createAccount(Model model){
        model.addAttribute("request", new Request());
        return "user/createAccount";
    }
    @PostMapping("/createAccount")
    public String createAccount(@ModelAttribute("request") Request request){
        try {
            User user = userService.getLoggedUser();
            requestService.createRequest(user, request);

            System.out.println(user.getUserId() + " created bank account request");

            return "user/accountRequestSent";
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/requestTransfers")
    public String requestTransfers() {
        return "user/requestTransfers";
    }
}
