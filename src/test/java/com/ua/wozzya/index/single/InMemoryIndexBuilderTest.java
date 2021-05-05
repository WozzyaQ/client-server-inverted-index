package com.ua.wozzya.index.single;

import com.ua.wozzya.index.IndexBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryIndexBuilderTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPEWhenSettingNullToExtractor() {
        IndexBuilder builder = new InMemoryIndexBuilder().setExtractor(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPEWhenSettingNullToTokenizer() {
        IndexBuilder builder = new InMemoryIndexBuilder().setTokenizer(null);
    }
}