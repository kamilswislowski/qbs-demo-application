package pl.swislowski.kamil.java.javaFx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultWindowController {
    private Stage primaryStage;

    public void initialize(){
    }

    public static Stage createStage(FXMLLoader loader, Stage primaryStage, String title) throws IOException {
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.showAndWait();

        return stage;
    }

    @FXML
    private void closeButtonAction(){
        this.primaryStage.close();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
