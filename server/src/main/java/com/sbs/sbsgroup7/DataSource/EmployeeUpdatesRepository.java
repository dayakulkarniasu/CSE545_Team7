package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.EmployeeUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeUpdatesRepository extends JpaRepository<EmployeeUpdate, String> {
    EmployeeUpdate findByUserId(String id);
}
