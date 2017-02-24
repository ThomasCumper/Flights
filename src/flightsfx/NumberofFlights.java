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
