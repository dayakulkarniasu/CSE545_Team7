
package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.CrudRepository;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
