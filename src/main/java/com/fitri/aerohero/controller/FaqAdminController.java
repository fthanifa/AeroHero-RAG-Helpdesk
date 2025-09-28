package com.fitri.aerohero.controller;

import com.fitri.aerohero.models.Faq;
import com.fitri.aerohero.utils.FaqUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.fitri.aerohero.utils.UIManager;


import java.util.List;
import java.util.stream.Collectors;

public class FaqAdminController {

    
    // UI elements from fxml
    @FXML private TextField searchField, categoryField, questionField;
    @FXML private TextArea answerField;
    @FXML private TableView<Faq> faqTable;
    @FXML private TableColumn<Faq, String> categoryColumn, questionColumn, answerColumn;
    @FXML private Label noResultsLabel;

    private ObservableList<Faq> faqList;

    @FXML
    public void initialize() {
        // sets up how dada appears in each column
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));
        questionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuestion()));
        answerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAnswer()));

        //loads data from files 
        loadFaqs();
    }

    private void loadFaqs() {
        List<Faq> faqs = FaqUtils.loadFaqs();
        faqList = FXCollections.observableArrayList(faqs);
        faqTable.setItems(faqList);
        noResultsLabel.setVisible(faqList.isEmpty());
    }
    
    
    //handles search/filtering based on keyword input
    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase().trim();

        if (keyword.isEmpty()) {
            faqTable.setItems(faqList);
            noResultsLabel.setVisible(faqList.isEmpty());
            return;
        }

        List<Faq> filtered = faqList.stream()
            .filter(f -> f.getCategory().toLowerCase().contains(keyword)
                      || f.getQuestion().toLowerCase().contains(keyword)
                      || f.getAnswer().toLowerCase().contains(keyword))
            .collect(Collectors.toList());

        faqTable.setItems(FXCollections.observableArrayList(filtered));
        noResultsLabel.setVisible(filtered.isEmpty());
    }

    
    //handles adding new FAQs
    @FXML
    private void handleAddFaq() {
        String category = categoryField.getText().trim();
        String question = questionField.getText().trim();
        String answer = answerField.getText().trim();

        if (category.isEmpty() || question.isEmpty() || answer.isEmpty()) {
            showAlert("All fields must be filled to add an FAQ.");
            return;
        }

        Faq newFaq = new Faq(category, question, answer);
        faqList.add(newFaq);
        faqTable.setItems(faqList);
        FaqUtils.saveFaqs(faqList);

        // Clear input fields
        categoryField.clear();
        questionField.clear();
        answerField.clear();
        searchField.clear();

        noResultsLabel.setVisible(false);
    }

    
    //handes deleting a selected FAQ from list
    @FXML
    private void handleDeleteFaq() {
        Faq selected = faqTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a FAQ to delete.");
            return;
        }

        faqList.remove(selected);
        faqTable.getSelectionModel().clearSelection();
        faqTable.setItems(faqList);
        FaqUtils.saveFaqs(faqList);

        searchField.clear();
        noResultsLabel.setVisible(faqList.isEmpty());
    }

    
    //shows alert dialogs 
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("FAQ Manager");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML   //goes back to previous page 
    private void handleBack() {
        UIManager.switchScene((Stage) faqTable.getScene().getWindow(), "/views/Profile.fxml", "AeroHero - Profile");
    }
    
}