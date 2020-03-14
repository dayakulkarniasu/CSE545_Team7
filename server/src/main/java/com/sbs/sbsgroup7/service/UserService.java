package com.sbs.sbsgroup7.service;

import java.util.List;

import com.sbs.sbsgroup7.dao.AcctDao;
import com.sbs.sbsgroup7.dao.AcctDaoInterface;
import com.sbs.sbsgroup7.dao.UserDaoInterface;
import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserService {

    private final UserDaoInterface userDao;
    private final AcctDaoInterface acctDao;

    @Autowired
    public UserService(@Qualifier("user") UserDaoInterface userDao, @Qualifier("account") AcctDaoInterface acctDao) {
        this.userDao = userDao;
        this.acctDao = acctDao;
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

    public User findById(String id) {
        //userDao.openCurrentSession();
        User user = userDao.findById(id);
        //userDao.closeCurrentSession();
        return user;
    }

    public void delete(String id) {
        //userDao.openCurrentSessionwithTransaction();
        User user = userDao.findById(id);
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

    public void createAccount(Account account){
        acctDao.createAccount(account);
    }

    public List<Account> getAccounts() {
        return acctDao.getAccounts();
    }





}