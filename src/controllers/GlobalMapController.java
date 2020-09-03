package controllers;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Helper;
import utils.HttpService;

public class GlobalMapController {
    WebEngine webEngine = null;
    WebView webView = null;
    @FXML
    private VBox vbox;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label Country;

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXListView<Label> list;
    private FilteredList<Label> filteredList;

    private JFXDialog jfxDialog;
    private JFXDialogLayout jfxDialogLayout;
    @FXML
    private TextField search;
    @FXML
    private Label totalCase;

    @FXML
    private Label totalDead;

    @FXML
    private Label totalRecover;
    @FXML
    void initialize() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loadCase();
                    }
                });
                return null;
            }
        };
        new Thread(task).start();
        Task<Void> task1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loadMap();
                    }
                });
                return null;
            }
        };
        new Thread(task1).start();
        loadLabel();
        loadSearch();
        loadList();
    }

    private void loadMap() {

        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        JSObject window = (JSObject) webEngine.executeScript("window");

        webEngine.setOnError(e -> {
            System.out.println(e.getMessage());

        });
        webView.setCache(true);
        webView.setContextMenuEnabled(true);

        webEngine.load(getClass().getResource("/views/map.html").toString());
        webEngine.setOnAlert(e -> {
            Platform.runLater(() -> {
                borderPane.setCenter(webView);
            });
        });

        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                        String data = HttpService.getCaseByCountries();

                        webEngine.executeScript("test(" + data + ")");


                        if (newValue != Worker.State.SUCCEEDED) {
                            return;
                        }

                    }
                }
        );


    }

    private void loadCase() {
        System.out.println("Hi");

        try {
            Main.addScreen("caseComponent", FXMLLoader.load(getClass().getResource("/views/components/case.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        borderPane.setLeft(Main.getScreen("caseComponent"));
        System.out.println("Hi");
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
            jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
            jfxDialog.show();


            jfxButton.setOnAction(event -> jfxDialog.close());
        } else {

            webEngine.executeScript("flyTo(" + label.getAccessibleHelp() + ")");

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
