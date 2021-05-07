package com.ua.wozzya;

import com.ua.wozzya.index.multi.ConcurrentInMemoryIndexBuilder;
import com.ua.wozzya.utils.extractor.FileNameListExtractor;
import com.ua.wozzya.utils.extractor.DirsFileNamesFileNameListExtractor;
import com.ua.wozzya.utils.extractor.ReusableFileLineIterator;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.index.IndexBuilder;
import com.ua.wozzya.utils.tokenizer.SimpleTokenizer;
import com.ua.wozzya.utils.tokenizer.Token;
import com.ua.wozzya.utils.tokenizer.Tokenizer;

import java.util.Set;

public class Demo {

    /**
     * Usage example
     */
    public static void main(String[] args) {
        //root dirs to files
        String[] paths = {"test/", "train/"};

        // get extractor instance
        var extractor =
                DirsFileNamesFileNameListExtractor.createExtractor(paths);

        //set tokenizer
        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);

        // create index
        IndexBuilder builder = new ConcurrentInMemoryIndexBuilder();
        builder.setFileLineExtractor(new ReusableFileLineIterator());
        builder.setTokenizer(tokenizer);
        builder.setFileNameExtractor(extractor);
        builder.setAutoBuild(true);

        var index = builder.build();


        // get list of file names by word
        Set<String> fileNames = index.search("suspect was killed");

        fileNames.forEach(System.out::println);
    }
}
