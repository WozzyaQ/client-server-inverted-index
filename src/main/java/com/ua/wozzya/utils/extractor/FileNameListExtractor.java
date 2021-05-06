package com.ua.wozzya.utils.extractor;

import java.util.List;

/**
 * Common interface for ListExtractors
 * @param  type of extractable entry
 */
public interface FileNameListExtractor extends Extractor<List<String>> {
    List<String> extract();
}
