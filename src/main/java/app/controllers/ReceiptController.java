package app.controllers;

import java.util.Calendar;

public class ReceiptController {
    public ReceiptController() {

        Calendar currentTime = Calendar.getInstance();

        String timeReady = currentTime.get(Calendar.HOUR_OF_DAY) +":" + currentTime.get(Calendar.MINUTE);
        String hours = String.valueOf(currentTime.get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(currentTime.get(Calendar.MINUTE));

    }
}

