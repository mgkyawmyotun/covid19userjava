package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MapController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton id;
    @FXML
    void initialize(){

            System.out.println(borderPane);

       //     borderPane.setCenter(new Button("Hello World"));
    }



}
