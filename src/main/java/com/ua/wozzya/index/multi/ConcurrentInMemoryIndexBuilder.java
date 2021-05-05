package com.ua.wozzya.index.multi;

import com.ua.wozzya.index.AbstractIndexBuilder;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.index.IndexBuilder;

public class ConcurrentInMemoryIndexBuilder extends AbstractIndexBuilder implements ConcurrentIndexBuilder {

    public static final int DEFAULT_PARALLEL_SECTIONS = 1;

    private int parallelAccessSections = DEFAULT_PARALLEL_SECTIONS;
    private int threadNum = Runtime.getRuntime().availableProcessors();


    @Override
    public IndexBuilder setParallelAccessSections(int sections) {
        if(sections <= 0) {
            throw new IllegalArgumentException("amount of sections should be >= 1");
        }

        this.parallelAccessSections = sections;
        return this;
    }

    @Override
    public IndexBuilder setNumberOfProcessingThreads(int threadNum) {
        if(threadNum <= 0) {
            throw new IllegalArgumentException("amount of processing threads should be >= 1");
        }
        this.threadNum = threadNum;
        return this;
    }

    @Override
    public Index build() {
        return new ConcurrentInMemoryInvertedIndex(extractor, tokenizer, parallelAccessSections, threadNum, autoBuild);
    }
}
