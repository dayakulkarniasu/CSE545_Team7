package com.sbs.sbsgroup7.dao;

import com.sbs.sbsgroup7.model.Account;

import java.util.List;
import java.util.UUID;

public interface AcctDaoInterface {

    List<Account> getAccounts();

    void createAccount(Integer accountNumber, Account account);

//    default void createAccount(Account account){
////        String accountNumber= String.randomUUID();
////        createAccount(accountNumber, account);
//
//    }
}
