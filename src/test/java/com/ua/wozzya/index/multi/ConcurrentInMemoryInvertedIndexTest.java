package com.ua.wozzya.index.multi;

import com.ua.wozzya.extractor.DirsFileNamesExtractor;
import com.ua.wozzya.extractor.FileLineExtractor;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.index.IndexBuilder;
import com.ua.wozzya.tokenizer.SimpleTokenizer;
import com.ua.wozzya.tokenizer.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static org.junit.Assert.*;

public class ConcurrentInMemoryInvertedIndexTest {

    static final String CONTENT = "Process finished finished finished with with exit code 0";
    static final String TEMP_FILE_NAME = "temp.txt";
    static final String TEMP_PATH = "temp/";
    static final DirsFileNamesExtractor EXTRACTOR = DirsFileNamesExtractor.createExtractor(TEMP_PATH);

    @Before
    public  void setUp() throws IOException {

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

    @After
    public  void tearDown() throws IOException {
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
        IndexBuilder builder = new ConcurrentInMemoryIndexBuilder();
        builder.setAutoBuild(false);
        builder.setExtractor(EXTRACTOR);
        builder.setTokenizer(new SimpleTokenizer(Token.WORD));
        Index index = builder.build();
        index.search("some-word");
    }

    @Test
    public void shouldFindAndReturnFileName()  {
        IndexBuilder builder = new ConcurrentInMemoryIndexBuilder();
        builder.setAutoBuild(true);
        builder.setExtractor(EXTRACTOR);
        builder.setTokenizer(new SimpleTokenizer(Token.WORD));
        builder.setFileLineExtractor(new FileLineExtractor());
        Index index = builder.build();

        Set<String> result =  index.search("finished");

        int expectedSize = 1;
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize);


        String expectedFileName = TEMP_PATH + TEMP_FILE_NAME;
        String actualFileName = new ArrayList<>(result).get(0);
        assertEquals(expectedFileName, actualFileName);

    }

    @Test
    public void shouldNotFindAndReturnEmptyList() {
        IndexBuilder builder = new ConcurrentInMemoryIndexBuilder();
        builder.setExtractor(EXTRACTOR);
        builder.setTokenizer(new SimpleTokenizer(Token.WORD));
        builder.setFileLineExtractor(new FileLineExtractor());
        Index index = builder.build();
        index.buildIndex();
        Set<String> result = index.search("gapldspld");

        int expectedSize = 0;
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize);
    }
}