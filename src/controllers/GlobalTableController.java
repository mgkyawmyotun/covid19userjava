package controllers;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import models.GlobalModel;
import org.json.JSONArray;
import org.json.JSONObject;

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
    void initialize() {
        loadTable();
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

        public Global(String country, String cases, String todayCases, String todayDeaths, String deaths, String recovered, String todayRecovered, String active) {
            this.country = new SimpleStringProperty(country);
            this.cases = new SimpleStringProperty(cases);
            this.todayCases = new SimpleStringProperty(todayCases);
            this.todayDeaths = new SimpleStringProperty(todayDeaths);
            this.deaths = new SimpleStringProperty(deaths);
            this.recovered = new SimpleStringProperty(recovered);
            this.todayRecovered = new SimpleStringProperty(todayRecovered);
            this.active = new SimpleStringProperty(active);
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
                            jsonObject.get("active").toString()

                    ));
        }
        return observableList;

    }
}
