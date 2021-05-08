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
### ok
  .
  ├─── bin      
  ├─── documentation
  ├─── lib     
  ├─── measurements    
  │   └─── concurrent-hashmap-to-hashmap-entryset
  │       └─── plots
  ├───scripts  
  ├───src
  │   ├───main.java.com.ua.wozzya
  │   │                         └───wozzya
  │   │                             ├───client
  │   │                             ├───index
  │   │                             │   ├───multi
  │   │                             │   └───single
  │   │                             ├───server
  │   │                             └───utils
  │   │                                 ├───extractor
  │   │                                 └───tokenizer
  │   └───test  <-- test (junit4)
  ├───test  <-- text data 
  │   ├───neg
  │   └───pos
  └───train <-- also text data
      ├───neg
      ├───pos
      └───unsup

# Requirements

# Setup

### Cloning

### Compiling sources

### Running

# Results
