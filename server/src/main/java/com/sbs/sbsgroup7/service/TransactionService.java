package com.sbs.sbsgroup7.service;

import com.sbs.sbsgroup7.DataSource.TransRepository;
import com.sbs.sbsgroup7.DataSource.UserRepository;
import com.sbs.sbsgroup7.dao.AcctDaoInterface;
import com.sbs.sbsgroup7.dao.TransDaoInterface;
import com.sbs.sbsgroup7.dao.UserDaoInterface;
import com.sbs.sbsgroup7.model.Transaction;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final UserDaoInterface userDao;
    private final AcctDaoInterface acctDao;
    private final TransDaoInterface transDao;

    @Autowired
    private TransRepository transRepository;


    @Autowired
    public TransactionService(@Qualifier("user") UserDaoInterface userDao, @Qualifier("account") AcctDaoInterface acctDao, @Qualifier("transaction") TransDaoInterface transDao) {
        this.userDao = userDao;
        this.acctDao = acctDao;
        this.transDao = transDao;
    }

    public Transaction createTransaction(Transaction transaction){
        Transaction t = new Transaction();
        t.setSrcAcct(transaction.getSrcAcct());
        t.setDstAcct(transaction.getDstAcct());
        t.setAmount(transaction.getAmount());
        t.setCommitTime(1);
        t.setCreatedTime(2);
        t.setStatus("complete");
        t.setDescription("fund transfer");
        transRepository.save(t);
        return t;
    }

}