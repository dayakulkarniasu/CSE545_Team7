package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.DataSource.*;
import com.sbs.sbsgroup7.model.*;
import com.sbs.sbsgroup7.service.AccountService;
import com.sbs.sbsgroup7.service.RequestService;
import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tier2")
public class Tier2Controller {

    @Autowired
    private RequestService requestService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransRepository transRepository;

    @Autowired
    private AcctRepository acctRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private SystemLogRepository systemLogRepository;

    @Autowired
    private SessionLogRepository sessionLogRepository;

    @RequestMapping("/home")
    public String userHome(Model model){
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

        return "tier2/home" ;
    }


    //Tier-2 employees can approve bank account requests
    @GetMapping("/approveRequests")
    public String approveRequests(Model model){
        model.addAttribute("requests", requestService.findPendingRequests());
        return "tier2/approveRequests";
    }
    @PostMapping("/approveRequests")
    public String approveRequests(@RequestParam("requestId") Long requestId,
                                   @RequestParam(value="action", required=true) String action  ) {
        User approvedUser=userService.getLoggedUser();
        Request request = requestService.findRequestById(requestId);
        if (action.equals("approved")) {
            request.setRequestStatus("approved");
            request.setApprovedUser(approvedUser);
            request.setModifiedTime(Instant.now());
            Account a=new Account();
            a.setAccountType(request.getRequestType());
            accountService.createAccount(request.getRequestedUser(), a);
            requestRepository.save(request);

            SystemLog systemLog=new SystemLog();
            systemLog.setMessage(approvedUser.getEmail() + " approved " + request.getRequestedUser().getEmail() + "'s " + request.getRequestType() + " account creation request");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);
        }
        else if (action.equals("declined")) {
            request.setRequestStatus("declined");
            request.setApprovedUser(approvedUser);
            request.setModifiedTime(Instant.now());
            requestRepository.save(request);

            SystemLog systemLog=new SystemLog();
            systemLog.setMessage(approvedUser.getEmail() + " declined " + request.getRequestedUser().getEmail() + "'s " + request.getRequestType() + " account creation request");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);
        }
        return "redirect:/tier2/approveRequests";
    }


    //Tier-2 employees can view accounts to edit, delete
    @GetMapping("/viewAccounts")
    public String viewAccounts(Model model) {
        List<String> accountTypes = new ArrayList<>();
        accountTypes.add("Checkings");
        accountTypes.add("Savings");
        accountTypes.add("Credit");
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("accountTypes", accountTypes);

        return "tier2/viewAccounts";
    }
    @PostMapping("/viewAccounts")
    public String viewAccounts(@RequestParam("accountNumber") Long accountNumber, @Valid String type,
                                   @RequestParam(value="action", required=true) String action  ) {
        try {
            if (action.equals("edit")) {
                accountService.editByAccountNumber(accountNumber, type);
                System.out.println("Editing Account# " + accountNumber + "'s type to " + type);
            } else if (action.equals("delete")) {

                acctRepository.deleteById(accountNumber);
                System.out.println("Deleting Account # " + accountNumber);
            }
            return "redirect:/tier2/viewAccounts";
        } catch(Exception e) {
            return "redirect:/tier2/deleteAccountError";
        }
    }

    //Tier-2 employees can view accounts to edit, delete
    @GetMapping("/approveTransfers")
    public String approveTransfers(Model model) {
        model.addAttribute("transfers",accountService.findPendingTransactions());
        return "tier2/approveTransfers";
    }
    @PostMapping("/approveTransfers")
    public String approveTransfers(@RequestParam("transactionId") Long transactionId,
                                   @RequestParam(value="action", required=true) String action  ) {
        Transaction transaction = accountService.findByTransactionId(transactionId);
        Account source = transaction.getFromAccount();
        Account destination = transaction.getToAccount();
        if (action.equals("approved")) {
            if(transaction.getTransactionType().equals("credit")){
                destination.setBalance(destination.getBalance()+transaction.getAmount());
            }
            else if(transaction.getTransactionType().equals("debit")){
                source.setBalance(source.getBalance()-transaction.getAmount());
            }
            else if(transaction.getTransactionType().equals("transferfunds")){
                source.setBalance(source.getBalance()-transaction.getAmount());
                destination.setBalance(destination.getBalance()+transaction.getAmount());
            }
            transaction.setTransactionStatus("approved");
            transaction.setModifiedTime(Instant.now());
            transRepository.save(transaction);
            acctRepository.save(source);
            acctRepository.save(destination);

            SystemLog systemLog=new SystemLog();
            systemLog.setMessage(userService.getLoggedUser().getEmail() + " approved " + transaction.getTransactionOwner().getEmail() + "'s transaction request");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);
        } else if (action.equals("declined")) {
            transaction.setTransactionStatus("declined");
            transaction.setModifiedTime(Instant.now());
            transRepository.save(transaction);

            SystemLog systemLog=new SystemLog();
            systemLog.setMessage(userService.getLoggedUser().getEmail() + " declined " + transaction.getTransactionOwner().getEmail() + "'s transaction request");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);
        }
        return "redirect:/tier2/approveTransfers";
    }

    @RequestMapping("/updateProfile")
    public String updateProfile(Model model){
        model.addAttribute("userInfo", userService.getLoggedUser());
        model.addAttribute("employeeInfo", new EmployeeInfo());
        return "tier2/updateProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("employeeInfo") EmployeeInfo employeeInfo, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            throw new Exception(result.getAllErrors().toString());
        }
        try {
            User user = userService.getLoggedUser();
            userService.requestProfileUpdates(user, employeeInfo);

            return "tier2/updateProfileRequest";
        } catch(Exception e) {
            throw new Exception(e);
        }
    }

    @RequestMapping("/error")
    public String error(){
        return "tier2/error";
    }

    @RequestMapping("/deleteAccountError")
    public String deleteAccountError(){
        return "tier2/deleteAccountError";
    }
}
