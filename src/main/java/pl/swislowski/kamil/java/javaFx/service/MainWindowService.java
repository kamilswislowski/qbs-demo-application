package pl.swislowski.kamil.java.javaFx.service;

import pl.swislowski.kamil.java.core.BytesManipulation;
import pl.swislowski.kamil.java.javaFx.exception.ProcessFilesException;
import pl.swislowski.kamil.java.javaFx.model.ProcessFilesResultModel;

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

    /**
     * Metoda rekurencyjnie przeszukująca katalogi i podkatalogi w poszukiwaniu plików o konkretnym rozszerzeniu.
     * @param file katalog źródłowy z którego rozpoczynamy poszukiwania plików
     * @param extension rozszerzenie wyszukiwanych plików
     * @return lista znalezionych plików, które spełniają kryteria wyszukiwania
     */
    public List<File> directorySearch(File file, String extension) {
        List<File> allSearchedFiles = new ArrayList<>();

        if (file.isDirectory()) {
            LOGGER.info("Directory : " + file);
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    //rekurencyjne wywołanie metody
                    List<File> partialFiles = directorySearch(f, extension);
                    allSearchedFiles.addAll(partialFiles);
                }
            }
        } else {
            LOGGER.info("File : " + file);
            boolean filter = fileChecker(file, extension);
            if (filter) {
                allSearchedFiles.add(file);
            }
        }

        return allSearchedFiles;
    }

    /**
     * Metoda przetwarzająca listę plików dla których wyszukiwany jest ciąg bajtów <code>wantedBytes</code>, a następnie
     * jest on zamieniany na inny ciąg bajtów <code>swapBytes</code>.
     * @param files lista plików do przetworzenia
     * @param wantedBytes wyszukiwany ciąg bajtów
     * @param swapBytes zamieniany ciąg bajtów
     * @return Zwraca obiekt klasy <code>{@link ProcessFilesResultModel}</code> zawierający wynik przetwarzania.
     * @throws ProcessFilesException Zgłaszany w momencie wystąpienia błędów wejścia wyjścia.
     */
    public ProcessFilesResultModel processFiles(List<File> files, byte[] wantedBytes, byte[] swapBytes) throws ProcessFilesException {
        LOGGER.info("processFiles()");
        ProcessFilesResultModel processFilesResultModel = new ProcessFilesResultModel();
        try {
            Path tempDirPath = Files.createTempDirectory("tempDirReplacedFiles");
            processFilesResultModel.setTempDirPath(tempDirPath);
            for (File file : files) {
                readFile(file, wantedBytes, swapBytes, tempDirPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new ProcessFilesException("Problem with creating temporary directory.", e);
        }

        return processFilesResultModel;
    }

    /**
     * Odczytuje zawartość pliku dla którego wyszukiwany jest ciąg bajtów <code>wantedBytes</code>, a następnie
     * jest on zamieniany na inny ciąg bajtów <code>swapBytes</code> i zapisywany do tymczasowego folderu.
     * @param file Plik, z którego odczytujemy zawartość.
     * @param wantedBytes wyszukiwany ciąg bajtów
     * @param swapBytes zamieniany ciąg bajtów
     * @param tempDirPath tymczasowy folder, do którego zostaną zapisane zmienione pliki
     * @return Zwraca zapisany i zmieniony plik.
     */
    File readFile(File file, byte[] wantedBytes, byte[] swapBytes, Path tempDirPath) {

        try {
            byte[] readBytes = Files.readAllBytes(file.toPath());
            BytesManipulation bytesManipulation = new BytesManipulation();

            bytesManipulation.replaceBytes(readBytes, wantedBytes, swapBytes);

            Path path = saveFile(readBytes, tempDirPath);
            LOGGER.info("#####Path : " + path);

            return path.toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Zapisuje wskazany ciąg bajtów do tymczasowego katalogu.
     * @param bytes ciąg bajtów do zapisania
     * @param tempDirPath tymczasowy katalog, do którego zapisujemy plik
     * @return zwraca informacje o zapisanym pliku w postaci obiektu klasy <code>Path</code>
     * @throws IOException Zgłaszany w momencie wystąpienia błędów wejścia wyjścia.
     */
    Path saveFile(byte[] bytes, Path tempDirPath) throws IOException {
        Path tempFilePath = Files.createTempFile(tempDirPath, "xxx", "yyy");
        return Files.write(tempFilePath, bytes);
    }

    /**
     * Filtruje tablicę plików po rozszerzeniu <code>extension</code> i zwraca listę pofiltrowanych plików.
     * @param files tablica filtrowanych plików
     * @param extension rozszerzenie po którym filtrujemy
     * @return listę przefiltrowanych plików
     */
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

    /**
     * Weryfikuje pliki niebędące katalogami o podanym rozszerzeniu.
     * @param file sprawdzany plik
     * @param extension wymagane rozszerzenie
     * @return <code>true</code> - jest plikiem o podanym rozszerzeniu, <code>false</code> - nie jest plikiem o podanym rozszerzeniu.
     */
    boolean fileChecker(File file, String extension) {
        if (!file.isDirectory() && file.getName().contains(extension)) {
            return true;
        } else {
            return false;
        }
    }
}
