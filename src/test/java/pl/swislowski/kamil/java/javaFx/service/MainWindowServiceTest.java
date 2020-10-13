package pl.swislowski.kamil.java.javaFx.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class MainWindowServiceTest {

    private static final String FILTERED_FILE_CORRECT_EXTENSION = ".file";
    private static final String FILTERED_FILE_CORRECT_EXTENSION_WITHOUT_DOT = "exe";
    private static final String FILTERED_FILE_CORRECT_EXTENSION_WITH_DOUBLE_DOT = "..exe";
    private static final String FILTERED_FILE_WRONG_EXTENSION = ".exe";
    private static final int FILTERED_FILES_EXPECTED_SIZE_0 = 0;
    private static final int FILTERED_FILES_EXPECTED_SIZE_1 = 1;
    private static final int ALL_FILES_COUNT = 4;

    @Test
    void givenServiceWithDirectory_whenDirectorySearch_thenFilesCountIsCorrect() {
        //given:
        MainWindowService mainWindowService = new MainWindowService();
        Path views = Paths.get("src/test/resources/views");
        File filePath = views.toFile();
        //when:
        List<File> files = mainWindowService.directorySearch(filePath, FILTERED_FILE_CORRECT_EXTENSION_WITHOUT_DOT);
        //then:
        Assertions.assertEquals(ALL_FILES_COUNT, files.size(), "Size of the list isn't equal with expected value.");

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
    void given_when_then() throws IOException {
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
    void given_when_then2() throws IOException {
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
    void given_when_then3() throws IOException {
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
}