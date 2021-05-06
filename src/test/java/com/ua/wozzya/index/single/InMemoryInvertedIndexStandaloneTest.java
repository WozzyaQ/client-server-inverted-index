package com.ua.wozzya.index.single;

import com.ua.wozzya.index.TestIndexUtils;
import com.ua.wozzya.utils.extractor.ReusableFileLineIterator;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.index.IndexBuilder;
import com.ua.wozzya.utils.tokenizer.SimpleTokenizer;
import com.ua.wozzya.utils.tokenizer.Token;
import org.junit.*;

import java.io.*;

import java.util.*;

import static com.ua.wozzya.index.TestIndexUtils.*;
import static org.junit.Assert.*;

public class InMemoryInvertedIndexStandaloneTest {

    @Before
    public void setUp() throws IOException {
        TestIndexUtils.setUp();
    }

    @After
    public void tearDown() throws IOException {
        TestIndexUtils.tearDown();
    }

    @Test
    public void testContent() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(TEMP_PATH + TEMP_FILE_NAME));

        String actualContent = null;
        if (sc.hasNextLine()) {
            actualContent = sc.nextLine();
        }

        assertNotNull(actualContent);
        assertEquals(SINGLE_LINE_CONTENT, actualContent);
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
        IndexBuilder builder = new InMemoryIndexBuilder();
        builder.setAutoBuild(false);
        builder.setFileNameListExtractor(EXTRACTOR);
        builder.setTokenizer(new SimpleTokenizer(Token.WORD));
        Index index = builder.build();
        index.search("some-word");
    }

    @Test
    public void shouldFindAndReturnFileName() {
        IndexBuilder builder = new InMemoryIndexBuilder();
        builder.setAutoBuild(true);
        builder.setFileNameListExtractor(EXTRACTOR);
        builder.setTokenizer(new SimpleTokenizer(Token.WORD));
        builder.setFileLineExtractor(new ReusableFileLineIterator());
        Index index = builder.build();

        Set<String> result = index.search("finished");

        int expectedSize = 1;
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize);


        String expectedFileName = TEMP_PATH + TEMP_FILE_NAME;
        String actualFileName = new ArrayList<>(result).get(0);
        assertEquals(expectedFileName, actualFileName);

    }

    @Test
    public void shouldNotFindAndReturnEmptyList() {
        IndexBuilder builder = new InMemoryIndexBuilder();
        builder.setFileNameListExtractor(EXTRACTOR);
        builder.setTokenizer(new SimpleTokenizer(Token.WORD));
        builder.setFileLineExtractor(new ReusableFileLineIterator());
        Index index = builder.build();
        index.buildIndex();
        Set<String> result = index.search("gapldspld");

        int expectedSize = 0;
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize);
    }
}