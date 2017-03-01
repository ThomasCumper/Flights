package com.tdc.flightsfx.flightdata;

import com.tdc.flightsfx.ui.FXMLDocumentController;

/**
 * 
 * @author TDC
 */
public class NumberofFlights {
    
    public NumberofFlights (int numberOfFlights, FXMLDocumentController controller){
    
        updatePageLabel(numberOfFlights, controller);
        
    }
    
    
    private void updatePageLabel(int numberOfFlights, FXMLDocumentController controller ){
        
        controller.updatePageLabel("Flights: "+numberOfFlights);
               
    }
            
}
