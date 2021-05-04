package com.ua.wozzya;

import com.google.common.base.Stopwatch;
import com.ua.wozzya.extractor.FileNameExtractor;
import com.ua.wozzya.index.InMemoryInvertedIndex;
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
    public static void main( String[] args ) throws IOException {
        //root dirs to files
        String[] paths = {"test/", "train/"};

        // get extractor instance
        FileNameExtractor extractor = FileNameExtractor.createExtractor(paths);
        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);

        Stopwatch stopwatch = Stopwatch.createStarted();
        // create index
        InMemoryInvertedIndex index = new InMemoryInvertedIndex(extractor, tokenizer, true);
        stopwatch.stop();
        System.out.println("Build time: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        // get list of file names by word
        List<String> fileNames = index.search("movies");
        System.out.println(fileNames.size());

        // print all
        fileNames.forEach(System.out::println);
    }
}
