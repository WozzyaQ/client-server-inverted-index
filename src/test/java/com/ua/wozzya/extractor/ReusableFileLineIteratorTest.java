package com.ua.wozzya.extractor;

import com.ua.wozzya.index.TestIndexUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReusableFileLineIteratorTest {


    @Before
    public void setUp(){
        TestIndexUtils.setUp();
    }

    @After
    public void tearDown() {
        TestIndexUtils.tearDown();
    }
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenSupplingADirectory() {
        ReusableFileLineIterator ex = new ReusableFileLineIterator();
        ex.setPathToFile("/");
    }

    @Test
    public void shouldIterateThroughSuppliedContent() {
        ReusableFileLineIterator ex = new ReusableFileLineIterator();
        ex.setPathToFile(TestIndexUtils.TEMP_PATH + TestIndexUtils.TEMP_FILE_NAME);

        String expectedContent = TestIndexUtils.SINGLE_LINE_CONTENT;

        StringBuilder sb = new StringBuilder();

        while (ex.hasNext()) {
            sb.append(ex.next());
        }

        String actualContent = sb.toString();
        assertEquals(expectedContent, actualContent);
    }
}