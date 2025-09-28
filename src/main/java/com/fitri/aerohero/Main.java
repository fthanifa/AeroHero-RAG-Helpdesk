package com.fitri.aerohero;

import com.fitri.aerohero.utils.UIManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    // This method is the entry point for JavaFX apps (called automatically when app launches)
    @Override
    public void start(Stage stage) throws Exception {
        //Load the LandingPage.fxml layout file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LandingPage.fxml"));
        Parent root = loader.load();
        
        //Set the scene size
        //1280x800 (fixed window size)
        Scene scene = new Scene(root, 1280, 800);

        //apply global styles (fonts, colours, etc.) using UIManager
        UIManager.setup(scene); // use centralized styling
        
        //set the window title n attach the scene to the stage
        stage.setTitle("AeroHero - Home");
        stage.setScene(scene);
        stage.show(); //finally, show the main window
    }

    // Main method that actually starts the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
