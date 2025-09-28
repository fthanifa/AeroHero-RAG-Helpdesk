package com.fitri.aerohero.ticket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketManager {
    private static TicketManager instance;  // Singleton instance
    private ObservableList<Ticket> tickets; // In-memory list of all tickets

    private TicketManager() {
        tickets = FXCollections.observableArrayList(); // Initialize the observable list
    }

    // Get the shared instance (singleton pattern)
    public static TicketManager getInstance() {
        if (instance == null) {
            instance = new TicketManager(); // only created once
        }
        return instance;
    }

    // Add new ticket
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    // Return all tickets
    public ObservableList<Ticket> getAllTickets() {
        return tickets;
    }

    // Filter tickets for a specific passenger (case-insensitive match)
    public ObservableList<Ticket> getTicketsByPassenger(String passengerName) {
        ObservableList<Ticket> passengerTickets = FXCollections.observableArrayList();
        for (Ticket ticket : tickets) {
            if (ticket.getPassengerName().equalsIgnoreCase(passengerName)) {
                passengerTickets.add(ticket);
            }
        }
        return passengerTickets;
    }

    // Find a ticket by its ID
    public Ticket getTicketById(String ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId().equals(ticketId)) {
                return ticket;
            }
        }
        return null; // not found
    }

    // Update the status of a ticket (if found)
    public void updateTicketStatus(String ticketId, String newStatus) {
        Ticket ticket = getTicketById(ticketId);
        if (ticket != null) {
            ticket.setStatus(newStatus);
        }
    }

    // Total tickets
    public int getTicketCount() {
        return tickets.size();
    }

    // Count how many tickets are still open
    public int getOpenTicketCount() {
        return (int) tickets.stream().filter(t -> t.getStatus().equals("Open")).count();
    }

    // Count of tickets currently being handled
    public int getInProgressTicketCount() {
        return (int) tickets.stream().filter(t -> t.getStatus().equals("In Progress")).count();
    }

    // Resolved ticket count
    public int getResolvedTicketCount() {
        return (int) tickets.stream().filter(t -> t.getStatus().equals("Resolved")).count();
    }
}