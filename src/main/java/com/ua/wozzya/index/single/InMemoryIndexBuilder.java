package com.ua.wozzya.index.single;

import com.ua.wozzya.index.AbstractIndexBuilder;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.index.IndexBuilder;


public class InMemoryIndexBuilder extends AbstractIndexBuilder implements IndexBuilder {
    @Override
    public Index build() {
        return new InMemoryInvertedIndexStandalone(extractor, tokenizer, fileLineExtractor,autoBuild);
    }
}
