/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flightsfx;

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
