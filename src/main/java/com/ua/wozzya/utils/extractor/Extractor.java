package com.ua.wozzya.utils.extractor;

/**
 * Functional interface that serves
 * to extract ALL the content
 * upon method invocation
 * @param <T> extracted value type
 */
@FunctionalInterface
public interface Extractor<T> {
    /**
     * Extracts all the content
     * @return content
     */
    T extract();
}
