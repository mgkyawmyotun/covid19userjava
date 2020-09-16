package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    void initialize() {
        Image image =new Image(getClass().getResourceAsStream("/views/Images/covid1.png"));
        ImageView imageView =new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setX(20);
        imageView.setY(20);
        Image imageTwo =new Image(getClass().getResourceAsStream("/views/Images/covid1.png"));
        ImageView imageViewTwo =new ImageView(image);
        imageViewTwo.setFitWidth(100);
        imageViewTwo.setFitHeight(100);
        imageViewTwo.setX(20);

   //     imageViewTwo.setY(anchorPane.getParent().getLayoutX());
        anchorPane.getChildren().addAll(imageView,imageViewTwo);
    }
}