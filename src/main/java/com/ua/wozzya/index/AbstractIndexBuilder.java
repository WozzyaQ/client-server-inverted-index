package com.ua.wozzya.index;

import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.extractor.FileLineExtractor;
import com.ua.wozzya.tokenizer.Tokenizer;

import java.util.Objects;

public abstract class AbstractIndexBuilder implements IndexBuilder{
    protected Extractor<String> extractor;
    protected Tokenizer tokenizer;
    protected FileLineExtractor fileLineExtractor;
    protected boolean autoBuild;

    @Override
    public void setExtractor(Extractor<String> extractor) {
        Objects.requireNonNull(extractor);

        this.extractor = extractor;
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
    public void setFileLineExtractor(FileLineExtractor fileLineExtractor) {
        Objects.requireNonNull(fileLineExtractor);

        this.fileLineExtractor = fileLineExtractor;
    }
}
