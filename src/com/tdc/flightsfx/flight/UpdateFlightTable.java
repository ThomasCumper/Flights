package com.tdc.flightsfx.flight;

import com.tdc.flightsfx.connect.Dbconnection;
import flightsfx.FXMLDocumentController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
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
    
    private Dbconnection connection = null;
    private Connection con;
    private String url,username,password;

    private CallableStatement stmt = null;
    private ResultSet rs = null;
    
    {
        try {
            in = new FileInputStream("G:\\Google Drive\\flightFX.properties");
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

    private final ObservableList<FlightInfo> flightList = FXCollections.observableArrayList();

    /* Gets data from SQL database and populates into ObservableList */
    public void getTableData(FXMLDocumentController controller) throws SQLException, ParseException, IOException {
        
        connection = new Dbconnection(url,username,password);
        
        con = connection.connect();

        stmt = con.prepareCall("{CALL `Flights`.`sp_Select_All_Flights`}");
        stmt.execute();
        rs = stmt.getResultSet();
        controller.updateDebugLabel("Updating table");
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

        if (con != null) 
            con.close();
        if (stmt != null) 
            stmt.close();
        if (rs != null) 
            rs.close();
        
        Date date = new Date();
        controller.updateDebugLabel("Table updated: "+date);
        controller.populateTable(flightList);
    }
    
    public ObservableList getFlightList(){
        
       return flightList;
       
    }

}
