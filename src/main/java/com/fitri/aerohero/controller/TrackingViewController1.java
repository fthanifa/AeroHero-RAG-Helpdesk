package com.fitri.aerohero.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class TrackingViewController1 {

    @FXML private TextField baggageIdField;
    @FXML private TextField flightNumberField;
    @FXML private Button trackButton;

    @FXML private Label trackingDetailsLabel;
    @FXML private Label infoBaggageId;
    @FXML private Label infoFlightNumber;
    @FXML private Label infoPassengerName;
    @FXML private Label infoFlightStatus;
    @FXML private Label infoBaggageStatus;
    @FXML private Label infoPickup;

    //basic mock "database" stored in memory using a map 
    private final Map<String, Map<String, String>> mockDatabase = new HashMap<>();
    
    // Rotating tip messages (used when button is pressed)
    //hard coded tips and tricks in array that will randomly show per user click of a button
    private final List<String> tips = Arrays.asList(
            "• Tracking updates may lag during peak hours, system refreshes, or when your bag decides to take the scenic route without informing your flight.",
            "• Double-check your flight number before panicking — sometimes it’s just a typo.",
            "• Bags sometimes get lonely and wander. Stay calm and refresh.",
            "• If your baggage is still 'loading' for more than an hour, try turning off and on your patience.",
            "• Flights can change gates last minute. Track frequently to avoid chasing planes."
    );
    
    //random generator to pick a tip from array
    private final Random random = new Random();

    @FXML
    public void initialize() {
        loadMockDataFromFile();  // load .txt file contents
        trackButton.setOnAction(e -> handleTrackFlight());  // bind button click to logic
    }

    //loads flight and baggage data from the track.txt file
    private void loadMockDataFromFile() {
        try {
            // uses absolute path to load from target/classes/data/track.txt so it works even after build
            String basePath = System.getProperty("user.dir");
            File file = Paths.get(basePath, "target", "classes", "data", "track.txt").toFile();

            System.out.println("🧾 Attempting to load from: " + file.getAbsolutePath());

            // if file doesn't exist, just print error and stop
            if (!file.exists()) {
                System.err.println("❌ File still not found at: " + file.getAbsolutePath());
                return;
            }

            // read each line and build the mockDatabase map
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("📄 " + line);
                    String[] parts = line.split(",", -1);
                    if (parts.length >= 6) {
                        String flightNum = parts[0].trim().toUpperCase();
                        Map<String, String> details = new HashMap<>();
                        details.put("baggageId", parts[1].trim());
                        details.put("passengerName", parts[2].trim());
                        details.put("flightStatus", parts[3].trim());
                        details.put("baggageStatus", parts[4].trim());
                        details.put("pickup", parts[5].trim());
                        mockDatabase.put(flightNum, details); // use flight num as key
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // print error if anything fails
        }
    }

    //handler method, triggered when "Track Now" button is clicked
    private void handleTrackFlight() {
         // get user input and convert to uppercase for consistency
        String flightNumber = flightNumberField.getText().trim().toUpperCase();
        String baggageId = baggageIdField.getText().trim().toUpperCase();

        // show a random fun/helpful tip at the top every time user tracks something
        String randomTip = tips.get(random.nextInt(tips.size()));
        trackingDetailsLabel.setText(randomTip);

        // try to find matching data by either flight number or baggage ID
        Map<String, String> data = null;

        // check if flight number exists in database
        if (!flightNumber.isEmpty() && mockDatabase.containsKey(flightNumber)) {
            data = mockDatabase.get(flightNumber);
        } else if (!baggageId.isEmpty()) {
            // loop through all entries to find matching baggage ID if flight number wasn't used
            for (Map.Entry<String, Map<String, String>> entry : mockDatabase.entrySet()) {
                if (entry.getValue().get("baggageId").equalsIgnoreCase(baggageId)) {
                    data = entry.getValue();
                    flightNumber = entry.getKey(); // update flight number if found by baggage ID
                    break;
                }
            }
        }

        if (data != null) {
            // Found matching data — display details
            infoBaggageId.setText("• Baggage ID: " + data.get("baggageId"));
            infoFlightNumber.setText("• Flight Number: " + flightNumber);
            infoPassengerName.setText("• Passenger Name: " + data.get("passengerName"));
            infoFlightStatus.setText("• Flight Status: " + data.get("flightStatus"));
            infoBaggageStatus.setText("• Baggage Status: " + data.get("baggageStatus"));
            infoPickup.setText("• Estimated Pickup: " + data.get("pickup"));
        } else {
            // No match found — show fallback message
            infoBaggageId.setText("• Baggage ID: Not found");
            infoFlightNumber.setText("• Flight Number: " + (flightNumber.isEmpty() ? "N/A" : flightNumber));
            infoPassengerName.setText("• Passenger Name: Unknown");
            infoFlightStatus.setText("• Flight Status: Not found");
            infoBaggageStatus.setText("• Baggage Status: Not found");
            infoPickup.setText("• Estimated Pickup: N/A");
        }
    }
}
