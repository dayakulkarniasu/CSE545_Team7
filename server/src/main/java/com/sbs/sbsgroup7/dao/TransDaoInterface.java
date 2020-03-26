package com.sbs.sbsgroup7.dao;

import com.sbs.sbsgroup7.model.Transaction;
import com.sbs.sbsgroup7.model.User;

import java.io.Serializable;
import java.util.List;

public interface TransDaoInterface<T, Id extends Serializable> {

    public void persist(T transactionEntity);

    //public void update(T transactionEntity);

    public Transaction findById(Id id);

    //public User findByUsername(String userName);

    public void save(T transactionEntity);

    public void delete(T transactionEntity);

    public List<Transaction> findAll();

    public void deleteAll();

}
