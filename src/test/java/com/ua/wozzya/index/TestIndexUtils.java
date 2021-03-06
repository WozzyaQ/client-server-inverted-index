package com.ua.wozzya.index;

import com.ua.wozzya.utils.extractor.DirsFileNamesFileNameListExtractor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestIndexUtils {

    public static final String SINGLE_LINE_CONTENT = "Process finished finished finished with with exit code 0";
    public static final String TEMP_FILE_NAME = "temp.txt";
    static final String SEPARATOR = File.separator;
    public static final String TEMP_PATH = "temp" + SEPARATOR;
    public static  DirsFileNamesFileNameListExtractor EXTRACTOR;


    public static void setUp()  {
        try{
            Path path = Paths.get(TEMP_PATH);
            Path filePath = Paths.get(TEMP_PATH + TEMP_FILE_NAME);

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);

                FileOutputStream fos = new FileOutputStream(String.valueOf(filePath));
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                osw.write(SINGLE_LINE_CONTENT);
                osw.close();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        EXTRACTOR = DirsFileNamesFileNameListExtractor.createExtractor(TEMP_PATH);

    }

    public static void tearDown() {
        File dir = new File(TEMP_PATH + TEMP_FILE_NAME);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        dir.delete();
    }
}
