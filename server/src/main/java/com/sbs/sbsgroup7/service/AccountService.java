package com.sbs.sbsgroup7.service;

import java.util.List;

import com.sbs.sbsgroup7.DataSource.AcctRepository;
import com.sbs.sbsgroup7.DataSource.UserRepository;
import com.sbs.sbsgroup7.dao.AcctDaoInterface;
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
public class AccountService {

    private final AcctDaoInterface acctDao;
    private final UserDaoInterface userDao;

    @Autowired
    private AcctRepository acctRepository;


    @Autowired
    public AccountService(@Qualifier("user") UserDaoInterface userDao, @Qualifier("account") AcctDaoInterface acctDao) {
        this.userDao = userDao;
        this.acctDao = acctDao;
    }

    public Account createAccount(User user,  Account account){
        Account a = new Account();
        a.setBalance(100.00);
        a.setAccountType(account.getAccountType());
        a.setUserId(user.getUserId());

        acctRepository.save(a);
        return a;
    }

    public List<Account> findAll() {
        return acctDao.findAll();
    }

}