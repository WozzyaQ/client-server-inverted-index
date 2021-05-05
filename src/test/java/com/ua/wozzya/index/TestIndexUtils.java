package com.ua.wozzya.index;

import com.ua.wozzya.extractor.DirsFileNamesListExtractor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestIndexUtils {

    public static final String CONTENT = "Process finished finished finished with with exit code 0";
    public static final String TEMP_FILE_NAME = "temp.txt";
    public static final String TEMP_PATH = "temp/";
    public static final DirsFileNamesListExtractor EXTRACTOR = DirsFileNamesListExtractor.createExtractor(TEMP_PATH);


    public static void setUp()  {
        try{
            Path path = Paths.get(TEMP_PATH);
            Path filePath = Paths.get(TEMP_PATH + TEMP_FILE_NAME);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
                Files.createFile(filePath);

                FileOutputStream fos = new FileOutputStream(String.valueOf(filePath));
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                osw.write(CONTENT);
                osw.close();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void tearDown() {
        File dir = new File(TEMP_PATH);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        dir.delete();
    }
}
