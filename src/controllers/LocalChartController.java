package controllers;

import com.jfoenix.controls.JFXButton;
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

public class LocalChartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXSpinner spinner;

    @FXML
    private JFXButton state;

    @FXML
    private JFXButton caseByCountries;

    @FXML
    private JFXButton caseByCountries1;

    @FXML
    private JFXButton gRecover;

    @FXML
    private JFXButton age;
    private WebEngine webEngine;
    private  WebView webView;
    @FXML
    void onAge(ActionEvent event) {
        loadChart("caseByAge");
    }



    @FXML
    void onGender(ActionEvent event) {
        loadChart("caseByGender");
    }

    @FXML
    void onState(ActionEvent event) {
        loadChart("caseByState");

    }
    @FXML
    void onStateDeath(ActionEvent event) {
        loadChart("deathByState");
    }

    @FXML
    void onStateRecover(ActionEvent event) {
        loadChart("recoverByState");
    }
    @FXML
    void onDate(ActionEvent event) {
        loadChart("caseByDate");
    }
    private void loadChart(String name) {
        spinner.setVisible(true);
        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webView.setCache(true);
        webView.setContextMenuEnabled(true);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    webEngine.load(getClass().getResource("/views/LocalCharts/"+name+".html").toString());
                    borderPane.setCenter(webView);

                });

                return null;
            }
        };
        new Thread(task).start();
    }

    @FXML
    void initialize() {
        loadChart("caseByState");

    }
}
