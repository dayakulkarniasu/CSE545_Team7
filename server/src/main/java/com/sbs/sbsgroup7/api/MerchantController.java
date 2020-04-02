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
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TransRepository transRepository;

    @Autowired
    public MerchantController(UserService userService)
    {
        this.userService=userService;
    }

    @RequestMapping("/home")
    public String merchantHome(){
        User user = userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
        transRepository.delete(transaction);
    }
        return "merchant/home" ;
    }


    @RequestMapping("/accounts")
    public String getAccounts(Model model) {
        User user=userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
            transRepository.delete(transaction);
        }
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
        User user = userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
            transRepository.delete(transaction);
        }
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
        User user = userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
            transRepository.delete(transaction);
        }
        model.addAttribute("transfer", new TransactionPage());
        return "merchant/transferFunds";
    }

    @PostMapping("/transferFunds")
    public String transferFunds(@ModelAttribute("transfer") TransactionPage transactionPage) {
        User user = userService.getLoggedUser();
        try {
            accountService.transferFunds(user, transactionPage);
            return "redirect:/otp/validateOtp";
        } catch (Exception e) {
            return "redirect:/merchant/error";
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
        return "merchant/emailTransfer";
    }

    @PostMapping("/emailTransfer")
    public String emailTransfer(@ModelAttribute("email") EmailPage emailPage) {
        User user = userService.getLoggedUser();
        try {
            accountService.emailTransfer(user, emailPage);
            return "redirect:/otp/validateOtp";
        } catch (Exception e) {
            return "redirect:/merchant/error";
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
        return "merchant/cashiercheque";
    }

    @PostMapping("/cashierCheque")
    public String cashierCheques(@ModelAttribute("cash") CashierCheque cashierCheque){
        User user=userService.getLoggedUser();
        Boolean b=accountService.cashierCheque(user,cashierCheque);
        if(b==true)
            return "merchant/chequeRequestSent";
        else{
            return "merchant/cashError";
        }
    }

    @GetMapping("/createAppointment")
    public String createAppointment(Model model){
        User user =userService.getLoggedUser();
        Appointment appointment=appointmentRepository.findByUser(user).orElse(null);
        if(appointment==null){
            model.addAttribute("scheduleApp", new Appointment());
            return "merchant/appointment";}
        else{
            return "merchant/appointmentExists";
        }
    }

    @PostMapping("/createAppointment")
    public String createAppointment(@ModelAttribute("scheduleApp") Appointment appointment){
        User user = userService.getLoggedUser();
        System.out.println(user.getUserId());
        appointmentService.createAppointment(user, appointment);
        return "redirect:/merchant/viewAppointment";
    }

    @GetMapping("/updateProfile")
    public String updateProfile(Model model){
        User currentUser = userService.getLoggedUser();
        model.addAttribute("updateProf", currentUser);
        return "merchant/updateProfile";
    }

    @PostMapping("/updateProfile")
    public String createAppointment(@ModelAttribute("updateProf") User user){
        //User sameUser = userService.getLoggedUser();
        //System.out.println(user.getUserId());
        userService.updateInformation(user);

        return "merchant/profileUpdated";
    }

    @RequestMapping("/support")
    public String userSupport(){

        User user = userService.getLoggedUser();
        Transaction transaction=transRepository.findByTransactionOwnerAndTransactionStatus(user, "temp");
        if(transaction!=null){
            transRepository.delete(transaction);
        }
        return "merchant/support" ;
    }

    @RequestMapping("/viewAppointment")
    public String getAppointment(Model model) {
        User user=userService.getLoggedUser();
        Appointment appointment=appointmentRepository.findByUser(user).orElse(null);
        if(appointment==null){
            return "merchant/noAppointment";
        }
        else {
            model.addAttribute("app", appointment);
            return "merchant/viewAppointment";
        }
    }


    @RequestMapping("/error")
    public String error(){
        return "merchant/error";
    }



}
