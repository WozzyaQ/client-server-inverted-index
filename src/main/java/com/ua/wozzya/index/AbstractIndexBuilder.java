package com.ua.wozzya.index;

import com.ua.wozzya.extractor.ListExtractor;
import com.ua.wozzya.extractor.ReusableFileLineIterator;
import com.ua.wozzya.tokenizer.Tokenizer;

import java.util.Objects;

/**
 * Abstract implementation of {@link IndexBuilder} interface
 * Encapsulates common setter logic
 */
//TODO builder directors
public abstract class AbstractIndexBuilder implements IndexBuilder {
    protected ListExtractor<String> listExtractor;
    protected Tokenizer tokenizer;
    protected ReusableFileLineIterator iteratorFileLineExtractor;
    protected boolean autoBuild;

    @Override
    public void setFileNameListExtractor(ListExtractor<String> listExtractor) {
        Objects.requireNonNull(listExtractor);

        this.listExtractor = listExtractor;
    }

    @Override
    public void setTokenizer(Tokenizer tokenizer) {
        Objects.requireNonNull(tokenizer);

        this.tokenizer = tokenizer;
    }

    @Override
    public void setAutoBuild(boolean autoBuild) {
        this.autoBuild = autoBuild;
    }

    @Override
    public void setFileLineExtractor(ReusableFileLineIterator iteratorFileLineExtractor) {
        Objects.requireNonNull(iteratorFileLineExtractor);

        this.iteratorFileLineExtractor = iteratorFileLineExtractor;
    }
}
