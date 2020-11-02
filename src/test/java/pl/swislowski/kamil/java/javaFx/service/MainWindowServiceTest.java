package pl.swislowski.kamil.java.javaFx.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class MainWindowServiceTest {

    private static final String FILTERED_FILE_CORRECT_EXTENSION = ".file";
    private static final String FILTERED_FILE_CORRECT_EXTENSION_WITHOUT_DOT = "exe";
    private static final String FILTERED_FILE_CORRECT_EXTENSION_WITH_DOUBLE_DOT = "..exe";
    private static final String FILTERED_FILE_WRONG_EXTENSION = ".exe";
    private static final int FILTERED_FILES_EXPECTED_SIZE_0 = 0;
    private static final int FILTERED_FILES_EXPECTED_SIZE_1 = 1;
    private static final int FILES_WITH_EXE_EXTENSION_COUNT = 2;
    private static final String FILTERED_FILE_CORRECT_EXTENSION_WITH_NONALPHANUMERIC = "$$__$$@exe....";

    @Test
    void givenServiceWithDirectory_whenDirectorySearch_thenFilesWithChosenExtensionCountIsCorrect() {
        //given:
        MainWindowService mainWindowService = new MainWindowService();
        Path views = Paths.get("src/test/resources/views");
        File filePath = views.toFile();
        //when:
        List<File> files = mainWindowService.directorySearch(filePath, FILTERED_FILE_CORRECT_EXTENSION_WITHOUT_DOT);
        //then:
        Assertions.assertEquals(FILES_WITH_EXE_EXTENSION_COUNT, files.size(), "Size of the list isn't equal with expected value.");

    }

    @Test
    void givenServiceWithDirectory_whenDirectorySearch_thenFilesWithChosenExtensionWithNonAlphanumericCountIsCorrect() {
        //given:
        MainWindowService mainWindowService = new MainWindowService();
        Path views = Paths.get("src/test/resources/views");
        File filePath = views.toFile();
        //when:
        List<File> files = mainWindowService.directorySearch(filePath, FILTERED_FILE_CORRECT_EXTENSION_WITH_NONALPHANUMERIC);
        //then:
        Assertions.assertEquals(FILES_WITH_EXE_EXTENSION_COUNT, files.size(), "Size of the list isn't equal with expected value.");

    }

    @Test
    void givenService_whenFilter_thenAssertTrue() throws IOException {
        //given:
        MainWindowService mainWindowService = new MainWindowService();
        Path tempPathCorrect = Files.createTempFile("hello", FILTERED_FILE_CORRECT_EXTENSION);
        File file = tempPathCorrect.toFile();
        //when:
        boolean filter = mainWindowService.filter(file, FILTERED_FILE_CORRECT_EXTENSION);
        //then:
        Assertions.assertTrue(filter, "Assertion returns false.");

    }

    @Test
    void givenService_whenFilterFiles_thenFileListNotNull() throws IOException {
        //given:
        MainWindowService mainWindowService = new MainWindowService();
        Path temp = Files.createTempFile("hello", FILTERED_FILE_CORRECT_EXTENSION);
        File[] files = {temp.toFile()};
        //when:
        List<File> fileList = mainWindowService.filterFiles(files, FILTERED_FILE_CORRECT_EXTENSION);
        //then:
        Assertions.assertNotNull(fileList, "File list is null!");
    }

    @Test
    void givenService_whenFilterFiles_thenFileSizesAreEquals() throws IOException {
        //given:
        MainWindowService mainWindowService = new MainWindowService();
        Path tempPathCorrect = Files.createTempFile("hello", FILTERED_FILE_CORRECT_EXTENSION);
        Path tempPathWrong = Files.createTempFile("hello", FILTERED_FILE_WRONG_EXTENSION);
        File[] files = {tempPathCorrect.toFile(), tempPathWrong.toFile()};
        //when:
        List<File> fileList = mainWindowService.filterFiles(files, FILTERED_FILE_CORRECT_EXTENSION);
        //then:
        Assertions.assertEquals(FILTERED_FILES_EXPECTED_SIZE_1, fileList.size(), "List size aren't equals.");
    }

    @Test
    void givenService_whenFilterFilesWithNoDot_thenFileSizesAreEquals() throws IOException {
        //given:
        MainWindowService mainWindowService = new MainWindowService();
        Path tempPathCorrect = Files.createTempFile("hello", FILTERED_FILE_CORRECT_EXTENSION);
        Path tempPathWrong = Files.createTempFile("hello", FILTERED_FILE_WRONG_EXTENSION);
        File[] files = {tempPathCorrect.toFile(), tempPathWrong.toFile()};
        //when:
        List<File> fileList = mainWindowService.filterFiles(files, FILTERED_FILE_CORRECT_EXTENSION_WITHOUT_DOT);
        //then:
        Assertions.assertEquals(FILTERED_FILES_EXPECTED_SIZE_1, fileList.size(), "List size aren't equals.");
    }

    @Test
    void givenService_whenFilterFilesWithDoubleDot_thenFileSizesAreEquals() throws IOException {
        //given:
        MainWindowService mainWindowService = new MainWindowService();
        Path tempPathCorrect = Files.createTempFile("hello", FILTERED_FILE_CORRECT_EXTENSION);
        Path tempPathWrong = Files.createTempFile("hello", FILTERED_FILE_WRONG_EXTENSION);
        File[] files = {tempPathCorrect.toFile(), tempPathWrong.toFile()};
        //when:
        List<File> fileList = mainWindowService.filterFiles(files, FILTERED_FILE_CORRECT_EXTENSION_WITH_DOUBLE_DOT);
        //then:
        Assertions.assertEquals(FILTERED_FILES_EXPECTED_SIZE_0, fileList.size(), "List size aren't equals.");
    }

    @Test
    void readFile() throws IOException {
        //given:
        MainWindowService mainWindowService = new MainWindowService();

        Path tempDirPath = Files.createTempDirectory("tempDirReplacedFilesTest");
        Path tempPathCorrect = Files.createTempFile("hello", FILTERED_FILE_CORRECT_EXTENSION);
        Path tempPathWrong = Files.createTempFile("hello", FILTERED_FILE_WRONG_EXTENSION);

        File file = tempPathCorrect.toFile();
        File fileToBytesArray = tempPathWrong.toFile();

        byte[] bytes = Files.readAllBytes(fileToBytesArray.toPath());
        byte[] swapBytes = {1, 4, 6};
        //when:
        File returnedFile = mainWindowService.readFile(file, bytes, tempDirPath, swapBytes);
        System.out.println("returnedFile : " + returnedFile);
        byte[] bytesFromReturnedFile = Files.readAllBytes(returnedFile.toPath());
        //then:
//        Assertions.assertArrayEquals(bytes,bytes);
        Assertions.assertArrayEquals(bytes, bytesFromReturnedFile);
    }

    @Test
    void saveFile() throws IOException {
        //given:
        MainWindowService mainWindowService = new MainWindowService();
        Path dataPath = Paths.get("src/test/resources/views/data.txt");
        byte[] bytes = Files.readAllBytes(dataPath);
        Path tempDirPath = Files.createTempDirectory("tempDirReplacedFilesTest");

        //when:
        Path saveFilePath = mainWindowService.saveFile(bytes, tempDirPath);
        System.out.println("######### : " + saveFilePath);

    }

    @Test
    void removeAllNonAlphanumeric() {
        //given:
        MainWindowService mainWindowService = new MainWindowService();
        //when:
        String s = mainWindowService.removeAllNonAlphanumeric(FILTERED_FILE_CORRECT_EXTENSION_WITH_DOUBLE_DOT);
        //then:
        Assertions.assertEquals(s.length(), FILTERED_FILE_CORRECT_EXTENSION_WITHOUT_DOT.length(), "Lengths aren't equal.");
    }

    @Test
    void processFiles() throws IOException {
        //given:
        MainWindowService mainWindowService = new MainWindowService();

        File file = Paths.get("src/test/resources/views/data.txt").toFile();
        byte[] bytes = Files.readAllBytes(file.toPath());

        List<File> files = new ArrayList<>();
        files.add(Paths.get("src/test/resources/views/data.txt").toFile());
        files.add(Paths.get("src/test/resources/views/koty.txt").toFile());

        //when:
        //FIXME : Napisać test do processFiles() - poprawne parametry i tp.

        byte[] wantedBytes = {97, 32, 107, 111, 116, 97, 46, 32};
        byte[] swapBytes = {11, 22, 33, 55, 66, 34, 21, 12};
        byte[] resultBytes = {65, 108, 97, 32, 109, 11, 22, 33, 55, 66, 34, 21, 12, 65, 32, 106, 97, 32, 109, 97, 109, 32, 109, 97, 107, 105, 46};
        mainWindowService.processFiles(files, wantedBytes, swapBytes);

        //then:
//        Assertions.assertArrayEquals(resultBytes, bytes, "Size of arrays isn't equal.");

    }
}