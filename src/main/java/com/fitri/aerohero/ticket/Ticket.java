package com.fitri.aerohero.ticket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Ticket {
    private String ticketId;
    private String title;
    private String message;
    private String category;
    private String priority;
    private String status;
    private LocalDateTime createdAt;
    private String passengerName;

    // Predefined categories (mapped to dropdowns later)
    public static final String[] CATEGORIES = {
            "Flight schedule & delays",
            "Baggage & lost items",
            "Refunds & cancellations",
            "Check in & Boarding pass issues",
            "Special assistance & general inquiries"
    };

    // Priorities
    public static final String[] PRIORITIES = {
            "Low", "Medium", "High"
    };

    // Status options
    public static final String[] STATUSES = {
            "Open", "In Progress", "Resolved", "Closed"
    };

    //default constructor for Ticket
    //auto gemerate unique ticket id
    //sets creation time to current time
    //sets the intiial ticket status to "Open"
    public Ticket() {
        this.ticketId = generateTicketId();
        this.createdAt = LocalDateTime.now();
        this.status = "Open";
    }
    
    //overloader constructor for Tickets with parameters
    //calls default constructor to initialise ticketId, createdAt and status
    //then sets title, message, category, priority and passengerName with provided values
    public Ticket(String title, String message, String category, String priority, String passengerName) {
        this();
        this.title = title;
        this.message = message;
        this.category = category;
        this.priority = priority;
        this.passengerName = passengerName;
    }
    
    //method to randomly generate unique ticket IDs per ticket submission
    private String generateTicketId() {
        Random random = new Random();
        return "TKT" + String.format("%06d", random.nextInt(1000000));
    }

    // Getters and Setters methods
    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    //method to format date
    public String getFormattedCreatedAt() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s)", ticketId, title, status, priority);
    }
}