package com.fitri.aerohero.chatbot;

public class ApiKeys {
    //use environment variable for api key to not hard code it
    public static final String OPENAI_API_KEY = 
            System.getenv("OPENAI_API_KEY") != null
            ? System.getenv("OPENAI_API_KEY")
            : "MISSING_ENV_VAR";
}
