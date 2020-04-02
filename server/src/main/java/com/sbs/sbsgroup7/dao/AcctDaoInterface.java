package com.sbs.sbsgroup7.dao;

import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.User;

import java.io.Serializable;
import java.util.List;

public interface AcctDaoInterface<T, Id extends Serializable> {
    public List<Account> findAll();

    public void deleteByAccountNumber(long accountNumber);

    public void editByAccountNumber(long accountNumber, String accountType);

}