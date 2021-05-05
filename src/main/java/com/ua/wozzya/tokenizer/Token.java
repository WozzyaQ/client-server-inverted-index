package com.ua.wozzya.tokenizer;

import java.util.regex.Pattern;

/**
 * Defines tokens that will be extracted
 * from text in {@link Tokenizer} implementations
 *
 * @author V.Chaykovsky
 */
// TODO refactor enum to class Hierarchy allowing functionality expansion
public enum Token {
    /**
     * Default split token
     * Matches single character from class \w
     */
    DEFAULT("\\w+?", 0),

    /**
     * Lookahead regex to match only unique words
     */
    UNIQUE_WORD("(\\b'??(\\w+?\\b))(?![\\s\\S]+?\\1\\b)", 2),

    /**
     *
     * Regex to match the word per se,
     * including words with " ' "
     */
    WORD("\\b\\w+'*\\w+?\\b", 0);

    final Pattern pattern;
    final int groupIndex;

    Token(String patternRaw, int groupIndex) {
        this.pattern = Pattern.compile(patternRaw);
        this.groupIndex = groupIndex;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getGroupIndex() {
        return groupIndex;
    }
}
