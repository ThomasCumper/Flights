package com.tdc.flightsfx.flight;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author TDC
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/tdc/flightsfx/ui/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("logo.png")));
        
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        stage.setOnCloseRequest(exit -> System.exit(0));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
