package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView image1;
    @FXML
    private Text loading;
    @FXML
    private ImageView image2;
    @FXML
    private Text cv;

    @FXML
    private Text welcome;

    @FXML
    private Text from;

    @FXML
    void initialize() {

        new ZoomInDown(welcome).setDelay(Duration.millis(500)).play();
        new LightSpeedOut(image1).setCycleCount(-1).setDelay(Duration.millis(1000)).play();
        LightSpeedOut lightSpeedOut = new LightSpeedOut(image2);
        lightSpeedOut.setCycleCount(-1).setDelay(Duration.millis(1000)).play();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {

            Main.stage.hide();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setFullScreen(false);
            stage.setTitle("Covid-19 Tracker App");
            stage.centerOnScreen();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/views/Images/covid1.png")));
            stage.setScene(new Scene(Main.getScreen("dashboard")));
            stage.show();

        });
        delay.play();
    }
}