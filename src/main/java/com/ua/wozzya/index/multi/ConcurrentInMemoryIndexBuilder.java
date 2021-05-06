package com.ua.wozzya.index.multi;

import com.ua.wozzya.index.AbstractIndexBuilder;
import com.ua.wozzya.index.Index;

public class ConcurrentInMemoryIndexBuilder extends AbstractIndexBuilder implements ConcurrentIndexBuilder {

    public static final int MAX_THREAD_NUM = 20;

    private int threadNum = Runtime.getRuntime().availableProcessors();

    @Override
    public void setNumberOfProcessingThreads(int threadNum) {
        if (threadNum <= 0 || threadNum >= MAX_THREAD_NUM) {
            throw new IllegalArgumentException("amount of processing threads should be >= 1");
        }

        this.threadNum = threadNum;
    }

    @Override
    public Index build() {
        return new ConcurrentInMemoryInvertedIndex(fileNameListExtractor,
                tokenizer,
                iteratorFileLineExtractor,
                threadNum,
                autoBuild);
    }
}
