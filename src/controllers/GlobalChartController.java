package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GlobalChartController {
    @FXML
    private JFXButton caseByContienets;

    @FXML
    private JFXSpinner spinner;
    private WebView webView;
    private WebEngine webEngine;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private JFXButton caseByCountries;

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXButton dcr10;

    @FXML
    void initialize() {
        loadCaseByContientsChart();
    }
    private void loadCaseByContientsChart() {
        spinner.setVisible(true);
        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webView.setCache(true);
        webView.setContextMenuEnabled(true);
        webView.setStyle(null);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    webEngine.load(getClass().getResource("/views/GlobalView/globalChart.html").toString());

                    borderPane.setCenter(webView);



                }  );

                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded((a) ->{

        });


    }
    @FXML
    void onCaseByContienets(ActionEvent event) {
        loadCaseByContientsChart();
    }

    private void loadCaseByCountries() {
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
                    webEngine.load(getClass().getResource("/views/GlobalView/globalChartGeo.html").toString());
                    borderPane.setCenter(webView);

                }  );

                return null;
            }
        };
        new Thread(task).start();
    }
    @FXML
    void onCaseByContries(ActionEvent event) {
        loadCaseByCountries();
    }


    private void loaddcr10() {
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
                    webEngine.load(getClass().getResource("/views/GlobalView/globalChartTime.html").toString());
                    borderPane.setCenter(webView);

                }  );

                return null;
            }
        };
        new Thread(task).start();
    }

    @FXML
    void onDcr10(ActionEvent event) {
        loaddcr10();
    }
    private void loadGCases() {
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
                    webEngine.load(getClass().getResource("/views/GlobalView/globalChartTimeLine.html").toString());
                    borderPane.setCenter(webView);

                }  );

                return null;
            }
        };
        new Thread(task).start();
    }
    @FXML
    void onGCases(ActionEvent event) {
        loadGCases();
    }
    private void loadGDeaths() {
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
                    webEngine.load(getClass().getResource("/views/GlobalView/globalChartDeathTime.html").toString());
                    borderPane.setCenter(webView);

                }  );

                return null;
            }
        };
        new Thread(task).start();
    }
    @FXML
    void onGDeaths(ActionEvent event) {
        loadGDeaths();
    }
    private void loadGRecover() {
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
                    webEngine.load(getClass().getResource("/views/GlobalView/globalChartRecoverLine.html").toString());
                    borderPane.setCenter(webView);

                }  );

                return null;
            }
        };
        new Thread(task).start();
    }
    @FXML
    void onGRecover(ActionEvent event) {
        loadGRecover();
    }


    @FXML
    void onAge(ActionEvent event) {

    }
}
