package com.sbs.sbsgroup7.api;


import com.sbs.sbsgroup7.model.*;

import com.sbs.sbsgroup7.service.AccountService;
import com.sbs.sbsgroup7.service.RequestService;
import com.sbs.sbsgroup7.service.TransactionService;
import com.sbs.sbsgroup7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/merchant")
@Controller
public class MerchantController {

    @Autowired
    private  UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    public MerchantController(UserService userService)
    {
        this.userService=userService;
    }
    @RequestMapping("/home")
    public String merchantHome(){
        return "merchant/home" ;
    }


    @RequestMapping("/accounts")
    public String getAccounts(Model model) {
        User user=userService.getLoggedUser();
        model.addAttribute("accounts", accountService.getAccountsByUser(user));

        return "merchant/accounts";
    }

    @GetMapping("/viewCheque/{id}")
    public String viewCheques(@PathVariable("id") Long id, Model model){
        User user=userService.getLoggedUser();
        List<Account> accts=accountService.getAccountsByUser(user);
        Account account=accountService.getAccountByAccountNumber(id);
        for (Account acct: accts){
            if(acct==account){
                List<Cheque> cheques=accountService.findChequeByAccount(account);
                model.addAttribute("cheques", cheques);
                return "merchant/viewCheque";
            }
        }
        return "redirect:/merchant/error";
    }

    @RequestMapping("/requestCheque/{id}")
    public String requestCheques(@PathVariable("id") Long id){
        User user = userService.getLoggedUser();
        Account account=accountService.getAccountByAccountNumber(id);
        List<Account> accts=accountService.getAccountsByUser(user);
        for (Account acct: accts){
            if(acct==account){
                Request request=new Request();
                requestService.createChequeRequest(user, acct, request);
                return "merchant/chequeRequestSent";
            }
        }
        return "redirect:/merchant/error";
    }

    @GetMapping("/creditdebit")
    public String debit(Model model){
        model.addAttribute("creditdebit", new CreditDebit());
        return "merchant/creditdebit";
    }

    @PostMapping("/creditdebit")
    public String debit(@ModelAttribute("creditdebit") CreditDebit creditDebit){
        User user = userService.getLoggedUser();
        try {
            accountService.creditDebitTransaction(user,creditDebit);
            return "redirect:/merchant/accounts";
        } catch(Exception e) {
            return "redirect:/merchant/error";
        }

    }
    @GetMapping("/transferFunds")
    public String transferFunds(Model model){
        model.addAttribute("transfer", new TransactionPage());
        return "merchant/transferFunds";
    }

    @PostMapping("/transferFunds")
    public String transferFunds(@ModelAttribute("transfer") TransactionPage transactionPage) {
        User user = userService.getLoggedUser();
        try {
            accountService.transferFunds(user, transactionPage);
            return "redirect:/merchant/accounts";
        } catch (Exception e) {
            return "redirect:/merchant/error";
        }
    }

    @GetMapping("/emailTransfer")
    public String emailTransfer(Model model){
        model.addAttribute("email", new EmailPage());
        return "merchant/emailTransfer";
    }

    @PostMapping("/emailTransfer")
    public String emailTransfer(@ModelAttribute("email") EmailPage emailPage) {
        User user = userService.getLoggedUser();
        try {
            accountService.emailTransfer(user, emailPage);
            return "redirect:/merchant/accounts";
        } catch (Exception e) {
            return "redirect:/merchant/error";
        }
    }



}
