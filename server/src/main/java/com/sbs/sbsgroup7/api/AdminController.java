package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.DataSource.EmployeeUpdatesRepository;
import com.sbs.sbsgroup7.DataSource.SystemLogRepository;
import com.sbs.sbsgroup7.model.SystemLog;
import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    SystemLogRepository systemLogRepository;

    @Autowired
    EmployeeUpdatesRepository employeeUpdatesRepository;

    @Autowired
    UserService userService;

    @RequestMapping("/home")
    public String adminHome() {
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
        if (action.equalsIgnoreCase("approved")) {
            userService.approveProfileUpdates(userId);
        }
        employeeUpdatesRepository.deleteById(userId);

        return "redirect:technicalAccountAccess";
    }
}
