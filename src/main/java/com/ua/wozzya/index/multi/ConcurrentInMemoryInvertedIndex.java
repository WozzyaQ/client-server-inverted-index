package com.ua.wozzya.index.multi;

import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.index.AbstractIndex;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.tokenizer.Tokenizer;

import java.util.List;

public class ConcurrentInMemoryInvertedIndex extends AbstractIndex implements Index {

    private final int parallelAccessSections;
    private final int threadNum;
    private volatile boolean readyMarker = false;

    protected ConcurrentInMemoryInvertedIndex(Extractor<String> extractor, Tokenizer tokenizer, int parallelAccessSections, int threadNum, boolean autoBuild) {
        super(extractor, tokenizer);

        this.parallelAccessSections = parallelAccessSections;
        this.threadNum = threadNum;

        if (autoBuild) {
            buildIndex();
        }
    }

    @Override
    protected void initIndex() {
        buildIndex();
    }

    @Override
    public void buildIndex() {
        initIndex();

        postBuildSectionSplit();
        readyMarker = true;
    }

    private void postBuildSectionSplit() {

    }


    @Override
    public List<String> search(String key) {
        return null;
    }

    @Override
    public long getFrequency(String key) {
        return 0;
    }


    @Override
    public boolean isReady() {
        return readyMarker;
    }
}
