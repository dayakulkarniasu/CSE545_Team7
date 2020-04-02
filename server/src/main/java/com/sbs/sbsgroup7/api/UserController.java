package com.sbs.sbsgroup7.api;


import com.sbs.sbsgroup7.DataSource.AppointmentRepository;
import com.sbs.sbsgroup7.DataSource.TransRepository;
import com.sbs.sbsgroup7.model.*;

import com.sbs.sbsgroup7.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TransRepository transRepository;

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
    public String getAccounts(Model model) {
        User user=userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
            transRepository.delete(transaction);
        }
        model.addAttribute("accounts", accountService.getAccountsByUser(user));

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
//            System.out.println(user.getUserId() + " created bank account request");

            return "user/accountRequestSent";
        } catch(Exception e) {
            return e.getMessage();
        }
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
                return "user/viewCheque";
            }
        }
        return "redirect:/user/error";
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
                    return "user/chequeRequestSent";
                    }
                }
            return "redirect:/user/error";
    }

    @GetMapping("/creditdebit")
    public String debit(Model model){
        User user = userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
            transRepository.delete(transaction);
        }
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
    @GetMapping("/transferFunds")
    public String transferFunds(Model model){
        User user = userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
            transRepository.delete(transaction);
        }
        model.addAttribute("transfer", new TransactionPage());
        return "user/transferFunds";
    }

    @PostMapping("/transferFunds")
    public String transferFunds(@ModelAttribute("transfer") TransactionPage transactionPage) {
        User user = userService.getLoggedUser();
        try {
            accountService.transferFunds(user, transactionPage);
            return "redirect:/otp/validateOtp";
        } catch (Exception e) {
            return "redirect:/user/error";
        }
    }

    @GetMapping("/emailTransfer")
    public String emailTransfer(Model model){
        User user = userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
            transRepository.delete(transaction);
        }
        model.addAttribute("email", new EmailPage());
        return "user/emailTransfer";
    }

    @PostMapping("/emailTransfer")
    public String emailTransfer(@ModelAttribute("email") EmailPage emailPage) {
        User user = userService.getLoggedUser();
        try {
            accountService.emailTransfer(user, emailPage);
            return "redirect:/otp/validateOtp";
        } catch (Exception e) {
            return "redirect:/user/error";
        }
    }

    @GetMapping("/cashierCheque")
    public String cashierCheques(Model model){
        User user = userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
            transRepository.delete(transaction);
        }
        model.addAttribute("cash", new CashierCheque());
        return "user/cashiercheque";
    }

    @PostMapping("/cashierCheque")
    public String cashierCheques(@ModelAttribute("cash") CashierCheque cashierCheque){
        User user=userService.getLoggedUser();
        Boolean b=accountService.cashierCheque(user,cashierCheque);
        if(b==true)
            return "user/chequeRequestSent";
        else{
            return "user/cashError";
        }
    }



    @GetMapping("/createAppointment")
    public String createAppointment(Model model){
        User user =userService.getLoggedUser();
        Appointment appointment=appointmentRepository.findByUser(user).orElse(null);
        if(appointment==null){
            model.addAttribute("scheduleApp", new Appointment());
            return "user/appointment";}
        else{
            return "user/appointmentExists";
        }
    }

    @PostMapping("/createAppointment")
    public String createAppointment(@ModelAttribute("scheduleApp") Appointment appointment){
        User user = userService.getLoggedUser();
        System.out.println(user.getUserId());
        appointmentService.createAppointment(user, appointment);
        return "redirect:/user/viewAppointment";
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

    @RequestMapping("/support")
    public String userSupport(){
        User user = userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
            transRepository.delete(transaction);
        }
        return "user/support" ;
    }

    @RequestMapping("/viewAppointment")
    public String getAppointment(Model model) {
        User user=userService.getLoggedUser();
        Appointment appointment=appointmentRepository.findByUser(user).orElse(null);
        if(appointment==null){
            return "user/noAppointment";
        }
        else {
            model.addAttribute("app", appointment);
            return "user/viewAppointment";
        }
    }



}
