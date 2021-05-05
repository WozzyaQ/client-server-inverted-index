package com.ua.wozzya.index;

import com.ua.wozzya.extractor.ListExtractor;
import com.ua.wozzya.extractor.IteratorFileFileLineExtractor;
import com.ua.wozzya.tokenizer.Tokenizer;

import java.util.Objects;

public abstract class AbstractIndexBuilder implements IndexBuilder{
    protected ListExtractor<String> listExtractor;
    protected Tokenizer tokenizer;
    protected IteratorFileFileLineExtractor iteratorFileLineExtractor;
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
    public void setFileLineExtractor(IteratorFileFileLineExtractor iteratorFileLineExtractor) {
        Objects.requireNonNull(iteratorFileLineExtractor);

        this.iteratorFileLineExtractor = iteratorFileLineExtractor;
    }
}
