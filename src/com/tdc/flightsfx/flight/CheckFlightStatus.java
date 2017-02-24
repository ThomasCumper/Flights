package com.tdc.flightsfx.flight;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author TDC
 */
public class CheckFlightStatus {

    private Date currentDate, currentTime, flightDate, flightTime;
    private final DateFormat day = new SimpleDateFormat("dd-MM-yyyy");
    private final DateFormat hourMin = new SimpleDateFormat("HH:mm");

    private long diff = 0L;
    private int gate,diffMins, _MINSINDAY=1440;

    private String status = "";

    public CheckFlightStatus(String flightTime, Date flightDate, int gate) {

        parseTime(flightTime, flightDate);
        this.gate = gate;

    }

    private void parseTime(String flightTime, Date flightDateTime) {
            
        try {
            currentDate = new Date();
            currentTime = hourMin.parse(hourMin.format(currentDate));
            currentDate = day.parse(day.format(currentDate));
          
            this.flightDate = day.parse(day.format(flightDateTime));
            this.flightTime = hourMin.parse(flightTime);
            
            diff = this.flightTime.getTime() - currentTime.getTime();
            diffMins = (int)  TimeUnit.MILLISECONDS.toMinutes(diff); // Takes 30 minutes off difference to account for boarding and gate closure

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public String updateStatus() throws ParseException, SQLException {
        
            // adds day in minutes to value if flight is following day to convert negative value
            if (!flightDate.equals(currentDate))
                diffMins+= _MINSINDAY;
            
            if (diffMins > 30) {
                gateOpeningIn();
            } else if (diffMins <= 30 && diffMins >= 20) {
                boarding();
            } else if (diffMins < 20 && diffMins >= 15) {
                gateClosing();
            } else if (diffMins < 15 && diffMins > 0) {
                gateClosed();
            } else {
                departed();
            }

        return status;
    }
    
    
    private String gateOpeningIn() {
        // Time until Gate closes (30 minutes before departure)
        long diffMinstoGateOpen = (diffMins - 30);
        int hourToGate, minsToGate = (int)diffMinstoGateOpen;

        if (diffMinstoGateOpen > 60) {
            hourToGate = (int) diffMinstoGateOpen / 60;
            minsToGate = (int) diffMinstoGateOpen % 60;

            status = "Gate opening in " + hourToGate + checkHour(hourToGate) + minsToGate + checkMin(minsToGate);
        } else {
            status = "Gate opening in " + minsToGate + checkMin(minsToGate);
        }

        return status;
    }
    
    private String checkHour(int hoursToGate){
        
        String hourPostFix;
        
        if (hoursToGate >1)
            hourPostFix = " Hours ";
        else
           hourPostFix = " Hour ";
 
        return hourPostFix;
    }
    
    private String checkMin(int minsToGate){
        
        String minPostFix;
        
        if (minsToGate <=1){
            minPostFix = " Minute";
        }else{
            minPostFix = " Minutes";
        }
        return minPostFix;
    }

    private String boarding() {

        return status = "Boarding at gate "+gate;
    }

    private String gateClosing() {

        return status = "Gate "+gate+" Closing";
    }

    private String gateClosed() {

        return status = "Gate "+gate+" Closed";
    }

    private String departed() {
        return status = "Departed";
    }

}
