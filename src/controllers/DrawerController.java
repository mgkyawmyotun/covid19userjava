package controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DrawerController {
    @FXML
    private JFXHamburger hamberger;

    @FXML
    private JFXDrawer drawer;
    @FXML
    private  void initialize(){
        try {
            VBox vb = FXMLLoader.load(getClass().getResource("../views/sideBar.fxml"));

            drawer.setSidePane(vb);

        } catch (IOException e) {
            System.out.println("Erro");
        }
        HamburgerBasicCloseTransition hst = new HamburgerBasicCloseTransition(hamberger);
        hst.setRate(-1);
        hamberger.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            hst.setRate(hst.getRate() * -1);
            hst.play();
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });
    }
}
