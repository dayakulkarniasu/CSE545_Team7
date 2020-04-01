package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.DataSource.AcctRepository;
import com.sbs.sbsgroup7.DataSource.RequestRepository;
import com.sbs.sbsgroup7.DataSource.TransRepository;
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
import java.util.List;


@Controller
@RequestMapping("/tier2")
//@PreAuthorize("TIER2")
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

    @RequestMapping("/home")
    public String userHome(){
        return "tier2/home" ;
    }


//    Tier-2 employees can approve bank account requests
    @GetMapping("/approveRequests")
    public String approveRequests(Model model){
        model.addAttribute("requests", requestService.findpendingRequests());
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
        }
        else if (action.equals("declined")) {
            request.setRequestStatus("declined");
            request.setApprovedUser(approvedUser);
            request.setModifiedTime(Instant.now());
            requestRepository.save(request);

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
        if (action.equals("edit")) {
            accountService.editByAccountNumber(accountNumber, type);
            System.out.println("Editing Account# " + accountNumber + "'s type to " + type);
        } else if (action.equals("delete")) {
            accountService.deleteByAccountNumber(accountNumber);
            System.out.println("Deleting Account # " + accountNumber);
        }
        return "redirect:/tier2/viewAccounts";
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
            transaction.setTransactionStatus("approved");
            transaction.setModifiedTime(Instant.now());
            transRepository.save(transaction);
            source.setBalance(source.getBalance()-transaction.getAmount());
            destination.setBalance(destination.getBalance()+transaction.getAmount());
            acctRepository.save(source);
            acctRepository.save(destination);
        } else if (action.equals("declined")) {
            transaction.setTransactionStatus("declined");
            transaction.setModifiedTime(Instant.now());
            transRepository.save(transaction);
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
    public String updateProfile(@ModelAttribute("employeeInfo") EmployeeInfo employeeInfo){
        try {
            User user = userService.getLoggedUser();
            userService.requestProfileUpdates(user, employeeInfo);

            return "tier2/updateProfileRequest";
        } catch(Exception e) {
            return e.getMessage();
        }
    }
}
