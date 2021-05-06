package com.ua.wozzya.utils.extractor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Simple implementation of {@link FileNameListExtractor} interface
 * Extracts file names from specified directories
 */
public class DirsFileNamesFileNameListExtractor implements FileNameListExtractor {
    private String[] paths;
    private final List<String> fileNames = new LinkedList<>();

    private DirsFileNamesFileNameListExtractor(String[] initPaths) {
        init(initPaths);
    }

    private void init(String[] initPaths) {
        paths = new String[initPaths.length];
        for (int i = 0; i < initPaths.length; ++i) {
            initPaths[i] = PathTransformer.checkAndTransform(initPaths[i]);
        }
        System.arraycopy(initPaths, 0, paths, 0, initPaths.length);
    }

    /**
     * Factory method to build extractor
     *
     * @param paths directory to be parsed
     * @return {@link DirsFileNamesFileNameListExtractor} instance
     */
    public static DirsFileNamesFileNameListExtractor createExtractor(String... paths) {
        Objects.requireNonNull(paths, "paths should not be null");
        return new DirsFileNamesFileNameListExtractor(paths);
    }

    public static class PathTransformer {

        public static String checkAndTransform(String suppliedPath) {
            Path path = Paths.get(suppliedPath);
            String transformed = path.normalize().toString();
            System.out.println(transformed);
            checkExistence(transformed);
            System.out.println(transformed);
            return transformed;
        }

        private static void checkExistence(String path) {
            File file = new File(path);
            if (!file.exists() || !file.isDirectory()) {
                throw new IllegalArgumentException("path should be pointing to real directory");
            }
        }
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
            System.out.println(path);
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
                    extractFromPath(basePath + File.separator + fileName, file);
                } else {
                    //TODO Consider filtering by name
                    fileNames.add(basePath + File.separator + fileName);
                }
            }
        }
    }
}
