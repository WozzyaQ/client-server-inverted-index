package com.ua.wozzya.index;

import com.ua.wozzya.utils.extractor.ListExtractor;
import com.ua.wozzya.utils.extractor.ReusableFileLineIterator;
import com.ua.wozzya.utils.tokenizer.Tokenizer;

/**
 * Builder interface
 */
public interface IndexBuilder {
    void setFileNameListExtractor(ListExtractor<String> listExtractor);

    void setTokenizer(Tokenizer tokenizer);

    void setAutoBuild(boolean autoBuild);

    void setFileLineExtractor(ReusableFileLineIterator iteratorFileLineExtractor);

    Index build();
}
