package controllers;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import utils.Helper;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton state;

    @FXML
    private JFXButton town;

    @FXML
    private JFXButton townShip;

    @FXML
    private JFXButton hospital;

    @FXML
    private JFXButton patient;

    @FXML
    private JFXButton admin;

    @FXML
    private JFXButton logout;

    @FXML
    void onAdmin(ActionEvent event) {

    }

    @FXML
    void onHospital(ActionEvent event) {

    }

    @FXML
    void onLogout(ActionEvent event) {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Helper.deleteToken();
                Platform.runLater(() -> {
                    System.exit(0);
                });
                return null;

            }
        };
        new Thread(task).start();

    }

    @FXML
    void onPatient(ActionEvent event) {

    }

    @FXML
    void onState(ActionEvent event) {
        borderPane.setCenter(new JFXSpinner());
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
              Pane screen =  loadState();
                Platform.runLater(() -> borderPane.setCenter(screen));
                return null;
            }
        };
        new Thread(task).start();
    }

    private Pane loadState() {
        Pane screen = null;
        try {
            Main.addScreen("stateView", FXMLLoader.load(getClass().getResource("/views/components/stateView.fxml")));
            screen = Main.getScreen("stateView");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screen;

    }

    @FXML
    void onTown(ActionEvent event) {

    }

    @FXML
    void onTownShip(MouseEvent event) {

    }

    @FXML
    void initialize() {

    }
}
