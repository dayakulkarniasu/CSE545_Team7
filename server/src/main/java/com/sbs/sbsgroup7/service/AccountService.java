package com.sbs.sbsgroup7.service;

import com.sbs.sbsgroup7.DataSource.AcctRepository;
import com.sbs.sbsgroup7.DataSource.RequestRepository;
import com.sbs.sbsgroup7.DataSource.UserRepository;
import com.sbs.sbsgroup7.dao.AcctDaoInterface;
import com.sbs.sbsgroup7.dao.UserDao;
import com.sbs.sbsgroup7.dao.UserDaoInterface;
import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.Request;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AccountService {

    private final UserDaoInterface userDao;
    private final AcctDaoInterface acctDao;

    @Autowired
    private AcctRepository acctRepository;

    @Autowired
    public AccountService(@Qualifier("user") UserDaoInterface userDao, @Qualifier("account") AcctDaoInterface acctDao) {
        this.userDao = userDao;
        this.acctDao = acctDao;
    }

    public Account createAccount(User user, Account account){
        Account a=new Account();
        a.setAccountType(account.getAccountType());
        a.setAccountNumber(123);
        a.setBalance(100.00);
        a.setUser(user);

        acctRepository.save(a);
        return a;
    }
}
