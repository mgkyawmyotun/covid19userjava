package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import netscape.javascript.JSObject;
import utils.HttpService;

import java.io.IOException;

public class DashBoardController {
    WebEngine webEngine = null;
    static WebView webView = null;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private AnchorPane topPane;
    @FXML
    private BorderPane borderPane;


    private JFXDrawer drawer;

    @FXML
    void initialize() {
        // Case Component
        drawer = new JFXDrawer();
        drawer.setDefaultDrawerSize(200);
        drawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        drawer.setPrefWidth(200);

        borderPane.setLeft(Main.getComponent("caseComponent"));


        //Drawer
        try {
            VBox vb = FXMLLoader.load(getClass().getResource("../views/sideBar.fxml"));
            for (Node button : vb.getChildren()) {
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    switch (button.getId()) {
                        case "loacal":
                            borderPane.setCenter(null);
                            System.out.println("I am Local");
                            break;
                        case "global":
                            borderPane.setCenter(webView);
                            System.out.println("I am Global");
                            break;
                        case "login":

                            System.out.println("I am Login ");
                            break;

                    }
                });
            }
            drawer.setSidePane(vb);

        } catch (IOException e) {
            System.out.println("Error On Loading VBox to drawer");
        }
        //Map Related
        Platform.runLater(() -> {

            webView = new WebView();
            webEngine = webView.getEngine();
            webEngine.setJavaScriptEnabled(true);
            JSObject window = (JSObject) webEngine.executeScript("window");

            try {
                window.setMember("global_case", HttpService.getCaseByClients());
            } catch (IOException e) {
                e.printStackTrace();
            }

            webEngine.setOnError(e -> {
                System.out.println(e.getMessage());

            });
            webView.setCache(true);
            webEngine.load(getClass().getResource("../views/map.html").toString());
            webEngine.setOnAlert(e -> {
                System.out.println(e.getData());
            });

            webEngine.getLoadWorker().stateProperty().addListener(
                    new ChangeListener() {
                        @Override
                        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                            try {
                                webEngine.executeScript("test(" + HttpService.getCaseByClients() + ")");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (newValue != Worker.State.SUCCEEDED) {
                                return;
                            }
                            JSObject window = (JSObject) webEngine.executeScript("window");

                        }
                    }
            );


            borderPane.setCenter(webView);
        });

        //Make Rippler  effect on topPane
        JFXRippler rippler = new JFXRippler(topPane);

        borderPane.setTop(rippler);
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


    public Pane getPane() {
        return borderPane;
    }

    public void setPane(BorderPane bp) {
        borderPane = bp;
    }

    public void test() {
        System.out.println("Hello Fro ");
    }


}
