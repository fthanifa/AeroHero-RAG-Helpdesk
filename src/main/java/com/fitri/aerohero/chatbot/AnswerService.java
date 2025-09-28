package com.fitri.aerohero.chatbot;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;
import static com.fitri.aerohero.chatbot.Utils.*;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AnswerService {
    
    //main assistant instance, built using LangChain4j
    private final Assistant assistant;

    //the constructor
    //sets up everything needed for the chatbot to work
    public AnswerService() {
        //create the openAI chat model using the hard coded API key
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY)
                .modelName("gpt-4o")
                .build();

        // Step 1: Load all text documents from resources/rag/
        List<Document> documents = loadDocuments(toPath("rag"), glob("*.txt"));
        System.out.println("🟢 Loaded docs count: " + documents.size());
        documents.forEach(doc -> System.out.println("📄 " + doc.text()));


        // Step 2: Build embedding model
        OpenAiEmbeddingModel embeddingModel = OpenAiEmbeddingModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY)
                .modelName("text-embedding-3-small")
                .build();

        // Step 3: Ingest documents into an in-memory embedding store (RAM)
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build()
                .ingest(documents);

        // Step 4: Build assistant with RAG
        // this is a retriever to pull relevant content from embeddings
        ContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
        .embeddingStore(embeddingStore)
        .embeddingModel(embeddingModel)
        .maxResults(3) // optional: limit results to 3
        .build();

        //build the assistant instance, connect it to RAG + memory + model
        this.assistant = AiServices.builder(Assistant.class)
                .chatModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(retriever)
                .build();
    }

    //method that the controller/UI calls to get a chatbot reply
    public String getResponse(String input) {
        try {
            return assistant.chat(input); //sends input to assistant and returns response
        } catch (Exception e) {
            System.out.println("LangChain4j failed: " + e.getMessage());
            return "[Error: No response from AeroBot]";
        }
    }

    //helper method to resolve resources path (for debugging/legacy use)
    private Path getResourcePath(String folder) {
        try {
            URL url = getClass().getClassLoader().getResource(folder);
            System.out.println("🟡 URL to rag folder: " + url);  // STEP 1 DEBUG PRINT

            if (url == null) throw new RuntimeException("Resource folder not found: " + folder);

            Path path = Paths.get(url.toURI());
            System.out.println("Resolved path to rag: " + path);  // STEP 2 DEBUG PRINT

            return path;
        } catch (Exception e) {
            e.printStackTrace();  // Print detailed error
            throw new RuntimeException("Failed to resolve path for: " + folder, e);
        }
    }
    
    //resolves a relative folder name to a file system path
    private Path toPath(String relativePath) {
        try {
            URL fileUrl = getClass().getClassLoader().getResource(relativePath);
            if (fileUrl == null) throw new RuntimeException("Cannot find resource: " + relativePath);
            return Paths.get(fileUrl.toURI());
        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve path for: " + relativePath, e);
        }
    }


}
