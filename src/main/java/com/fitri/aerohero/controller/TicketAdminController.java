package com.fitri.aerohero.controller;

import com.fitri.aerohero.ticket.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TicketAdminController {
    
    //FXML linked labels to show ticket stats
    @FXML private Label totalTicketsLabel;
    @FXML private Label openTicketsLabel;
    @FXML private Label inProgressTicketsLabel;
    @FXML private Label resolvedTicketsLabel;
    @FXML private VBox ticketTableContent;

    //using ticketManager class to handle all ticket-related operations
    private final TicketManager ticketManager = TicketManager.getInstance();
    
    //text file path for where the submitted tickets are stored
    private final String TICKET_FILE = "src/main/resources/data/tickets.txt";

    //when FXML is loaded, initiliase and get called immediately
    @FXML
    public void initialize() {
        System.out.println("AdminViewController loaded");
        loadTicketsFromFile(); //reading existing tickets from file submitted by user
        refresh();
    }

    // reads each line from the tickets.txt file and creates Ticket objects from it
    private void loadTicketsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKET_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    //create a new ticket from file values
                    Ticket ticket = new Ticket(
                            parts[0],     // title
                            parts[1],     // message
                            parts[2],     // category
                            parts[3],     // priority
                            parts[5]      // passenger name
                    );
                    ticket.setStatus(parts[4]); // set status SEPARATELY
                    ticketManager.addTicket(ticket); //add to ticketManager class for admin
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load tickets: " + e.getMessage());
        }
    }

    //refreshes the ticket dashboard view
    private void refresh() {
        //update labels with the current ticket counts
        totalTicketsLabel.setText(String.valueOf(ticketManager.getTicketCount()));
        openTicketsLabel.setText(String.valueOf(ticketManager.getOpenTicketCount()));
        inProgressTicketsLabel.setText(String.valueOf(ticketManager.getInProgressTicketCount()));
        resolvedTicketsLabel.setText(String.valueOf(ticketManager.getResolvedTicketCount()));

        ticketTableContent.getChildren().clear();
        
        //looping through all tickets and build a row for each ticket
        for (Ticket ticket : ticketManager.getAllTickets()) {
            HBox row = new HBox(10); //HBox used as each ticket is a horizontal row
            row.setAlignment(Pos.CENTER_LEFT);

            //values for the tickets; ticketID, category, priority, message and status column
            Label idLabel = createCell(ticket.getTicketId(), 100);
            Label categoryLabel = createCell(ticket.getCategory(), 200);

            ComboBox<String> priorityBox = new ComboBox<>();
            priorityBox.getItems().addAll(Ticket.PRIORITIES);
            priorityBox.setValue(ticket.getPriority());
            priorityBox.setPrefWidth(150);
            priorityBox.setOnAction(e -> {
                ticket.setPriority(priorityBox.getValue());
                refresh();
            });

            Label messageLabel = createCell(ticket.getMessage(), 350);

            ComboBox<String> statusBox = new ComboBox<>();
            statusBox.getItems().addAll(Ticket.STATUSES);
            statusBox.setValue(ticket.getStatus());
            statusBox.setPrefWidth(200);
            statusBox.setOnAction(e -> {
                ticket.setStatus(statusBox.getValue());
                refresh();
            });
            
            //add all components into the row
            row.getChildren().addAll(idLabel, categoryLabel, priorityBox, messageLabel, statusBox);
            ticketTableContent.getChildren().add(row);
        }
    }

    //a helper method
    //in order to make styled label cells
    private Label createCell(String text, int width) {
        Label label = new Label(text);
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        label.setPrefWidth(width);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 14;");
        return label;
    }
}
