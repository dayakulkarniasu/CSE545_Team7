package com.sbs.sbsgroup7.api;


import com.sbs.sbsgroup7.DataSource.SessionLogRepository;
import com.sbs.sbsgroup7.model.*;

import com.sbs.sbsgroup7.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
    private TransactionService transactionService;

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
    public String approveRequests(Model model) {
        User user = userService.getLoggedUser();
        model.addAttribute("accounts", accountService.findByUser(user));
        model.addAttribute("userId", user.getUserId());

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

    @GetMapping("/creditdebit")
    public String debit(Model model){
        model.addAttribute("creditdebit", new CreditDebit());
        return "user/creditdebit";
    }

    @PostMapping("/creditdebit")
    public String debit(@Valid @ModelAttribute("creditdebit") CreditDebit creditDebit, BindingResult result){
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
            return "redirect:/user/error";
        }

    }
    @GetMapping("/transferFunds")
    public String transferFunds(Model model){
        model.addAttribute("transfer", new TransactionPage());
        return "user/transferFunds";
    }

    @PostMapping("/transferFunds")
    public String transferFunds(@Valid @ModelAttribute("transfer") TransactionPage transactionPage, BindingResult result) {
        User user = userService.getLoggedUser();
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(System.out::println);
            return "user/transferFunds";
        }
        try {
            accountService.transferFunds(user, transactionPage);
            if (transactionPage.getAmount() >= 1000) {
                return "user/accountRequestSent";
            }
            else {
                return "redirect:/user/accounts";
            }
        } catch (Exception e) {
            return "redirect:/user/error";
        }
    }

    @GetMapping("/emailTransfer")
    public String emailTransfer(Model model){
        model.addAttribute("email", new EmailPage());
        return "user/emailTransfer";
    }

    @PostMapping("/emailTransfer")
    public String emailTransfer(@Valid @ModelAttribute("email") EmailPage emailPage, BindingResult result) {
        User user = userService.getLoggedUser();
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(System.out::println);
            return "user/emailTransfer";
        }
        try {
            accountService.emailTransfer(user, emailPage);
            if (emailPage.getAmount() >= 1000) {
                return "user/accountRequestSent";
            }
            else {
                return "redirect:/user/accounts";
            }
        } catch (Exception e) {
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

    @DeleteMapping(path="/removeAll")
    public void deleteAll(){
        userService.deleteAll();
    }

    @RequestMapping("/error")
    public String error(){
        return "user/error";
    }



//    @RequestMapping("/accounts")
//    public String approveRequests(Model model) {
//        model.addAttribute("accounts", accountService.findAll());
//
//        return "user/accounts";
//    }

    @GetMapping(value = "/downloadStatement", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpEntity<byte[]> bankStatement(@RequestParam("userId") String userId, @RequestParam("accountId") Long accountNumber, HttpServletResponse response) throws IOException {
        System.out.println(userId + " === " + accountNumber.toString());

        byte[] pdfToSign = pdfService.generatePdf(userId, accountNumber);
        byte[] signedPdf = signingService.signPdf(pdfToSign);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=bankStatement.pdf");

        return new HttpEntity<byte[]>(signedPdf, headers);
    }




}
