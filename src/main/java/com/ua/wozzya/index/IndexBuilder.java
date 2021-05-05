package com.ua.wozzya.index;

import com.ua.wozzya.extractor.ListExtractor;
import com.ua.wozzya.extractor.ReusableFileLineIterator;
import com.ua.wozzya.tokenizer.Tokenizer;

public interface IndexBuilder {
    void setFileNameListExtractor(ListExtractor<String> listExtractor);
    void setTokenizer(Tokenizer tokenizer);
    void setAutoBuild(boolean autoBuild);
    void setFileLineExtractor(ReusableFileLineIterator iteratorFileLineExtractor);
    Index build();
}
