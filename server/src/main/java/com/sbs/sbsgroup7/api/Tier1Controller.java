package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.DataSource.AcctRepository;
import com.sbs.sbsgroup7.DataSource.ChequeRepository;
import com.sbs.sbsgroup7.DataSource.RequestRepository;
import com.sbs.sbsgroup7.DataSource.TransRepository;
import com.sbs.sbsgroup7.model.*;
import com.sbs.sbsgroup7.service.AccountService;
import com.sbs.sbsgroup7.service.RequestService;
import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;

@Controller
@RequestMapping("/tier1")
@PreAuthorize("TIER1")
public class Tier1Controller {

    @Autowired
    private RequestService requestService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChequeRepository chequeRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private TransRepository transRepository;

    @Autowired
    private AcctRepository acctRepository;

    @RequestMapping("/home")
    public String userHome(){
        return "tier1/home" ;
    }

    @GetMapping("/viewAccounts")
    public String viewAccounts(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "tier1/viewAccounts";
    }
    @GetMapping("/viewTransactions")
    public String viewTransactions(Model model) {
        model.addAttribute("transfers", accountService.findAllTransactions());
        return "tier1/viewTransactions";
    }

    @GetMapping("/approvecRequests")
    public String approvecRequests(Model model){
        model.addAttribute("crequests", requestService.findPendingChequeRequests());
        return "tier1/approvecRequests";
    }

    @PostMapping("/approvecRequests")
    public String approvecRequests(@RequestParam("requestId") Long requestId,
                                  @RequestParam(value="action", required=true) String action  ) {
        User approvedUser=userService.getLoggedUser();
        Request request = requestService.findRequestById(requestId);
        if (action.equals("approved")) {
            request.setRequestStatus("approved");
            request.setApprovedUser(approvedUser);
            request.setModifiedTime(Instant.now());
            for(int i=0; i<5; i++){
                Cheque cheque=new Cheque();
                cheque.setAccount(request.getAccount());
                cheque.setModifiedTime(request.getModifiedTime());
                cheque.setRequestedTime(request.getRequestedTime());
                cheque.setActive(true);
                chequeRepository.save(cheque);
            }
            requestRepository.save(request);
        }
        else if (action.equals("declined")) {
            request.setRequestStatus("declined");
            request.setApprovedUser(approvedUser);
            request.setModifiedTime(Instant.now());
            requestRepository.save(request);

        }
        return "redirect:/tier1/approvecRequests";

    }

    @GetMapping("/chequeTransfers")
    public String approveTransfers(Model model) {
        model.addAttribute("transfers",accountService.findPendingChequeTransactions());
        return "tier1/chequeTransfers";
    }

    @PostMapping("/chequeTransfers")
    public String approveTransfers(@RequestParam("transactionId") Long transactionId,
                                   @RequestParam(value="action", required=true) String action  ) {

        Transaction transaction = accountService.findByTransactionId(transactionId);
        Account source = transaction.getFromAccount();
        Account destination = transaction.getToAccount();

        if (action.equals("approved")) {
            if(transaction.getTransactionType().equals("cheque")){
                source.setBalance(source.getBalance()-transaction.getAmount());
                destination.setBalance(destination.getBalance()+transaction.getAmount());
            }
            transaction.setTransactionStatus("approved");
            transaction.setModifiedTime(Instant.now());
            transRepository.save(transaction);
            acctRepository.save(source);
            acctRepository.save(destination);

        }
        else if (action.equals("declined")) {
            transaction.setTransactionStatus("declined");
            transaction.setModifiedTime(Instant.now());
            transRepository.save(transaction);

        }
        return "redirect:/tier1/chequeTransfers";

    }

}
