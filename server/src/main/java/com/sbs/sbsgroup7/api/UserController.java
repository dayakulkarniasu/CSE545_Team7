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
import com.sbs.sbsgroup7.model.Transaction;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService=userService;
    }
    @RequestMapping("/home")
    public String userHome(){
        return "user/home" ;
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

    @GetMapping("/requestTransfers")
    public String transfer(Model model){
        model.addAttribute("transaction", new Transaction());
        return "user/requestTransfers";
    }

    @PostMapping("/requestTransfers")
    public String transfer(@ModelAttribute("transaction") Transaction transferForm){
        try {
            transactionService.createTransaction(transferForm);
            return "user/requestTransfers";
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/creditdebit")
    public String debit(Model model){
        model.addAttribute("creditdebit", new CreditDebit());
        return "user/creditdebit";
    }

    @PostMapping("/creditdebit")
    public String debit(@ModelAttribute("creditdebit") CreditDebit creditDebit){
        User user = userService.getLoggedUser();
        try {
            accountService.creditDebitTransaction(user,creditDebit);
            return "redirect:/user/accounts";
        } catch(Exception e) {
            return "redirect:/user/error";
        }

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

    @GetMapping(path = "/")
    public List<User> getAllUsers(){
        return userService.findAll();
    }
//    public String getAllUsers(Model model) {
//        model.addAttribute("name", "John");
//        return "index";
//    }


    @GetMapping("/EmailPhoneTransfer")
    public String EPTransfer(Model model){
        model.addAttribute("EPTransfer", new EmailPhoneTransfer());
        return "user/EmailPhoneTransfer";
    }

    @Autowired
    UserService userserv;
    @Autowired
    UserService userserv1;
    @PostMapping("/EmailPhoneTransfer")
    public String EPTransfer(@ModelAttribute("EPTransfer") EmailPhoneTransfer EPTransfer){
        try {

            //UserRepository userRepo;
            //find the userid inusers to then get the account number to then use account number


            //User getAccinfo = try1.findByUsername(EPTransfer.getArcEmail());
            //User getAccinfo2 = try2.findByUsername(EPTransfer.getDesEmail());
           // System.out.println("in the post method and the email getter value is: " + EPTransfer.getArcEmail());
            //  userserv.findByUsername("dayakulkarni@gmail.com");
            User getAccinfo = userserv.findByUsername(EPTransfer.getArcEmail());
           // System.out.println("outside the getacctinfo method: " + getAccinfo.getFirstName() );

            User getAccinfo2 = userserv1.findByUsername(EPTransfer.getDesEmail());

           // System.out.println("outside the getacctinfo2 method: " + getAccinfo.getFirstName() );

            //System.out.println("in the post method and the email getter value is: " + EPTransfer.getArcEmail());
            //this is list so just grab one value for now

            List<Account> list1 = getAccinfo.getAccounts();
            List<Account> list2 =getAccinfo2.getAccounts();

          //  System.out.println("the list 1 account info is: " + list1.get(0).getAccountNumber());
           // System.out.println("the list 2 account info is: " + list2.get(0).getAccountNumber());

            Transaction tran = new Transaction();

            tran.setAmount(EPTransfer.getAmtEmail());
            tran.setSrcAcct(list1.get(0).getAccountNumber());
            tran.setDstAcct(list2.get(0).getAccountNumber());

            transactionService.createTransaction(tran);


            // Optional<User> trial = userRepo.findByEmail(EPTransfer.getArcEmail());
            //System.out.println("in the post method and the trial value is: " + trial);
            return "user/EmailPhoneTransfer";
            //transactionService.createTransaction(transferForm);
            // return "accounts";
        } catch(Exception e) {
            return e.getMessage();
        }
    }


    @DeleteMapping(path="/removeAll")
    public void deleteAll(){
        userService.deleteAll();
    }



//    @RequestMapping("/accounts")
//    public String approveRequests(Model model) {
//        model.addAttribute("accounts", accountService.findAll());
//
//        return "user/accounts";
//    }




}
