package com.fitri.aerohero.controller;

import com.fitri.aerohero.models.Passenger;
import com.fitri.aerohero.utils.UIManager;
import com.fitri.aerohero.utils.UserManager;
import com.fitri.aerohero.utils.UserSession;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField phoneField;
    @FXML private TextField countryField;
    @FXML private Label errorLabel;

    private final UserManager userManager = new UserManager();

    
    //triggered when "Register" button is clicked and takes user to register page 
    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();
        String phone = phoneField.getText().trim();
        String country = countryField.getText().trim();

        
        //input validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()
                || phone.isEmpty() || country.isEmpty()) {
            errorLabel.setText("All fields are required.");
            return;
        }

        if (!password.equals(confirm)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        if (userManager.userExists(email)) {
            errorLabel.setText("Email already registered.");
            return;
        }

        try {   //cretes and registers new passenger 
            Passenger newUser = new Passenger(name, email, password, phone, country, "passenger");
            userManager.registerUser(newUser);
            UserSession.setCurrentUser(newUser);

            //loads pprofile page after registering 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Profile.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1280, 800);
            UIManager.setup(scene);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("AeroHero - Profile");
            stage.show();

        } catch (Exception e) {
            errorLabel.setText("Registration failed.");
            e.printStackTrace();
        }
    }

    
    //button which takes users back from register page to login page 
    @FXML
    private void handleGoToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
            Scene scene = new Scene(root, 1280, 800);
            UIManager.setup(scene);
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            errorLabel.setText("Unable to return to login.");
            e.printStackTrace();
        }
    }
}