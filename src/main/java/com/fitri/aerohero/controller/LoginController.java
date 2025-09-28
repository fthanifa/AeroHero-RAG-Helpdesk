package com.fitri.aerohero.controller;

import com.fitri.aerohero.models.*;
import com.fitri.aerohero.utils.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.fitri.aerohero.utils.LoginException;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final UserManager userManager = new UserManager();
    
    
    //handles login logic when the user clicks the "Login" button. 
    //authenticates whether the user is registered or not
    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        errorLabel.setVisible(false);

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in both fields.");
            errorLabel.setVisible(true);
            return;
        }

        try {
            User user = userManager.login(email, password);
            UserSession.setCurrentUser(user);

            // Load profile page after login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Profile.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1280, 800);
            UIManager.setup(scene); // Apply CSS/fonts

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("AeroHero - Profile");
            stage.show();

        } catch (LoginException e) {
            errorLabel.setText("Invalid email or password.");
            errorLabel.setVisible(true);
        } catch (Exception e) {
            errorLabel.setText("Something went wrong.");
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }

    
    //loads register page when the user clicks "Go To Register"
    @FXML
    private void handleGoToRegister() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));
            Scene scene = new Scene(root, 1280, 800);
            UIManager.setup(scene); // Apply styling
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("AeroHero - Register");
            stage.show();
        } catch (Exception e) {
            errorLabel.setText("Unable to load register page.");
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }

    
}