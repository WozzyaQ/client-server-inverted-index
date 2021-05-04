package com.ua.wozzya.index;


import com.ua.wozzya.Token;
import com.ua.wozzya.extractor.Extractor;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InMemoryInvertedIndex implements Index{

    private Map<String, List<String>> index = new LinkedHashMap<>();
    private Extractor<String> extractor;
    private boolean ready = false;

    /**
     *
     * @param extractor - not-nilled extractor
     */
    public InMemoryInvertedIndex(Extractor<String> extractor) {
        this(extractor, false);
    }

    public InMemoryInvertedIndex(Extractor<String> extractor, boolean autobuild) {
        Objects.requireNonNull(extractor);
        this.extractor = extractor;

        if(autobuild) buildIndex();
    }


    private void buildIndex() {
        if(ready) return;

        List<String> fileNames = extractor.extract();

        for (String fileName : fileNames) {
            collectFromFileAndStore(new File(fileName));
        }
        ready = true;
    }

    private void collectFromFileAndStore(File file) {
        try (FileInputStream fis = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader buffReader = new BufferedReader(reader)) {

            String line;
            while((line = buffReader.readLine()) != null) {
                String[] tokens = tokenize(line);
                store(tokens, file.getPath());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void store(String[] tokens, String fileName) {
        for(String token : tokens) {
            List<String> list = index.getOrDefault(token, new LinkedList<>());
            list.add(fileName);
            index.put(token, list);
        }
    }

    private String[] tokenize(String line) {
        Pattern p = Pattern.compile(Token.UNIQUE_WORD_PATTERN);
        Matcher m = p.matcher(line);

        List<String> tokenList = new LinkedList<>();
        while(m.find()) {
            tokenList.add(m.group(Token.UNIQUE_WORD_GROUP_INDEX).toLowerCase());
        }
        return tokenList.toArray(new String[0]);
    }

    /**
     *
     * @param key - word to search
     * @return - list of file names
     */
    @Override
    public List<String> search(String key) {
        if(!ready) {
            throw new IllegalStateException("index should be built before calling search");
        }

        Objects.requireNonNull(key);
        key = key.toLowerCase();
        return index.getOrDefault(key, Collections.emptyList());
    }
}
