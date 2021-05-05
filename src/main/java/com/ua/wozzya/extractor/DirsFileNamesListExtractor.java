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
     * @return list of file names
     */
    @Override
    public List<String> extract() {
        if (fileNames.isEmpty()) {
            doExtract();
        }
        return fileNames;
    }

    private void doExtract() {
        for (String path : paths) {
            extractFromDir(path, new File(path));
        }
    }

    public void extractFromDir(String from, File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {

                if (file.isDirectory()) {
                    extractFromDir(from + file.getName() + "/", file);
                } else {
                    fileNames.add(from + file.getName());
                }
            }
        }
    }

}