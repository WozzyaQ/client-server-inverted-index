package com.ua.wozzya.index;

import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.tokenizer.Tokenizer;

public abstract class AbstractIndex implements Index {
    protected final Extractor<String> extractor;
    protected final Tokenizer tokenizer;

    /**
     * @param extractor instance of {@link Extractor<String>}
     * @param tokenizer instance of {@link Tokenizer}
     */
    protected AbstractIndex(Extractor<String> extractor, Tokenizer tokenizer) {
        this.extractor = extractor;
        this.tokenizer = tokenizer;
    }

    protected abstract void initIndex();

}
