package com.tdc.flightsfx.flightdata;

import com.tdc.flightsfx.ui.FXMLDocumentController;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.TableView;

/**
 *
 * @author TDC
 */
public class UpdateFlightNotification {

    private TableView<FlightInfo> view;
    private FXMLDocumentController doc;

    private static int currentRow = 0;
    private static String currentFlight = null;
    
    public UpdateFlightNotification(FXMLDocumentController doc, TableView<FlightInfo> view) {

        this.doc = doc;
        this.view = view;
    }

    
    public void updateNotification(){
        
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                
                if (currentRow > doc.getRowCount()) {
                        currentRow = 0;
                    }
                
                    try {
                        view.getSelectionModel().select(currentRow);

                        if (checkStatus(view.getSelectionModel().getSelectedItem().getStatus())) {
                                setNotification();
                                currentRow++;
                        } else if(!checkStatus(view.getSelectionModel().getSelectedItem().getStatus()) && currentRow == 0){
                                setDefault();
                                currentRow++;
                        }else{
                            currentRow=0;
                            run();
                        }
                        
                    } catch (NullPointerException ex) {
                        setDefault(); // set to default notification if no list exists
                    }

            }
        });
        
    }
  
    private void setNotification() {

        doc.updateNotification(view.getSelectionModel().getSelectedItem().getFlightID()
                + " to "
                + view.getSelectionModel().getSelectedItem().getDestination()
                + " "
                + view.getSelectionModel().getSelectedItem().getStatus());
        
        currentFlight = view.getSelectionModel().getSelectedItem().getFlightID();
    }

    private void setDefault() {
        
        doc.setUpdateNotificationStyle("-fx-background-color: Green;");
        doc.updateNotification("COSTA COFFEE");
        
    }

    private boolean checkStatus(String status) {

        boolean displayNotification = false;

        if (status.startsWith("Boarding")) {
            displayNotification = true;
            doc.setUpdateNotificationStyle("-fx-background-color: rgb(2, 41, 119);");
        }

        if (status.endsWith("Closing")) {
            displayNotification = true;
            doc.setUpdateNotificationStyle("-fx-background-color: rgb(81, 2, 119);");
        }
        if (status.contains("Closed")) {
            displayNotification = true;
            doc.setUpdateNotificationStyle("-fx-background-color: rgb(119, 2, 2);");
        }
        if (status.endsWith("Departed")) {
            displayNotification = true;
            doc.setUpdateNotificationStyle("-fx-background-color: rgb(2, 119, 4);");
        }

        return displayNotification;
    }

}
