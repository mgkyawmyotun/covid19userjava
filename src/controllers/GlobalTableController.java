package controllers;

import com.jfoenix.controls.*;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import models.GlobalModel;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Helper;
import utils.HttpService;

public class GlobalTableController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane main;

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXTreeTableView<Global> treeView;

    @FXML
    private JFXSpinner tableLoading;
    private ObservableList<Global> globals;


    @FXML
    private JFXTextField searchInput;
    private JFXDialog jfxDialog;
    private JFXDialogLayout jfxDialogLayout;
    @FXML
    void initialize() {

        loadTable();
        searchInput.textProperty().addListener(c ->{
            treeView.setPredicate(g ->{
                Global global =g.getValue();
                return  global.country.getValue().toLowerCase().contains(searchInput.getText().toLowerCase());
            });
        });
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED,(e) ->{
            if(e.getClickCount()  >=2){
                Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        Global global =treeView.getSelectionModel().getSelectedItem().getValue();


                        TableView countryTable = loadTable(global.iso2);
                        Platform.runLater(() -> {
                            jfxDialogLayout.setBody(countryTable);

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
                jfxDialog = new JFXDialog(main, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
                jfxDialog.show();


                jfxButton.setOnAction(event -> jfxDialog.close());     }
        });
    }

    private void loadTable() {
        treeView.setVisible(false);
        tableLoading.setVisible(true);
        JFXTreeTableColumn<Global, String> countryCol = new JFXTreeTableColumn<>("Country Name");
        countryCol.setCellValueFactory(param -> param.getValue().getValue().country);

        JFXTreeTableColumn<Global, String> CasesCol = new JFXTreeTableColumn<>("Cases");
        CasesCol.setCellValueFactory(param -> param.getValue().getValue().cases);

        JFXTreeTableColumn<Global, String> todayCasesCol = new JFXTreeTableColumn<>("Today Cases");
        todayCasesCol.setCellValueFactory(param -> param.getValue().getValue().todayCases);
        JFXTreeTableColumn<Global, String> deathsCol = new JFXTreeTableColumn<>("Deaths");
        deathsCol.setCellValueFactory(param -> param.getValue().getValue().deaths);
        JFXTreeTableColumn<Global, String> todayDeathsCol = new JFXTreeTableColumn<>("Today Deaths");
        todayDeathsCol.setCellValueFactory(param -> param.getValue().getValue().todayDeaths);
        JFXTreeTableColumn<Global, String> recovered = new JFXTreeTableColumn<>("Recovered");
        recovered.setCellValueFactory(param -> param.getValue().getValue().recovered);
        JFXTreeTableColumn<Global, String> todayRecovered = new JFXTreeTableColumn<>("Today Recovered");
        todayRecovered.setCellValueFactory(param -> param.getValue().getValue().todayRecovered);
        JFXTreeTableColumn<Global, String> active = new JFXTreeTableColumn<>("Active");
        active.setCellValueFactory(param -> param.getValue().getValue().active);
        Task<Void> tableRequest = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                globals = loadGlobals();
                Platform.runLater(() -> {
                    final TreeItem<Global> root = new RecursiveTreeItem<Global>(globals, RecursiveTreeObject::getChildren);
                    treeView.getColumns().setAll(countryCol,
                            CasesCol,
                            todayCasesCol,
                            deathsCol,
                            todayDeathsCol,
                            recovered,
                            todayRecovered,
                            active);
                    treeView.setRoot(root);
                    treeView.setShowRoot(false);
                    tableLoading.setVisible(false);
                    treeView.setVisible(true);
                });
                return null;
            }
        };
        new Thread(tableRequest).start();


    }


    class Global extends RecursiveTreeObject<Global> {

        StringProperty country;
        StringProperty cases;
        StringProperty todayCases;
        StringProperty todayDeaths;
        StringProperty deaths;
        StringProperty recovered;
        StringProperty todayRecovered;
        StringProperty active;
        String iso2;
        public Global(String country, String cases, String todayCases, String todayDeaths, String deaths, String recovered, String todayRecovered, String active,String iso2) {
            this.country = new SimpleStringProperty(country);
            this.cases = new SimpleStringProperty(cases);
            this.todayCases = new SimpleStringProperty(todayCases);
            this.todayDeaths = new SimpleStringProperty(todayDeaths);
            this.deaths = new SimpleStringProperty(deaths);
            this.recovered = new SimpleStringProperty(recovered);
            this.todayRecovered = new SimpleStringProperty(todayRecovered);
            this.active = new SimpleStringProperty(active);
            this.iso2 =iso2;
        }
    }

    private ObservableList<Global> loadGlobals() {
        ObservableList<Global> observableList = FXCollections.observableArrayList();
        GlobalModel globalModel = new GlobalModel();

        JSONArray jsonGlobals = globalModel.getCasesByCountries();
        for (int i = 0; i < jsonGlobals.length(); i++) {
            JSONObject jsonObject = jsonGlobals.getJSONObject(i);
            observableList.add(
                    new Global(
                            jsonObject.get("country").toString(),
                            jsonObject.get("cases").toString(),
                            jsonObject.get("todayCases").toString(),
                            jsonObject.get("todayDeaths").toString(),
                            jsonObject.get("deaths").toString(),
                            jsonObject.get("recovered").toString(),
                            jsonObject.get("todayRecovered").toString(),
                            jsonObject.get("active").toString(),
                            jsonObject.getJSONObject("countryInfo").get("iso2") + ""

                    ));
        }
        return observableList;

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
                String keyData =country.get(key)+"";
                observableList.add(new DetailsView(key, keyData ));
            }
        });
        return observableList;
    }
}
