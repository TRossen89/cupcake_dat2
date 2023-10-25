package app.controllers;

import org.w3c.dom.ls.LSOutput;

import java.sql.Timestamp;
import java.util.Date;

public class timeController {
    public timeController() {

        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }
}
