package controllers;


import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;


import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import utils.HttpService;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Main extends Application {
    public static HashMap<String, Pane> screenMap = new HashMap<>();
    public static Scene sc = null;


    public static void main(String[] args) throws IOException {


//        JSONArray countryJson =HttpService.getCasesByCountriesAsJson();
//
//        for(int i=0;i<countryJson.length();i++){
//            JSONObject jsonObject =countryJson.getJSONObject(i);
//            String imagePath = jsonObject.getJSONObject("countryInfo").getString("flag");
//            Connection.Response resultImageResponse = Jsoup.connect(imagePath)
//                    .ignoreContentType(true).execute();
//
//            FileOutputStream out = (new FileOutputStream(new java.io.File("/views/images/country/one.png" )));
//            out.write(resultImageResponse.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
//            out.close();
//        }

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
        primaryStage.setTitle("Covid-19 Tracker App");
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/views/images/covid1.png")));
        primaryStage.setIconified(true);
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
