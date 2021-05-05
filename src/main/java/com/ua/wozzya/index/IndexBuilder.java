package com.ua.wozzya.index;

import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.extractor.FileLineExtractor;
import com.ua.wozzya.tokenizer.Tokenizer;

public interface IndexBuilder {
    void setExtractor(Extractor<String> extractor);
    void setTokenizer(Tokenizer tokenizer);
    void setAutoBuild(boolean autoBuild);
    void setFileLineExtractor(FileLineExtractor fileLineExtractor);
    Index build();
}
