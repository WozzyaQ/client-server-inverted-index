package com.ua.wozzya.index;

import com.ua.wozzya.extractor.Extractor;
import com.ua.wozzya.tokenizer.Tokenizer;

public interface IndexBuilder {
    IndexBuilder setExtractor(Extractor<String> extractor);
    IndexBuilder setTokenizer(Tokenizer tokenizer);
    IndexBuilder setAutoBuild(boolean autoBuild);
    Index build();
}
