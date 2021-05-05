package com.ua.wozzya.index;

import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.extractor.FileLineExtractor;
import com.ua.wozzya.tokenizer.Tokenizer;

public abstract class AbstractIndex implements Index {
    protected final Extractor<String> extractor;
    protected final Tokenizer tokenizer;
    protected final FileLineExtractor fileReader;

    /**
     * @param extractor instance of {@link Extractor<String>}
     * @param tokenizer instance of {@link Tokenizer}
     * @param fileReader
     */
    protected AbstractIndex(Extractor<String> extractor, Tokenizer tokenizer, FileLineExtractor fileReader) {
        this.extractor = extractor;
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
