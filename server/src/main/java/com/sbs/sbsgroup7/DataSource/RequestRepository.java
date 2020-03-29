package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.Request;
import com.sbs.sbsgroup7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, String> {

    @Query("DELETE FROM Request WHERE requestId=:requestId")
    void deleteByRequestId(@Param("requestId") long requestId);

    Request findByRequestId(long requestId);
}
