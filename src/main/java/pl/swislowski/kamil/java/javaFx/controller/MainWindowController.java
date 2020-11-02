package pl.swislowski.kamil.java.javaFx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.swislowski.kamil.java.javaFx.model.ProcessFilesResultModel;
import pl.swislowski.kamil.java.javaFx.service.MainWindowService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;


public class MainWindowController {
    private static final Logger LOGGER = Logger.getLogger(MainWindowController.class.getName());
    //    private Main main;
    private Stage primaryStage;

    private MainWindowService mainWindowService;

    private File selectedFile;

    @FXML
    private Label chosenFileDirectoryLabel;
    @FXML
    private Label tempDirectoryPathLabel;
    @FXML
    private TextField fileExtensionTextField;
    @FXML
    private TextField wantedBytesTextField;
    @FXML
    private TextField swapBytesTextField;
    @FXML
    private Button replaceBytesButton;

    public MainWindowController() {
        this.mainWindowService = new MainWindowService();
    }

    public void initialize() {
    }

    public static Stage createStage(FXMLLoader loader, Stage primaryStage, String title) throws IOException {
        Parent pane = loader.load();

        Stage stage = new Stage();
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);

        Scene scene = new Scene(pane);
        stage.setScene(scene);

        stage.show();

        return stage;
    }

//    public static Stage createStage(FXMLLoader loader, Stage primaryStage, String title) {
//
//        return stage;
//
//    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
//    public void setMain(Main main) {
//        this.main = main;
//    }

    @FXML
    public void replaceBytes() throws Exception {
        LOGGER.info("replaceBytes method invocation #############");
//        BytesManipulation bytesManipulation = new BytesManipulation();
//        bytesManipulation.replace(null, null);

        FXMLLoader loader = new FXMLLoader(ResultWindowController.class.getClassLoader().getResource("views/ResultWindowView.fxml"));


        try {

            Stage stage = ResultWindowController.createStage(loader, primaryStage, "Result Window");

            ResultWindowController controller = loader.getController();
            controller.setPrimaryStage(stage);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void chooseDirectoryAction() {
//        FileChooser fileChooser = new FileChooser();
//        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        DirectoryChooser directoryChooser = new DirectoryChooser();
        selectedFile = directoryChooser.showDialog(primaryStage);
        chosenFileDirectoryLabel.setText(selectedFile.getAbsolutePath());


    }

    private void noFileExtensionAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "You haven't chosen the file extension!"
                , ButtonType.OK);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    @FXML
    public void showResultAction() throws Exception {
        if (selectedFile != null) {

            String fileExtension = fileExtensionTextField.getText();
            String wantedBytesString = wantedBytesTextField.getText();
            String swapBytesString = swapBytesTextField.getText();

            LOGGER.info("###### fileextension : " + fileExtension);
            if (fileExtension != null && !fileExtension.equals("")
                    && wantedBytesString != null && !wantedBytesString.equals("")
                    && swapBytesString != null && !swapBytesString.equals("")) {


                List<File> fileList = mainWindowService.directorySearch(selectedFile, fileExtension);

                ProcessFilesResultModel processFilesResultModel = mainWindowService.processFiles(fileList, wantedBytesString.getBytes(), swapBytesString.getBytes());
                Path tempDirPath = processFilesResultModel.getTempDirPath();
                if (tempDirPath != null) {
                    tempDirectoryPathLabel.setText(tempDirPath.toString());
                }
            } else {
                noFileExtensionAlert();
            }
        }
    }

}
