package com.ua.wozzya.index;

import com.ua.wozzya.utils.extractor.FileLineIterator;
import com.ua.wozzya.utils.extractor.FileNameListExtractor;
import com.ua.wozzya.utils.tokenizer.Tokenizer;

import java.util.Objects;

/**
 * Abstract implementation of {@link IndexBuilder} interface
 * Encapsulates common setter logic
 */
//TODO builder directors
public abstract class AbstractIndexBuilder implements IndexBuilder {
    protected FileNameListExtractor fileNameListExtractor;
    protected Tokenizer tokenizer;
    protected FileLineIterator iteratorFileLineExtractor;
    protected boolean autoBuild;

    @Override
    public void setFileNameExtractor(FileNameListExtractor fileNameListExtractor) {
        Objects.requireNonNull(fileNameListExtractor, "line extractor should not be null");
        this.fileNameListExtractor = fileNameListExtractor;
    }

    @Override
    public void setTokenizer(Tokenizer tokenizer) {
        Objects.requireNonNull(tokenizer, "tokenizer should not be null");
        this.tokenizer = tokenizer;
    }

    @Override
    public void setAutoBuild(boolean autoBuild) {
        this.autoBuild = autoBuild;
    }

    @Override
    public void setFileLineExtractor(FileLineIterator iteratorFileLineExtractor) {
        Objects.requireNonNull(iteratorFileLineExtractor, "reusableFileLineIterator should not be null");
        this.iteratorFileLineExtractor = iteratorFileLineExtractor;
    }
}
