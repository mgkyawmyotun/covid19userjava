package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.LocalModel;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Helper;
import utils.HttpService;

public class LocalMapController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vbox;


    @FXML
    private Label totalCase;

    @FXML
    private Label totalDead;

    @FXML
    private Label totalRecovered;
    @FXML
    private TextField search;

    private WebEngine webEngine = null;
    private WebView webView = null;
    private JFXListView<Label> list;
    private FilteredList<Label> filteredList;
    @FXML
    private JFXSpinner spinner;

    @FXML
    void initialize() {
        loadData();
        loadMap("localMap");
        loadList();
        loadSearch();
    }

    private void loadSearch() {
        search.addEventHandler(KeyEvent.KEY_RELEASED, this::handleSearch);
    }

    private void handleSearch(KeyEvent k) {
        filteredList.setPredicate(la -> Helper.search(la.getText(), search.getText()));
    }
    private void loadData() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                JSONObject total = new LocalModel().getTotal();
                Platform.runLater(() -> {
                    totalCase.setText(totalCase.getText() + total.get("totalCase"));
                    totalDead.setText(totalDead.getText() + total.get("totalDeath"));
                    totalRecovered.setText(totalRecovered.getText() + total.get("recovered"));
                });
                return null;
            }
        };
        new Thread(task).start();
    }

    private void loadMap(String mapName) {

        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webView.setCache(true);
        webView.setContextMenuEnabled(true);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                Platform.runLater(() -> {
                   webEngine.load(getClass().getResource("/views/LocalViews/"+mapName+".html").toString());
                    webEngine.setOnAlert(e -> {
                        Platform.runLater(() -> {

                            borderPane.setCenter(webView);
                        });
                    });
                });
                return null;
            }
        };
        new Thread(task).start();
    }

    private void loadList() {


        Task<Void> task1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ObservableList<Label> observableList = FXCollections.observableArrayList();


                String states[] = {"Yangon", "Sagaing", "Ayeyarwady","Tanintharyi", "Kayah", "Kayin", "Mon", "Shan", "Chin", "Mandalay", "Kachin", "Rakhine", "Nay Pyi Taw", "Bago", "Magway"};
                for (int i = 0; i < states.length; i++) {

                    Label label = new Label(states[i]);

                    Image image = new Image("/views/Images/state/" + "chin" + ".png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    label.setGraphic(imageView);
                    observableList.add(label);
                }
                Platform.runLater(() -> {

                    filteredList = new FilteredList<Label>(observableList);
                    list = new JFXListView<>();
                    list.setItems(filteredList);

                    list.getStylesheets().add("/views/css/listview.css");
                    list.setCursor(Cursor.HAND);
                    list.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> labelClicked(e));
                    vbox.getChildren().remove(vbox.getChildren().size() - 1);
                    vbox.getChildren().add(list);
                });
                return null;
            }
        };

        new Thread(task1).start();

    }

    private void labelClicked(MouseEvent e) {
        Label label = list.getSelectionModel().getSelectedItem();
       String mapName =label.getText().trim().toLowerCase().replace(" ","_");
             borderPane.setCenter(spinner);

             loadMap(mapName+"Map");
    }
}
