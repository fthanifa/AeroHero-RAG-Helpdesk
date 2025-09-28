package com.fitri.aerohero.models;

public class Admin extends User {

    public Admin(String name, String email, String password, String phoneNumber, String country, String role) {
        super(name, email, password, phoneNumber, country, role);
    }

    // Optional: if needed, keep default constructor too
    public Admin() {
        super("Admin", "admin@aerohero.com", "admin123", "N/A", "N/A", "admin");
    }
}
