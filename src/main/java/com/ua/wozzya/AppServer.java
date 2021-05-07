package com.ua.wozzya;

import com.ua.wozzya.index.multi.ConcurrentInMemoryIndexBuilder;
import com.ua.wozzya.index.multi.ConcurrentIndexBuilder;
import com.ua.wozzya.server.MultiConnectionIndexServer;
import com.ua.wozzya.utils.extractor.DirsFileNamesFileNameListExtractor;
import com.ua.wozzya.utils.extractor.FileLineIterator;
import com.ua.wozzya.utils.extractor.FileNameListExtractor;
import com.ua.wozzya.utils.extractor.ReusableFileLineIterator;
import com.ua.wozzya.utils.tokenizer.SimpleTokenizer;
import com.ua.wozzya.utils.tokenizer.Token;
import com.ua.wozzya.utils.tokenizer.Tokenizer;


public class AppServer {
    public static void main(String[] args) {
        if (args.length <= 1) {
            System.out.println("error: requires at least 2 arguments");
            System.out.println("usage: ./runserver <port> <path-list>");
            return;
        }

        System.out.println("app server starts");

        String[] paths = new String[args.length - 1];
        int port = Integer.parseInt(args[0]);
        System.arraycopy(args, 1, paths, 0, args.length - 1);

        ConcurrentIndexBuilder builder = new ConcurrentInMemoryIndexBuilder();

        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);
        FileLineIterator lineExtractor = new ReusableFileLineIterator();
        FileNameListExtractor fileNameExtractor = DirsFileNamesFileNameListExtractor.createExtractor(paths);
        builder.setAutoBuild(true);
        builder.setTokenizer(tokenizer);
        builder.setNumberOfProcessingThreads(4);
        builder.setFileNameExtractor(fileNameExtractor);
        builder.setFileLineExtractor(lineExtractor);

        var index = builder.build();
        System.out.println("index was built");

        var serverManager = new MultiConnectionIndexServer(port, index);
        serverManager.start();
    }
}
