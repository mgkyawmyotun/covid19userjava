package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.ContactModel;
import org.json.JSONObject;
import utils.EmailValidator;

public class ContactController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private JFXTextField email;
    @FXML
    private Text textMessage;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextArea message;

    @FXML
    private JFXButton send;
    boolean e =false;
    boolean f=false;
    boolean m =false;
    @FXML
    void initialize() {

        name.textProperty().addListener(c->{
            if(!name.getText().isEmpty())e=true;
            else e=false;
            if(e &&f &&m)send.setDisable(false);
            else send.setDisable(true);
        });
        message.textProperty().addListener(c->{
            if(!message.getText().isEmpty())f=true;
            else f=false;
            if(e &&f &&m)send.setDisable(false);
            else send.setDisable(true);
        });
        email.setValidators(new EmailValidator());
        email.textProperty().addListener(c ->{
        if(!email.getText().isEmpty()){
            if(email.validate()){
                m=true;
                if(e && f &&m)send.setDisable(false);
                else send.setDisable(true);

            }   else {
                m=false;
                send.setDisable(true);
            }
        }
        });
        send.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            Task<Void> task= new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    send.setVisible(false);
                    spinner.setVisible(true);
                    JSONObject jsonObject =new JSONObject();
                    jsonObject.put("username",name.getText());
                    jsonObject.put("message",message.getText());
                    jsonObject.put("email",email.getText());
                    ContactModel contactModel =new ContactModel();
                    contactModel.sendMessage(jsonObject.toString());
                    Platform.runLater(() ->{
                        send.setVisible(true);
                        spinner.setVisible(false);
                        textMessage.setVisible(true);
                        message.setText("");
                        name.setText("");
                        email.setText("");
                    });
                    return null;
                }
            };
            new Thread(task).start();
        });
    }
}
