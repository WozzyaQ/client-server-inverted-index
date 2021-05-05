package com.ua.wozzya.index;



import java.util.Set;

/**
 * Common interface for indexes
 */
public interface Index {
    /**
     * @param key to search
     * @return list of entries corresponding to key
     */
    Set<String> search(String key);

    long getFrequency(String key);

    void buildIndex();

    boolean isReady();
}
