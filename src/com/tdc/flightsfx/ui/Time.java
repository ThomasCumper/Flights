package com.tdc.flightsfx.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

/**
 *
 * @author TDC
 */
public class Time {

    Date date = null;
    DateFormat df = new SimpleDateFormat("dd MMM  HH:mm");
    Timer timer = new Timer();

    public void getCurrentTime(FXMLDocumentController controller) {
        
        //updates GUI time label
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
                    date = new Date();
                    controller.updateTimeLabel(df.format(date));
             
                });
            }
        }, 0, 1000);
        
        

        
        
        
        
        
        
        
        

    }

}
