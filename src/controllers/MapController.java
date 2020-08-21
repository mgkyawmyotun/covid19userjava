package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class MapController {
    WebEngine webEngine =null;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton myanmar;

    @FXML
    private JFXButton london;

    @FXML
    void onLondon(ActionEvent event) {
       webEngine.executeScript("alert('Hello')");
    }

    @FXML
    void onMyanamar(ActionEvent event) {
        System.out.println("From Myanmar");

    }
    @FXML
    void initialize() {
        Platform.runLater(() -> {
            System.out.println(borderPane);
            WebView webView = new WebView();
            webEngine =webView.getEngine();
            webEngine.load(getClass().getResource("../views/map.html").toString());
            borderPane.setCenter(new HBox(webView));
        });

    }


}
