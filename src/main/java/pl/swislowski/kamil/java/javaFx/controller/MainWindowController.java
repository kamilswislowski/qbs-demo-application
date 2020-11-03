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
import org.apache.commons.lang3.StringUtils;
import pl.swislowski.kamil.java.javaFx.exception.ProcessFilesException;
import pl.swislowski.kamil.java.javaFx.model.ProcessFilesResultModel;
import pl.swislowski.kamil.java.javaFx.service.MainWindowService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;


public class MainWindowController {
    private static final Logger LOGGER = Logger.getLogger(MainWindowController.class.getName());
    private Stage primaryStage;

    private MainWindowService mainWindowService;

    private File selectedFile;

    @FXML
    private Label chosenFileDirectoryLabel;
    @FXML
    private TextField fileTempDirPathTextField;
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
        fileTempDirPathTextField.setEditable(false);
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

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void chooseDirectoryAction() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        selectedFile = directoryChooser.showDialog(primaryStage);
        chosenFileDirectoryLabel.setText(selectedFile.getAbsolutePath());
    }

    private void alertDialog(String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING, contentText, ButtonType.OK);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    @FXML
    public void showResultAction() throws ProcessFilesException {
        if (selectedFile != null) {

            String fileExtension = fileExtensionTextField.getText();
            String wantedBytesString = wantedBytesTextField.getText();
            String swapBytesString = swapBytesTextField.getText();

            LOGGER.info("###### fileextension : " + fileExtension);
            if (fileExtension != null && !fileExtension.equals("")
                    && wantedBytesString != null && !wantedBytesString.equals("")
                    && swapBytesString != null && !swapBytesString.equals("")) {

                List<File> fileList = mainWindowService.directorySearch(selectedFile, fileExtension);
                boolean alphanumeric = StringUtils.isAlphanumeric(fileExtension);
                if (alphanumeric) {

                    ProcessFilesResultModel processFilesResultModel = mainWindowService.processFiles(fileList, wantedBytesString.getBytes(), swapBytesString.getBytes());
                    Path tempDirPath = processFilesResultModel.getTempDirPath();
                    if (tempDirPath != null) {
                        fileTempDirPathTextField.setText(tempDirPath.toString());
                    }

                } else {
                    alertDialog("You have provided incorrect characters! ");
                }

            } else {
                alertDialog("You haven't chosen the file extension!");
            }
        }
    }
}
