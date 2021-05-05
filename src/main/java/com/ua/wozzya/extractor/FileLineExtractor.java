package com.ua.wozzya.extractor;

import java.util.Iterator;

public interface FileLineExtractor extends Extractor<String>, Iterator<String> {
    void setPathToFile(String path);
}
