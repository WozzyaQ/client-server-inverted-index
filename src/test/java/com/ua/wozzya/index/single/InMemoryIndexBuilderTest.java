package com.ua.wozzya.index.single;

import com.ua.wozzya.index.Index;
import com.ua.wozzya.index.IndexBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryIndexBuilderTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPEWhenSettingNullToExtractor() {
        IndexBuilder builder = new InMemoryIndexBuilder();
        builder.setExtractor(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPEWhenSettingNullToTokenizer() {
        IndexBuilder builder = new InMemoryIndexBuilder();
        builder.setTokenizer(null);
    }
}