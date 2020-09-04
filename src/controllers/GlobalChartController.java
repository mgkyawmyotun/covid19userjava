package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import utils.HttpService;

public class GlobalChartController {

    @FXML
    private JFXSpinner spinner;
    private WebView webView;
    private WebEngine webEngine;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    void initialize() {
        loadMap();
    }
    private void loadMap() {
        spinner.setVisible(true);
        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webView.setCache(true);
        webView.setContextMenuEnabled(true);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
//                data = HttpService.getCaseByCountries();
                Platform.runLater(() -> {
                    webEngine.load(getClass().getResource("/views/GlobalView/globalChart.html").toString());
                    borderPane.setCenter(webView);
                    webEngine.setOnAlert(e ->{
                            System.out.println(e.getData());
                    });


                }  );


//
//                    webEngine.getLoadWorker().stateProperty().addListener(
//                            new ChangeListener() {
//                                @Override
//                                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//
//
//                                    webEngine.executeScript("test(" + data + ")");
//
//
//                                    if (newValue != Worker.State.SUCCEEDED) {
//                                        return;
//                                    }
//
//                                }
//                            }
//                    );
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded((a) ->{

        });


    }
}
