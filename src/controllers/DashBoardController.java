package controllers;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

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
    @FXML
    private JFXRippler ripple;
    @FXML
    private JFXDrawer drawer;

    @FXML
    void initialize() {
        try {
            VBox vb = FXMLLoader.load(getClass().getResource("../views/sideBar.fxml"));

            drawer.setSidePane(vb);

        } catch (IOException e) {
            System.out.println("Erro");
        }
        //Map Related
        Platform.runLater(() -> {

            webView = new WebView();
            webEngine = webView.getEngine();
            webEngine.setJavaScriptEnabled(true);
            webEngine.load(getClass().getResource("../views/map.html").toString());
            webEngine.getLoadWorker().stateProperty().addListener(
                    new ChangeListener() {
                        @Override
                        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                            if (newValue != Worker.State.SUCCEEDED) {
                                return;
                            }

                            JSObject window = (JSObject) webEngine.executeScript("window");
                            window.setMember("myanmar", new String());
                        }
                    }
            );


            borderPane.setCenter(webView);
        });

        //Make rippler effect on topPane
        JFXRippler rippler = new JFXRippler(topPane);

        borderPane.setTop(rippler);
        HamburgerBasicCloseTransition hst = new HamburgerBasicCloseTransition(hamburger);
        hst.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            hst.setRate(hst.getRate() * -1);
            hst.play();
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });


    }
}
