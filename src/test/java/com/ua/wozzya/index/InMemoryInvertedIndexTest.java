package com.ua.wozzya.index;

import com.ua.wozzya.extractor.FileNameExtractor;
import com.ua.wozzya.tokenizer.SimpleTokenizer;
import com.ua.wozzya.tokenizer.Token;
import com.ua.wozzya.tokenizer.Tokenizer;
import org.junit.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class InMemoryInvertedIndexTest {

    static final String CONTENT = "Process finished with exit code 0";
    static final String TEMP_FILE_NAME = "temp.txt";
    static final String TEMP_PATH = "temp/";
    static final FileNameExtractor EXTRACTOR = FileNameExtractor.createExtractor(TEMP_PATH);

    @BeforeClass
    public static void setUp() throws IOException {

        Path path = Paths.get(TEMP_PATH);
        Path filePath = Paths.get(TEMP_PATH + TEMP_FILE_NAME);

        if(!Files.exists(path)) {
            Files.createDirectory(path);
            Files.createFile(filePath);

            FileOutputStream fos = new FileOutputStream(String.valueOf(filePath));
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(CONTENT);
            osw.close();
            fos.close();
        }
    }

    @AfterClass
    public static void tearDown() {
        File dir = new File(TEMP_PATH);
        File[] files = dir.listFiles();

        if(files != null) {
            for(File file : files) {
                file.delete();
            }
        }
        dir.delete();
    }

    @Test
    public void testContent() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(TEMP_PATH+TEMP_FILE_NAME));

        String actualContent = null;
        if(sc.hasNextLine()) {
            actualContent = sc.nextLine();
        }

        assertNotNull(actualContent);
        assertEquals(CONTENT, actualContent);
    }

    @Test
    public void extractorShouldReturnSingleFileName() {
        int expectedSize = 1;
        List<String> fileNames = EXTRACTOR.extract();
        int actualSize = fileNames.size();


        assertEquals(expectedSize, actualSize);

        String expectedFileName = TEMP_PATH + TEMP_FILE_NAME;
        String actualFileName = fileNames.get(0);

        assertEquals(expectedFileName, actualFileName);

    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalWhenTryingToFindWithoutBuilding() {
        InMemoryInvertedIndex index = new InMemoryInvertedIndex(EXTRACTOR);
        index.search("some-word");
    }

    @Test
    public void shouldFindAndReturnFileName() throws IOException {
        InMemoryInvertedIndex index = new InMemoryInvertedIndex(EXTRACTOR, new SimpleTokenizer(Token.WORD));
        index.buildIndex();
        List<String> result = index.search("finished");

        int expectedSize = 1;
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize);


        String expectedFileName = new File(TEMP_PATH + TEMP_FILE_NAME).getPath();
        String actualFileName = result.get(0);
        assertEquals(expectedFileName, actualFileName);

    }

    @Test
    public void shouldNotFindAndReturnEmptyList() {
        InMemoryInvertedIndex index = new InMemoryInvertedIndex(EXTRACTOR);
        index.buildIndex();
        List<String> result = index.search("gapldspld");

        int expectedSize = 0;
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize);
    }
}