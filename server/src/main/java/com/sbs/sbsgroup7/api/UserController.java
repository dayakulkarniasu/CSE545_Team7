package com.sbs.sbsgroup7.api;


import com.sbs.sbsgroup7.model.Appointment;
import com.sbs.sbsgroup7.model.User;
import com.sbs.sbsgroup7.service.AppointmentService;

import com.sbs.sbsgroup7.model.Request;
import com.sbs.sbsgroup7.service.AccountService;
import com.sbs.sbsgroup7.service.RequestService;

import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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

    @Autowired
    private AppointmentService AppointmentService;




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

    /*@GetMapping("/createAppointment")
    public String createAppointment(Model model){
        model.addAttribute("scheduleApp", new Appointment());
        return "createAppointment";
    }*/

    @GetMapping("/createAppointment")
    public String createAppointment(Model model){
        model.addAttribute("scheduleApp", new Appointment());
        return "user/appointment";
    }

    @PostMapping("/createAppointment")
    public String createAppointment(@ModelAttribute("scheduleApp") Appointment appointment){
        User user = userService.getLoggedUser();
        System.out.println(user.getUserId());
        AppointmentService.createAppointment(user, appointment);

        return "user/createdAppointment";
    }

    @GetMapping("/updateProfile")
    public String updateProfile(Model model){
        User currentUser = userService.getLoggedUser();
        model.addAttribute("updateProf", currentUser);
        return "user/updateProfile";
    }

    @PostMapping("/updateProfile")
    public String createAppointment(@ModelAttribute("updateProf") User user){
        //User sameUser = userService.getLoggedUser();
        //System.out.println(user.getUserId());
        userService.updateInformation(user);

        return "user/profileUpdated";
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
