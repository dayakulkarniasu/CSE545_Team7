package com.sbs.sbsgroup7.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.sbs.sbsgroup7.DataSource.AcctRepository;
import com.sbs.sbsgroup7.DataSource.ChequeRepository;
import com.sbs.sbsgroup7.DataSource.TransRepository;
import com.sbs.sbsgroup7.DataSource.UserRepository;
import com.sbs.sbsgroup7.dao.AcctDaoInterface;
import com.sbs.sbsgroup7.dao.UserDaoInterface;
import com.sbs.sbsgroup7.errors.PhoneUsedException;
import com.sbs.sbsgroup7.errors.RoleException;
import com.sbs.sbsgroup7.errors.SsnUsedException;
import com.sbs.sbsgroup7.model.*;
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
    private UserRepository userRepository;

    @Autowired
    private TransRepository transRepository;

    @Autowired
    private ChequeRepository chequeRepository;


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
        //should have a check for if that account already exists

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
                    if (creditDebit.getTransferType().equals("CREDIT"))
                        creditAmount(useracct, creditDebit.getAmount(), useracct, user);
                    else if (creditDebit.getTransferType().equals("DEBIT"))
                        debitAmount(useracct, creditDebit.getAmount(), useracct, user);
                    return;

            }

        }
        throw new Exception("Account is invalid");

    }

    public void transferFunds(User user, TransactionPage transactionPage) throws Exception{
        List<Account> useraccts = acctRepository.findByUser(user);
        Account targetAcc=acctRepository.findByAccountNumber(transactionPage.getToAcc());
        if(targetAcc==null){
            throw new Exception("Target account is invalid");
        }
        for(Account useracct : useraccts){
            if(useracct.getAccountNumber()==transactionPage.getFromAcc()){
                debitTransfers(useracct,transactionPage.getAmount(), targetAcc, user);
                return;
            }

        }
        throw new Exception("Source Account is invalid");

    }
    public void emailTransfer(User user, EmailPage emailPage) throws Exception{
        List<Account> useraccts = acctRepository.findByUser(user);
        User targetuser= userRepository.findByEmail(emailPage.getEmailId()).orElse(null);
        if(targetuser==null){
//            System.out.println("/////////////////////");
            throw new Exception("Target email is invalid");
        }
        Account targetAcc=acctRepository.findOneByUser(targetuser);
        for(Account useracct : useraccts){
            if(useracct.getAccountNumber()==emailPage.getFromAcc()){
//                System.out.println("123456789");
                debitTransfers(useracct,emailPage.getAmount(), targetAcc, user);
                return;
            }

        }
//        System.out.println(".............");
        throw new Exception("Source Account is invalid");

    }


    public void creditAmount(Account source, double amount, Account destination,User user) throws Exception{
        try{
            double balance=source.getBalance();
            if(amount<1000){
                Transaction transaction=new Transaction();
                transaction.setAmount(amount);
                transaction.setFromAccount(source);
                transaction.setToAccount(destination);
                transaction.setTransactionStatus("approved");
                transaction.setTransactionOwner(user);
                transaction.setTransactionTime(Instant.now());
                transaction.setTransactionType("credit");
                transaction.setModifiedTime(Instant.now());
                transRepository.save(transaction);
                source.setBalance(balance+amount);
                acctRepository.save(source);
            }
            else{
                Transaction transaction=new Transaction();
                transaction.setAmount(amount);
                transaction.setFromAccount(source);
                transaction.setToAccount(destination);
                transaction.setTransactionStatus("pending");
                transaction.setTransactionOwner(user);
                transaction.setTransactionTime(Instant.now());
                transaction.setTransactionType("credit");
                transRepository.save(transaction);

            }
        }
        catch(Exception e){
            throw new Exception("Credit transaction failed to account "+source.getAccountNumber(),e);
        }
    }

    public void debitAmount(Account source, double amount, Account destination,User user) throws Exception{
        try{
            double balance=source.getBalance();
            if(balance<amount) {

                throw new Exception("Insufficient funds to debit");

            }
            if(amount<1000){
                Transaction transaction=new Transaction();
                transaction.setAmount(amount);
                transaction.setFromAccount(source);
                transaction.setToAccount(destination);
                transaction.setTransactionStatus("approved");
                transaction.setTransactionOwner(user);
                transaction.setTransactionTime(Instant.now());
                transaction.setTransactionType("debit");
                transaction.setModifiedTime(Instant.now());
                transRepository.save(transaction);
                source.setBalance(balance-amount);
                acctRepository.save(source);

            }
            else{
                Transaction transaction=new Transaction();
                transaction.setAmount(amount);
                transaction.setFromAccount(source);
                transaction.setToAccount(destination);
                transaction.setTransactionStatus("pending");
                transaction.setTransactionOwner(user);
                transaction.setTransactionTime(Instant.now());
                transaction.setTransactionType("debit");
                transRepository.save(transaction);
            }
        }catch(Exception e){
            throw new Exception("Debit transaction failed from account "+source.getAccountNumber(),e);
        }
    }


    public void debitTransfers(Account source, double amount, Account destination,User user) throws Exception{
        try{
            double balance=source.getBalance();
            if(balance<amount){
//                System.out.println("Insufficient funds to debit");
                throw new Exception("Insufficient funds to debit");}
            if(amount<1000){
                Transaction transaction=new Transaction();
                transaction.setAmount(amount);
                transaction.setFromAccount(source);
                transaction.setToAccount(destination);
                transaction.setTransactionStatus("approved");
                transaction.setTransactionOwner(user);
                transaction.setTransactionTime(Instant.now());
                transaction.setTransactionType("transferfunds");
                transaction.setModifiedTime(Instant.now());
                transRepository.save(transaction);
//                System.out.println("transaction done");
                source.setBalance(balance-amount);
                destination.setBalance(destination.getBalance()+amount);
                acctRepository.save(source);
                acctRepository.save(destination);
//                System.out.println("email transfer done");
            }
            else{
                Transaction transaction=new Transaction();
                transaction.setAmount(amount);
                transaction.setFromAccount(source);
                transaction.setToAccount(destination);
                transaction.setTransactionStatus("pending");
                transaction.setTransactionOwner(user);
                transaction.setTransactionTime(Instant.now());
                transaction.setTransactionType("transferfunds");
                transRepository.save(transaction);
//                System.out.println("transfer request done");

            }
        }catch(Exception e){
            throw new Exception("Debit transaction failed from account "+source.getAccountNumber(),e);
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

    public List<Transaction> findAllTransactions(){
        return transRepository.findAll();

    }


    public List<Transaction> findPendingTransactions(){
        return transRepository.findByTransactionStatus("pending");
    }

    public Transaction findByTransactionId(Long id){
        return transRepository.findByTransactionId(id);
    }

    public List<Cheque> findChequeByAccount(Account account){
        return chequeRepository.findByAccount(account);
    }


}