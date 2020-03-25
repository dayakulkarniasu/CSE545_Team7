package com.sbs.sbsgroup7.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sbs.sbsgroup7.DataSource.UserRepository;

import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("user")
public class UserDao implements UserDaoInterface<User, String> {

    @Autowired
    private UserRepository userRepository;



    @Override
    public void saveUpdate(User user) {
        Optional<User> us=userRepository.findByUserId(user.getUserId());
        if(us.isPresent()){
            userRepository.save(user);
        }
        else{
            User u=new User();
            u.setUserId(user.getUserId());
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setEmail(user.getEmail());
            u.setPhone(user.getPhone());
            u.setUserName(user.getUsername());
            u.setPassword(user.getPassword());
            u.setSsn(user.getSsn());
            u.setDob(user.getDob());
            u.setAddress(user.getAddress());
            u.setActive(user.getActive());
            userRepository.save(u);

        }

    }


    @Override
    public void delete(int userId) {
        Optional<User> user=userRepository.findByUserId(userId);
//        if(user.isPresent()){
//            userRepository.delete(user);}

    }

    @Override
    public User get(int exUserId) {
        return null;
    }

    @Override
    public List<User> list() {
        return null;
    }

    @Override
    public void getOutstandingPayment(int customerAccountNumber) {

    }

    @Override
    public User ifExists(String mail) {
        return null;
    }

    @Override
    public User get(String username) {
        return null;
    }

    @Override
    public void saveOrUpdate(User externalUser, String userID) {

    }

    @Override
    public void update_password(String password, String email) {

    }

    @Override
    public List<String> ifnewRegisteredUserExists(String mail) {
        return null;
    }

    @Override
    public String getexternID(String username) {
        return null;
    }

    @Override
    public void persist(User userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void update(User userEntity) {
        Optional<User> foundUser = userRepository.findByUserId(userEntity.getUserId());
        if(foundUser.isPresent()){
            userRepository.save(userEntity);
        }

    }

    @Override
    public User findByUserId(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(User userEntity) {
        userRepository.delete(userEntity);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        Iterable<User> it = userRepository.findAll();
        it.forEach(users::add);
        return users;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}