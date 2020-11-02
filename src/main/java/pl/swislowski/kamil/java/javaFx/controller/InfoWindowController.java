package pl.swislowski.kamil.java.javaFx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoWindowController {
    private Stage primaryStage;

    @FXML
    private Button goMainWindowViewButton;

    @FXML
    private void goMainWindowView(){
        FXMLLoader loader = new FXMLLoader(MainWindowController.class.getClassLoader().getResource("views/MainWindowView.fxml"));


        try {

            Stage stage = MainWindowController.createStage(loader, primaryStage, "Main Window");

            MainWindowController controller = loader.getController();
            controller.setPrimaryStage(stage);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
