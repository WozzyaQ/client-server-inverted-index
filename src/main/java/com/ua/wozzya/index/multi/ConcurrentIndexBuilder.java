package com.ua.wozzya.index.multi;

import com.ua.wozzya.index.IndexBuilder;

public interface ConcurrentIndexBuilder extends IndexBuilder {
    void setNumberOfProcessingThreads(int threadNum);
}