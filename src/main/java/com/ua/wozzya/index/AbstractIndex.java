package com.ua.wozzya.index;

import com.ua.wozzya.utils.Pair;
import com.ua.wozzya.utils.extractor.FileLineIterator;
import com.ua.wozzya.utils.extractor.FileNameListExtractor;
import com.ua.wozzya.utils.tokenizer.Tokenizer;

import java.util.*;

// docs & logging
public abstract class AbstractIndex implements Index {

    protected static final Pair<Long, Set<String>> EMPTY_PAIR = new Pair<>(0L, Collections.emptySet());


    protected final FileNameListExtractor fileNameListExtractor;
    protected final Tokenizer tokenizer;
    protected final FileLineIterator lineIterator;
    protected Map<String, Pair<? extends Number, Set<String>>> index;


    /**
     * @param fileNameListExtractor implementation of {@link FileNameListExtractor <String>}
     * @param tokenizer             implementation of {@link Tokenizer}
     * @param fileLineIterator      implementation of {@link FileLineIterator}
     */
    protected AbstractIndex(FileNameListExtractor fileNameListExtractor, Tokenizer tokenizer, FileLineIterator fileLineIterator) {
        this.fileNameListExtractor = fileNameListExtractor;
        this.tokenizer = tokenizer;
        this.lineIterator = fileLineIterator;
    }

    /**
     * Initialize index storage
     */
    protected abstract void initIndex();


    protected void collectFromFileAndStore(String fileName) {
        lineIterator.setPathToFile(fileName);
        String[] tokens = tokenizer.tokenizeDistinct(lineIterator.extract());
        store(tokens, fileName);
    }

    protected abstract void store(String[] tokens, String fileName);


    @Override
    public Set<String> search(String key) {
        indexReadinessCheck();
        return matchALl(key);
    }

    /**
     * Checks if index was built
     */
    protected void indexReadinessCheck() {
        if (!isReady()) {
            throw new IllegalStateException("index should be built first");
        }
    }

    protected Set<String> matchALl(String key) {
        String[] tokens = tokenizer.tokenize(key);
        if (tokens == null) {
            return Collections.emptySet();
        }

        List<Set<String>> foundDocsToToken = new ArrayList<>();

        for (String token : tokens) {
            Set<String> curFound = index.getOrDefault(token, EMPTY_PAIR).getRight();
            foundDocsToToken.add(curFound);
        }

        return intersectMatch(foundDocsToToken);
    }

    protected Set<String> intersectMatch(List<Set<String>> foundDocsToToken) {
        Set<String> emptySet = Collections.emptySet();
        if (foundDocsToToken.isEmpty()) {
            return emptySet;
        }
        var it = foundDocsToToken.iterator();

        Set<String> initSet = it.next();

        //if first one is empty - so there is no match
        if (initSet.isEmpty()) {
            return emptySet;
        }

        Set<String> matchSet = new HashSet<>(initSet);

        while (it.hasNext()) {
            Set<String> curMatch = it.next();
            if (curMatch.isEmpty()) {
                return emptySet;
            }
            matchSet.retainAll(curMatch);
        }

        return matchSet;
    }

}
