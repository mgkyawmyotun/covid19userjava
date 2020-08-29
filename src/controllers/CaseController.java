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
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import org.omg.CORBA.INTERNAL;
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
    private TextField search;
    @FXML
    private Label Country;

    @FXML
    private JFXButton serachButton;
    @FXML
    private JFXListView<Label> list;
    private FilteredList<Label> filteredList;

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
        Task task = new Task<Void>() { // create new task

            @Override
            public Void call() {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        ObservableList<Label> observableList = FXCollections.observableArrayList();

                        JSONArray countryJson = HttpService.getCasesByCountriesAsJson();

                        for (int i = 0; i < countryJson.length(); i++) {
                            JSONObject jsonObject = countryJson.getJSONObject(i);
                            Label label = new Label(jsonObject.getString("country"));
                            String imagePath = jsonObject.getJSONObject("countryInfo").get("iso2") + "";
                            JSONObject countryInfo = jsonObject.getJSONObject("countryInfo");
                            label.setAccessibleText(imagePath);
                            label.setAccessibleHelp("[" + countryInfo.get("lat") + "," + countryInfo.get("long") + "]");
                            Image image = new Image("/views/Images/country/" + imagePath.toLowerCase() + ".png");

                            ImageView imageView = new ImageView(image);
                            imageView.setFitWidth(50);
                            imageView.setFitHeight(50);
                            label.setGraphic(imageView);
                            observableList.add(label);
                        }
                        filteredList = new FilteredList<Label>(observableList);

                        list = new JFXListView<>();
                        list.setItems(filteredList);

                        list.getStylesheets().add("/views/css/listview.css");
                        list.setCursor(Cursor.HAND);
                        list.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> labelClicked(e));

                        vbox.getChildren().remove(vbox.getChildren().size() - 1);
                        vbox.getChildren().add(list);
                        System.out.println("added");
                    }
                });
                return null;

            }
        };
        Thread thread = new Thread(task);
        thread.start();


    }

    private void labelClicked(MouseEvent e) {
        Label label
                = (Label) list.getSelectionModel().getSelectedItem();
        if (e.getClickCount() == 2) {
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    TableView tableView = loadTable(label.getAccessibleText());
                    Platform.runLater(() -> {
                        jfxDialogLayout.setBody(tableView);

                    });
                    return null;
                }
            };
            new Thread(task).start();
            JFXButton jfxButton = new JFXButton(" Ok ");
            jfxButton.setFont(new Font(20));
            jfxButton.setCursor(Cursor.HAND);

            jfxButton.setPadding(new Insets(0, 40, 0, 20));
            jfxButton.setStyle("-fx-background-color:#ff7043");
            jfxDialogLayout = new JFXDialogLayout();
            Label detailsViews = new Label("Details Views");
            detailsViews.setFont(new Font(22));

            jfxDialogLayout.setHeading(detailsViews);
            jfxDialogLayout.setBody(new JFXSpinner());
            jfxDialogLayout.setActions(jfxButton);
            jfxDialog = new JFXDialog(DashBoardController.publicStackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
            jfxDialog.show();


            jfxButton.setOnAction(event -> jfxDialog.close());
        } else {

            DashBoardController.webEngine.executeScript("flyTo(" + label.getAccessibleHelp() + ")");

        }

    }

    private void loadLabel() {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                JSONObject totalCaseData = HttpService.getTotalCase();
                int cases = totalCaseData.getInt("cases");

                int deaths = totalCaseData.getInt("deaths");

                int recover = totalCaseData.getInt("recovered");

                int affectedCountries = totalCaseData.getInt("affectedCountries");
                Platform.runLater(() -> {
                    totalCase.setText(totalCase.getText() + cases);
                    totalRecover.setText(totalRecover.getText() + recover);
                    totalDead.setText(totalDead.getText() + deaths);
                    Country.setText(Country.getText() + affectedCountries);
                });
                return null;
            }
        }).start();
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

    private TableView loadTable(String iso2) {
        TableView<String> tableView = new TableView<>();


        JSONObject country = HttpService.getDetailsByCountry(iso2);
        tableView.setItems(loadData(country));
        TableColumn aboutColumn = new TableColumn<>("Content");
        aboutColumn.setPrefWidth(200);

        TableColumn numbersColumn = new TableColumn<>("cases");
        aboutColumn.setCellValueFactory(new PropertyValueFactory<DetailsView, String>("About"));
        numbersColumn.setCellValueFactory(new PropertyValueFactory<DetailsView, String>("Numbers"));


        tableView.getColumns().addAll(aboutColumn, numbersColumn);

        return tableView;
    }

    private ObservableList loadData(JSONObject country) {
        ObservableList<DetailsView> observableList = FXCollections.observableArrayList();
        country.keySet().forEach(key -> {
            if (country.get(key) instanceof JSONObject || country.get(key) instanceof String) {

            } else {

                observableList.add(new DetailsView(key, country.get(key) + ""));
            }
        });
        return observableList;
    }
}
