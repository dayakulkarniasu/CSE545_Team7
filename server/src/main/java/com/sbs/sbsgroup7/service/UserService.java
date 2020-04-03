package com.sbs.sbsgroup7.service;

import java.util.Date;
import java.util.List;

import com.sbs.sbsgroup7.DataSource.EmployeeUpdatesRepository;
import com.sbs.sbsgroup7.DataSource.SystemLogRepository;
import com.sbs.sbsgroup7.DataSource.UserRepository;
import com.sbs.sbsgroup7.dao.UserDaoInterface;
import com.sbs.sbsgroup7.errors.PhoneUsedException;
import com.sbs.sbsgroup7.errors.RoleException;
import com.sbs.sbsgroup7.errors.SsnUsedException;
import com.sbs.sbsgroup7.model.EmployeeInfo;
import com.sbs.sbsgroup7.model.EmployeeUpdate;
import com.sbs.sbsgroup7.model.SystemLog;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import com.sbs.sbsgroup7.errors.EmailUsedException;

@Repository
public class UserService {

    private final UserDaoInterface userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;


    @Autowired
    private EmployeeUpdatesRepository employeeUpdatesRepository;

    @Autowired
    private SystemLogRepository systemLogRepository;

    @Autowired
    public UserService(@Qualifier("user") UserDaoInterface userDao) {
        this.userDao = userDao;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
//        u.setPassword(user.getPassword());
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        u.setSsn(user.getSsn());
        u.setDob(user.getDob());
        u.setAddress(user.getAddress());
        u.setActive(true);
        userRepository.save(u);
        if(user.getRole().equals("USER") || user.getRole().equals("MERCHANT"))
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
        userDao.persist(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
    public User findByUserId(User user){ return userRepository.findByUserId(user.getUserId());}

    public void delete(String username) {
        User user = userDao.findByUsername(username);
        userDao.delete(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void deleteAll() {
        userDao.deleteAll();
    }

    public void requestProfileUpdates(User user, EmployeeInfo employeeInfo) {
        EmployeeUpdate employeeUpdate = new EmployeeUpdate(user.getUserId(), employeeInfo.getEmail(), employeeInfo.getPhone(), employeeInfo.getSsn(), employeeInfo.getAddress(), new Date());
        employeeUpdatesRepository.save(employeeUpdate);

        SystemLog systemLog=new SystemLog();
        systemLog.setMessage(user.getEmail() + " requested for the saving of his/her profile updates");
        systemLog.setTimestamp(new Date());
        systemLogRepository.save(systemLog);
    }

    public void approveProfileUpdates(String userId) {
        EmployeeUpdate employeeUpdate = employeeUpdatesRepository.findByUserId(userId);
        User user = userRepository.findByUserId(userId);
        user.setEmail(employeeUpdate.getEmail());
        user.setPhone(employeeUpdate.getPhone());
        user.setSsn(employeeUpdate.getSsn());
        user.setAddress(employeeUpdate.getAddress());
        userRepository.save(user);

        SystemLog systemLog=new SystemLog();
        systemLog.setMessage(getLoggedUser() + " approved " + user.getEmail() + "'s profile updates request");
        systemLog.setTimestamp(new Date());
        systemLogRepository.save(systemLog);
    }

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
        sameUser.setEmail(user.getEmail());
        sameUser.setAddress(user.getAddress());
        sameUser.setPhone(user.getPhone());
        sameUser.setSsn(user.getSsn());
        System.out.println("Hi" +sameUser.getUserId());
        userRepository.save(sameUser);
        return sameUser;
    }
}