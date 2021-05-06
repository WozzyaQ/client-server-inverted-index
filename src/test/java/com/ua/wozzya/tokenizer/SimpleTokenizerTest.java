package com.ua.wozzya.tokenizer;

import com.ua.wozzya.utils.tokenizer.SimpleTokenizer;
import com.ua.wozzya.utils.tokenizer.Token;
import com.ua.wozzya.utils.tokenizer.Tokenizer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class SimpleTokenizerTest {

    String[] splittedText = {"this","is","my","text","that","will","be","tokenized","text", "we'll"};
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
    public void shouldTokenizeWithWordToken() {
        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);
        String[] expectedTokens = splittedText;
        String[] actualTokens = tokenizer.tokenize(joinedText);

        assertArrayEquals(expectedTokens, actualTokens);
    }

    @Test
    public void shouldTokenizeWithWordTokenMultiLine() {
        Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);
        String[] expectedTokens = {"here", "we", "go", "again"};
        String content = Arrays.stream(expectedTokens).collect(Collectors.joining(System.lineSeparator()));
        String[] actualTokens = tokenizer.tokenize(content);

        assertArrayEquals(expectedTokens, actualTokens);
    }
}