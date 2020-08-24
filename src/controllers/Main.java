package controllers;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.layout.Pane;

import javafx.stage.Stage;


import utils.HttpService;

import java.awt.*;
import java.io.IOException;

import java.net.URL;



import java.util.HashMap;

public class Main extends Application {
    public static HashMap<String, Pane> screenMap = new HashMap<>();
    public static Scene sc = null;


    public static void main(String[] args) throws IOException {

        launch(args);
    }

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static double width = screenSize.getWidth();
    static double height = screenSize.getHeight();

    @Override
    public void init() throws Exception {
        addComponent("caseComponent",FXMLLoader.load(getClass().getResource("/views/components/case.fxml")));
        addScreen("dashboard", FXMLLoader.load(getClass().getResource("/views/dashboard.fxml")));
        addScreen("login",FXMLLoader.load(getClass().getResource("/views/login.fxml")));
        super.init();
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        sc = new Scene(getComponent("dashboard"));
        activate("dashboard");
        primaryStage.setScene(sc);
        primaryStage.setFullScreen(false);

        primaryStage.show();
    }


    public static void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    public static void removeScreen(String name) {
        screenMap.remove(name);
    }
    public static void addComponent(String name, Pane pane) {
        screenMap.put(name, pane);

    }
    public  static  Pane getComponent(String name){
        return screenMap.get(name);
    }
    public static void activate(String name) {
        screenMap.get(name).setPrefSize(width - 200, height - 200);
        sc.setRoot(screenMap.get(name));
    }

    public  static  Object getController(URL location){
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(location);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fxmlLoader.getController();
    }
}
