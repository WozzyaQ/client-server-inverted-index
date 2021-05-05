package com.ua.wozzya.extractor;

import java.util.Iterator;

/**
 * Common interface for FileLineIterators
 */
public interface FileLineIterator extends Iterator<String> {
    void setPathToFile(String path);
}
