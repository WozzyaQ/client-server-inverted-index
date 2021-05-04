package com.ua.wozzya.tokenizer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleTokenizerTest {

    String[] splittedText = {"this","is","my","text","that","will","be","tokenized"};
    String joinedText;

    @Before
    public void setUp() {
        StringBuilder sb = new StringBuilder();
        for(String s : splittedText) {
            sb.append(s + " ");
        }

        joinedText = sb.toString();
    }

    @Test
    public void shouldTokenize() {
        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);
        String[] expectedTokens = splittedText;
        String[] actualTokens = tokenizer.tokenize(joinedText);

        assertArrayEquals(expectedTokens, actualTokens);
    }
}