package com.ua.wozzya.index;

import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.tokenizer.Tokenizer;

import java.util.Objects;

public abstract class AbstractIndexBuilder implements IndexBuilder{
    protected Extractor<String> extractor;
    protected Tokenizer tokenizer;
    protected boolean autoBuild;

    @Override
    public IndexBuilder setExtractor(Extractor<String> extractor) {
        Objects.requireNonNull(extractor);

        this.extractor = extractor;
        return this;
    }

    @Override
    public IndexBuilder setTokenizer(Tokenizer tokenizer) {
        Objects.requireNonNull(tokenizer);

        this.tokenizer = tokenizer;
        return this;
    }

    @Override
    public IndexBuilder setAutoBuild(boolean autoBuild) {
        this.autoBuild = autoBuild;
        return this;
    }
}
