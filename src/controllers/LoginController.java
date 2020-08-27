package controllers;

import animatefx.animation.BounceIn;
import animatefx.animation.ZoomInDown;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.StringLengthValidator;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.UserModel;
import org.json.JSONObject;
import utils.EmailValidator;

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
    private Text backText;
    @FXML
    void cancel(ActionEvent event) {
        Main.activate("dashboard");
    }

    @FXML
    void onBack(MouseEvent event) {
        Main.activate("dashboard",200,200);
    }

    @FXML
    void onForget(MouseEvent event) {
       new ZoomInDown(forget).play();

    }

    @FXML
    void onLogin(ActionEvent event) {
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("email","kyawmyotun472001@gmail.com");
        jsonObject.put("password","adminkyasaw");
        new UserModel().loginUser(jsonObject.toString());
        System.out.println("onLogin");

    }

    @FXML
    void initialize() {
        ValidatorBase emailValidator =new EmailValidator();
        username.setValidators(emailValidator);
        ValidatorBase passwordValidator =new StringLengthValidator(6);
        password.setValidators(passwordValidator);
        username.textProperty().addListener((e) ->{
            username.validate();
            System.out.println(username.getText());
        });
        password.textProperty().addListener((e) ->{
            password.validate();
            System.out.println(password.getText());
        });

    }
}
