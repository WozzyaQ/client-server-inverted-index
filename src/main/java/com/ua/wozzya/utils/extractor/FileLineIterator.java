package com.ua.wozzya.utils.extractor;

import java.util.Iterator;

/**
 * Common interface for FileLineIterators
 */
public interface FileLineIterator extends Iterator<String>, Extractor<String>{
    void setPathToFile(String path);
}
