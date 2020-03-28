package com.sbs.sbsgroup7.service;

import java.util.ArrayList;
import java.util.List;

import com.sbs.sbsgroup7.DataSource.AcctRepository;
import com.sbs.sbsgroup7.DataSource.UserRepository;
import com.sbs.sbsgroup7.dao.AcctDaoInterface;
import com.sbs.sbsgroup7.dao.UserDaoInterface;
import com.sbs.sbsgroup7.errors.PhoneUsedException;
import com.sbs.sbsgroup7.errors.RoleException;
import com.sbs.sbsgroup7.errors.SsnUsedException;
import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.CreditDebit;
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

    public Account createAccount(User user){
        Account a =new Account();
        a.setAccountType("Savings");
        createAccount(user,a);
        return a;
    }


    public Account createAccount(User user,  Account account){
        Account a = new Account();
        a.setBalance(100.00);
        a.setAccountType(account.getAccountType());
        a.setUser(user);

        acctRepository.save(a);
        return a;
    }

    public List<Account> getAllAccounts(){
        List<Account> accts= new ArrayList<>();
        acctRepository.findAll().forEach(accts::add);
        return accts;
    }

    public void creditDebitTransaction(User user, CreditDebit creditDebit) throws Exception{
        List<Account> useraccts = acctRepository.findByUser(user);
        for(Account useracct : useraccts){
            if(useracct.getAccountNumber()==creditDebit.getAccountNumber()){
                if(creditDebit.getTransferType().equals("CREDIT"))
                    creditAmount(useracct, creditDebit.getAmount());
                else if(creditDebit.getTransferType().equals("DEBIT"))
                    debitAmount(useracct,creditDebit.getAmount());
                return;
            }

        }
        throw new Exception("Account is invalid");

    }


    public void creditAmount(Account account, double amount) throws Exception{
        try{
            double balance=account.getBalance();
            account.setBalance(balance+amount);
            acctRepository.save(account);
        }
        catch(Exception e){
            throw new Exception("Credit transaction failed to account "+account.getAccountNumber(),e);
        }
    }

    public void debitAmount(Account account, double amount) throws Exception{
        try{
            double balance=account.getBalance();
            if(balance<amount)
                throw new Exception("Insufficient funds to debit");
            account.setBalance(balance-amount);
            acctRepository.save(account);
        }catch(Exception e){
            throw new Exception("Debit transaction failed from account "+account.getAccountNumber(),e);
        }
    }

    public List<Account> getAccountsByUser(User user){
        return acctRepository.findByUser(user);
    }

    public Account getAccountByAccountNumber(long accountNumber){
        return acctRepository.findByAccountNumber(accountNumber);
    }

    public void updateAccountType(Long accountNumber, String accountType) {
        Account account = getAccountByAccountNumber(accountNumber);
        account.setAccountType(accountType);
    }

    public List<Account> findAll() {
        return acctDao.findAll();
    }

}