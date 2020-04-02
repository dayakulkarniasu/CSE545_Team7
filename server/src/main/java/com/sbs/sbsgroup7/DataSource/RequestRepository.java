package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RequestRepository extends JpaRepository<Request, String> {
    Request findByRequestId(long requestId);

    @Query(value="delete from Request r where r.requestId = ?1")
    void deleteByRequestId(long requestId);

    List<Request> findByRequestStatus(String requestStatus);

    List<Request> findByRequestStatusAndRequestType(String requestStatus, String requestType);
}

