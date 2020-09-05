package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import utils.HttpService;

public class LocalMapController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vbox;

    @FXML
    private Label totalCase;

    @FXML
    private Label totalDead;

    @FXML
    private TextField search;
    WebEngine webEngine = null;
    WebView webView = null;
    @FXML
    void initialize() {
        loadMap();
    }
    private void loadMap() {

        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webView.setCache(true);
        webView.setContextMenuEnabled(true);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
               Platform.runLater(() ->{  webEngine.load(getClass().getResource("/views/map.html").toString());
                    webEngine.setOnAlert(e -> {
                        Platform.runLater(() -> {
                            borderPane.setCenter(webView);
                        });
                    });

                    webEngine.getLoadWorker().stateProperty().addListener(
                            new ChangeListener() {
                                @Override
                                public void changed(ObservableValue observable, Object oldValue, Object newValue) {


                                    if (newValue != Worker.State.SUCCEEDED) {
                                        return;
                                    }

                                }
                            }
                    );
                });
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded((a) ->{

        });


    }
}
