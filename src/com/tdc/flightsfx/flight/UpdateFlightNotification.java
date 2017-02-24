/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tdc.flightsfx.flight;
import javafx.scene.control.TableView;

/**
 * 
 * @author TDC
 */
public class UpdateFlightNotification {
    
    TableView<FlightInfo> view;
    

    public void printFlightList(){
     
            view.getSelectionModel().select(0);
            System.out.println(view.getSelectionModel().getSelectedItem().getFlightID());

    }
    
    
    
    

}
