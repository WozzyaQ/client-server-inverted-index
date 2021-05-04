package com.ua.wozzya.extractor;

import java.util.List;

/**
 * Common interface for extractors
 * @param <T> type of extractable entry
 */
public interface Extractor<T> {
    List<T> extract();
}
