package com.tdc.flightsfx.flightdata;

import com.tdc.flightsfx.ui.FXMLDocumentController;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.TableView;

/**
 *
 * @author TDC
 */
public class UpdateFlightNotification {

    private TableView<FlightInfo> view;
    private FXMLDocumentController doc;
    private Timer timer = new Timer();

    private static int currentRow = 0;

    public UpdateFlightNotification(FXMLDocumentController doc, TableView<FlightInfo> view) {

        this.doc = doc;
        this.view = view;
    }

    public void updateNotification() {

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {

                    if (currentRow > doc.getRowCount()) {
                        currentRow = 0;
                    }
                            
                    try {
                        view.getSelectionModel().select(currentRow);

                        if (checkStatus(view.getSelectionModel().getSelectedItem().getStatus())) {
                                setNotification();
                                currentRow++;
                        } else if(!checkStatus(view.getSelectionModel().getSelectedItem().getStatus()) && currentRow == 0){
                                setBlank();
                                currentRow++;
                        }else{
                            currentRow=0;
                            run();
                        }
                        
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                });
            }
        }, 0, 15000);

    }

    private void setNotification() {

        doc.updateNotification(view.getSelectionModel().getSelectedItem().getFlightID()
                + " to "
                + view.getSelectionModel().getSelectedItem().getDestination()
                + " "
                + view.getSelectionModel().getSelectedItem().getStatus());
    }

    private void setBlank() {

        doc.setUpdateNotificationStyle("-fx-background-color: Green;");
        doc.updateNotification("COSTA COFFEE");
        
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
