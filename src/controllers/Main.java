package controllers;



import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;



import java.awt.*;

import java.io.IOException;


import java.net.URISyntaxException;


import java.text.ParseException;

import java.util.HashMap;

public class Main extends Application {
    public static HashMap<String, Pane> screenMap = new HashMap<>();
    public static Scene sc = null;

    public  static Stage stage =null;
    public static void main(String[] args) throws IOException, URISyntaxException, ParseException {
          launch(args);
    }


    @Override
    public void init() throws Exception {

        super.init();
    }


    @Override
    public void start(Stage primaryStage) throws IOException {

        addScreen("dashboard",FXMLLoader.load(getClass().getResource("/views/dashboard.fxml")));

        sc = new Scene(getScreen("dashboard"));

        stage = primaryStage;

        stage.setScene(sc);
        stage.setFullScreen(false);
        stage.setTitle("Covid-19 Tracker App");
        stage.centerOnScreen();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/views/Images/covid1.png")));
        stage.show();
    }


    public static void addScreen(String name, Pane pane) {

        screenMap.put(name, pane);
    }

    public  static  Pane getScreen(String name){
        return screenMap.get(name);
    }


    public  static  void load(Pane pane){

            stage.getScene().setRoot(pane);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.hide();
            stage.show();

    }
    @Override
    public void stop() throws Exception {

        System.exit(0);

    }


}
