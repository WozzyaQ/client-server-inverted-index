package com.ua.wozzya.index;

import com.ua.wozzya.utils.extractor.FileLineIterator;
import com.ua.wozzya.utils.extractor.FileNameListExtractor;
import com.ua.wozzya.utils.tokenizer.Tokenizer;

public abstract class AbstractIndex implements Index {
    protected final FileNameListExtractor fileNameListExtractor;
    protected final Tokenizer tokenizer;
    protected final FileLineIterator lineIterator;

    /**
     * @param fileNameListExtractor    implementation of {@link FileNameListExtractor <String>}
     * @param tokenizer        implementation of {@link Tokenizer}
     * @param fileLineIterator implementation of {@link FileLineIterator}
     */
    protected AbstractIndex(FileNameListExtractor fileNameListExtractor, Tokenizer tokenizer, FileLineIterator fileLineIterator) {
        this.fileNameListExtractor = fileNameListExtractor;
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
