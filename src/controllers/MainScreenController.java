package controllers;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.*;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
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
    private ImageView image3;
    @FXML
    private Text cv;

    @FXML
    private Text welcome;

    @FXML
    private Text from;

    @FXML
    void initialize() {

        RotateTransition rotateTransition4 =new RotateTransition(Duration.seconds(2),image3);
        rotateTransition4.setFromAngle(0);
        rotateTransition4.setToAngle(360);
        rotateTransition4.setCycleCount(Transition.INDEFINITE);
        rotateTransition4.play();
        RotateTransition rotateTransition1 = new RotateTransition(Duration.seconds(3), image1);
        rotateTransition1.setFromAngle(0);
        rotateTransition1.setToAngle(360);

        rotateTransition1.setCycleCount(Transition.INDEFINITE);
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), image2);
        rotateTransition.setFromAngle(-360);
        rotateTransition.setToAngle(0);

        rotateTransition.setCycleCount(Transition.INDEFINITE);
        rotateTransition.play();
        rotateTransition1.play();
        new ZoomInDown(welcome).setDelay(Duration.millis(500)).play();
        new LightSpeedOut(image1).setCycleCount(-1).setDelay(Duration.millis(1000)).play();
        LightSpeedOut lightSpeedOut = new LightSpeedOut(image2);
        lightSpeedOut.setCycleCount(-1).setDelay(Duration.millis(1000)).play();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {

            Main.stage.close();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setFullScreen(false);
            stage.setTitle("Covid-19 Tracker App");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/views/Images/covid1.png")));
            stage.setScene(new Scene(Main.getScreen("dashboard")));
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();
            stage.setWidth(width-200);
            stage.setHeight(height-200);
            stage.centerOnScreen();
            stage.show();

        });
        delay.play();
    }
}