package com.sbs.sbsgroup7.service;

import java.util.List;

import com.sbs.sbsgroup7.DataSource.RequestRepository;
import com.sbs.sbsgroup7.dao.RequestDaoInterface;
import com.sbs.sbsgroup7.dao.UserDaoInterface;
import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.Request;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
public class RequestService {

    private final UserDaoInterface userDao;
    private final RequestDaoInterface requestDao;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    public RequestService(@Qualifier("user") UserDaoInterface userDao, @Qualifier("request")RequestDaoInterface requestDao) {
        this.userDao = userDao;
        this.requestDao = requestDao;
    }

    public Request createRequest(User requestedUser, Request request){
        Request r = new Request();
        r.setStatus("Pending");
        r.setDescription("null");
        r.setRequestType(request.getRequestType());
        r.setApprovedUser("null");
        r.setRequestedUser(requestedUser.getUserId());
        r.setSourceAccount("null");

        requestRepository.save(r);
        return r;
    }

    public List<Account> findAll() {
        return requestDao.findAll();
    }
}