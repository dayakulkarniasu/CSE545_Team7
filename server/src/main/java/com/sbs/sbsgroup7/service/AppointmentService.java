package com.sbs.sbsgroup7.service;

import com.sbs.sbsgroup7.DataSource.AppointmentRepository;
import com.sbs.sbsgroup7.model.Appointment;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public void createAppointment(User user, Appointment appointment){
        Appointment app = new Appointment();
        app.setContactWay("InPerson");
        app.setDescription(appointment.getDescription());
        app.setTitle(appointment.getTitle());
        app.setUser(user);
        appointmentRepository.save(app);
    }
}
