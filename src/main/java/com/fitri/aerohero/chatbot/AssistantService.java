package com.fitri.aerohero.chatbot;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class AssistantService {

    // defines the Assistant interface inside this class (has just one method: chat)
    public interface Assistant {
        String chat(String userMessage);
    }

    // instance of the assistant that handles chatting
    private final Assistant assistant;

    // constructor sets up the assistant using the provided OpenAI API key
    public AssistantService(String apiKey) {
        // create the OpenAI chat model (e.g. GPT-4o)
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o")
                .build();

        // use LangChain4j to auto-generate an Assistant implementation from the interface + model
        this.assistant = AiServices.create(Assistant.class, chatModel);
    }

    // method used by other classes (like a controller) to get a chatbot reply
    public String getResponse(String input) {
        return assistant.chat(input);
    }
}
