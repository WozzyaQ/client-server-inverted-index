package com.ua.wozzya;

import com.google.common.base.Stopwatch;
import com.ua.wozzya.extractor.ListExtractor;
import com.ua.wozzya.extractor.DirsFileNamesListExtractor;
import com.ua.wozzya.extractor.ReusableFileLineIterator;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.index.IndexBuilder;
import com.ua.wozzya.index.multi.ConcurrentInMemoryIndexBuilder;
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
        String[] paths = {"test/","train/"};

        // get extractor instance
        DirsFileNamesListExtractor extractor = DirsFileNamesListExtractor.createExtractor(paths);
        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);

        Stopwatch stopwatch = Stopwatch.createStarted();
        // create index
        IndexBuilder builder = new ConcurrentInMemoryIndexBuilder();
        builder.setFileLineExtractor(new ReusableFileLineIterator());
        builder.setTokenizer(tokenizer);
        builder.setFileNameListExtractor(extractor);
        builder.setAutoBuild(true);

        Index index = builder.build();

        stopwatch.stop();
        System.out.println("Build time: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // get list of file names by word

        stopwatch.start();
        Set<String> fileNames = index.search("template");
        System.out.println(index.getFrequency("template"));
        System.out.println(fileNames.size());
        stopwatch.stop();
        System.out.println("Query1 time: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        stopwatch.start();
        Set<String> fileNames2 = index.search("index");
        System.out.println(index.getFrequency("index"));
        System.out.println(fileNames2.size());
        stopwatch.stop();
        System.out.println("Query2 time: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));


        // print all
        fileNames.forEach(System.out::println);
        System.out.println("finished");
        System.out.println("active threads " + Thread.activeCount());
    }

    public static void extractorExtractorTest(ListExtractor<String> listExtractor) {
        listExtractor.extract().forEach(System.out::println);
    }
}
