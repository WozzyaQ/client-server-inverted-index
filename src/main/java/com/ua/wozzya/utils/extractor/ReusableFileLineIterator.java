package com.ua.wozzya.utils.extractor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implements iterator-like behaviour associated with iterating
 * through the lines in the file, specified in {@link ReusableFileLineIterator#setPathToFile}.
 * <p>
 * Can be used multiple times if new {@link ReusableFileLineIterator#pathToFile} was supplied
 * <p>
 * Automatically extracts all the lines from {@link ReusableFileLineIterator#pathToFile}
 * on {@link ReusableFileLineIterator#setPathToFile(String)} method invocation.
 */
public class ReusableFileLineIterator implements FileLineIterator {
    private String pathToFile;
    private List<String> lines = new ArrayList<>();
    private int cursor = 0;


    public ReusableFileLineIterator() {
        //Leave empty constructor to use reflection mechanism
        // in ConcurrentInMemoryIndex
    }


    /**
     * Sets path to a content file
     * and extracts all lines from the file
     *
     * @param path relative or absolute path to file
     */
    public void setPathToFile(String path) {
        this.pathToFile = path;
        lines.clear();
        readAllLines();
        cursor = 0;
    }

    private void readAllLines() {
        var file = new File(pathToFile);
        if (file.isDirectory()) {
            throw new IllegalArgumentException("only works for files, not directories");
        }

        try (var fis = new FileInputStream(file);
             var reader = new InputStreamReader(fis);
             var buffReader = new BufferedReader(reader)) {

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
        var sb = new StringBuilder();
        while (hasNext()) {
            sb.append(next()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
