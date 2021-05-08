# Table of contents
- [Description](#Description)
- [Usage example](#Usage-example)
- [Requirements](#Requirements)
- [Project structure](#Project-structure)
- [Setup](#Setup)
  * [Cloning](#Cloning)
  * [Compiling sources](#Compiling-sources)
  * [Running](#Running)
- [Results](#Results)

# Description

# Usage example
```java
//root dirs to files
String[] paths = {"test/", "train/"};

// get extractor instance
var extractor =
        DirsFileNamesFileNameListExtractor.createExtractor(paths);
        
//set tokenizer
Tokenizer tokenizer = new SimpleTokenizer(Token.WORD);

// create index
IndexBuilder builder = new ConcurrentInMemoryIndexBuilder();
builder.setFileLineExtractor(new ReusableFileLineIterator());
builder.setTokenizer(tokenizer);
builder.setFileNameExtractor(extractor);
builder.setAutoBuild(true);

var index = builder.build();

// get list of file names by word
Set<String> fileNames = index.search("suspect was killed");

fileNames.forEach(System.out::println);
```

# Project structure
### General structure
```
  .
  ├─── bin                                              <-- compiled binaries
  ├─── documentation 
  ├─── lib                                              <-- depending libs
  ├─── measurements                                     <-- measurements folder
  │   └─── concurrent-hashmap-to-hashmap-entryset       <-- concrete impl test      
  │       └─── plots     
  ├─── scripts                                          
  ├─── src
  │   ├─── main.java.com.ua.wozzya
  │   │                         └─── wozzya
  │   │                             ├─── client
  │   │                             ├─── index
  │   │                             │   ├─── multi
  │   │                             │   └─── single
  │   │                             ├─── server
  │   │                             └─── utils
  │   │                                 ├─── extractor
  │   │                                 └─── tokenizer
  │   └─── test                                          <-- test(junit)
  ├─── test                                              <-- text data for indexing
  │   ├─── neg
  │   └─── pos
  └─── train                                             <- also text data for indexing
      ├─── neg
      ├─── pos
      └─── unsup
```

### Packages and classes hierarchy
```
 pkg
 │   AppClient.java    
 │   AppServer.java                                          
 │   Demo.java                                              
 │   PerformanceTest.java                                   
 │
 ├───client
 │        AbstractConsoleClient.java
 │        ActionConstants.java
 │        CyclicMessenger.java
 │        SimpleConsoleCyclicMessenger.java
 ├───index
     │   AbstractIndex.java
     │   AbstractIndexBuilder.java
     │   Index.java
     │   IndexBuilder.java
     ├───multi
     │       ConcurrentIndexBuilder.java
     │       ConcurrentInMemoryIndexBuilder.java
     │       ConcurrentInMemoryInvertedIndex.java
     └───single
     │       InMemoryIndexBuilder.java
     │       InMemoryInvertedIndexStandalone.java
     ├───server
     │       IndexClientHandler.java
     │       MultiConnectionIndexServer.java
     └───utils
         │   Pair.java
         │
         ├───extractor
         │       DirsFileNamesFileNameListExtractor.java
         │       Extractor.java
         │       FileLineIterator.java
         │       FileNameListExtractor.java
         │       ReusableFileLineIterator.java
         │
         └───tokenizer
                 SimpleTokenizer.java
                 Token.java
                 Tokenizer.java
```
# Requirements
- Java 11 and later
- bash 4 (Linux) or wsl (Windows)


# Setup

### Cloning

### Compiling sources

### Running

# Results
