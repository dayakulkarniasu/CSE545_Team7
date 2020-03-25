package com.sbs.sbsgroup7.dao;

import com.sbs.sbsgroup7.model.User;

import java.io.Serializable;
import java.util.List;

public interface UserDaoInterface<T, Id extends Serializable> {
    public void saveUpdate(User user);

    public void delete(int userId);

    public User get(int userId );

    public List<User> list();

    public void getOutstandingPayment(int customerAccountNumber);

    public User ifExists(String mail);

    public User get(String username );

    void saveOrUpdate(User user, String userId);

    //public List<String> ifnewRegisteredUserExists(String mail);

    void update_password(String password, String email);

    List<String> ifnewRegisteredUserExists(String mail);

    public String getexternID(String username);

    //public void update_password(String password, String email);




















    public void persist(T userEntity);

    public void update(T userEntity);

    public User findByUserId(Id id);

    public void delete(T userEntity);

    public List<User> findAll();

    public void deleteAll();

}