package com.ua.wozzya.utils.extractor;

import java.util.List;

/**
 * Common interface for ListExtractors
 */
public interface FileNameListExtractor extends Extractor<List<String>> {
    List<String> extract();
}
