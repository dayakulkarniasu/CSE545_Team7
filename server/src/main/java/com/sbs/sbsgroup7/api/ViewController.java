package com.sbs.sbsgroup7.api;

import com.sbs.sbsgroup7.dao.UserDao;
import com.sbs.sbsgroup7.model.contact;
import com.sbs.sbsgroup7.service.OTP;
import com.sbs.sbsgroup7.service.RequestService;
import com.sbs.sbsgroup7.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ViewController {
    @Autowired
    RequestService requestService;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/home")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", auth.getName());
        return "index";
    }

    @RequestMapping(value = "/newcontactemailandssn", method = RequestMethod.GET)
    public String newcontactemailandssn() {
        return "register1";
    }

//    @RequestMapping(value = "/admin", method = RequestMethod.GET)
//    public String newcontactemailandssn11() {
//        return "admin";
//    }
    @RequestMapping(value = "/newcontactemailandssn2", method = RequestMethod.GET)
    public String newcontactemailandssn2() {
        return "register2";
    }

    @RequestMapping(value = "/finalsignup", method = RequestMethod.POST)
    public ModelAndView finalscreen(HttpServletRequest request, HttpSession session, ModelAndView model) {

        String otp = request.getParameter("OTP");
        String email=request.getParameter("email");



        String otpStringgen=session.getAttribute("otp-valid-str")+"";
        session.setAttribute("email_sign_up",email)	;

        System.out.println(otpStringgen);
        System.out.println(otp);
        if(otp.equals(otpStringgen))
        {
            return new ModelAndView("redirect:/sign_up");
        }
        else{
            //model.addObject("form", new ExternalUser());
            model.setViewName("register1");
        }
        return model;
    }

//    @RequestMapping(value = "/admin/newContact")
//    public ModelAndView newContact(ModelAndView model,HttpSession session,@ModelAttribute contact cnt) throws IOException {
//
//        String otpStringgen=session.getAttribute("otp-valid-str")+"";
//
//        if(otpStringgen.equals(cnt.getAddress()))
//        {
//
//            contact newContact = new contact();
//            System.out.println("newcontact");
//            model.addObject("contact", newContact);
//            model.setViewName("Register");
//            return model;
//        }
//        else
//        {
//            model.setViewName("register1");
//            return model;
//        }
//    }

    @RequestMapping(value = "/forgotpassword1", method = RequestMethod.GET)
    public String forgotpassword1() {
        return "forgotpassword1";
    }

    @RequestMapping(value = "/forgotpassword2", method = RequestMethod.GET)
    public String forgotpassword2() {
        return "forgotpassword2";
    }

    @RequestMapping(value = "/sendtoemail", method = RequestMethod.POST)
    public ModelAndView sendtoemail(ModelAndView model,HttpServletRequest request,HttpSession session) {
        String email=request.getParameter("email");
        String ssninput = request.getParameter("ssn1");
        session.setAttribute("ssn_entered", ssninput);
        //System.out.println("inside email");

        List<String> userexists= userDao.ifnewRegisteredUserExists(email);
        //System.out.println("ext"+userexists);


        if(userexists.size() >0)
        {
            // System.out.println("userexists");
            return new ModelAndView("redirect:/newcontactemailandssn");
        }

        else{

            //System.out.println("send email");
            String otpString= OTP.generatePIN();
            session.setAttribute("otp-valid-str", otpString);

            com.sbs.sbsgroup7.service.email.sendEmail(email,otpString);
            return new ModelAndView("redirect:/newcontactemailandssn2");
        }

    }

    @RequestMapping(value = "/sendtoemailforgotpassword", method = RequestMethod.POST)
    public ModelAndView sendemailforgotpassword(ModelAndView model,HttpServletRequest request,HttpSession session) {
        String email=request.getParameter("email");
        //System.out.println("email :"+email);
        List<String> userexists= userDao.ifnewRegisteredUserExists(email);

        if(userexists.size()==0){
            return new ModelAndView("redirect:/forgotpassword1");
        }
        else{
            String otpString=OTP.generatePIN();
            session.setAttribute("otp-valid-str", otpString);
            session.setAttribute("Email", email);
            com.sbs.sbsgroup7.service.email.sendEmail(email,otpString);
            return new ModelAndView("redirect:/forgotpassword2");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();

        model.setViewName("login");

        return model;
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginfalied(ModelMap model) {
        model.addAttribute("error", "true");
        return "login";
    }

    @RequestMapping(value = "/forgotpasswordfinal", method = RequestMethod.POST)
    public String forgotpasswordfinal(HttpServletRequest request, HttpSession session) {

        String otp = request.getParameter("OTP");
        String otpStringgen=session.getAttribute("otp-valid-str")+"";
        String emailentered = request.getParameter("email");
        String passwordentered = request.getParameter("Password");

        String emailinsession = session.getAttribute("Email")+"";

        //System.out.println("otp "+otp);
        //System.out.println("otpsession "+otpStringgen);
        //System.out.println("emailent "+emailentered);
        //System.out.println("emailinsession "+emailinsession);
        if(otp.equals(otpStringgen) && emailentered.equals(emailinsession))
        {
            //String password = Myencoder.hashedpassword(passwordentered);
            //System.out.println("pass"+password);
            userDao.update_password(passwordentered, emailentered);



            return "login";
        }
        else{
            return "forgotpassword1";
        }
    }







    @RequestMapping("/accounts")
    public String accounts() {
        return "accounts";
    }

    @RequestMapping("/createAccount")
    public String createAccount() {
        return "createAccount";
    }
    @RequestMapping("/EmailPhoneTransfer")
    public String EmailPhoneTransfer() {
        return "EmailPhoneTransfer";
    }

    @RequestMapping("/requestTransfers")
    public String requestTransfers() {
        return "requestTransfers";
    }

    @RequestMapping("/approveTransfers")
    public String approveTransfers() {
        return "approveTransfers";
    }

    //Only viewable by Tier-2 employees (approving bank account requests)
    @RequestMapping("/approveRequests")
    public String approveRequests(Model model) {
        model.addAttribute("requests", requestService.findAll());

        return "approveRequests";
    }
    /*@RequestMapping("/transactions")
    public String transaction(Model model) {
        model.addAttribute("requests", TransactionService.TIDVAL());
        return "transactions";
    }*/
}
