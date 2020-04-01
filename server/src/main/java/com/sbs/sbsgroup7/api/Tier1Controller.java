package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.model.EmployeeInfo;
import com.sbs.sbsgroup7.model.User;
import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/tier1")
@PreAuthorize("TIER1")
public class Tier1Controller {

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String userHome(){
        return "tier1/home" ;
    }

    @RequestMapping("/updateProfile")
    public String updateProfile(Model model){
        model.addAttribute("userInfo", userService.getLoggedUser());
        model.addAttribute("employeeInfo", new EmployeeInfo());
        return "tier1/updateProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("employeeInfo") EmployeeInfo employeeInfo, BindingResult result){
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(System.out::println);
            return "tier1/updateProfile";
        }
        try {
            User user = userService.getLoggedUser();
            userService.requestProfileUpdates(user, employeeInfo);

            return "tier1/updateProfileRequest";
        } catch(Exception e) {
            return e.getMessage();
        }
    }
}
