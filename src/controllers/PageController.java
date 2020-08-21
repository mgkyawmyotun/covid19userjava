package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import models.UserModel;
import utils.Helper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PageController {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton register;

    @FXML
    private JFXButton cancel;

    @FXML
    void cancel(ActionEvent event) {
        System.out.println("Cancel");
    }

    @FXML
    void register(ActionEvent event) {
        UserModel user = new UserModel();
        ResultSet res = user.selectAll();
        Helper.outPutAll(res);
    }

}
