package com.sbs.sbsgroup7.api;

import java.util.Date;

import com.sbs.sbsgroup7.DataSource.AcctRepository;
import com.sbs.sbsgroup7.DataSource.SystemLogRepository;
import com.sbs.sbsgroup7.DataSource.TransRepository;
import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.SystemLog;
import com.sbs.sbsgroup7.model.Transaction;
import com.sbs.sbsgroup7.model.User;
import com.sbs.sbsgroup7.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.sbs.sbsgroup7.service.MailService;

/**
 * @author shrisowdhaman
 * Dec 15, 2017
 */
@Controller
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private TransRepository transRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AcctRepository acctRepository;

    @Autowired
    private SystemLogRepository systemLogRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OtpService otpService;

    @Autowired
    public MailService emailService;

    @Autowired
    private UserService userService;

    @GetMapping("/generateOtp")
    public String generateOtp(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        int otp = otpService.generateOTP(username);
        logger.info("OTP : "+otp);
        //Generate The Template to send OTP
        //        EmailTemplate template = new EmailTemplate("SendOtp.html");
        //        Map<String,String> replacements = new HashMap<String,String>();
        //        replacements.put("user", username);
        //        replacements.put("otpnum", String.valueOf(otp));
        //        String message = template.getTemplate(replacements);
        emailService.sendOTPMail(username, Integer.toString(otp));
        return "otp/otppage";
    }

    @RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
    public String validateOtp(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        int otp = otpService.generateOTP(username);
        logger.info("OTP : "+otp);
        emailService.sendOTPMail(username, Integer.toString(otp));
        return "otp/otppage";
    }

    @RequestMapping(value ="/validateOtp", method = RequestMethod.POST)
    public String validateOtp(@ModelAttribute("otp") String otpnum){
        User user=userService.getLoggedUser();
        final String SUCCESS = "Entered Otp is valid";
        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        logger.info(" Otp Number : "+otpnum);

        //Validate the Otp
        if(Integer.parseInt(otpnum )>= 0){
            int serverOtp = otpService.getOtp(username);
            if(serverOtp > 0){
                if(Integer.parseInt(otpnum ) == serverOtp) {
                    otpService.clearOTP(username);
                    Transaction transaction = transRepository.findByTransactionOwnerAndTransactionStatus(user,"temp");
                    Account source= transaction.getFromAccount();
                    Account destination = transaction.getToAccount();
                    if(transaction.getAmount()<1000){
                        transaction.setTransactionStatus("approved");
                        transRepository.save(transaction);
                        source.setBalance(source.getBalance()-transaction.getAmount());
                        destination.setBalance(destination.getBalance()+transaction.getAmount());
                        acctRepository.save(source);
                        acctRepository.save(destination);
                        SystemLog systemLog=new SystemLog();
                        systemLog.setMessage(user.getEmail() + " successfully transferred $" + transaction.getAmount());
                        systemLog.setTimestamp(new Date());
                        systemLogRepository.save(systemLog);
                    }
                    else{
                        transaction.setTransactionStatus("pending");
                        transRepository.save(transaction);
                        SystemLog systemLog=new SystemLog();
                        systemLog.setMessage(user.getEmail() + " requested transfer of $" + transaction.getAmount());
                        systemLog.setTimestamp(new Date());
                        systemLogRepository.save(systemLog);
                    }
                    if(user.getRole().equals("USER")) {
                        return "redirect:/user/accounts";
                    }
                    else if(user.getRole().equals("MERCHANT")){
                        return "redirect:/merchant/accounts";
                    }
                    else{
                        return "403error";
                    }
                }
            }
        }
        return "otp/invalid";
    }
}
