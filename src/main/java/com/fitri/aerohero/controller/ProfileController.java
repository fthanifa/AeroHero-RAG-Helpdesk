package com.fitri.aerohero.controller;

import com.fitri.aerohero.models.User;
import com.fitri.aerohero.utils.UserSession;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


//displays user information retrieved from the current session.
public class ProfileController implements Initializable {

    @FXML private Label nameLabel;
    @FXML private Hyperlink emailLink;

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField countryField;

    
    //populates the UI with the current user's data from session.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = UserSession.getCurrentUser();

        if (user != null) {
            nameLabel.setText(user.getName());
            emailLink.setText(user.getEmail());

            //pre-fill form fields with user's stored data
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
            phoneField.setText(user.getPhoneNumber());
            countryField.setText(user.getCountry());
        }
    }
    
}