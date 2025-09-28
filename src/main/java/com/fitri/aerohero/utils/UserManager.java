package com.fitri.aerohero.utils;

import com.fitri.aerohero.models.*;

import java.io.*;
import java.util.*;


//data is stored in a serialized binary file (users.dar)
public class UserManager {

    private static final String FILE_NAME = "users.dar";
    private List<User> users = new ArrayList<>();

    public UserManager() {
        loadUsers();
    }

    //registers a new passenger user and saves to DAR file
    public void registerUser(Passenger passenger) throws IOException {
        if (userExists(passenger.getEmail())) {
            throw new IOException("Email already registered.");
        }
        users.add(passenger);
        saveUsers();  //saves updated user list
    }

    //authenticates user by email and password, supports hard coded admin
    public User login(String email, String password) throws LoginException {
        // Hardcoded Admin
        if (email.equalsIgnoreCase("admin@aerohero.com") && password.equals("admin123")) {
            return new Admin("Admin", "admin@aerohero.com", "admin123", "N/A", "N/A", "admin");
        }

        // Check stored passengers
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user; // already contains role = "passenger"
            }
        }

        throw new LoginException("Invalid email or password.");
    }

    //checks whether a user already exists by email
    public boolean userExists(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    private void saveUsers() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        }
    }

    //loads the user list from file if it exists
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                users = (List<User>) obj;
            }
        } catch (Exception e) {
            System.err.println("Failed to load users:");
            e.printStackTrace();
        }
    }
}