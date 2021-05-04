package com.ua.wozzya.index;

import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.tokenizer.Tokenizer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of an single
 * threaded, single-pass inverted index
 * that processes data in-memory
 */
public class InMemoryInvertedIndex implements Index{

    private final Map<String, Set<String>> index = new HashMap<>();
    private final Extractor<String> extractor;
    private final Tokenizer tokenizer;
    private boolean ready = false;


    /**
     * Initialize index with supplied extractor
     * Need to manually build with {@link InMemoryInvertedIndex#buildIndex()}
     * before starting to using
     * @param extractor instance of {@link Extractor<String>}
     */
    public InMemoryInvertedIndex(Extractor<String> extractor) {
        this(extractor, Tokenizer.getDefault());
    }

    /**
     * Initialize index with supplied extractor and tokenizer
     * Need to manually build with {@link InMemoryInvertedIndex#buildIndex()}
     * before starting to using
     * @param extractor instance of {@link Extractor<String>}
     * @param tokenizer instance of {@link Tokenizer}
     */
    public InMemoryInvertedIndex(Extractor<String> extractor, Tokenizer tokenizer) {
        this(extractor, tokenizer,false);
    }

    /**
     * @param extractor instance of {@link Extractor<String>}
     * @param tokenizer instance of {@link Tokenizer}
     * @param autobuild build on instantiation
     */
    public InMemoryInvertedIndex(Extractor<String> extractor, Tokenizer tokenizer, boolean autobuild) {
        Objects.requireNonNull(extractor);
        Objects.requireNonNull(tokenizer);

        this.extractor = extractor;
        this.tokenizer = tokenizer;

        if(autobuild) buildIndex();
    }

    public void buildIndex() {
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
        if(!ready) {
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
