package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.DataSource.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    SystemLogRepository systemLogRepository;

    @RequestMapping("/home")
    public String adminHome() {
        return "admin/home" ;
    }

    @RequestMapping("/logs")
    public String systemLogs(Model model) {
        model.addAttribute("logs", systemLogRepository.findAll());

        return "admin/logs";
    }
}
