package com.tdc.flightsfx.ui;

import com.tdc.flightsfx.flightdata.NumberofFlights;
import com.tdc.flightsfx.flightdata.UpdateFlightTable;
import com.tdc.flightsfx.flightdata.FlightInfo;
import com.tdc.flightsfx.flightdata.UpdateFlightNotification;
import com.tdc.flightsfx.flightdata.statusColor;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 *
 * @author TDC
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label lblTime;
    @FXML
    private Label lblPages;
    @FXML
    public ImageView imgAirlines;
    @FXML
    public GridPane gpNotifications;
    @FXML
    private TextField txtNotifications;
    @FXML
    public TableView<FlightInfo> tblFlights;
    @FXML
    private TableColumn<FlightInfo, String> flightID;
    @FXML
    private TableColumn<FlightInfo, String> destination;
    @FXML
    private TableColumn<FlightInfo, Image> airlineLogo;
    @FXML
    private TableColumn<FlightInfo, String> flightTime;
    @FXML
    private TableColumn<FlightInfo, String> status;

    private final FXMLDocumentController doc = this;
    
   
    UpdateFlightNotification udFlightNote;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Timer timer = new Timer();
        UpdateFlightTable flightTT = new UpdateFlightTable();
        udFlightNote = new UpdateFlightNotification(doc,tblFlights);
        
        setColumnWidth();
        try {

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        try {
                            tblFlights.getItems().clear();
                            flightTT.getTableData(doc);
                        } catch (SQLException | ParseException ex) {
                            System.out.println(ex.getMessage());
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IndexOutOfBoundsException ex){
                            System.out.println("IndexOutOfBoundsException : "+ex.getMessage());
                        }
                    });
                }
            }, 0, 16000);

            Time time = new Time();
            time.getCurrentTime(this);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateTimeLabel(String currentTime) {
        
        lblTime.setText(currentTime);
    }

    public synchronized void updateNotification(String notification) {

        fadeInOut();
        txtNotifications.setText(notification);

      }
    
    
    private void fadeInOut(){
        
         SequentialTransition fade = new SequentialTransition();
         FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), txtNotifications);
         FadeTransition stay = new FadeTransition(Duration.millis(12000), txtNotifications);
         FadeTransition fadeOut = new FadeTransition(Duration.millis(2000), txtNotifications);
           fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            stay.setFromValue(1);
            stay.setToValue(1);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
         fade.getChildren().addAll(fadeIn, stay, fadeOut); 
         fade.play();
        
    }

    public void setUpdateNotificationStyle(String Style){
 
         gpNotifications.setStyle(Style);
    
    }

    public void updatePageLabel(String pages) {

        lblPages.setText(pages);
    }
            
    /*Populates Flight tableView*/
    public void populateTable(ObservableList list) {

        flightID.setCellValueFactory(new PropertyValueFactory<>("flightID"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        airlineLogo.setCellValueFactory (new PropertyValueFactory<>("airlineLogo"));
        flightTime.setCellValueFactory(new PropertyValueFactory<>("flightTime"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblFlights.setItems(list);
        updateCellColor();
        getNumberofRows(tblFlights);
        udFlightNote.updateNotification();
    }

    private void setColumnWidth() {

        flightID.prefWidthProperty().bind(tblFlights.widthProperty().divide(6));
        destination.prefWidthProperty().bind(tblFlights.widthProperty().divide(6));
        airlineLogo.prefWidthProperty().bind(tblFlights.widthProperty().divide(6));
        flightTime.prefWidthProperty().bind(tblFlights.widthProperty().divide(6));
        status.prefWidthProperty().bind(tblFlights.widthProperty().divide(3));
    }

    private void updateCellColor() {

        status.setCellFactory(column -> {
            return new TableCell<FlightInfo, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                   statusColor statColor = new statusColor(item);
                   TableRow<FlightInfo> currentRow = getTableRow();
                   currentRow.setStyle(statColor.getCellColor());
                   setText(item);
                }
            };
        });
    }
    
    public int getRowCount(){
        
        int size = tblFlights.getItems().size();
        return size >1 ? size : 0;
    }

    private void getNumberofRows(TableView tblFlights){
        
        NumberofFlights NoOfFlights = new NumberofFlights (tblFlights.getItems().size(),doc);
  
    }

}
