package com.ua.wozzya.index.multi;

import com.ua.wozzya.extractor.FileLineExtractor;
import com.ua.wozzya.index.Pair;
import com.ua.wozzya.extractor.ListExtractor;
import com.ua.wozzya.extractor.ReusableIteratorFileFileLineExtractor;
import com.ua.wozzya.index.AbstractIndex;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.tokenizer.Tokenizer;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ConcurrentInMemoryInvertedIndex extends AbstractIndex implements Index {

    private static final Pair<AtomicLong, Set<String>> EMPTY_PAIR =
            new Pair<>(new AtomicLong(0), ConcurrentHashMap.newKeySet());

    private Map<String, Pair<AtomicLong, Set<String>>> index;

    private final int parallelAccessSections;
    private final int threadNum;
    private volatile boolean readyMarker = false;

    protected ConcurrentInMemoryInvertedIndex(ListExtractor<String> listExtractor,
                                              Tokenizer tokenizer,
                                              ReusableIteratorFileFileLineExtractor fileReader,
                                              int parallelAccessSections,
                                              int threadNum,
                                              boolean autoBuild) {
        super(listExtractor, tokenizer, fileReader);

        this.parallelAccessSections = parallelAccessSections;
        this.threadNum = threadNum;

        if (autoBuild) {
            buildIndex();
        }
    }

    @Override
    protected void initIndex() {
        index = new ConcurrentHashMap<>();
    }

    @Override
    public void buildIndex() {
        initIndex();

        List<String> fileNames = listExtractor.extract();
        parallelizeCollecting(fileNames);

        readyMarker = true;
    }

    private void parallelizeCollecting(List<String> fileNames) {
        int filesAmount = fileNames.size();
        int threadAmount = Math.min(filesAmount, threadNum);

        int splitLength = filesAmount / threadAmount;
        int reminder = filesAmount % threadAmount;


        ExecutorService ex = Executors.newFixedThreadPool(threadAmount);

        for (int i = 0; i < threadAmount; ++i) {
            int startIndex = i * splitLength;
            int endIndex = (i + 1) * splitLength;
            if (i == threadAmount - 1) {
                endIndex += reminder;
            }
            int finalEndIndex = endIndex;

            ex.submit(() -> {
                List<String> subFileNameList = fileNames.subList(startIndex, finalEndIndex);
                for (String fileName : subFileNameList) {
                    collectFromFileAndStore(fileName);
                }
            });
        }

        ex.shutdown();

        try {
            ex.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void collectFromFileAndStore(String fileName) {
        try {
            FileLineExtractor extractor = fileReader.getClass().getConstructor().newInstance();
            extractor.setPathToFile(fileName);
            while (extractor.hasNext()) {
                String[] tokens = tokenizer.tokenize(extractor.next());
                store(tokens, fileName);
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void store(String[] tokens, String fileName) {
        for (String token : tokens) {
            Pair<AtomicLong, Set<String>> pair = index.getOrDefault(token, new Pair<>(new AtomicLong(0), ConcurrentHashMap.newKeySet()));

            AtomicLong oldAtomic = pair.getLeft();
            long oldValue;
            long newValue;
            do {
                oldValue = oldAtomic.get();
                newValue = oldValue + 1;
            } while (!oldAtomic.compareAndSet(oldValue, newValue));

            pair.getRight().add(fileName);
            index.put(token, pair);
        }
    }


    @Override
    public Set<String> search(String key) {
        buildCheck();
        return index.getOrDefault(key, EMPTY_PAIR)
                .getRight()
                .stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public long getFrequency(String key) {
        buildIndex();
        return index.getOrDefault(key, EMPTY_PAIR)
                .getLeft()
                .get();
    }


    @Override
    public boolean isReady() {
        return readyMarker;
    }

}
