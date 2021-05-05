package com.ua.wozzya.extractor;

import java.io.File;
import java.util.*;

/**
 * Simple implementation of {@link ListExtractor} interface
 * Extracts file names from specified directories
 */
public class DirsFileNamesListExtractor implements ListExtractor<String> {
    private String[] paths;
    private final List<String> fileNames = new LinkedList<>();

    private DirsFileNamesListExtractor(String[] initPaths) {
        init(initPaths);
    }

    private void init(String[] initPaths) {
        paths = new String[initPaths.length];
        System.arraycopy(initPaths, 0, paths, 0, initPaths.length);
    }

    /**
     * Factory method to build extractor
     *
     * @param paths directory to be parsed
     * @return {@link DirsFileNamesListExtractor} instance
     */
    public static DirsFileNamesListExtractor createExtractor(String... paths) {
        Objects.requireNonNull(paths);
        return new DirsFileNamesListExtractor(paths);
    }

    /**
     * Extracts all the file names from the specified directory
     * and it's subdirectories
     *
     * @return list of file names
     */
    @Override
    public List<String> extract() {
        if (fileNames.isEmpty()) {
            extractFromAllPaths();
        }
        return fileNames;
    }

    private void extractFromAllPaths() {
        for (String path : paths) {
            extractFromPath(path, new File(path));
        }
    }

    public void extractFromPath(String basePath, File dir) {
        File[] files = dir.listFiles();
        //if contains file
        if (files != null) {
            for (File file : files) {
                //recursive search if directory
                String fileName = file.getName();
                if (file.isDirectory()) {
                    extractFromPath(basePath + fileName + "/", file);
                } else {
                    //TODO Consider filtering by name
                    fileNames.add(basePath + fileName);
                }
            }
        }
    }
}
