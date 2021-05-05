package com.ua.wozzya.extractor;

/**
 * @param <T> extracted value type
 */
@FunctionalInterface
public interface Extractor<T> {
    /**
     * Extracts all the content
     * @return
     */
    T extract();
}
