package controllers;

import com.jfoenix.controls.JFXSpinner;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LocalGraphController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXSpinner spinner;
    private WebEngine webEngine;
    private  WebView webView;
    @FXML
    void initialize() {
        loadGraph();
    }
    private void loadGraph() {

        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webView.setCache(true);
        webView.setContextMenuEnabled(true);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                Platform.runLater(() -> {
                    webEngine.load(getClass().getResource("/views/LocalGraph/" + "connection" + ".html").toString());

                    borderPane.setCenter(webView);
                });
                return null;
            }
        };
        new Thread(task).start();
    }
}
