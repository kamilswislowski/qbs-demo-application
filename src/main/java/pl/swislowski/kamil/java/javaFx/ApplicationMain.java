package pl.swislowski.kamil.java.javaFx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.swislowski.kamil.java.javaFx.controller.InfoWindowController;

public class ApplicationMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getClassLoader().getResource("views/InfoWindowView.fxml"));
        Parent root = loader.load();
        InfoWindowController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Okno powitalne");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
