package com.ua.wozzya.extractor;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IteratorFileFileLineExtractor implements FileLineExtractor {
    private String pathToFile;
    private List<String> lines = new ArrayList<>();
    private int cursor = 0;

    public IteratorFileFileLineExtractor() {
    }

    public void setPathToFile(String path) {
        this.pathToFile = path;
        lines.clear();
        readAllLines();
        cursor = 0;
    }

    private void readAllLines() {
        File file = new File(pathToFile);
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(fis);
             BufferedReader buffReader = new BufferedReader(reader)) {

            String line;
            while ((line = buffReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return cursor < lines.size();
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return lines.get(cursor++);
    }

    @Override
    public String extract() {
        return next();
    }
}
