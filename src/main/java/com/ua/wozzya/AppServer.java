package com.ua.wozzya;

import com.ua.wozzya.index.multi.ConcurrentInMemoryIndexBuilder;
import com.ua.wozzya.index.multi.ConcurrentIndexBuilder;
import com.ua.wozzya.server.IndexServerManager;
import com.ua.wozzya.utils.extractor.DirsFileNamesFileNameListExtractor;
import com.ua.wozzya.utils.extractor.FileLineIterator;
import com.ua.wozzya.utils.extractor.FileNameListExtractor;
import com.ua.wozzya.utils.extractor.ReusableFileLineIterator;
import com.ua.wozzya.utils.tokenizer.SimpleTokenizer;
import com.ua.wozzya.utils.tokenizer.Token;
import com.ua.wozzya.utils.tokenizer.Tokenizer;

public class AppServer {
    public static void main(String[] args) {
        System.out.println("app server starts");

        String[] scanFolders = {"test/","train/"};


        ConcurrentIndexBuilder builder = new ConcurrentInMemoryIndexBuilder();

        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);
        FileLineIterator lineExtractor = new ReusableFileLineIterator();
        FileNameListExtractor fileNameExtractor = DirsFileNamesFileNameListExtractor.createExtractor(scanFolders);
        builder.setAutoBuild(true);
        builder.setTokenizer(tokenizer);
        builder.setNumberOfProcessingThreads(4);
        builder.setFileNameExtractor(fileNameExtractor);
        builder.setFileLineExtractor(lineExtractor);

        var index = builder.build();
        System.out.println("index was built");

        var serverManager = new IndexServerManager(2236,index);
        serverManager.start();
    }
}
