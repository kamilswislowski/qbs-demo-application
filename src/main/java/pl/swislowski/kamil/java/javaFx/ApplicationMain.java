package pl.swislowski.kamil.java.javaFx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.swislowski.kamil.java.javaFx.controller.InfoWindowController;

public class ApplicationMain extends Application {
//    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getClassLoader().getResource("views/InfoWindowView.fxml"));
        Parent root = loader.load();
        InfoWindowController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Okno powitalne");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    private void mainWindow() {
//        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getClassLoader().getResource("views/InfoWindowView.fxml"));
//        try {
//
//            Parent root = loader.load();
////            primaryStage.setMinWidth(600.0);
////            primaryStage.setMinHeight(400.0);
//
//            Scene scene = new Scene(root);
//
//            InfoWindowController infoWindowController = loader.getController();
//
//            if (infoWindowController != null) {
//                infoWindowController.setPrimaryStage(primaryStage);
//            }
//
//            primaryStage.setTitle("Info Window");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
