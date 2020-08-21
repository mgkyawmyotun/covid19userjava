package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import netscape.javascript.JSObject;


public class MapController {
    WebEngine webEngine = null;
    static WebView webView = null;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton myanmar;

    @FXML
    private JFXButton london;

    @FXML
    void onLondon(ActionEvent event) {
            myanmar.setText("Hello");
    }

    @FXML
    void onMyanamar(ActionEvent event) {
        System.out.println("From Myanmar");

    }

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            System.out.println(borderPane);
            webView = new WebView();
            webEngine = webView.getEngine();
            webEngine.setJavaScriptEnabled(true);
            webEngine.load(getClass().getResource("../views/map.html").toString());
            webEngine.getLoadWorker().stateProperty().addListener(
                    new ChangeListener() {
                        @Override
                        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                            if (newValue != Worker.State.SUCCEEDED) { return; }

                            JSObject window = (JSObject) webEngine.executeScript("window");
                            window.setMember("myanmar", myanmar);
                        }
                    }
            );
            borderPane.setCenter(webView);
        });

    }


}
