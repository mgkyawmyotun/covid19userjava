package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import netscape.javascript.JSObject;


public class MapController {
    WebEngine webEngine = null;
    static WebView webView = null;
    @FXML
    private Pane mapPane;

    @FXML
    void initialize() {


    }


}
