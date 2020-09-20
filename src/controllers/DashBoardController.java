package controllers;


import animatefx.animation.*;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


import java.io.IOException;

public class DashBoardController {


    @FXML
    private JFXHamburger hamburger;
    @FXML
    private AnchorPane topPane;
    @FXML
    private BorderPane borderPane;
    private  JFXButton contactUs;
    private JFXDrawer drawer;
    JFXButton localMap;
    JFXButton localTable;
    JFXButton globalMap;
    JFXButton globalTable;
    JFXButton globalChart;
    JFXButton localChart;
    JFXButton localGraph;
    @FXML
    void initialize()  {
        borderPane.setCenter(loadGlobalMap());
       loadDrawer();

        loadTopPane();

        activateHamberger();

    }
    private void loadMainScreen(){
        try {
            borderPane.setCenter( FXMLLoader.load(getClass().getResource("/views/mainScreen.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void activateHamberger() {
        HamburgerBasicCloseTransition hst = new HamburgerBasicCloseTransition(hamburger);
        hst.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            hst.setRate(hst.getRate() * -1);
            hst.play();
            borderPane.setRight(drawer);
            if (drawer.isOpened()) {
                borderPane.setRight(null);
                drawer.close();
            } else {
                drawer.open();
            }

        });

    }

    private void loadTopPane() {
        JFXRippler rippler = new JFXRippler(topPane);
        borderPane.setTop(rippler);
    }


    private void loadDrawer() {
        drawer = new JFXDrawer();
        drawer.setDefaultDrawerSize(200);
        drawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        drawer.setPrefWidth(200);
        try {
            VBox vb = FXMLLoader.load(getClass().getResource("/views/sideBar.fxml"));
             globalMap = (JFXButton) vb.getChildren().get(0);

             localMap=(JFXButton) vb.getChildren().get(1);
             globalTable=(JFXButton) vb.getChildren().get(2);
             localTable=(JFXButton) vb.getChildren().get(3);
             localChart=(JFXButton) vb.getChildren().get(4);
             globalChart=(JFXButton) vb.getChildren().get(5);
             localGraph=(JFXButton) vb.getChildren().get(6);
                contactUs = (JFXButton) vb.getChildren().get(7);
             localMap.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onLocalMap);
             globalMap.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onGlobalMap);
             localTable.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onLocalTable);
             globalTable.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onGlobalTable);
             localChart.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onLocalChart);
             globalChart.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onGlobalChart);
             localGraph.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onLocalGraph);
            contactUs.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onContactUs);

            drawer.setSidePane(vb);

        } catch (IOException e) {
            System.out.println("Error On Loading VBox to drawer");
        }
    }

    private  void onContactUs(MouseEvent e){
        new FadeOutRightBig(borderPane).playOnFinished(new FadeInRightBig(borderPane)).play();
        borderPane.setCenter(loadContactUs());
    }
    private  Pane loadContactUs(){
        Pane screen = null;
        try {
            Main.addScreen("loadContact", FXMLLoader.load(getClass().getResource("/views/contactView.fxml")));
            screen = Main.getScreen("loadContact");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return screen;
    }
    private Pane loadLocalMap() {
        Pane screen = null;
        try {
            Main.addScreen("localMap", FXMLLoader.load(getClass().getResource("/views/LocalViews/localMapView.fxml")));
            screen = Main.getScreen("localMap");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return screen;

    }
    private  void onLocalMap(MouseEvent e){

          new ZoomInDown(borderPane).play();
           borderPane.setCenter( loadLocalMap());

    }
    private  void onGlobalMap(MouseEvent e){
       new Flip(borderPane).play();

        borderPane.setCenter(loadGlobalMap());
    }
    private Pane loadGlobalMap() {
        Pane screen = null;
        try {

           if(Main.getScreen("globalMap") instanceof  Pane){
                screen = Main.getScreen("globalMap");
            }
           else{
                Main.addScreen("globalMap", FXMLLoader.load(getClass().getResource("/views/GlobalView/globalMapView.fxml")));
               screen = Main.getScreen("globalMap");

           }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return screen;

    }
    private  void onLocalTable(MouseEvent e){
        new   JackInTheBox(borderPane).play();
        borderPane.setCenter(loadLocalTable());


    }
    private Pane loadLocalTable() {
        Pane screen = null;
        try {
            if(Main.getScreen("localTable") instanceof Pane){
                screen = Main.getScreen("localTable");

            }
            else{
                Main.addScreen("localTable", FXMLLoader.load(getClass().getResource("/views/LocalTable/LocalTableView.fxml")));
                screen = Main.getScreen("localTable");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screen;

    }
    private void onGlobalTable(MouseEvent e){
         new   JackInTheBox(borderPane).play();
        borderPane.setCenter(loadGlobalTable());
    }
    private Pane loadGlobalTable() {
        Pane screen = null;
        try {
            if(Main.getScreen("globalTable") instanceof Pane){
                screen = Main.getScreen("globalTable");

            }
            else{
                Main.addScreen("globalTable", FXMLLoader.load(getClass().getResource("/views/GlobalView/globalTableView.fxml")));
                screen = Main.getScreen("globalTable");

            }
             } catch (IOException e) {
            e.printStackTrace();
        }
        return screen;

    }

    private  void onLocalChart(MouseEvent e){
        new FadeOutRightBig(borderPane).playOnFinished(new FadeInRightBig(borderPane)).play();
        borderPane.setCenter(onLocalChart());
    }
    private Pane onLocalChart() {
        Pane screen = null;
        try {
            if(Main.getScreen("localChart") instanceof  Pane){
                screen = Main.getScreen("localChart");

            }
            else{
                Main.addScreen("localChart", FXMLLoader.load(getClass().getResource("/views/LocalCharts/localChartView.fxml")));
                screen = Main.getScreen("localChart");

            }
           } catch (IOException e) {
            e.printStackTrace();
        }
        return screen;

    }
    private  void onGlobalChart(MouseEvent e){
        new FadeOutRightBig(borderPane).playOnFinished(new FadeInRightBig(borderPane)).play();
        borderPane.setCenter(loadGlobalChart());
    }
    private Pane loadGlobalChart() {
        Pane screen = null;
        try {

            Main.addScreen("globalChart", FXMLLoader.load(getClass().getResource("/views/GlobalView/globalChartView.fxml")));
            screen = Main.getScreen("globalChart");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screen;

    }
    private void onLocalGraph(MouseEvent e){
        new FadeOutRightBig(borderPane).playOnFinished(new FadeInRightBig(borderPane)).play();
        borderPane.setCenter(loadLocalGraph());
    }
    private Pane loadLocalGraph() {
        Pane screen = null;
        try {


                Main.addScreen("localGraph", FXMLLoader.load(getClass().getResource("/views/LocalGraph/localViewController.fxml")));
                screen = Main.getScreen("localGraph");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return screen;

    }

}
