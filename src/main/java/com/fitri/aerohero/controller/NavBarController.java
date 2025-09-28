package com.fitri.aerohero.controller;

import com.fitri.aerohero.models.User;
import com.fitri.aerohero.utils.UIManager;
import com.fitri.aerohero.utils.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;

public class NavBarController implements javafx.fxml.Initializable {

    @FXML private Button loginLogoutButton;
    @FXML private Button profileButton; 

    private Stage getStageFromEvent(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    
    //checks whether the user is logged in or not and depending on that
    //it displays "Login" or "LogOut" in the NavBar
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = UserSession.getCurrentUser();

        if (user == null) {
            loginLogoutButton.setText("Login");
            loginLogoutButton.setOnAction(this::handleLogin);
            if (profileButton != null) profileButton.setVisible(false);
        } else {
            loginLogoutButton.setText("Logout");
            loginLogoutButton.setOnAction(this::handleLogout);
            if (profileButton != null) profileButton.setVisible(true);
        }
    }

    
    //takes user to login page
    private void handleLogin(ActionEvent event) {
        try {
            UIManager.switchScene(getStageFromEvent(event), "/views/Login.fxml", "AeroHero - Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    //logs users out and takes them back to login page
    private void handleLogout(ActionEvent event) {
        try {
            UserSession.clear();
            UIManager.switchScene(getStageFromEvent(event), "/views/Login.fxml", "AeroHero - Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    //takes users to the appropriate FAQ page depending on whether they are admin or passenger
    @FXML
    private void handleFaqClick(ActionEvent event) {
        try {
            User user = UserSession.getCurrentUser();
            String fxmlPath = (user != null && user.getRole().equalsIgnoreCase("admin"))
                    ? "/views/FaqAdminView.fxml"
                    : "/views/FaqPassengerView.fxml";

            UIManager.switchScene(getStageFromEvent(event), fxmlPath, "AeroHero - FAQs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    //takes users to the appropriate tickets page depending on whether they are admin or passenger
    @FXML
    private void handleTicketsClick(ActionEvent event) {
        try {
            User user = UserSession.getCurrentUser();
            String fxmlPath = (user != null && user.getRole().equalsIgnoreCase("admin"))
                    ? "/views/AdminView.fxml"
                    : "/views/PassengerView.fxml";

            UIManager.switchScene(getStageFromEvent(event), fxmlPath, "AeroHero - Tickets");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    //takes users to trackng page 
    @FXML
    private void handleTrackClick(ActionEvent event) {
        try {
            UIManager.switchScene(getStageFromEvent(event), "/views/TrackingView.fxml", "AeroHero - Track");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    //takes users to AI chatbot page
    @FXML
    private void handleChatClick(ActionEvent event) {
        try {
            UIManager.switchScene(getStageFromEvent(event), "/views/chat.fxml", "AeroHero - Chat");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    //takes users to their profile
    @FXML
    private void handleProfileClick(ActionEvent event) {
        try {
            UIManager.switchScene(getStageFromEvent(event), "/views/Profile.fxml", "AeroHero - Profile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Logo click handler to go to landing page
    @FXML
    private void handleLogoClick(ActionEvent event) {
        try {
            UIManager.switchScene(getStageFromEvent(event), "/views/LandingPage.fxml", "AeroHero - Home");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}