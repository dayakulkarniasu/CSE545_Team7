package com.sbs.sbsgroup7.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sbs.sbsgroup7.DataSource.AcctRepository;
import com.sbs.sbsgroup7.DataSource.UserRepository;

import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("account")
public class AcctDao implements AcctDaoInterface<Account, String> {

    @Autowired
    private AcctRepository acctRepository;
//
//    @Override
//    public Account findById(String id) {
//        return acctRepository.findById(id).orElse(null);
//    }
//
//
//    @Override
//    public void save(Account accountEntity) {
//        acctRepository.save(accountEntity);
//    }
//
    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<Account>();
        Iterable<Account> it = acctRepository.findAll();
        it.forEach(accounts::add);
        return accounts;
    }
}