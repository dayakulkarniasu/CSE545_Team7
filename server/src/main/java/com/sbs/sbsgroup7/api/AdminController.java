package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.DataSource.EmployeeUpdatesRepository;
import com.sbs.sbsgroup7.DataSource.SessionLogRepository;
import com.sbs.sbsgroup7.DataSource.SystemLogRepository;
import com.sbs.sbsgroup7.DataSource.UserRepository;
import com.sbs.sbsgroup7.model.SessionLog;
import com.sbs.sbsgroup7.model.SystemLog;
import com.sbs.sbsgroup7.model.User;
import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    SystemLogRepository systemLogRepository;

    @Autowired
    EmployeeUpdatesRepository employeeUpdatesRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionLogRepository sessionLogRepository;

    @RequestMapping("/home")
    public String adminHome(Model model) {
        User user = userService.getLoggedUser();
        List<SessionLog> sessionLogs = sessionLogRepository.findAll();
        if (sessionLogs != null) {
            sessionLogs = sessionLogs
                    .stream()
                    .filter(e -> e.getUserId() != null)
                    .filter(e -> e.getUserId().equals(user.getUserId()))
                    .sorted((s1, s2) -> s1.getTimestamp().compareTo(s2.getTimestamp()))
                    .collect(Collectors.toList());

            model.addAttribute("lastAccess", sessionLogs.get(0).getTimestamp());

        } else {
            model.addAttribute("lastAccess", "Never");
        }

        return "admin/home" ;
    }

    @RequestMapping("/logs")
    public String systemLogs(Model model) {
        model.addAttribute("logs", systemLogRepository.findAll());

        return "admin/logs";
    }

    @RequestMapping("/technicalAccountAccess")
    public String technicalAccountAccess(Model model) {
        model.addAttribute("updates", employeeUpdatesRepository.findAll());

        return "admin/technicalAccountAccess";
    }

    @PostMapping("/approveUpdates")
    public String approveUpdates(@RequestParam("userId") String userId, @RequestParam("action") String action) {
        User requestedUser = userService.findByUserId(userId);
        User loggedUser = userService.getLoggedUser();
        if (action.equalsIgnoreCase("approved")) {
            userService.approveProfileUpdates(userId);

            SystemLog systemLog=new SystemLog();
            systemLog.setMessage("admin (" + loggedUser.getEmail() + ") approved " + requestedUser.getEmail() + "'s profile updates request");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);

            systemLog.setMessage(requestedUser.getEmail() + " successfully updated profile information");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);
        }
        else {
            SystemLog systemLog=new SystemLog();
            systemLog.setMessage("admin (" + loggedUser.getEmail() + ") declined " + requestedUser.getEmail() + "'s profile updates request");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);
        }
        employeeUpdatesRepository.deleteById(userId);

        return "redirect:technicalAccountAccess";
    }

    @RequestMapping("/manageAccounts")
    public String manageAccounts(Model model) {
        Stream<User> employeeList1 = userService.findAll().stream()
                .filter(e -> e.getRole().equals("TIER1"));
        Stream<User> employeeList2 = userService.findAll().stream()
                .filter(e -> e.getRole().equals("TIER2"));
        List<User> employeeList = Stream.concat(employeeList1, employeeList2)
                .collect(Collectors.toList());

        model.addAttribute("employees", employeeList);

        return "admin/manageAccounts";
    }

    @RequestMapping(value = "/manageAccounts/{userId}", method = RequestMethod.GET)
    public String manageAccount(@PathVariable("userId") String userId, Model model) {
        User user = userService.findByUserId(userId);

        model.addAttribute("user", user);

        return "admin/updateEmployeeProfile";
    }

    @PostMapping("/manageAccount")
    public String manageAccount(@RequestParam("userId") String userId, @RequestParam("action") String action) {
        if (action.equals("delete")) {
            userService.delete(userId);
            return "redirect:manageAccounts";
        } else {
            return "redirect:manageAccounts/" + userId;
        }
    }

    @PostMapping("/updateEmployeeProfile")
    public String updateEmployeeProfile(@RequestParam("firstName") String firstName,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("email") String email,
                                        @RequestParam("ssn") String ssn,
                                        @RequestParam("address") String address,
                                        @RequestParam("phone") String phone,
                                        @RequestParam("userId") String userId) {

        try {
            User user = userRepository.findByUserId(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setSsn(ssn);
            user.setAddress(address);
            user.setPhone(phone);
            userRepository.save(user);

            return "redirect:manageAccounts";
        } catch (Exception e) {
            return "redirect:/admin/error";
        }
    }

    @GetMapping("/registerEmployee")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "admin/registerEmployee";
    }

    @PostMapping("/registerEmployee")
    public String register(@Valid @ModelAttribute("user") User userForm, BindingResult result){
        if (result.hasErrors()) {
            return "admin/registerEmployee";
        }
        try {
            userService.registerUser(userForm);

            SystemLog systemLog=new SystemLog();
            systemLog.setMessage(userForm.getEmail() + " successfully registered");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);

            return "admin/employeeRegistered";
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/error")
    public String error(){
        return "admin/error";
    }
}
