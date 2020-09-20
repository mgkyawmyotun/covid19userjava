package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class ContactController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextArea message;

    @FXML
    private JFXButton send;
    boolean e =false;
    boolean f=false;
    @FXML
    void initialize() {

        name.textProperty().addListener(c->{
            if(!name.getText().isEmpty())e=true;
            else e=false;
            if(e &&f)send.setDisable(false);
            else send.setDisable(true);
        });
        message.textProperty().addListener(c->{
            if(!message.getText().isEmpty())f=true;
            else f=false;
            if(e &&f)send.setDisable(false);
            else send.setDisable(true);

        });
    }
}
