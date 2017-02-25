package flightsfx;

import com.tdc.flightsfx.flight.UpdateFlightTable;
import com.tdc.flightsfx.flight.FlightInfo;
import com.tdc.flightsfx.flight.UpdateFlightNotification;
import com.tdc.flightsfx.flight.statusColor;
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
    private Label lblDebug;
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
    
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Timer timer = new Timer();
        UpdateFlightTable flightTT = new UpdateFlightTable();
        UpdateFlightNotification udFlightNote = new UpdateFlightNotification(doc,tblFlights);
        udFlightNote.start();

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
                        }
                    });
                }
            }, 0, 15000);

            Time time = new Time();
            time.getCurrentTime(this);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateTimeLabel(String currentTime) {
        
        lblTime.setText(currentTime);
    }

    public void updateNotification(String notification) {
        fadeIn();
        txtNotifications.setText(notification);
    }
    
    private void fadeIn(){
        FadeTransition ft = new FadeTransition(Duration.millis(2000), txtNotifications);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setAutoReverse(true);
        ft.setCycleCount(2);
        ft.play(); 
    }
    

    public void setUpdateNotificationStyle(String Style){
        
     gpNotifications.setStyle(Style);
                
    }

    public void updatePageLabel(String pages) {

        lblPages.setText(pages);
    }
    
    public void updateDebugLabel (String debugMsg){
        
        lblDebug.setText(debugMsg);
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
        
        return tblFlights.getItems().size();
    }

    private void getNumberofRows(TableView tblFlights){
        
        NumberofFlights NoOfFlights = new NumberofFlights (tblFlights.getItems().size(),doc);
  
    }

}
