package com.ua.wozzya.index.multi;

import com.ua.wozzya.index.IndexBuilder;

public interface ConcurrentIndexBuilder extends IndexBuilder {

    void setParallelAccessSections(int sections);
    void setNumberOfProcessingThreads(int threadNum);
}