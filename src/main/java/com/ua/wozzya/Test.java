package com.ua.wozzya;

import com.ua.wozzya.utils.extractor.DirsFileNamesFileNameListExtractor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) {

        String pathstr = "/University";
        Path path = Paths.get(pathstr);
        System.out.println(path);
        File file = new File(String.valueOf(path.normalize()));
        System.out.println("isDirectory" + file.isDirectory());
        System.out.println("getName:" + file.getName());
        System.out.println("absolutepath: " + file.getAbsolutePath());
        System.out.println(file.exists());

        DirsFileNamesFileNameListExtractor.PathTransformer.checkAndTransform("./src");

    }


}
