package com.fitri.aerohero.models;

public class Faq {
    private String category;
    private String question;
    private String answer;

    public Faq(String category, String question, String answer) {
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
