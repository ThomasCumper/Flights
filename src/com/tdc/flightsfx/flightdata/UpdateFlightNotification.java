package com.tdc.flightsfx.flightdata;

import com.tdc.flightsfx.ui.FXMLDocumentController;
import javafx.scene.control.TableView;

/**
 *
 * @author TDC
 */
public class UpdateFlightNotification extends Thread {

    TableView<FlightInfo> view;
    FXMLDocumentController doc;

    public UpdateFlightNotification(FXMLDocumentController doc, TableView<FlightInfo> view) {

        this.doc = doc;
        this.view = view;
    }

    public synchronized void run() {

        int check = -1;
        while (!isInterrupted()) {

            for (int i = 0; i <= doc.getRowCount(); i++) {
                System.out.println(i);
                if (i>doc.getRowCount())
                    i=0;
                  
            try {
                    view.getSelectionModel().select(i);
               
                    if (checkStatus(view.getSelectionModel().getSelectedItem().getStatus()) && i!=check) {   
                            doc.updateNotification(view.getSelectionModel().getSelectedItem().getFlightID() + " to " + view.getSelectionModel().getSelectedItem().getDestination() + " " + view.getSelectionModel().getSelectedItem().getStatus()); 
                           Thread.sleep(6000);
                            check = i;
                     }
                } catch (NullPointerException ex) {
                    System.out.println("NullPointer - UFN Thread: "+ex.getMessage());
                }catch (InterruptedException ex) {
                    System.out.println("InterruptedException - UFN Thread: "+ex.getMessage());
                }

        }

    }
 }
    private boolean checkStatus(String status) {

        boolean displayNotification = false;

        if (status.startsWith("Boarding")) {
            displayNotification = true;
            doc.setUpdateNotificationStyle("-fx-background-color: Blue;");
        }

        if (status.endsWith("Closing")) {
            displayNotification = true;
            doc.setUpdateNotificationStyle("-fx-background-color: rgb(145,0,245);");
        }
        if (status.contains("Closed")) {
            displayNotification = true;
            doc.setUpdateNotificationStyle("-fx-background-color: rgb(196,0,0);");
        }
        if (status.endsWith("Departed")) {
            displayNotification = true;
            doc.setUpdateNotificationStyle("-fx-background-color: Green;");
        }

        return displayNotification;
    }

}
