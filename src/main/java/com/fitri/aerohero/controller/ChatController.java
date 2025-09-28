package com.fitri.aerohero.controller;

import com.fitri.aerohero.chatbot.AnswerService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ChatController {
    
    //user input field, send button and chat container
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private VBox chatBox;
    
    //handles communication with the AI backend
    private final AnswerService answerService = new AnswerService();
    
    //label for the "AeroBot is typing..." indicator
    private Label typingIndicator;

    @FXML
    private void initialize() {
        // when send button is clicked, trigger handleSend
        sendButton.setOnAction(e -> handleSend());
        
        //creating the typing indicator
        //with stylings
        typingIndicator = new Label("AeroBot is typing...");
        typingIndicator.setStyle("-fx-font-size: 12px; -fx-text-fill: #9ca0b3;");
        typingIndicator.setPadding(new Insets(0, 10, 10, 10));
        typingIndicator.setVisible(false);  // Hidden by default
        
        //add the typing indicator to the chatbox
        chatBox.getChildren().add(typingIndicator);

    }
    
    //auto scroll to latest message
    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private void handleSend() {
        String input = userInput.getText().trim();

        if (!input.isEmpty()) {
            addMessage("You", input);
            userInput.clear();

            // Show typing indicator
            typingIndicator.setVisible(true);

            // Run the AI call in a background thread
            new Thread(() -> {
                final String[] responseHolder = new String[1];  // mutable holder
                responseHolder[0] = answerService.getResponse(input);

                Platform.runLater(() -> {
                    typingIndicator.setVisible(false);
                    String response = responseHolder[0];
                    if (response == null || response.isBlank()) {
                        response = "(No reply received)";
                    }
                    addMessage("AeroBot", response);
                });

            }).start();
        }
    }


    
    @FXML
    private void handleStart(){
        addMessage("AeroBot", "Hi! How can I assist you today?");
    }
    
    private void addMessage(String sender, String message) {
        Label bubble = new Label(message);
        bubble.setWrapText(true);
        
        // Force width so it doesn’t go vertical
        bubble.setMaxWidth(350); // allows wrapping, not squeezing
        
        bubble.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
        
        HBox.setHgrow(bubble,Priority.ALWAYS);
        
        //apply css class
        bubble.getStyleClass().add(sender.equals("You") ? "user-bubble" : "bot-bubble");
        bubble.maxWidthProperty().bind(chatBox.widthProperty().multiply(0.75));

        HBox row = new HBox();
        row.setPadding(new Insets(5));
        row.setSpacing(10);
        row.setPrefWidth(chatBox.getWidth());
        row.setMaxWidth(Double.MAX_VALUE);
        row.setFillHeight(true);
        
        row.setAlignment(sender.equals("You") ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        // Spacer to push bubble left/right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        if (sender.equals("You")) {
            row.getChildren().addAll(spacer, bubble); // align right
            row.setAlignment(Pos.TOP_RIGHT);
            bubble.getStyleClass().add("user-bubble");
        } else {
            row.getChildren().addAll(bubble, spacer); // align left
            row.setAlignment(Pos.TOP_LEFT);
            bubble.getStyleClass().add("bot-bubble");
        }

        chatBox.getChildren().add(row);

        // Scroll after render
        Platform.runLater(() -> chatScrollPane.setVvalue(1.0));
        
        //bubble fade animation
        FadeTransition ft = new FadeTransition(Duration.millis(300), row);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

    }
    
    //method to handle the quick questions
    @FXML
    private void handleQuickQuestion(MouseEvent event) {
        Label clicked = (Label) event.getSource();
        String question = clicked.getText().replace("• ", "").trim();

        userInput.setText(question);
        handleSend(); // send it immediately
}

 
    
}
