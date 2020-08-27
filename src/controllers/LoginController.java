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

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.UserModel;
import org.json.JSONObject;
import utils.EmailValidator;
import utils.PasswordValidator;

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
       // Main.activate("dashboard");
    }

    @FXML
    void onBack(MouseEvent event) {
        Main.activate("dashboard",200,200);
    }

    @FXML
    void onForget(MouseEvent event) {

    }

    @FXML
    void onLogin(ActionEvent event) {
        Task task =new Task() {
            @Override
            protected Object call() throws Exception {
                JSONObject jsonObject =new JSONObject();
                jsonObject.put("email",username.getText());
                jsonObject.put("password",password.getText());
                new UserModel().loginUser(jsonObject.toString());
                return null;
            }
        };
        new Thread(task).start();


    }
    boolean emailCheck =false;
    boolean passwordCheck =false;
    @FXML
    void initialize() {


        ValidatorBase emailValidator =new EmailValidator();
        username.setValidators(emailValidator);
        ValidatorBase passwordValidator =new PasswordValidator();

        password.setValidators(passwordValidator);
        username.textProperty().addListener((e) ->{
            if(username.validate()) {
                emailCheck =true;
            }
            else{
                emailCheck =false;
            }
            if(emailCheck && passwordCheck) {
                login.setDisable(false);
            }
            else{
                login.setDisable(true);
            }

        });
        password.textProperty().addListener((e) ->{
            if(password.validate()){
                passwordCheck =true;
            }
            else{
                passwordCheck =false;
            }
            if(emailCheck && passwordCheck) {
                login.setDisable(false);
            }
            else{
                login.setDisable(true);
            }

        });

    }
}
