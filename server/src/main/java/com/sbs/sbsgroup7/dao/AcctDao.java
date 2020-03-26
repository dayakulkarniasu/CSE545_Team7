package com.sbs.sbsgroup7.dao;

import com.sbs.sbsgroup7.DataSource.AcctRepository;

import com.sbs.sbsgroup7.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository("account")
public class AcctDao implements AcctDaoInterface{

    @Autowired
    private AcctRepository acctRepository;


    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<Account>();
        Iterable<Account> it = acctRepository.findAll();
        it.forEach(accounts::add);
        return accounts;
    }

//    @Override
    public void createAccount(Integer accountNumber, Account account) {
        acctRepository.save(new Account(accountNumber, account.getAccountType(), account.getBalance()));
    }


}


