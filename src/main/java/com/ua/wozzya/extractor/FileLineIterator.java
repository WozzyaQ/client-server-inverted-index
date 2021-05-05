package com.ua.wozzya.extractor;

import java.util.Iterator;

public interface FileLineIterator extends Iterator<String> {
    void setPathToFile(String path);
}
