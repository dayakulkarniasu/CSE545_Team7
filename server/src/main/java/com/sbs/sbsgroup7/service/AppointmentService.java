package com.sbs.sbsgroup7.service;

import com.sbs.sbsgroup7.DataSource.AppointmentRepository;
import com.sbs.sbsgroup7.model.Appointment;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    public void createAppointment(User user, Appointment appointment, String selectedDate, String selectedTime) throws ParseException {
        Appointment app = new Appointment();
        app.setContactWay(appointment.getContactWay());
        app.setDescription(appointment.getDescription());
        app.setTitle(appointment.getTitle());
        app.setUser(user);

        System.out.println("SELECTED TIME: " + selectedTime + " AND SELECTED DATE: " + selectedDate);

        //get time from input
        String strTime = selectedTime;

        //parse time to Date
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date time = sdf.parse(strTime);

        //get selected date
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date day = formatter.parse(selectedDate);

        //today's date and selected time
        Date inputDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatter.format(day) + " " + sdf.format(time));

        //current date and time
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDateTime = new Date();
        System.out.println(dateFormatter.format(currentDateTime));

        //if it's currently past office hours, they need to schedule for tomorrow
        Date finalStartDate = inputDate;
        if(currentDateTime.after(inputDate)) {
            Calendar c = Calendar.getInstance();
            c.setTime(inputDate);
            c.add(Calendar.DATE, 1);  // number of days to add
            finalStartDate = c.getTime();
        }

        //parse final start time to Date
        app.setStartTime(finalStartDate);
        System.out.println("START TIME/DATE: " + finalStartDate.toString());

        //end time
        Calendar c = Calendar.getInstance();
        c.setTime(finalStartDate);
        c.add(Calendar.HOUR_OF_DAY, 1);  // next hour
        Date finalEndDate = c.getTime();
        System.out.println("END TIME/DATE: " + finalEndDate.toString());

        app.setEndTime(finalEndDate);
        System.out.println(app.getDescription());
        System.out.println(app.getTitle());
        System.out.println(app.getContactWay());
        System.out.println(app.getStartTime());
        System.out.println(app.getEndTime());
        appointmentRepository.save(app);
    }
}