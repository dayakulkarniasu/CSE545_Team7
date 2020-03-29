package com.sbs.sbsgroup7.dao;

import java.util.ArrayList;
import java.util.List;

//import com.sbs.sbsgroup7.DataSource.RequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//@Repository("request")
//public class RequestDao implements RequestDaoInterface<Request, String> {
//
//    @Autowired
//    private RequestRepository requestRepository;
//
//    @Override
//    public void save(Request requestEntity) {
//        requestRepository.save(requestEntity);
//    }
//
//    @Override
//    public void delete(Request requestEntity) {
//        requestRepository.delete(requestEntity);
//    }
//
//    @Override
//    public void deleteByRequestId(long requestId) {
//        requestRepository.deleteByRequestId(requestId);
//    }
//
//    @Override
//    public Request findByRequestId(long id) {
//        return requestRepository.findByRequestId(id);
//    }
//
//    @Override
//    public List<Request> findAll() {
//        List<Request> requests = new ArrayList<Request>();
//        Iterable<Request> it = requestRepository.findAll();
//        it.forEach(requests::add);
//        return requests;
//    }
//}