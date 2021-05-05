package com.ua.wozzya.index;

import com.ua.wozzya.extractor.FileLineExtractor;
import com.ua.wozzya.extractor.ListExtractor;
import com.ua.wozzya.extractor.IteratorFileFileLineExtractor;
import com.ua.wozzya.tokenizer.Tokenizer;

public abstract class AbstractIndex implements Index {
    protected final ListExtractor<String> listExtractor;
    protected final Tokenizer tokenizer;
    protected final FileLineExtractor fileReader;

    /**
     * @param listExtractor instance of {@link ListExtractor <String>}
     * @param tokenizer instance of {@link Tokenizer}
     * @param fileReader
     */
    protected AbstractIndex(ListExtractor<String> listExtractor, Tokenizer tokenizer, IteratorFileFileLineExtractor fileReader) {
        this.listExtractor = listExtractor;
        this.tokenizer = tokenizer;
        this.fileReader = fileReader;
    }

    protected abstract void initIndex();

    protected void buildCheck() {
        if (!isReady()) {
            throw new IllegalStateException("index should be built first");
        }
    }

}
