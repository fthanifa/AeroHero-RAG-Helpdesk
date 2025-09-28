package com.fitri.aerohero.controller;

import com.fitri.aerohero.ticket.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.*;

public class TicketPassengerController {
    //UI components linked from FXML
    @FXML private TextField subjectField;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private ComboBox<String> priorityCombo;
    @FXML private TextArea messageArea;
    @FXML private Button submitButton;
    @FXML private VBox ticketList;
    
    //ticket manager instance (singelton)
    private final TicketManager ticketManager = TicketManager.getInstance();
    
    //hard coded passenger name
    private final String passengerName = "Sample Passenger";

    //file path for where tickets are stored
    private final String TICKET_FILE = "src/main/resources/data/tickets.txt";

    //auto run when FXML view is loaded
    @FXML
    public void initialize() {
        System.out.println("PassengerViewController loaded");
        
        //fill dropdowns with available options
        categoryCombo.getItems().addAll(Ticket.CATEGORIES);
        priorityCombo.getItems().addAll(Ticket.PRIORITIES);
        
        //set what happens when submit button is clicked
        submitButton.setOnAction(e -> handleSubmit());

        //load any existing tickets from file and show them
        loadTicketsFromFile();
        renderTicketList();
    }
    
    //method to handle submissions
    private void handleSubmit() {
        
        //validation; to check if all fields are filled
        if (validateForm()) {
            //create a new ticket with form data 
            Ticket ticket = new Ticket(
                    subjectField.getText(),
                    messageArea.getText(),
                    categoryCombo.getValue(),
                    priorityCombo.getValue(),
                    passengerName
            );
            
            //add the ticket to ticketManager and txt file
            ticketManager.addTicket(ticket);
            appendTicketToFile(ticket);
            
            //clear the form for every submission and update the UI
            clearForm();
            showAlert("Success", "Ticket submitted: " + ticket.getTicketId());
            renderTicketList();
        } else {
            //show error if form is incomplete
            showAlert("Error", "Please fill in all fields.");
        }
    }

    //writes the submitted ticket into the .txt file
    private void appendTicketToFile(Ticket ticket) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TICKET_FILE, true))) {
            String line = String.join(",",
                    ticket.getTitle().replace(",", " "),
                    ticket.getMessage().replace(",", " "),
                    ticket.getCategory(),
                    ticket.getPriority(),
                    ticket.getStatus(),
                    ticket.getPassengerName()
            );
            
            //writing to the file
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write ticket to file: " + e.getMessage());
        }
    }
    
    //loading all the tickets from file
    //add them to memory
    private void loadTicketsFromFile() {
        File file = new File(TICKET_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    Ticket ticket = new Ticket(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[5] // passengerName
                    );
                    ticket.setStatus(parts[4]);
                    ticketManager.addTicket(ticket);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load tickets: " + e.getMessage());
        }
    }

    //shows all the passenger's tickets submitted
    private void renderTicketList() {
        ticketList.getChildren().clear();

        for (Ticket ticket : ticketManager.getTicketsByPassenger(passengerName)) {
            HBox row = new HBox(15);
            row.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 25; -fx-padding: 15 20;");
            row.setPrefWidth(400);
            row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            //create a small coloured dot depending on status of ticket
            Region colorDot = new Region();
            colorDot.setStyle("-fx-background-color: " + getStatusColor(ticket.getStatus()) + "; -fx-background-radius: 50;"
                    + "-fx-min-width: 20; -fx-min-height: 20; -fx-max-width: 20; -fx-max-height: 20;");

            Label titleLabel = new Label(ticket.getTitle());
            titleLabel.setStyle("-fx-text-fill: #333333; -fx-font-family: 'Poppins'; -fx-font-size: 16;");

            row.getChildren().addAll(colorDot, titleLabel);
            ticketList.getChildren().add(row);
        }
    }

    //getter method, returns colour based on the status string
    private String getStatusColor(String status) {
        return switch (status) {
            case "Open" -> "#FFA726"; //orange
            case "Resolved" -> "#66BB6A"; //green
            case "In Progress" -> "#42A5F5"; //blue
            case "Closed" -> "#9E9E9E"; //gray
            default -> "#E5E5E5"; //fallback
        };
    }
    
    //validate user input, checks if all form inputs are filled
    private boolean validateForm() {
        return !(subjectField.getText().isEmpty() || categoryCombo.getValue() == null ||
                priorityCombo.getValue() == null || messageArea.getText().isEmpty());
    }

    //clears the form inputs after submission
    private void clearForm() {
        subjectField.clear();
        categoryCombo.setValue(null);
        priorityCombo.setValue(null);
        messageArea.clear();
    }

    //shows popup alert messages
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
