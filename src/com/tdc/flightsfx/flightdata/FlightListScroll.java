/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tdc.flightsfx.flightdata;

import com.tdc.flightsfx.ui.FXMLDocumentController;
import javafx.scene.control.TableView;

/**
 * 
 * @author TDC
 */
public class FlightListScroll {
    
    FXMLDocumentController doc;
    TableView <FlightInfo> view;

    
    public FlightListScroll (FXMLDocumentController doc, TableView<FlightInfo> view){
        
        this.doc = doc;
        this.view = view;
    }
    
    
    public void scrollFlightList(){
        
      
    }
    
    
}
