package com.ua.wozzya.utils.tokenizer;

import com.ua.wozzya.index.single.InMemoryInvertedIndexStandalone;

/**
 * Common interface for tokenizers
 * Instance of that interface are used
 * in {@link InMemoryInvertedIndexStandalone} class
 * to retrieve tokens from the string
 * @author V. Chaykovsky
 */
public interface Tokenizer {

    /**
     * @param text text data to be tokenized
     * @return list of tokens that correspond to {@link Token} pattern
     */
    String[] tokenize(String text);

    static Tokenizer getDefault() {
        return new SimpleTokenizer(Token.DEFAULT);
    }
}
