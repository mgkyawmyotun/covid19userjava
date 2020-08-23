package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SideBarController {
    DashBoardController dashBoardController =null;

    @FXML
    private JFXButton global;

    @FXML
    private JFXButton loacal;

    @FXML
    private JFXButton login;
    @FXML
    void  initialize(){
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        try {
//            Pane p = fxmlLoader.load(getClass().getResource("../views/dashboard.fxml").openStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//         dashBoardController = (DashBoardController) fxmlLoader.getController();
    }
    @FXML
    void onGlobal(ActionEvent event) throws IOException {
       
        System.out.println("Global");
    }

    @FXML
    void onLocal(ActionEvent event) {
        System.out.println("Local");
    }
    @FXML
    void onLogin(ActionEvent event) {
        System.out.println("Login");
    }
}
