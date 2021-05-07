package com.ua.wozzya.index;


import java.util.Set;

/**
 * Common interface for indexes
 */
public interface Index {
    /**
     * @param key a word of a group of words
     *            that should be compliant with
     *            {@link com.ua.wozzya.utils.tokenizer.Tokenizer}
     * @return list of entries corresponding to key
     */
    Set<String> search(String key);

    /**
     *
     * @param key single word compliant with {@link com.ua.wozzya.utils.tokenizer.Tokenizer}
     * @return frequency of a word
     */
    long getFrequency(String key);

    /**
     * Start building an index.
     */
    void buildIndex();

    /**
     * @return whether index has been built
     */
    boolean isReady();
}
