package com.ua.wozzya;

import com.ua.wozzya.extractor.FileNameExtractor;
import com.ua.wozzya.index.InMemoryInvertedIndex;

import java.io.IOException;
import java.util.List;

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

        // create index
        InMemoryInvertedIndex index = new InMemoryInvertedIndex(extractor, true);

        // get list of file names by word
        List<String> fileNames = index.search("movies");

        // print all
        fileNames.forEach(System.out::println);
    }
}
