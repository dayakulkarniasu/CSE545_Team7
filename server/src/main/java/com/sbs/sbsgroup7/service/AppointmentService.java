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

    public AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    public void createAppointment(User user, Appointment appointment){
        Appointment app = new Appointment();
        app.setContactWay(appointment.getContactWay());
        app.setDescription(appointment.getDescription());
        app.setTitle(appointment.getTitle());
        app.setUser(user);
        app.setStartTime(appointment.getStartTime());
        app.setEndTime(appointment.getEndTime());
        System.out.println(app.getDescription());
        System.out.println(app.getTitle());
        System.out.println(app.getContactWay());
        System.out.println(app.getStartTime());
        System.out.println(app.getStartTime());
        appointmentRepository.save(app);
    }
}