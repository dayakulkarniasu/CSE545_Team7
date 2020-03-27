package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, String> {

}
