package controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import models.LocalModel;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.java2d.pipe.SpanShapeRenderer;
import utils.Helper;
import utils.HttpService;

import java.net.URL;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

public class LocalTableController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane main;

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXTreeTableView<Local> treeView;

    @FXML
    private JFXSpinner tableLoading;
    private ObservableList<Local> locals;


    @FXML
    private JFXTextField searchInput;
    private JFXDialog jfxDialog;
    private JFXDialogLayout jfxDialogLayout;

    private Map<String, JSONObject> localsMap;

    @FXML
    void initialize() {

        loadTable();
        searchInput.textProperty().addListener(c -> {
            treeView.setPredicate(g -> {
                Local local = g.getValue();
                return local.patient_id.getValue().toLowerCase().contains(searchInput.getText().toLowerCase())
                        ||
                        local.state.getValue().toLowerCase().contains(searchInput.getText().toLowerCase());
            });
        });
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (e.getClickCount() >= 2) {
                Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        Local local = treeView.getSelectionModel().getSelectedItem().getValue();


                        TableView patient_idTable = loadTable(local.patient_id.getValue());
                        Platform.runLater(() -> {
                            jfxDialogLayout.setBody(patient_idTable);

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


                jfxButton.setOnAction(event -> jfxDialog.close());
            }
        });
    }

    private void loadTable() {
        treeView.setVisible(false);
        tableLoading.setVisible(true);
        JFXTreeTableColumn<Local, Number> number = new JFXTreeTableColumn<>("No");
        number.setCellValueFactory(param -> param.getValue().getValue().number);

        JFXTreeTableColumn<Local, String> patient_idCol = new JFXTreeTableColumn<>("Patient Id");
        patient_idCol.setCellValueFactory(param -> param.getValue().getValue().patient_id);
        JFXTreeTableColumn<Local, String> CasesCol = new JFXTreeTableColumn<>("Gender");
        CasesCol.setCellValueFactory(param -> param.getValue().getValue().gender);
        JFXTreeTableColumn<Local, String> ageCol = new JFXTreeTableColumn<>("Age");
        ageCol.setCellValueFactory(param -> param.getValue().getValue().age);
        JFXTreeTableColumn<Local, String> stateCol = new JFXTreeTableColumn<>("State");
        stateCol.setCellValueFactory(param -> param.getValue().getValue().state);
        Task<Void> tableRequest = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                locals = loadLocals();
                Platform.runLater(() -> {
                    final TreeItem<Local> root = new RecursiveTreeItem<Local>(locals, RecursiveTreeObject::getChildren);
                    treeView.getColumns().setAll(number,patient_idCol,
                            CasesCol,
                            ageCol,
                            stateCol
                    );
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


    class Local extends RecursiveTreeObject<Local> {
        IntegerProperty number;
        StringProperty patient_id;
        StringProperty gender;
        StringProperty age;
        StringProperty state;

        public Local(int number,String patient_id, String gender, String age, String state) {
            this.number =new SimpleIntegerProperty(number);
           this.patient_id = new SimpleStringProperty(patient_id);
            this.gender = new SimpleStringProperty(gender);
            this.age = new SimpleStringProperty(age);
            this.state = new SimpleStringProperty(state);

        }
    }

    private ObservableList<Local> loadLocals() {
        ObservableList<Local> observableList = FXCollections.observableArrayList();
        LocalModel localModel = new LocalModel();
        localsMap = new Hashtable<>();
        JSONArray jsonLocals = localModel.getAll();
        for (int i = 0; i < jsonLocals.length(); i++) {
            JSONObject jsonObject = jsonLocals.getJSONObject(i);
            JSONObject patientObject = new JSONObject();
            patientObject.put("No",i+1);
            patientObject.put("patient_id", jsonObject.get("patient_id").toString());
            patientObject.put("age", jsonObject.get("age").toString());
            patientObject.put("gender", jsonObject.get("gender").toString());
            patientObject.put("town", jsonObject.getJSONObject("town").get("name").toString());
            patientObject.put("state", jsonObject.getJSONObject("state").get("name").toString());
            patientObject.put("townShip", jsonObject.getJSONObject("townShip").get("name").toString());
            patientObject.put("hospital", jsonObject.getJSONObject("hospital").get("name").toString());
            patientObject.put("date", Helper.formatDate(jsonObject.get("date").toString()));
            patientObject.put("contact_person", jsonObject.get("contact_person").toString());
            patientObject.put("oversea_country", jsonObject.get("oversea_country").toString());

            localsMap.put(jsonObject.get("patient_id").toString(), patientObject);

            observableList.add(
                    new Local(
                            Integer.parseInt(patientObject.get("No")+""),
                            jsonObject.get("patient_id").toString(),
                            jsonObject.get("gender").toString(),
                            jsonObject.get("age").toString(),
                            jsonObject.getJSONObject("state").get("name").toString()
                    ));
        }
        return observableList;

    }

    private TableView loadTable(String patient_id) {
        TableView<String> tableView = new TableView<>();

        tableView.setItems(loadData(localsMap.get(patient_id)));
        TableColumn aboutColumn = new TableColumn<>("Content");
        aboutColumn.setPrefWidth(200);

        TableColumn numbersColumn = new TableColumn<>("gender");
        aboutColumn.setCellValueFactory(new PropertyValueFactory<DetailsView, String>("About"));
        numbersColumn.setCellValueFactory(new PropertyValueFactory<DetailsView, String>("Numbers"));


        tableView.getColumns().addAll(aboutColumn, numbersColumn);

        return tableView;
    }

    private ObservableList loadData(JSONObject patient_id) {
        ObservableList<DetailsView> observableList = FXCollections.observableArrayList();
        patient_id.keySet().forEach(key -> {

            observableList.add(new DetailsView(key, patient_id.get(key) + ""));

        });
        return observableList;
    }
}
