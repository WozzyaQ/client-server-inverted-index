package com.ua.wozzya;

import com.google.common.base.Stopwatch;
import com.ua.wozzya.index.IndexBuilder;
import com.ua.wozzya.index.multi.ConcurrentInMemoryIndexBuilder;
import com.ua.wozzya.utils.extractor.DirsFileNamesFileNameListExtractor;
import com.ua.wozzya.utils.extractor.FileLineIterator;
import com.ua.wozzya.utils.extractor.ReusableFileLineIterator;
import com.ua.wozzya.utils.tokenizer.SimpleTokenizer;
import com.ua.wozzya.utils.tokenizer.Token;
import com.ua.wozzya.utils.tokenizer.Tokenizer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class PerformanceTest {

    private static final String PATH_TO_DATA = "../perf-test-data";
    private static final String MEASUREMENTS_CSV = "./temp/";

    private static final int[] FILE_LIMITS = {1000, 2000, 5000, 10_000, 20_000, 50_000, 100_000};
    private static final int[] THREADS;

    private static final Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);
    private static final FileLineIterator lineIterator = new ReusableFileLineIterator();


    static {
        THREADS = IntStream.range(1, 20).toArray();
    }


    public static void main(String[] args) {
        test(PerformanceTest::taskConcurrent, MEASUREMENTS_CSV, "concurrent-hashmap-to-hashmap-entryset");
    }

    public static DirsFileNamesFileNameListExtractor getExtractorWithLimit(int limit, String... paths) {
        return DirsFileNamesFileNameListExtractor.createExtractor(limit, paths);
    }

    public static void setCommons(IndexBuilder builder, int limit) {
        builder.setTokenizer(tokenizer);
        builder.setAutoBuild(true);
        builder.setFileLineExtractor(lineIterator);
        builder.setFileNameExtractor(getExtractorWithLimit(limit, PATH_TO_DATA));
    }

    private static long measure(IndexBuilder builder) {
        var stopwatch = Stopwatch.createUnstarted();
        stopwatch.start();
        builder.build();
        stopwatch.stop();
        return stopwatch.elapsed(TimeUnit.MILLISECONDS);
    }


    public static long taskConcurrent(Integer limit, Integer thread) {
        var builder = new ConcurrentInMemoryIndexBuilder();
        setCommons(builder, limit);
        builder.setNumberOfProcessingThreads(thread);
        long execTime = measure(builder);
        System.out.println("[THREADFUL] Execution time: " + execTime
                + "; limit :" + limit + "; threads: " + thread);

        return execTime;
    }

    private static void test(BiFunction<Integer, Integer, Long> task, String directoryToStore, String taskName) {
        FileWriter fw = null;
        try {
            for (int limit : FILE_LIMITS) {
                String fileName = createDirectoryIfNotExistAndFormatName(directoryToStore, taskName, limit);
                fw = new FileWriter(fileName);
                fw.write("threads,exec_time" + System.lineSeparator());
                for (int thread : THREADS) {
                    var execTime = task.apply(limit, thread);
                    fw.write(thread + "," + execTime + System.lineSeparator());
                }
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String createDirectoryIfNotExistAndFormatName(String directoryToStore, String taskName, int limit) throws IOException {
        Path pathToStoreDirectory = Paths.get(directoryToStore);
        if (Files.notExists(pathToStoreDirectory)) {
            Files.createDirectory(pathToStoreDirectory);
        }
        Path pathToTaskDirectory = Paths.get(directoryToStore + "/" + taskName);
        if (Files.notExists(pathToTaskDirectory)) {
            Files.createDirectory(pathToTaskDirectory);
        }

        return pathToTaskDirectory.toString() + "/" + "measurement-limit-" + limit + ".csv";
    }
}
