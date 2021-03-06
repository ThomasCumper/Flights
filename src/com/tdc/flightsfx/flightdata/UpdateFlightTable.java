package com.tdc.flightsfx.flightdata;

import com.tdc.flightsfx.connect.DbConnection;
import com.tdc.flightsfx.ui.FXMLDocumentController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author TDC
 */
//
public class UpdateFlightTable {

    private final Properties prop = new Properties();
    private FileInputStream in = null;
    
    private DbConnection connection = null;
    private String url,username,password;

    private CallableStatement stmt = null;
    private ResultSet rs = null;
    
    {
        try {
          //  in = new FileInputStream("properties/flightFX.properties");
              in = new FileInputStream("c:/properties/flightFX.properties");
            prop.load(in);
            in.close();
            
            url = prop.getProperty("jdbc.url");
            username = prop.getProperty("jdbc.username");
            password = prop.getProperty("jdbc.password");
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(UpdateFlightTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private final ObservableList <FlightInfo> flightList = FXCollections.observableArrayList();

    /* Gets data from SQL database and populates into ObservableList */
    public void getTableData(FXMLDocumentController controller){
        
        connection = new DbConnection(url,username,password);
       try(Connection con = connection.connect()){

        stmt = con.prepareCall("{CALL `Flights`.`sp_Select_All_Flights`}");
        stmt.execute();
        rs = stmt.getResultSet();
        while (rs.next()) {

            /*Populate Observable List*/
            flightList.add(new FlightInfo(
                    rs.getString("flight_id"),
                    rs.getString("destination"),
                    rs.getString("airline_Name"),
                    rs.getBinaryStream("logo"),
                    rs.getString("flight_time"),
                    rs.getDate("flight_date_time"),
                    rs.getInt("gate"),
                    controller
            ));
        }
       }catch(SQLException | IOException ex){
           System.out.println(ex.getClass().getSimpleName()+" - "+ex.getMessage());
       }
             
        controller.populateTable(flightList);
    }
    
    public ObservableList getFlightList(){
        
       return flightList;
       
    }

}
