package com.fitri.aerohero.controller;

import com.fitri.aerohero.models.Faq;
import com.fitri.aerohero.utils.FaqUtils;
import com.fitri.aerohero.utils.UIManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FaqPassengerController {

    @FXML
    private TextField searchField;  //text input for keyword search

    @FXML
    private VBox faqListVBox;   //container to hold dynamically generated FAQ panels

    private final List<Faq> allFaqs = new ArrayList<>();

    @FXML  // loads all FAQs from storage and displays them
    public void initialize() {
        allFaqs.addAll(FaqUtils.loadFaqs());
        showFaqs(allFaqs);
    }

    /*
     Dynamically creates and displays FAQ entries as expandable TitledPanes inside the VBox.
     Each question becomes a clickable panel, and its answer is shown when expanded.
     */
    
    private void showFaqs(List<Faq> faqs) {
    faqListVBox.getChildren().clear();
    for (Faq faq : faqs) {
        TitledPane pane = new TitledPane();
        pane.setText(faq.getQuestion());

        Label answerLabel = new Label(faq.getAnswer());
        answerLabel.setWrapText(true);
        answerLabel.setMaxWidth(800); // Makes multiline answers wrap
        answerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        // Wrapper VBox to allow dynamic height
        VBox wrapper = new VBox(answerLabel);
        wrapper.setPadding(new Insets(10));
        wrapper.setFillWidth(true);
        wrapper.setMaxWidth(800);
        wrapper.setPrefHeight(Region.USE_COMPUTED_SIZE);
        answerLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);

        pane.setContent(wrapper);
        pane.setExpanded(false);
        pane.setPrefWidth(850);

        faqListVBox.getChildren().add(pane);
    }
    
    //if no FAQ matches the search, show a fallback message
    if (faqs.isEmpty()) {
        Label noResult = new Label("No FAQs matched your search.");
        noResult.setStyle("-fx-text-fill: #ccc; -fx-font-style: italic;");
        faqListVBox.getChildren().add(noResult);
    }
}

    
    /*
     Filters FAQs by checking if the keyword appears in the question, answer, or category.
     Triggered when the user clicks the Search button.
     */

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase().trim();

        if (keyword.isEmpty()) {   // If search is empty, show all FAQs
            showFaqs(allFaqs);
            return;
        }

        List<Faq> filtered = new ArrayList<>();
        for (Faq faq : allFaqs) {
            if (faq.getQuestion().toLowerCase().contains(keyword) ||
                faq.getAnswer().toLowerCase().contains(keyword) ||
                faq.getCategory().toLowerCase().contains(keyword)) {
                filtered.add(faq);
            }
        }

        showFaqs(filtered);
    }
    
}