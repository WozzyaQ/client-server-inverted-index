package com.ua.wozzya;

import com.google.common.base.Stopwatch;
import com.ua.wozzya.extractor.ListExtractor;
import com.ua.wozzya.extractor.DirsFileNamesListExtractor;
import com.ua.wozzya.extractor.IteratorFileFileLineExtractor;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.index.IndexBuilder;
import com.ua.wozzya.index.single.InMemoryIndexBuilder;
import com.ua.wozzya.tokenizer.SimpleTokenizer;
import com.ua.wozzya.tokenizer.Token;
import com.ua.wozzya.tokenizer.Tokenizer;

import java.util.Set;
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
        DirsFileNamesListExtractor extractor = DirsFileNamesListExtractor.createExtractor(paths);
        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);

        Stopwatch stopwatch = Stopwatch.createStarted();
        // create index
        IndexBuilder builder = new InMemoryIndexBuilder();
        builder.setFileLineExtractor(new IteratorFileFileLineExtractor());
        builder.setTokenizer(tokenizer);
        builder.setFileNameListExtractor(extractor);
        builder.setAutoBuild(true);
        Index index = builder.build();

        stopwatch.stop();
        System.out.println("Build time: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // get list of file names by word
        Set<String> fileNames = index.search("the");
        System.out.println(index.getFrequency("the"));
        System.out.println(fileNames.size());

        // print all
        fileNames.forEach(System.out::println);

    }

    public static void extractorExtractorTest(ListExtractor<String> listExtractor) {
        listExtractor.extract().forEach(System.out::println);
    }
}
