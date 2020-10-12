package pl.swislowski.kamil.java.javaFx.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainWindowService {

    public void directorySearch(File file, String extension) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            filterFiles(files, extension);
        }
    }

    List<File> filterFiles(File[] files, String extension) {
//        List<File> filteredFiles = new ArrayList<>();
//        for (File file : files) {
//            String fileName = file.getName();
//            if (fileName.contains(extension)) {
//                filteredFiles.add(file);
//            }
//        }
//        return filteredFiles;
        return Arrays.stream(files)
                .filter(file -> file.getName().contains(extension))
                .collect(Collectors.toList());
    }
}
