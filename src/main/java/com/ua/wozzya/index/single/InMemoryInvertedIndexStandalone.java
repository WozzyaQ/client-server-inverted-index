package com.ua.wozzya.index.single;

import com.ua.wozzya.Pair;
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

    private boolean readyMarker;
    private static Pair<Long,Set<String>> EMPTY_PAIR = new Pair<>(0L, Collections.emptySet());
    private Map<String, Pair<Long, Set<String>>> index;



    protected InMemoryInvertedIndexStandalone(Extractor<String> extractor, Tokenizer tokenizer, boolean autobuild) {
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
        if(readyMarker) return;

        initIndex();

        List<String> fileNames = extractor.extract();

        for (String fileName : fileNames) {
            collectFromFileAndStore(new File(fileName));
        }
        readyMarker = true;
    }

    @Override
    public boolean isReady() {
        return readyMarker;
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
            Pair<Long, Set<String>> pair = index.getOrDefault(token, new Pair<>(0L, new HashSet<>()));

            pair.setLeft(pair.getLeft() + 1);
            Set<String> curSet = pair.getRight();
            curSet.add(fileName);
            index.put(token, pair);
        }
    }

    @Override
    public List<String> search(String key) {
        if(!readyMarker) {
            throw new IllegalStateException("index should be built before calling search");
        }

        Objects.requireNonNull(key);
        key = key.toLowerCase();

        var pair = index.getOrDefault(key, EMPTY_PAIR);

        return pair.getRight()
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public long getFrequency(String key) {
        if(!readyMarker) {
            throw new IllegalStateException("index should be built before calling search");
        }

        Objects.requireNonNull(key);
        key = key.toLowerCase();

        var pair = index.getOrDefault(key, EMPTY_PAIR);
        return pair.getLeft();
    }
}
