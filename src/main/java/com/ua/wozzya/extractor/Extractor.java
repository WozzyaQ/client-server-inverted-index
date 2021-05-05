package com.ua.wozzya.extractor;

/**
 * @param <T> extracted value type
 */
@FunctionalInterface
public interface Extractor<T> {
    T extract();
}
