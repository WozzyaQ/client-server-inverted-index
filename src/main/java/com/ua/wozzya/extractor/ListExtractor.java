package com.ua.wozzya.extractor;

import java.util.List;

/**
 * Common interface for ListExtractors
 * @param <T> type of extractable entry
 */
public interface ListExtractor<T> extends Extractor<List<T>> {
    List<T> extract();
}
