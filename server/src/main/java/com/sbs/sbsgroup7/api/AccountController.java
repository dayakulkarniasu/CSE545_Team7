package com.sbs.sbsgroup7.api;


import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.User;
import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/account")
@RestController
public class AccountController {

    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/add")
//    public void createAccount(@NotNull @Validated @RequestBody Account account){
//        userService.createAccount(account);
//    }

//    @GetMapping(path = "/")
//    public List<Account> getAccounts(){
//        return userService.getAccounts();
//    }


}
