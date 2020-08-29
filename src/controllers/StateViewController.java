package  controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.StateModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class StateViewController implements Initializable {

    @FXML
    private StackPane main;
    @FXML
    private JFXTreeTableView<State> treeView;
    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton editButton;

    @FXML
    private JFXTextField state;
    private Pane editPane;
    private Pane addPane;
    @FXML
    private JFXTextField latitude;

    @FXML
    private JFXTextField longitude;
    @FXML
    private JFXButton deleteButton;
    ObservableList<State> states;
    private JFXDialog jfxDialog;
    private JFXDialogLayout jfxDialogLayout;
    private  Text editErrorText;
    private  JFXSpinner editSpinner;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            editPane = FXMLLoader.load(getClass().getResource("/views/components/stateEdit.fxml"));


            GridPane gp = (GridPane) editPane.getChildren().get(editPane.getChildren().size() - 1);
            state = (JFXTextField) editPane.getChildren().get(1);
            latitude =(JFXTextField) editPane.getChildren().get(3);
            longitude =(JFXTextField) editPane.getChildren().get(5);
            editSpinner = (JFXSpinner) editPane.getChildren().get(6);
            editErrorText = (Text) editPane.getChildren().get(7);

            gp.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, this::onCancel);
            gp.getChildren().get(1).addEventHandler(MouseEvent.MOUSE_CLICKED, this::onEdit);

        } catch (IOException e) {
            e.printStackTrace();
        }


        loadTable();



    }
    private  void loadTable(){
        JFXTreeTableColumn<State, String> deptName = new JFXTreeTableColumn<>("State Name");
        deptName.setCellValueFactory(param -> param.getValue().getValue().name);

        JFXTreeTableColumn<State, String> lonCol = new JFXTreeTableColumn<>("Latitude");
        lonCol.setCellValueFactory(param -> param.getValue().getValue().lon);

        JFXTreeTableColumn<State, String> nameCol = new JFXTreeTableColumn<>("Longitude");
        nameCol.setCellValueFactory(param -> param.getValue().getValue().lat);
        states = FXCollections.observableArrayList();
        states =loadSates();

        final TreeItem<State> root = new RecursiveTreeItem<State>(states, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(deptName, lonCol, nameCol);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        treeView.getSelectionModel().selectedItemProperty().addListener(c -> {

            editButton.setDisable(false);
            deleteButton.setDisable(false);

        });
    }
    private  ObservableList<State> loadSates(){
        ObservableList<State> observableList =FXCollections.observableArrayList();
        StateModel stateModel =new StateModel();
        stateModel.refreshStates();
        JSONArray jsonStates =stateModel.getStates();
        for(int i=0;i<jsonStates.length();i++){
            JSONObject jsonObject =jsonStates.getJSONObject(i);
            JSONArray jsonArray =jsonObject.getJSONArray("location");
           observableList.add(new State(jsonObject.getString("name"),jsonArray.get(0)+"",
                   jsonArray.get(1)+""));
        }
       return observableList;
    }
    @FXML
    private void filter(ActionEvent event) {
    }

    @FXML
    void onAdd(ActionEvent event) {

    }

    @FXML
    void onDelete(ActionEvent event) {

         states =loadSates();
        System.out.println();
    }

    @FXML
    void onEdit(ActionEvent event) {

        int selectedIndex =  treeView.getSelectionModel().getSelectedIndex();
        State selectedState =states.get(selectedIndex);
        state.setText(selectedState.name.getValue());
        latitude.setText(selectedState.lat.getValue());
        longitude.setText(selectedState.lon.getValue());

        jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text("I am Heading"));
        jfxDialogLayout.setBody(editPane);
        jfxDialog = new JFXDialog(main, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        jfxDialog.setOverlayClose(false);
        jfxDialog.show();

        System.out.println(treeView.getSelectionModel().getSelectedIndex());

    }

    class State extends RecursiveTreeObject<State> {

        StringProperty name;
        StringProperty lat;
        StringProperty lon;

        public State(String name, String lat, String lon) {
            this.name = new SimpleStringProperty(name);
            this.lat = new SimpleStringProperty(lat);
            this.lon = new SimpleStringProperty(lon);
        }

    }



    private void onEdit(MouseEvent e) {
        editSpinner.setVisible(true);
        JFXButton jfxButton = (JFXButton) e.getTarget();
        jfxButton.setDisable(true);


    }

    private void onCancel(MouseEvent e) {
        jfxDialog.close();
    }
}
