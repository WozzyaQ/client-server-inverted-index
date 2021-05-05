package com.ua.wozzya;

import com.google.common.base.Stopwatch;
import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.extractor.DirsFileNamesExtractor;
import com.ua.wozzya.extractor.FileLineExtractor;
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
        DirsFileNamesExtractor extractor = DirsFileNamesExtractor.createExtractor(paths);
        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);

        Stopwatch stopwatch = Stopwatch.createStarted();
        // create index
        IndexBuilder builder = new InMemoryIndexBuilder();
        builder.setFileLineExtractor(new FileLineExtractor());
        builder.setTokenizer(tokenizer);
        builder.setExtractor(extractor);
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

    public static void extractorExtractorTest(Extractor<String> extractor) {
        extractor.extract().forEach(System.out::println);
    }
}
