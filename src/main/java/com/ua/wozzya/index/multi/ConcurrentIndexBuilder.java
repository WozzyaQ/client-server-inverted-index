package com.ua.wozzya.index.multi;


import com.ua.wozzya.index.IndexBuilder;

public interface ConcurrentIndexBuilder extends IndexBuilder {
    IndexBuilder setParallelAccessSections(int sections);
    IndexBuilder setNumberOfProcessingThreads(int threadNum);
}
