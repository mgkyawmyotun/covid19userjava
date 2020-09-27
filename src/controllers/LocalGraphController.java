package controllers;

import com.jfoenix.controls.JFXSpinner;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
    private WebView webView;
    int load =0;
    @FXML
    void initialize() {
        loadGraph("connection");

    }

    private void loadGraph(String name) {
        load =0;
        webView = new WebView();
        webEngine = webView.getEngine();

        webEngine.setJavaScriptEnabled(true);
        webView.setCache(true);
        webView.setContextMenuEnabled(true);

        webEngine.documentProperty().addListener(c -> {
            System.out.println("Document Property Call");
            if(load  >0){
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        Platform.runLater(() -> {
                            webEngine.load(getClass().getResource("/views/LocalGraph/" + name + ".html").toString());
                            borderPane.setCenter(webView);

                        });
                        return null;
                    }
                };
                new Thread(task).start();
                load =0;
            }
            else{
                load+=1;
            }
        });

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                Platform.runLater(() -> {
                    webEngine.load(getClass().getResource("/views/LocalGraph/" + name + ".html").toString());
                    borderPane.setCenter(webView);

                });
                return null;
            }
        };
        new Thread(task).start();
    }

    @FXML
    void onByContact(ActionEvent event) {
        loadGraph("connection");
    }

    @FXML
    void onByState(ActionEvent event) {
        loadGraph("connectionByState");
    }

    @FXML
    void onByTown(ActionEvent event) {
        loadGraph("connectionByTown");
    }

    @FXML
    void onByTownShip(ActionEvent event) {
        loadGraph("connectionByTownShip");
    }
}
