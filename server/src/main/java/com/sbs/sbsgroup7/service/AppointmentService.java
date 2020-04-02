package com.sbs.sbsgroup7.service;

import com.sbs.sbsgroup7.DataSource.AppointmentRepository;
import com.sbs.sbsgroup7.dao.AcctDaoInterface;
import com.sbs.sbsgroup7.dao.UserDaoInterface;
import com.sbs.sbsgroup7.model.Appointment;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AppointmentService {

//    private final AcctDaoInterface acctDao;
//    private final UserDaoInterface userDao;

    @Autowired
    private AppointmentRepository appointmentRepository;

    //    public AppointmentService(@Qualifier("user") UserDaoInterface userDao, @Qualifier("account") AcctDaoInterface acctDao) {
//        this.userDao = userDao;
//        this.acctDao = acctDao;
//    }
    public AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public void createAppointment(User user, Appointment appointment){
        Appointment app = new Appointment();
        app.setContactWay(appointment.getContactWay());
        app.setDescription(appointment.getDescription());
        app.setTitle(appointment.getTitle());
        app.setUser(user);
        //Date date = new Date();
        app.setStartTime(appointment.getStartTime());
        app.setEndTime(appointment.getEndTime());
        System.out.println(app.getDescription());
        System.out.println(app.getTitle());
//        System.out.println(app.getUserId());
        //System.out.println(app.getAppId());
        System.out.println(app.getContactWay());
        System.out.println(app.getStartTime());
        System.out.println(app.getStartTime());
        //appointmentRepository.save(app);
        appointmentRepository.save(app);
    }
}