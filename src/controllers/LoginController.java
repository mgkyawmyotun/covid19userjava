package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane main;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton cancel;

    @FXML
    private Text forget;

    @FXML
    private FontAwesomeIconView back;

    @FXML
    private Text forget1;

    @FXML
    void cancel(ActionEvent event) {
        Main.activate("dashboard",200,200);
    }

    @FXML
    void onBack(MouseEvent event) {
        Main.activate("dashboard",200,200);
    }

    @FXML
    void onForget(MouseEvent event) {
        System.out.println("onForget");
    }

    @FXML
    void onLogin(ActionEvent event) {
        System.out.println("onLogin");

    }

    @FXML
    void initialize() {
        System.out.println("I got call");
    }
}
