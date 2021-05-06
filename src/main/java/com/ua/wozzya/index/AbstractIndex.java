package com.ua.wozzya.index;

import com.ua.wozzya.utils.extractor.FileLineIterator;
import com.ua.wozzya.utils.extractor.ListExtractor;
import com.ua.wozzya.utils.tokenizer.Tokenizer;

public abstract class AbstractIndex implements Index {
    protected final ListExtractor<String> listExtractor;
    protected final Tokenizer tokenizer;
    protected final FileLineIterator lineIterator;

    /**
     * @param listExtractor    implementation of {@link ListExtractor <String>}
     * @param tokenizer        implementation of {@link Tokenizer}
     * @param fileLineIterator implementation of {@link FileLineIterator}
     */
    protected AbstractIndex(ListExtractor<String> listExtractor, Tokenizer tokenizer, FileLineIterator fileLineIterator) {
        this.listExtractor = listExtractor;
        this.tokenizer = tokenizer;
        this.lineIterator = fileLineIterator;
    }

    /**
     * Initialize index storage
     */
    protected abstract void initIndex();

    /**
     * Checks if index was built
     */
    protected void indexReadinessCheck() {
        if (!isReady()) {
            throw new IllegalStateException("index should be built first");
        }
    }

}
