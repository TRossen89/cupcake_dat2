package app.controllers;

import org.w3c.dom.ls.LSOutput;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class timeController {
    public timeController() {

        Calendar currentTime = Calendar.getInstance();

        System.out.println(currentTime.get(Calendar.HOUR_OF_DAY) +":" + currentTime.get(Calendar.MINUTE));
    }
}
