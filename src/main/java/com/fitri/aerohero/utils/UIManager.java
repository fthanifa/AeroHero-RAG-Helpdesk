package com.fitri.aerohero.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UIManager {

    //load custom fonts and apply global CSS
    public static void setup(Scene scene) {
        Font.loadFont(UIManager.class.getResourceAsStream("/fonts/Poppins-Regular.ttf"), 12);
        Font.loadFont(UIManager.class.getResourceAsStream("/fonts/Poppins-Bold.ttf"), 12);
        Font.loadFont(UIManager.class.getResourceAsStream("/fonts/Poppins-ExtraBold.ttf"), 12);

        //apply main stylesheet
        scene.getStylesheets().add(UIManager.class.getResource("/css/style.css").toExternalForm());
    }

    //main method to switch scenes from one fxml to another 
    public static void switchScene(Stage stage, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(UIManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1280, 800);
            setup(scene); // Apply fonts + CSS

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            System.err.println("Failed to switch to scene: " + fxmlPath);
            e.printStackTrace();
        }
    }

    //fallback version when passed a Window
    public static void switchScene(Window window, String fxmlPath, String title) {
        if (window instanceof Stage) {
            switchScene((Stage) window, fxmlPath, title);
        } else {
            System.err.println("Window is not a Stage");
        }
    }
}