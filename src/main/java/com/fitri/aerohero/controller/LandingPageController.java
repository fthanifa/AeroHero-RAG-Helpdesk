package com.fitri.aerohero.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.fitri.aerohero.utils.UIManager; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class LandingPageController {
    
    // large airplane image in the center of the home page
    @FXML
private ImageView heroImage;
    
    
    //sets the image for the hero banner using the aero.png file.
    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResource("/images/aero.png").toExternalForm());
        heroImage.setImage(image);
    }
    
    //takes users to tickets when button is clicked
    @FXML
    private void handleTicketClick(ActionEvent event) {
        UIManager.switchScene(((Button) event.getSource()).getScene().getWindow(),
            "/views/PassengerView.fxml", "AeroHero - Tickets");
    }

    //takes users to tracking page when button is clicked 
    @FXML
    private void handleTrackClick(ActionEvent event) {
        UIManager.switchScene(((Button) event.getSource()).getScene().getWindow(),
            "/views/TrackingView.fxml", "AeroHero - Track");
    }

    //takes users to chat page when button is clicked
    @FXML
    private void handleChatClick(ActionEvent event) {
        UIManager.switchScene(((Button) event.getSource()).getScene().getWindow(),
            "/views/chat.fxml", "AeroHero - Chat");
    }

}