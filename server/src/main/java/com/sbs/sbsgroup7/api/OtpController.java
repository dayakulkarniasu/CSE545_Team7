package com.sbs.sbsgroup7.api;

import java.util.HashMap;
import java.util.Map;

import com.sbs.sbsgroup7.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.sbs.sbsgroup7.service.MailService;
import com.sbs.sbsgroup7.service.OtpService;
/**
 * @author shrisowdhaman
 * Dec 15, 2017
 */
@Controller
public class OtpController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public OtpService otpService;
    @Autowired
    public MailService emailService;
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
        return "otppage";
    }
    @RequestMapping(value ="/validateOtp", method = RequestMethod.POST)
    public String validateOtp(@ModelAttribute("otp") String otpnum){
        final String SUCCESS = "Entered Otp is valid";
        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        logger.info(" Otp Number : "+otpnum);
//Validate the Otp
        if(Integer.parseInt(otpnum )>= 0){
            int serverOtp = otpService.getOtp(username);
            if(serverOtp > 0){
                if(Integer.parseInt(otpnum ) == serverOtp){
                    otpService.clearOTP(username);
                    return "index";
                }else{
                    return SUCCESS;
                }
            }else {
                return FAIL;
            }
        }else {
            return FAIL;
        }
    }
}