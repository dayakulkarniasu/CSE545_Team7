package com.sbs.sbsgroup7.dao;

import java.util.ArrayList;
import java.util.List;

import com.sbs.sbsgroup7.DataSource.AcctRepository;
import com.sbs.sbsgroup7.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("account")
public class AcctDao implements AcctDaoInterface<Account, String> {

    @Autowired
    private AcctRepository acctRepository;

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<Account>();
        Iterable<Account> it = acctRepository.findAll();
        it.forEach(accounts::add);
        return accounts;
    }

    @Override
    public void deleteByAccountNumber(long accountNumber) {
        acctRepository.deleteByAccountNumber(accountNumber);
    }

    @Override
    public void editByAccountNumber(long accountNumber, String accountType) {
        acctRepository.editByAccountNumber(accountNumber, accountType);
    }
}