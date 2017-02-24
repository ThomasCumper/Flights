package com.tdc.flightsfx.flight;
import flightsfx.FXMLDocumentController;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 * 
 * @author TDC
 */
public class UpdateFlightNotification extends Thread {
    
    TableView<FlightInfo> view;
    FXMLDocumentController doc;
    
    public UpdateFlightNotification (FXMLDocumentController doc, TableView<FlightInfo> view){
        
        this.doc = doc;
        this.view = view;
    }
    
    public synchronized void run(){
        
        while(!isInterrupted()){
            
          for (int i =0; i<doc.getRowCount();i++){
              
               if (i>=doc.getRowCount())
                 i=0;
              System.out.println(doc.getRowCount()+"-----"+i);
            view.getSelectionModel().select(i);
              try {
                  if(checkStatus(view.getSelectionModel().getSelectedItem().getStatus())){
                      try {
                          doc.updateNotification(view.getSelectionModel().getSelectedItem().getFlightID()+" to "+view.getSelectionModel().getSelectedItem().getDestination()+" "+view.getSelectionModel().getSelectedItem().getStatus());
                          Thread.currentThread().sleep(20000);
                      } catch (InterruptedException | SQLException ex) {
                          System.out.println(ex.getMessage());
                      }       
                  }     } catch (SQLException ex) {
                  Logger.getLogger(UpdateFlightNotification.class.getName()).log(Level.SEVERE, null, ex);
              }
             }
            
        }

    } 
    
  
  private boolean checkStatus(String status){
      
      boolean displayNotification = false;
      
      if (status.startsWith("Boarding")){
          displayNotification = true;
          doc.setUpdateNotificationStyle("-fx-background-color: Blue;" + "-fx-border-width: none;" + "-fx-border-color: transparent;");
      }
              
      if (status.endsWith("Closing")){
          displayNotification = true;
          doc.setUpdateNotificationStyle("-fx-background-color: rgb(145,0,245);" + "-fx-border-width: none;" + "-fx-border-color: transparent;");
      }
      if (status.contains("Closed")){
          displayNotification = true;
           doc.setUpdateNotificationStyle("-fx-background-color: rgb(196,0,0);");  
      }
      if (status.endsWith("Departed")){
          displayNotification = true;
          doc.setUpdateNotificationStyle("-fx-background-color: Green;" + "-fx-border-width: none;" + "-fx-border-color: transparent;");
      }
      
      return displayNotification;
  }


}
