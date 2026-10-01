# Java Hash Table Word Index

A Java text-indexing and search application developed as part of my university Data Structures and Algorithms coursework.

The project indexes words contained within collections of text files and records the files and line numbers in which each word occurs. It explores alternative data structures for storing the index, including a linked-list implementation and a custom hash table with collision handling, load-factor monitoring and dynamic resizing.

This repository preserves my original coursework implementation alongside the supporting classes and interfaces supplied as part of the assignment.

## Project Overview

The application was designed to process collections of text files and construct a searchable word index.

For each indexed word, the system records its occurrences as positions containing information such as:

- Source file
- Line number
- Word

The resulting index can then be queried to determine:

- How many times a word occurs
- How many files contain the word
- Which files contain the word
- The line numbers on which the word appears
- The files containing the greatest number of occurrences

Two alternative map implementations were explored:

1. A linked-list-backed word map
2. A custom hash-table-backed word map

This allowed the project to explore both data-structure implementation and the performance implications of different approaches to storing and retrieving indexed data.

## Technologies

- Java
- Object-Oriented Programming
- Data Structures and Algorithms
- File I/O
- Collections and Iterators

## Repository Structure

```text
java-hash-table-word-index/
│
├── README.md
├── LICENSE
├── .gitignore
│
├── src/
│   └── F28DA_CW1/
│       ├── HashWordMap.java
│       ├── ListWordMap.java
│       ├── WordEntry.java
│       ├── WordException.java
│       └── WordIndex.java
│
└── provided/
    └── F28DA_CW1/
        ├── IWordMap.java
        ├── IHashMonitor.java
        ├── IPosition.java
        ├── WordPosition.java
        ├── WordTxtReader.java
        └── HashWordMapProvidedExp.java
```

## My Implementation

The coursework supplied supporting interfaces and utility classes defining parts of the required application architecture.

My implementation included the main data structures and indexing behaviour required to construct and query the word index.

### `HashWordMap`

`HashWordMap` implements a custom hash table for mapping individual words to their positions within the indexed text files.

The implementation includes:

- Custom string hashing
- Hash compression
- Collision handling
- Probe tracking
- Load-factor monitoring
- Dynamic table resizing
- Rehashing existing entries after resizing
- Word insertion
- Word removal
- Position insertion and removal
- Position lookup
- File-occurrence counting

Conceptually:

```text
                     Word
                       |
                       v
                 Hash Function
                       |
                       v
                  Compression
                       |
                       v
               Initial Table Index
                       |
              +--------+--------+
              |                 |
           Empty              Collision
              |                 |
              v                 v
          Insert Entry      Probe Sequence
                                |
                                v
                          Locate Free Slot
```

The hash table begins with a fixed initial capacity and monitors its current load factor.

When the configured maximum load factor is reached, the table is resized and existing entries are reinserted into the new table.

## Collision Resolution

The implementation uses a secondary hash-derived probe value when collisions occur.

Rather than immediately failing when two words map to the same location, the table follows a probe sequence until the required word or an available table position is located.

The implementation also records:

- Number of operations
- Number of probes
- Average number of probes per operation

This allows the effect of hash-table load factor on collision behaviour to be investigated.

## Dynamic Resizing

The hash table monitors:

```text
number of stored entries
------------------------
       table size
```

against a configurable maximum load factor.

When the threshold is reached, a larger table size is selected and the existing entries are rehashed into the resized table.

Prime-number calculations are used when selecting values associated with the resized hash table and collision-probing behaviour.

## `ListWordMap`

A second implementation stores word entries using a Java `LinkedList`.

This implementation supports operations including:

- Adding word positions
- Removing words
- Removing positions
- Iterating through indexed words
- Retrieving positions associated with a word
- Counting indexed entries
- Counting files containing a word
- Counting stored positions

The two implementations provided an opportunity to explore how the choice of underlying data structure affects lookup and indexing behaviour.

## `WordEntry`

I introduced a `WordEntry` class to associate each indexed word with its collection of positions.

Conceptually:

```text
WordEntry
│
├── key: String
│
└── positions
    ├── file / line
    ├── file / line
    ├── file / line
    └── ...
```

This allowed each unique word to maintain multiple occurrences across one or more files.

## Word Index Application

`WordIndex` provides the main indexing and search functionality.

The application processes commands that allow text files to be added to or removed from the index and indexed words to be searched.

The overall architecture can be represented as:

```text
                 Text Files
                     |
                     v
               Word Reader
                     |
                     v
              WordPosition
                     |
                     v
             +---------------+
             |   IWordMap    |
             +-------+-------+
                     |
          +----------+----------+
          |                     |
          v                     v
   ListWordMap             HashWordMap
          |                     |
          +----------+----------+
                     |
                     v
                Word Index
                     |
                     v
             Search / Results
```

## Search Results

For a requested word, the application groups occurrences by source file and records the line numbers on which the word appears.

The original coursework tests included indexing a collection of 16 text files containing 3,107 indexed entries.
