package controllers;

import com.jfoenix.controls.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Helper;
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
    private JFXListView<Label> list;
    private  FilteredList<Label> filteredList;

    private JFXDialog jfxDialog;
    private JFXDialogLayout jfxDialogLayout;
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

        ObservableList<Label> observableList = FXCollections.observableArrayList();

        JSONArray countryJson = HttpService.getCasesByCountriesAsJson();

        for (int i = 0; i < countryJson.length(); i++) {
            JSONObject jsonObject = countryJson.getJSONObject(i);
            Label label = new Label(jsonObject.getString("country"));
            String imagePath = jsonObject.getJSONObject("countryInfo").get("iso2") + "";
            JSONObject countryInfo =jsonObject.getJSONObject("countryInfo");
            label.setAccessibleText(imagePath);
            label.setAccessibleHelp("["+countryInfo.get("lat")+","+countryInfo.get("long")+"]");
            Image image = new Image("/views/Images/country/" + imagePath.toLowerCase() + ".png");

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            label.setGraphic(imageView);
            observableList.add(label);
        }
        filteredList =new FilteredList<Label>(observableList);

        list = new JFXListView<>();
        list.setItems(filteredList);

        list.getStylesheets().add("/views/css/listview.css");
        list.setCursor(Cursor.HAND);
        list.addEventHandler(MouseEvent.MOUSE_CLICKED, this::labelClicked);

        vbox.getChildren().remove(vbox.getChildren().size() - 1);
        vbox.getChildren().add(list);

    }

    private void labelClicked(MouseEvent e) {
            if(e.getClickCount() == 2){
                JFXButton jfxButton =new JFXButton(" Ok ");
                jfxButton.setFont(new Font(20));
                jfxButton.setCursor(Cursor.HAND);

                jfxButton.setPadding(new Insets(0,40,0,20));
                jfxButton.setStyle("-fx-background-color:#ff7043");
                jfxDialogLayout =new JFXDialogLayout();
                jfxDialogLayout.setHeading(new Text("Hello World"));
                jfxDialogLayout.setBody(new Text("lorem ipsdfj sldjf lksjdlk jsdkljf lksdj lksdj f"));
                jfxDialogLayout.setActions(jfxButton);
                jfxDialog =new JFXDialog(DashBoardController.publicStackPane,jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
                jfxDialog.show();

                jfxButton.setOnAction(event ->jfxDialog.close());
            }
            else{
                Label label
                        = (Label) list.getSelectionModel().getSelectedItem();
                DashBoardController.webEngine.executeScript("flyTo("+label.getAccessibleHelp()+")");

            }

        }

    private void loadLabel() {
        int cases = HttpService.getTotalCase().getInt("cases");
        totalCase.setText(totalCase.getText() + cases);
        int deaths = HttpService.getTotalCase().getInt("deaths");
        totalDead.setText(totalDead.getText() + deaths);
        int recover = HttpService.getTotalCase().getInt("recovered");
        totalRecover.setText(totalRecover.getText() + recover);
        int affectedCountries = HttpService.getTotalCase().getInt("affectedCountries");
        Country.setText(Country.getText() + affectedCountries);
    }

    private void loadSearch() {
        search.addEventHandler(KeyEvent.KEY_RELEASED, this::handleSearch);
    }

    private void handleSearch(KeyEvent k) {
        filteredList.setPredicate(la -> Helper.search(la.getText(), search.getText()));

        System.out.println(search.getText());
    }

    @FXML
    void onChoice(MouseEvent event) {

    }
}
