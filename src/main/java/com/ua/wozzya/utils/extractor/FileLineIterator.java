package com.ua.wozzya.utils.extractor;

import java.util.Iterator;

/**
 * Common interface for FileLineIterators
 */
public interface FileLineIterator extends Iterator<String>, Extractor<String>{
    /**
     * @param path path pointing to existing directory
     */
    void setPathToFile(String path);
}
