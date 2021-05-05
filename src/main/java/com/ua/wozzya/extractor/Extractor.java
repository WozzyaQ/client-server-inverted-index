package com.ua.wozzya.extractor;

@FunctionalInterface
public interface Extractor<T> {
    T extract();
}
