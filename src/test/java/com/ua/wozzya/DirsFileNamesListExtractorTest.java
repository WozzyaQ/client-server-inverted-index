package com.ua.wozzya;

import com.ua.wozzya.utils.extractor.DirsFileNamesListExtractor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class DirsFileNamesListExtractorTest {

    static final String TEMP_PATH = "/tempo/";
    static final String TEMP_FILE = "temp.txt";

    @Before
    public void setUp() {
        Path path = Paths.get(TEMP_PATH);
        try {
            Files.createDirectories(path);
            Files.createFile(Path.of(TEMP_PATH + TEMP_FILE));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        File dir = new File(TEMP_PATH);
        deleteDir(dir);
    }

    public void deleteDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (final File file : files) {
                deleteDir(file);
            }
        }
        dir.delete();
    }


    @Test
    public void shouldReturnExtractorInstance() {
        DirsFileNamesListExtractor e = DirsFileNamesListExtractor.createExtractor("path1");
        assertNotNull(e);
    }

    @Test
    public void shouldExtractFileNamesFromPath() {
        String path = TEMP_PATH;

        DirsFileNamesListExtractor extractor = DirsFileNamesListExtractor.createExtractor(path);
        List<String> fileNames = extractor.extract();
        assertEquals(TEMP_PATH + TEMP_FILE, fileNames.get(0));
    }

    @Test
    public void extractedSizeShouldBeEqualToActual() {
        int expected = 250;
        DirsFileNamesListExtractor extractor = DirsFileNamesListExtractor.createExtractor("test/neg/");
        int actual = extractor.extract().size();
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowIllegalStateExceptionWhenNoPathSupplied() {
        DirsFileNamesListExtractor.createExtractor(null);
    }

}