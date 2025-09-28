package com.fitri.aerohero.chatbot;

//this interface defines the structure for our chatbot assistant
//right now, our chatbot only has one method: chat()

public interface Assistant {

    //takes a message from the user and returns a chatbot response
    String chat(String userMessage);
}
