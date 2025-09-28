package com.fitri.aerohero.utils;

import com.fitri.aerohero.models.User;


//utility class to temporarily store the current logged-in user duing app runtime
public class UserSession {
    
    //holds currently logged-in user
    private static User currentUser;

    //setter for current user
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    //getter for current user
    public static User getCurrentUser() {
        return currentUser;
    }

    //clears the currently logged in user session after logging out
    public static void clear() {
        currentUser = null;
    }
}