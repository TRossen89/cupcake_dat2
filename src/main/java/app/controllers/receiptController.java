package app.controllers;

import java.util.Calendar;

public class receiptController {
    public receiptController() {

        Calendar currentTime = Calendar.getInstance();

        String timeReady = (Calendar.HOUR_OF_DAY) +":" + currentTime.get(Calendar.MINUTE);
        String hours = String.valueOf(currentTime.get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(currentTime.get(Calendar.MINUTE));

    }
}

