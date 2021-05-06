package com.ua.wozzya.index.multi;

import com.ua.wozzya.utils.extractor.FileLineIterator;
import com.ua.wozzya.utils.Pair;
import com.ua.wozzya.utils.extractor.FileNameListExtractor;
import com.ua.wozzya.index.AbstractIndex;
import com.ua.wozzya.index.Index;
import com.ua.wozzya.utils.tokenizer.Tokenizer;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ConcurrentInMemoryInvertedIndex extends AbstractIndex implements Index {

    private static final Pair<AtomicLong, Set<String>> EMPTY_PAIR =
            new Pair<>(new AtomicLong(0), ConcurrentHashMap.newKeySet());

    private Map<String, Pair<AtomicLong, Set<String>>> index;

    private final int suppliedThreadAmount;
    private volatile boolean readyMarker = false;

    protected ConcurrentInMemoryInvertedIndex(FileNameListExtractor fileNameListExtractor,
                                              Tokenizer tokenizer,
                                              FileLineIterator lineIterator,
                                              int suppliedThreadAmount,
                                              boolean autoBuild) {
        super(fileNameListExtractor, tokenizer, lineIterator);

        this.suppliedThreadAmount = suppliedThreadAmount;

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
        parallelizeCollecting();
        readyMarker = true;
    }

    private void parallelizeCollecting() {
        List<String> fileNames = fileNameListExtractor.extract();

        int filesAmount = fileNames.size();
        int actualThreadAmount = Math.min(filesAmount, suppliedThreadAmount);

        //occurs if no files in supplied directory
        if (actualThreadAmount == 0) {
            return;
        }

        ExecutorService ex = Executors.newFixedThreadPool(actualThreadAmount);
        var latch = new CountDownLatch(actualThreadAmount);

        int splitLength = filesAmount / actualThreadAmount;
        int reminder = filesAmount % actualThreadAmount;
        for (var i = 0; i < actualThreadAmount; ++i) {

            int startIndex = i * splitLength;
            int endIndex = (i + 1) * splitLength;
            if (i == actualThreadAmount - 1) {
                endIndex += reminder;
            }
            //need to be effectively final to be captured with
            //the following lambda
            int finalEndIndex = endIndex;

            ex.submit(() -> {
                List<String> subFileNameList = fileNames.subList(startIndex, finalEndIndex);
                FileLineIterator fileLineIterator = reflectFLI(lineIterator.getClass());
                for (String fileName : subFileNameList) {
                    collectFromFileAndStore(fileName, fileLineIterator);
                }
                latch.countDown();
            });
        }
        ex.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Create instance of {@link FileLineIterator} using reflection
     * mechanism to supply in {@link ConcurrentInMemoryInvertedIndex#collectFromFileAndStore} function.
     * Thus, each thread uses own copy of supplied {@link FileLineIterator}.
     *
     * @param fliClass class object of {@link FileLineIterator} and its child
     * @return instance of {@link FileLineIterator}
     * null if reflection error occurs, causing NPE
     */
    private FileLineIterator reflectFLI(Class<? extends FileLineIterator> fliClass) {
        FileLineIterator result = null;

        try {
            result = fliClass.getConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //return null reference and catch NPE if something unexpected happens
        return result;
    }

    private void collectFromFileAndStore(String fileName, FileLineIterator lineExtractor) {
        lineExtractor.setPathToFile(fileName);
        String[] tokens = tokenizer.tokenize(lineExtractor.extract());
        store(tokens, fileName);
    }

    private void store(String[] tokens, String fileName) {
        for (String token : tokens) {
            Pair<AtomicLong, Set<String>> pair = index.getOrDefault(token,
                    new Pair<>(new AtomicLong(0), ConcurrentHashMap.newKeySet()));

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
        indexReadinessCheck();
        return index.getOrDefault(key, EMPTY_PAIR)
                .getRight()
                .stream()
                .sorted()
                // TODO optimize sorting
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public long getFrequency(String key) {
        indexReadinessCheck();
        return index.getOrDefault(key, EMPTY_PAIR)
                .getLeft()
                .get();
    }

    @Override
    public boolean isReady() {
        return readyMarker;
    }

}
