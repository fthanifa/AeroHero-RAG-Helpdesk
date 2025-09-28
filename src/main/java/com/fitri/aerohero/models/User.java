package com.fitri.aerohero.models;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String name;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected String country;
    protected String role;

    public User(String name, String email, String password, String phoneNumber, String country, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.role = role;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCountry() { return country; }
    public String getRole() { return role; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setCountry(String country) { this.country = country; }
}
