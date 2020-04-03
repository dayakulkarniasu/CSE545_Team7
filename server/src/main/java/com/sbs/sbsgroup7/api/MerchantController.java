package com.sbs.sbsgroup7.api;


import com.sbs.sbsgroup7.DataSource.AppointmentRepository;
import com.sbs.sbsgroup7.DataSource.SessionLogRepository;
import com.sbs.sbsgroup7.DataSource.TransRepository;
import com.sbs.sbsgroup7.model.*;

import com.sbs.sbsgroup7.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private SessionLogRepository sessionLogRepository;

    @Autowired
    public MerchantController(UserService userService)
    {
        this.userService=userService;
    }

    @Autowired
    private PdfService pdfService;

    @Autowired
    private SigningService signingService;

    @RequestMapping("/home")
    public String merchantHome(Model model){
        User loggedUser = userService.getLoggedUser();
        List<SessionLog> sessionLogs = sessionLogRepository.findAll();
        if (sessionLogs != null) {
            sessionLogs = sessionLogs
                    .stream()
                    .filter(e -> e.getUserId() != null)
                    .filter(e -> e.getUserId().equals(loggedUser.getUserId()))
                    .sorted((s1, s2) -> s1.getTimestamp().compareTo(s2.getTimestamp()))
                    .collect(Collectors.toList());

            model.addAttribute("lastAccess", sessionLogs.get(0).getTimestamp());

        } else {
            model.addAttribute("lastAccess", "Never");
        }

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
    public String createAppointment(@ModelAttribute("scheduleApp") Appointment appointment, @RequestParam("date") String date, @RequestParam("time") String time) throws Exception {
        if (appointment.getContactWay().equals("null")) {
            throw new Exception("Contact type cannot be null!");
        }
        User user = userService.getLoggedUser();
        System.out.println(user.getUserId());
        appointmentService.createAppointment(user, appointment, date, time);
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

    @GetMapping(value = "/downloadStatement", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpEntity<byte[]> bankStatement(@RequestParam("userId") String userId, @RequestParam("accountId") Long accountNumber, HttpServletResponse response) throws IOException {

        byte[] pdfToSign = pdfService.generatePdf(userId, accountNumber);
        byte[] signedPdf = signingService.signPdf(pdfToSign);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String fileName = "bankStatement " + formatter.format(date) + ".pdf";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        return new HttpEntity<byte[]>(signedPdf, headers);
    }
}
