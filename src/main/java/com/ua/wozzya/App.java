package com.ua.wozzya;

import com.google.common.base.Stopwatch;
import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.extractor.FileNameExtractor;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.index.IndexBuilder;
import com.ua.wozzya.index.single.InMemoryIndexBuilder;
import com.ua.wozzya.index.single.InMemoryInvertedIndexStandalone;
import com.ua.wozzya.tokenizer.SimpleTokenizer;
import com.ua.wozzya.tokenizer.Token;
import com.ua.wozzya.tokenizer.Tokenizer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App 
{
    /**
     * Usage example
     */
    public static void main( String[] args )  {
        //root dirs to files
        String[] paths = {"test/", "train/"};

        // get extractor instance
        FileNameExtractor extractor = FileNameExtractor.createExtractor(paths);
        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);

        Stopwatch stopwatch = Stopwatch.createStarted();
        // create index
        IndexBuilder builder = new InMemoryIndexBuilder();
        builder.setTokenizer(tokenizer).setExtractor(extractor).setAutoBuild(true);
        Index index = builder.build();

        stopwatch.stop();
        System.out.println("Build time: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // get list of file names by word
        List<String> fileNames = index.search("the");
        System.out.println(index.getFrequency("the"));
        System.out.println(fileNames.size());

        // print all
        fileNames.forEach(System.out::println);
    }

    public static void extractorExtractorTest(Extractor<String> extractor) {
        extractor.extract().forEach(System.out::println);
    }
}
