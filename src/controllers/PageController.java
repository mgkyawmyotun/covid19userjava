package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import models.UserModel;
import utils.Helper;

import java.io.IOException;
import java.sql.ResultSet;


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
    void initialize() {
        RequiredFieldValidator vb = new RequiredFieldValidator();

        vb.setMessage("Cannot Be Empty");
        username.setValidators(vb);
        username.focusedProperty().addListener((observable, old, n) -> {
            if (!n) {
                username.validate();
            }
        });
        StringLengthValidator stringLengthValidator = new StringLengthValidator(6);

        stringLengthValidator.setMessage("At Least 6 Character Long");
        password.setValidators(stringLengthValidator);
        password.setOnKeyTyped(e -> {
            if (password.validate()) {

            }

        });
        password.focusedProperty().addListener((observable, old, n) -> {
            if (!n) password.validate();
        });

    }

    @FXML
    void cancel(ActionEvent event) throws IOException {

        Main.activate("map");

    }

    @FXML
    void register(ActionEvent event) {
        username.setValidators();
        UserModel user = new UserModel();
        ResultSet res = user.selectAll();
        Helper.outPutAll(res);
    }

}
