package com.sbs.sbsgroup7.dao;

import com.sbs.sbsgroup7.model.Request;

import java.io.Serializable;
import java.util.List;

public interface RequestDaoInterface<T, Id extends Serializable> {

    public void save(T requestEntity);

    public List<Request> findAll();

}