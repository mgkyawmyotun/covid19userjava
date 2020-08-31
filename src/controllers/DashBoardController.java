package controllers;

import animatefx.animation.Bounce;
import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javafx.util.Duration;
import netscape.javascript.JSObject;
import utils.Helper;
import utils.HttpService;

import java.io.IOException;

public class DashBoardController {
    static WebEngine webEngine = null;
    static WebView webView = null;
    static StackPane publicStackPane;
    @FXML
    private StackPane stackPane;

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private AnchorPane topPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXSpinner spinner;

    private JFXDrawer drawer;
    JFXButton localMap;
    JFXButton localTable;
    JFXButton globalMap;
    JFXButton globalTable;
    JFXButton globalChart;
    JFXButton localChart;
    JFXButton localGraph;
    @FXML
    void initialize() {
        System.out.println("I got call db");
        publicStackPane = stackPane;
        // Case Component

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loadCase();
                    }
                });
                return null;
            }
        };
        new Thread(task).start();
        Task<Void> task1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loadMap();
                    }
                });
                return null;
            }
        };
        new Thread(task1).start();
        //Drawer
        loadDrawer();
        //Map Related

        //Make rip  effect on topPane

        //activate Hamberger
        loadTopPane();

        activateHamberger();

    }

    private void activateHamberger() {
        HamburgerBasicCloseTransition hst = new HamburgerBasicCloseTransition(hamburger);
        hst.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            hst.setRate(hst.getRate() * -1);
            hst.play();
            borderPane.setRight(drawer);
            if (drawer.isOpened()) {
                borderPane.setRight(null);
                drawer.close();
            } else {
                drawer.open();
            }

        });

    }

    private void loadTopPane() {
        JFXRippler rippler = new JFXRippler(topPane);
        borderPane.setTop(rippler);
    }

    private void loadMap() {

        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        JSObject window = (JSObject) webEngine.executeScript("window");

        webEngine.setOnError(e -> {
            System.out.println(e.getMessage());

        });
        webView.setCache(true);
        webView.setContextMenuEnabled(true);

        webEngine.load(getClass().getResource("/views/map.html").toString());
        webEngine.setOnAlert(e -> {
            Platform.runLater(() -> {
                borderPane.setCenter(webView);
            });
        });

        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                        String data = HttpService.getCaseByCountries();

                        webEngine.executeScript("test(" + data + ")");


                        if (newValue != Worker.State.SUCCEEDED) {
                            return;
                        }

                    }
                }
        );


    }

    private void loadCase() {
        System.out.println("Hi");

        try {
            Main.addScreen("caseComponent", FXMLLoader.load(getClass().getResource("/views/components/case.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        borderPane.setLeft(Main.getScreen("caseComponent"));
        System.out.println("Hi");
    }

    private void loadDrawer() {
        drawer = new JFXDrawer();
        drawer.setDefaultDrawerSize(200);
        drawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        drawer.setPrefWidth(200);
        try {
            VBox vb = FXMLLoader.load(getClass().getResource("/views/sideBar.fxml"));
             globalMap = (JFXButton) vb.getChildren().get(0);
             localMap=(JFXButton) vb.getChildren().get(1);
             globalTable=(JFXButton) vb.getChildren().get(2);
             localTable=(JFXButton) vb.getChildren().get(3);
             localChart=(JFXButton) vb.getChildren().get(4);
             globalChart=(JFXButton) vb.getChildren().get(5);
             localGraph=(JFXButton) vb.getChildren().get(6);
                localMap.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onLocalMap);
                globalMap.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onGlobalMap);
                localTable.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onLocalTable);
                globalTable.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onGlobalTable);
                localChart.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onLocalChart);
                globalChart.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onGlobalChart);
                localGraph.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onLocalGraph);


            drawer.setSidePane(vb);

        } catch (IOException e) {
            System.out.println("Error On Loading VBox to drawer");
        }
    }
    private  void onLocalMap(MouseEvent e){
        System.out.println("onLocalMap");
    }
    private  void onGlobalMap(MouseEvent e){
        System.out.println("onGlobalMap");
    }
    private  void onLocalTable(MouseEvent e){
        System.out.println("onLocalTable");
    }
    private void onGlobalTable(MouseEvent e){
        System.out.println("onGlobalTable");
    }
    private  void onLocalChart(MouseEvent e){
        System.out.println("onLocalChart");
    }    private  void onGlobalChart(MouseEvent e){
        System.out.println("onGlobalChart");
    }
    private void onLocalGraph(MouseEvent e){
        System.out.println("OnLocalGraph");

    }

}
