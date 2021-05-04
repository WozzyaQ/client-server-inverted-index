package com.ua.wozzya;

public class Token {
    public static final String WORD_PATTERN = "\\b\\w+?\\b";

    public static final String UNIQUE_WORD_PATTERN = "(\\b'??(\\w+?\\b))(?![\\s\\S]+?\\1\\b)";
    public static final int UNIQUE_WORD_GROUP_INDEX = 2;
}
