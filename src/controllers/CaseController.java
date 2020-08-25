package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.HttpService;



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

    @FXML
    void initialize() {
        loadLabel();
        loadSearch();

           loadList();
    }

    private void loadList() {

            list =new JFXListView<>();
            JSONArray countryJson =HttpService.getCasesByCountriesAsJson();

            for(int i=0;i<countryJson.length();i++){
                JSONObject jsonObject =countryJson.getJSONObject(i);
                Label label =new Label(jsonObject.getString("country"));
                String imagePath = jsonObject.getJSONObject("countryInfo").get("iso2")+"";
              //  System.out.println("@views/images/country/"+imagePath+".png");

                Image image = new Image("/views/images/AF.png");

                ImageView imageView =new ImageView(image);
                imageView.setFitWidth(50);
                imageView.setFitWidth(50);
                label.setGraphic(imageView);
                list.getItems().add(label);
            }
            vbox.getChildren().remove(vbox.getChildren().size()-1);
            vbox.getChildren().add(list);

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
    private  void loadSearch(){

    }

    @FXML
    void onChoice(MouseEvent event){

    }
}
