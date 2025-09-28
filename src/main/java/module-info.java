module com.fitri.aerohero {
    requires javafx.controls;
    requires javafx.fxml;
    requires langchain4j;
    requires langchain4j.open.ai;
    requires java.net.http;
    requires org.apache.logging.log4j;
    requires org.slf4j;
    requires langchain4j.core;
    requires com.fasterxml.jackson.core;


    opens com.fitri.aerohero to javafx.fxml;
    opens com.fitri.aerohero.controller to javafx.fxml;
    opens com.fitri.aerohero.chatbot to javafx.fxml;

    exports com.fitri.aerohero;
    exports com.fitri.aerohero.controller;
    exports com.fitri.aerohero.chatbot;
}
