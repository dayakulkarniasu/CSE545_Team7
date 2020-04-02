package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }
}
