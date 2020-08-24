package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import utils.HttpService;

import javax.swing.*;

public class CaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label totalCase;

    @FXML
    private Label totalDead;

    @FXML
    private Label totalRecover;
    @FXML
    private VBox vbox;

    @FXML
    private JFXTextField search;
    @FXML
    private Label Country;

    @FXML
    private JFXButton serachButton;
    @FXML
    private JFXListView<Node> list;
    @FXML
    void onSearch(ActionEvent event) {
        System.out.println("on Search ..");
    }

    @FXML
    void onSearching(ActionEvent event) {
        System.out.println("Searching ..");
    }
  int i=0;
    @FXML
    void initialize() {
        loadLabel();
    }

    private void loadLabel() {
        int cases = HttpService.getTotalCase().getInt("cases");
        totalCase.setText(totalCase.getText() + cases);
        int deaths =HttpService.getTotalCase().getInt("deaths");
        totalDead.setText(totalDead.getText() + deaths);
        int recover =HttpService.getTotalCase().getInt("recovered");
        totalRecover.setText(totalRecover.getText() +recover);
        int affectedCountries = HttpService.getTotalCase().getInt("affectedCountries");
        Country.setText(Country.getText()+affectedCountries);
    }

    @FXML
    void onChoice(MouseEvent event){

    }
}
