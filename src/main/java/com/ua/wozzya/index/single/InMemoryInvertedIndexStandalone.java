package com.ua.wozzya.index.single;

import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.index.AbstractIndex;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.tokenizer.Tokenizer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of an single
 * threaded, single-pass inverted index
 * that processes data in-memory
 */
public class InMemoryInvertedIndexStandalone extends AbstractIndex implements Index {

    private boolean isReady;
    private Map<String, Set<String>> index;

    public InMemoryInvertedIndexStandalone(Extractor<String> extractor, Tokenizer tokenizer, boolean autobuild) {
        super(extractor, tokenizer);

        if(autobuild) {
            buildIndex();
        }
    }

    @Override
    protected void initIndex() {
        index = new HashMap<>();
    }

    @Override
    public void buildIndex() {
        if(isReady) return;

        initIndex();

        List<String> fileNames = extractor.extract();

        for (String fileName : fileNames) {
            collectFromFileAndStore(new File(fileName));
        }
        isReady = true;
    }

    @Override
    public boolean isReady() {
        return isReady;
    }

    private void collectFromFileAndStore(File file) {
        try (FileInputStream fis = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader buffReader = new BufferedReader(reader)) {

            String line;
            while((line = buffReader.readLine()) != null) {
                String[] tokens = tokenizer.tokenize(line);
                store(tokens, file.getPath());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void store(String[] tokens, String fileName) {
        for(String token : tokens) {
            Set<String> set = index.getOrDefault(token, new HashSet<>());
            set.add(fileName);
            index.put(token, set);
        }
    }

    @Override
    public List<String> search(String key) {
        if(!isReady) {
            throw new IllegalStateException("index should be built before calling search");
        }

        Objects.requireNonNull(key);
        key = key.toLowerCase();
        return index.getOrDefault(key, Collections.emptySet())
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
