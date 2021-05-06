package com.ua.wozzya.index;

import com.ua.wozzya.utils.extractor.FileLineIterator;
import com.ua.wozzya.utils.extractor.FileNameListExtractor;
import com.ua.wozzya.utils.tokenizer.Tokenizer;

/**
 * Builder interface
 */
public interface IndexBuilder {
    void setFileNameExtractor(FileNameListExtractor fileNameListExtractor);

    void setTokenizer(Tokenizer tokenizer);

    void setAutoBuild(boolean autoBuild);

    void setFileLineExtractor(FileLineIterator iteratorFileLineExtractor);

    Index build();
}
