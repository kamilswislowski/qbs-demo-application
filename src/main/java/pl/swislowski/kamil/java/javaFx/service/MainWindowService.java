package pl.swislowski.kamil.java.javaFx.service;

import pl.swislowski.kamil.java.core.BytesManipulation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainWindowService {
    private static final Logger LOGGER = Logger.getLogger(MainWindowService.class.getName());

    public List<File> directorySearch(File file, String extension) {
        List<File> searchedFiles = new ArrayList<>();

        String trimmedExtension = extension;
//                removeAllNonAlphanumeric(extension);

        if (file.isDirectory()) {
            LOGGER.info("Directory : " + file);
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    List<File> partialFiles = directorySearch(f, trimmedExtension);
                    searchedFiles.addAll(partialFiles);
                }
            }
        } else {
            LOGGER.info("File : " + file);
            boolean filter = filter(file, trimmedExtension);
            if (filter) {
                searchedFiles.add(file);
            }
        }

        return searchedFiles;
    }

    public String removeAllNonAlphanumeric(String string) {
        return string.replaceAll("[^a-zA-Z0-9]", "");
//        return string.trim();
    }

    boolean filter(File file, String extension) {
        if (!file.isDirectory() && file.getName().contains(extension)) {
            return true;
        } else {
            return false;
        }
    }

    public void processFiles(List<File> files, byte[] wantedBytes, byte[] swapBytes){
        LOGGER.info("processFiles()");
        //Przeiterować po liście
        try {
            Path tempDirPath = Files.createTempDirectory("tempDirReplacedFiles");
            for (File file : files) {
                readFile(file, wantedBytes, tempDirPath, swapBytes);
            }
            //TODO: Rzucić własny wyjątek biznesowy.
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Otworzyć pliki i odczytać zawartość
        //Wyszukać zadane ciągi bajtów
        //Zamienić je z nowym ciągiem bajtów
        //Stworzyć oddzielny folder
        //Zapisać zmodyfikowane pliki do oddzielnego folderu
    }

    public File readFile(File file, byte[] wantedBytes, Path tempDirPath, byte[] swapBytes) {

        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            BytesManipulation bytesManipulation = new BytesManipulation();

            byte[] bytes1 = bytesManipulation.replaceBytes(bytes, wantedBytes, swapBytes);
            LOGGER.info("###############replacedBytes : " + Arrays.toString(bytes1));

            Path path = saveFile(bytes, tempDirPath);
            LOGGER.info("#####Path : " + path);

            return path.toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Path saveFile(byte[] bytes, Path tempDirPath) throws IOException {
        Path tempFilePath = Files.createTempFile(tempDirPath, "xxx", "yyy");
        return Files.write(tempFilePath, bytes);
    }

    List<File> filterFiles(File[] files, String extension) {
        return Arrays.stream(files)
                .filter(file -> file.getName().contains(extension))
                .collect(Collectors.toList());
//        List<File> filteredFiles = new ArrayList<>();
//        for (File file : files) {
//            String fileName = file.getName();
//            if (fileName.contains(extension)) {
//                filteredFiles.add(file);
//            }
//        }
//        return filteredFiles;
    }
}
