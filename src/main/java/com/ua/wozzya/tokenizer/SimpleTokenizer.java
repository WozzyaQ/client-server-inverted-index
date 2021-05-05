package com.ua.wozzya.tokenizer;


import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Simple tokenizer implementation
 */
//TODO factory method for tokenizer
public class SimpleTokenizer implements Tokenizer {
    private final Token token;

    public SimpleTokenizer() {
        this(Token.DEFAULT);
    }

    public SimpleTokenizer(Token token) {
        Objects.requireNonNull(token, "token should not be null");
        this.token = token;
    }

    @Override
    public String[] tokenize(String text) {
        var p = token.getPattern();
        var m = p.matcher(text);

        List<String> tokenList = new LinkedList<>();
        while (m.find()) {
            tokenList.add(m.group(token.getGroupIndex()).toLowerCase());
        }
        return tokenList.toArray(new String[0]);
    }
}
