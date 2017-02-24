package com.tdc.flightsfx.flight;

import flightsfx.FXMLDocumentController;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author TDC
 */
public class FlightInfo {
    
    private final String airlinePrefix;
    private final Date flightDateTime;
    private final SimpleStringProperty flightID;
    private final SimpleStringProperty destination;
    private final SimpleStringProperty flightTime;
    private final SimpleStringProperty status = new SimpleStringProperty("Null");
    private final int gate;
    private final Image img;
    private ImageView imageView;
    
    FXMLDocumentController controller;

    public FlightInfo(String flightID, String destination, String airlinePrefix, InputStream airlineLogo, String flightTime, Date flightDateTime, int gate, FXMLDocumentController controller) throws IOException, SQLException{
        
        this.flightID = new SimpleStringProperty(flightID);
        this.destination = new SimpleStringProperty(destination);
        this.flightTime = new SimpleStringProperty(flightTime);
        this.flightDateTime = flightDateTime;
        this.gate = gate;
        this.airlinePrefix = airlinePrefix;
        this.img = new Image(airlineLogo);
        this.controller = controller;
    }

    public String getFlightID() {

        return airlinePrefix + flightID.get();
    }

    public String getDestination() {

        return destination.get();
    }
    
    public ImageView getAirlineLogo (){
        
        return imageView = new ImageView (img);
        
    }

    public String getFlightTime() {

        return flightTime.get();
    }
    
    public Date getFlightDateTime(){
        
        return flightDateTime;
    }
    
    private int getGate(){
        
        return gate;
    }

    public String getStatus() throws SQLException {

        try {

            CheckFlightStatus checkStatus = new CheckFlightStatus(flightTime.get(),flightDateTime,getGate());
            status.set(checkStatus.updateStatus());

        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return status.get();
    }

}
