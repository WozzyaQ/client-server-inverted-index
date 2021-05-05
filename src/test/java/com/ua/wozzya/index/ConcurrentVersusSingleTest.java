package com.ua.wozzya.index;

import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.extractor.DirsFileNamesExtractor;
import com.ua.wozzya.extractor.FileLineExtractor;
import com.ua.wozzya.index.multi.ConcurrentInMemoryIndexBuilder;
import com.ua.wozzya.index.single.InMemoryIndexBuilder;
import com.ua.wozzya.tokenizer.SimpleTokenizer;
import com.ua.wozzya.tokenizer.Token;
import com.ua.wozzya.tokenizer.Tokenizer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConcurrentVersusSingleTest {

    Index single;
    Index multi;

    FileLineExtractor lineExtractor = new FileLineExtractor();
    Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);
    String[] directories = {"test/","train/"};
    Extractor<String> fileNameExtractor = DirsFileNamesExtractor.createExtractor(directories);

    @Before
    public void setUp() {
        single = buildSingle();
        multi = buildMulti();
    }

    private Index buildMulti() {
        IndexBuilder builder = new ConcurrentInMemoryIndexBuilder();
        builder.setFileLineExtractor(lineExtractor);
        builder.setAutoBuild(true);
        builder.setTokenizer(tokenizer);
        builder.setExtractor(fileNameExtractor);
        return builder.build();
    }

    public Index buildSingle() {
        IndexBuilder builder = new InMemoryIndexBuilder();
        builder.setFileLineExtractor(lineExtractor);
        builder.setAutoBuild(true);
        builder.setTokenizer(tokenizer);
        builder.setExtractor(fileNameExtractor);
        return builder.build();
    }

    @Test
    public void shouldReturnSameFiles() {
        Set<String> singleResponse = single.search("mirror");
        Set<String> multiResponse = multi.search("mirror");

        int singleSize = singleResponse.size();
        int multiSize = multiResponse.size();

        assertEquals(singleSize, multiSize);

        singleResponse.removeAll(multiResponse);
        assertEquals(0, singleResponse.size());

    }

    @Test
    public void shouldReturnSameFilesLineByLineAssertion() {
        Set<String> singleResponse = single.search("yellow");
        Set<String> multiResponse = multi.search("yellow");

        List<String> singleList = new ArrayList<>(singleResponse);
        List<String> multiList = new ArrayList<>(multiResponse);

        assertEquals(singleList.size(), multiList.size());
        for (int i = 0; i < singleList.size(); ++i) {
            String curInSingle = singleList.get(i);
            String curInMulti = multiList.get(i);
            assertEquals(curInMulti, curInSingle);
        }
    }

    @Test
    public void concurrentShouldBeFasterThanSingleInMostCases() {
        int runs = 100;

        int singleWins = 0;
        int multiWins = 0;
        for(int i = 0; i < runs; ++i) {
            long startSingle = System.currentTimeMillis();
            buildSingle();
            long endSingle = System.currentTimeMillis();
            long singleTime = endSingle - startSingle;

            long startMulti = System.currentTimeMillis();
            buildMulti();
            long endMulti = System.currentTimeMillis();
            long multiTime = endMulti - startMulti;

            System.out.println("single time -->" + singleTime);
            System.out.println("concurrent time -->" + multiTime);
            if(multiTime < singleTime) {
                multiWins++;
            } else {
                singleWins++;
            }
        }

        assertTrue(multiWins > singleWins);
    }
}
