package pl.swislowski.kamil.java.javaFx.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainWindowService {
    private static final Logger LOGGER = Logger.getLogger(MainWindowService.class.getName());

    public List<File> directorySearch(File file, String extension) {
        List<File> searchedFiles = new ArrayList<>();

        if (file.isDirectory()) {
            LOGGER.info("Directory : " + file);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                List<File> partialFiles = directorySearch(files[i], extension);
                searchedFiles.addAll(partialFiles);
            }
        } else {
            LOGGER.info("File : " + file);
            searchedFiles.add(file);
        }

        return searchedFiles;
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
