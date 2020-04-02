package com.sbs.sbsgroup7.service;

import java.util.List;

import com.sbs.sbsgroup7.DataSource.UserRepository;
import com.sbs.sbsgroup7.dao.UserDaoInterface;
import com.sbs.sbsgroup7.errors.PhoneUsedException;
import com.sbs.sbsgroup7.errors.RoleException;
import com.sbs.sbsgroup7.errors.SsnUsedException;
import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import com.sbs.sbsgroup7.errors.EmailUsedException;


@Repository
public class UserService {

    private final UserDaoInterface userDao;
//    private final AcctDaoInterface acctDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;


    @Autowired
    public UserService(@Qualifier("user") UserDaoInterface userDao) { //, @Qualifier("account") AcctDaoInterface acctDao) {
        this.userDao = userDao;
//        this.acctDao = acctDao;
    }

    public User registerUser(User user){
        return registerUser(user, user.getRole());
    }

    public User registerUser(User user, String role){
        validateUser(user);
        validateUserRole(role);
        User u=new User();
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setEmail(user.getEmail());
        u.setPhone(user.getPhone());
        u.setRole(role);
        u.setPassword(user.getPassword());
        u.setSsn(user.getSsn());
        u.setDob(user.getDob());
        u.setAddress(user.getAddress());
        u.setActive(false);
        userRepository.save(u);
        accountService.createAccount(u);
        return u;

    }


    public void validateUser(User user){
        userRepository.findOneByEmailIgnoreCaseOrSsnOrPhone(user.getEmail(), user.getSsn(),user.getPhone()).
                ifPresent(existing ->{
                    if(existing.getEmail().equalsIgnoreCase(user.getEmail().toLowerCase())){
                        throw new EmailUsedException();
                    } else if (existing.getPhone().equals(user.getPhone())){
                        throw new PhoneUsedException();
                    } else if (existing.getSsn().equals(user.getSsn())){
                        throw new SsnUsedException();
                    }
                });


    }

    public void validateUserRole(String role){
        if(!(role.equals("USER") || role.equals("MERCHANT") || role.equals("TIER1") || role.equals("TIER2"))){
            throw new RoleException();
        }
    }


    public void add(User user) {
        //userDao.openCurrentSessionwithTransaction();
        userDao.persist(user);
        //userDao.closeCurrentSessionwithTransaction();
    }

    public void update(User user) {
        //userDao.openCurrentSessionwithTransaction();
        userDao.update(user);
        //userDao.closeCurrentSessionwithTransaction();
    }



    public User findByUsername(String username) {
        //userDao.openCurrentSession();
        User user = userDao.findByUsername(username);
        //userDao.closeCurrentSession();
        return user;
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
    public User findByUserId(User user){ return userRepository.findByUserId(user.getUserId());}

    public void delete(String username) {
        //userDao.openCurrentSessionwithTransaction();
        User user = userDao.findByUsername(username);
        userDao.delete(user);
        //userDao.closeCurrentSessionwithTransaction();
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void deleteAll() {
        //userDao.openCurrentSessionwithTransaction();
        userDao.deleteAll();
        //userDao.closeCurrentSessionwithTransaction();
    }

//    public void createAccount(Account account){
//        acctDao.createAccount(account);
//    }
//
//    public List<Account> getAccounts() {
//        return acctDao.getAccounts();
//    }


    public User getLoggedUser() {
        String loggedUserName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            loggedUserName = authentication.getName();
        }
        return userRepository.findByEmail(loggedUserName).orElse(null);
    }

    public User updateInformation(User user){
        User sameUser = getLoggedUser();
        //User updateUser = new User();
        //updateUser.setUserId(sameUser.getUserId());
        sameUser.setEmail(user.getEmail());
        sameUser.setAddress(user.getAddress());
//        sameUser.setDob(user.getDob());
//        sameUser.setFirstName(user.getFirstName());
//        sameUser.setLastName(user.getLastName());
        sameUser.setPhone(user.getPhone());
        sameUser.setSsn(user.getSsn());
        //System.out.println("Hi" +updateUser.getUserId());
        System.out.println("Hi" +sameUser.getUserId());
        userRepository.save(sameUser);
        return sameUser;
    }


}