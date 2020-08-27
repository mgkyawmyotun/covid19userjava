package controllers;


import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;


import javafx.stage.StageStyle;
import models.UserModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import utils.HttpService;

import java.awt.*;

import java.io.IOException;


import java.net.URL;


import java.util.HashMap;

public class Main extends Application {
    public static HashMap<String, Pane> screenMap = new HashMap<>();
    public static Scene sc = null;

    public  static Stage stage =null;
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
        sc = new Scene(getComponent("login"));

        stage = primaryStage;
           //  activate("dashboard",200,200);
         activate("login");
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

    public static void removeScreen(String name) {
        screenMap.remove(name);
    }
    public static void addComponent(String name, Pane pane) {

        screenMap.put(name, pane);

    }
    public  static  Pane getComponent(String name){

        return screenMap.get(name);
    }
    public static void activate(String name,double width1,double height1) {

        screenMap.get(name);

        sc.setRoot(screenMap.get(name));
        stage.sizeToScene();

    }
    public static void activate(String name) {

        screenMap.get(name);
        sc.setRoot(screenMap.get(name));
    }

    @Override
    public void stop() throws Exception {

        System.exit(0);

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
