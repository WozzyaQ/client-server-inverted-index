package com.ua.wozzya.extractor;

import java.util.List;

public interface Extractor<T> {
    List<T> extract();
}
