package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.DataSource.SessionLogRepository;
import com.sbs.sbsgroup7.DataSource.AppointmentRepository;
import com.sbs.sbsgroup7.DataSource.TransRepository;
import com.sbs.sbsgroup7.model.*;
import com.sbs.sbsgroup7.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private SessionLogRepository sessionLogRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private SigningService signingService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService=userService;
    }

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
    public String debit(@Valid @ModelAttribute("creditdebit") CreditDebit creditDebit, BindingResult result) throws Exception {
        User user = userService.getLoggedUser();
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(System.out::println);
            return "user/creditDebit";
        }
        try {
            accountService.creditDebitTransaction(user,creditDebit);
            if(creditDebit.getAmount() >= 1000) {
                return "user/accountRequestSent";
            }
            else {
                return "redirect:/user/accounts";
            }
        } catch(Exception e) {
            throw new Exception(e);
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
    public String transferFunds(@Valid @ModelAttribute("transfer") TransactionPage transactionPage, BindingResult result) throws Exception {
        User user = userService.getLoggedUser();
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(System.out::println);
            return "user/transferFunds";
        }
        try {
            accountService.transferFunds(user, transactionPage);
            return "redirect:/otp/validateOtp";
        } catch (Exception e) {
            throw new Exception(e);
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
    public String emailTransfer(@Valid @ModelAttribute("email") EmailPage emailPage, BindingResult result) throws Exception {
        User user = userService.getLoggedUser();
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(System.out::println);
            return "user/emailTransfer";
        }
        try {
            accountService.emailTransfer(user, emailPage);
            return "redirect:/otp/validateOtp";
        } catch (Exception e) {
            throw new Exception(e);
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

    @PostMapping("/add")
    public void addUser(@NotNull @Validated @RequestBody User user){
        userService.add(user);
    }

    @PutMapping ("/update")
    public void update(@NotNull @Validated @RequestBody User user){
        userService.update(user);
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


    @RequestMapping("/error")
    public String error(){
        return "user/error";
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
    public String createAppointment(@ModelAttribute("scheduleApp") Appointment appointment, @RequestParam("date") String date, @RequestParam("time") String time) throws Exception {
        if (appointment.getContactWay().equals("null")) {
            throw new Exception("Contact type cannot be null!");
        }
        User user = userService.getLoggedUser();
        System.out.println(user.getUserId());
        appointmentService.createAppointment(user, appointment, date, time);
        return "redirect:/user/viewAppointment";
    }

    @GetMapping("/updateProfile")
    public String updateProfile(Model model){
        User currentUser = userService.getLoggedUser();
        model.addAttribute("updateProf", currentUser);
        return "user/updateProfile";
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

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("updateProf") User user){
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
