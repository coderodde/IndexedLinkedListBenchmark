package com.coderodde;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import com.github.coderodde.util.IndexedLinkedList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.list.CursorableLinkedList;
import org.apache.commons.collections4.list.NodeCachingLinkedList;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.apache.commons.collections4.list.TreeList;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class IndexedLinkedListPerformance {

    // Small data:
//    private static final int ADD_FIRST_OPERATIONS           = 1_000;
//    private static final int ADD_LAST_OPERATIONS            = 1_000;
//    private static final int ADD_AT_OPERATIONS              = 1_000;
//    private static final int ADD_COLLECTION_AT_OPERATIONS   = 1_000;
//    private static final int ADD_COLLECTION_OPERATIONS      = 1_000;
//    private static final int REMOVE_COLLECTION_SIZE         = 10_000;
//    private static final int REMOVE_OBJECT_OPERATIONS       = 1_000;
//    private static final int REMOVE_ALL_SIZE                = 10_000;
//    private static final int REMOEVE_COLLECTION_CAPACITY    = 2_000;
//    private static final int GET_SIZE                       = 10_000;
//    private static final int GET_OPERATIONS                 = 1_000;
//    private static final int CLEAR_LIST_SIZE                = 50_000;
//    private static final int CLEAR_RANGE_SIZE               = 49_000;
//    private static final int SORT_LIST_SIZE                 = 10_000;
//    private static final int CACHE_NODES                    = 500;

    // Medium size data:
//    private static final int ADD_FIRST_OPERATIONS           = 3_000;
//    private static final int ADD_LAST_OPERATIONS            = 3_000;
//    private static final int ADD_AT_OPERATIONS              = 3_000;
//    private static final int ADD_COLLECTION_AT_OPERATIONS   = 3_000;
//    private static final int ADD_COLLECTION_OPERATIONS      = 3_000;
//    private static final int REMOVE_COLLECTION_SIZE         = 30_000;
//    private static final int REMOVE_OBJECT_OPERATIONS       = 3_000;
//    private static final int REMOVE_ALL_SIZE                = 30_000;
//    private static final int REMOEVE_COLLECTION_CAPACITY    = 6_000;
//    private static final int GET_SIZE                       = 30_000;
//    private static final int GET_OPERATIONS                 = 3_000;
//    private static final int CLEAR_LIST_SIZE                = 100_000;
//    private static final int CLEAR_RANGE_SIZE               = 99_000;
//    private static final int SORT_LIST_SIZE                 = 50_000;
//    private static final int CACHE_NODES                    = 1500;
    
    // Larger data:
    private static final int ADD_FIRST_OPERATIONS           = 5_000;
    private static final int ADD_LAST_OPERATIONS            = 5_000;
    private static final int ADD_AT_OPERATIONS              = 5_000;
    private static final int ADD_COLLECTION_AT_OPERATIONS   = 5_000;
    private static final int ADD_COLLECTION_OPERATIONS      = 5_000;
    private static final int REMOVE_COLLECTION_SIZE         = 50_000;
    private static final int REMOVE_OBJECT_OPERATIONS       = 5_000;
    private static final int REMOVE_ALL_SIZE                = 50_000;
    private static final int REMOEVE_COLLECTION_CAPACITY    = 10_000;
    private static final int GET_SIZE                       = 50_000;
    private static final int GET_OPERATIONS                 = 5_000;
    private static final int CLEAR_LIST_SIZE                = 150_000;
    private static final int CLEAR_RANGE_SIZE               = 149_000;
    private static final int SORT_LIST_SIZE                 = 150_000;
    private static final int CACHE_NODES                    = 3_000;

    private static final int MAXIMUM_INTEGER         = 1_000;
    private static final int MAXIMUM_COLLECTION_SIZE = 20;

    private static final long seed = System.currentTimeMillis();

    //// State get /////////////////////////////////////////////////////////////
    @State(Scope.Benchmark)
    public static class IndexedLinkedListStateGet {
        public List<Integer> list;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            random = new Random(seed);

            for (int i = 0; i < GET_SIZE; ++i) {
                list.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class ArrayListStateGet {
        public List<Integer> list;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            random = new Random(seed);

            for (int i = 0; i < GET_SIZE; ++i) {
                list.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class LinkedListStateGet {
        public List<Integer> list;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            random = new Random(seed);

            for (int i = 0; i < GET_SIZE; ++i) {
                list.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class TreeListStateGet {
        public List<Integer> list;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            random = new Random(seed);

            for (int i = 0; i < GET_SIZE; ++i) {
                list.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class NodeCachingLinkedListStateGet {
        public List<Integer> list;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new NodeCachingLinkedList<>(CACHE_NODES);
            random = new Random(seed);

            for (int i = 0; i < GET_SIZE; ++i) {
                list.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class CursorableLinkedListStateGet {
        public List<Integer> list;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new CursorableLinkedList<>();
            random = new Random(seed);

            for (int i = 0; i < GET_SIZE; ++i) {
                list.add(i);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////


    //// State removeFirst /////////////////////////////////////////////////////
    @State(Scope.Benchmark)
    public static class IndexedLinkedListStateRemoveFirst {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class ArrayListStateRemoveFirst {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class LinkedListStateRemoveFirst {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class TreeListStateRemoveFirst {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class NodeCachingLinkedListStateRemoveFirst {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new NodeCachingLinkedList<>(CACHE_NODES);
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class CursorableLinkedListStateRemoveFirst {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new CursorableLinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////


    //// State removeLast //////////////////////////////////////////////////////
    @State(Scope.Benchmark)
    public static class IndexedLinkedListStateRemoveLast {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class ArrayListStateRemoveLast {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class LinkedListStateRemoveLast {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class TreeListStateRemoveLast {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class NodeCachingLinkedListStateRemoveLast {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new NodeCachingLinkedList<>(CACHE_NODES);
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class CursorableLinkedListStateRemoveLast {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new CursorableLinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////


    //// State removeAt ////////////////////////////////////////////////////////
    @State(Scope.Benchmark)
    public static class IndexedLinkedListStateRemoveAt {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class ArrayListStateRemoveAt {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class LinkedListStateRemoveAt {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class TreeListStateRemoveAt {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class NodeCachingLinkedListStateRemoveAt {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new NodeCachingLinkedList<>(CACHE_NODES);
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class CursorableLinkedListStateRemoveAt {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new CursorableLinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////


    //// State removeObject ////////////////////////////////////////////////////
    @State(Scope.Benchmark)
    public static class IndexedLinkedListStateRemoveObject {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class ArrayListStateRemoveObject {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class LinkedListStateRemoveObject {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class TreeListStateRemoveObject {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class NodeCachingLinkedListStateRemoveObject {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new NodeCachingLinkedList<>(CACHE_NODES);
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class CursorableLinkedListStateRemoveObject {
        public List<Integer> list;
        public List<Integer> contentList;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            list = new CursorableLinkedList<>();
            contentList = new ArrayList<>(REMOVE_COLLECTION_SIZE);
            random = new Random(seed);

            for (int i = 0; i < REMOVE_COLLECTION_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////


    //// State removeRange /////////////////////////////////////////////////////
    @State(Scope.Benchmark)
    public static class IndexedLinkedListStateRemoveRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            contentList = new ArrayList<>(CLEAR_LIST_SIZE);

            for (int i = 0; i < CLEAR_LIST_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class ArrayListStateRemoveRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            contentList = new ArrayList<>(CLEAR_LIST_SIZE);

            for (int i = 0; i < CLEAR_LIST_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class LinkedListStateRemoveRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            contentList = new ArrayList<>(CLEAR_LIST_SIZE);

            for (int i = 0; i < CLEAR_LIST_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class TreeListStateRemoveRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            contentList = new ArrayList<>(CLEAR_LIST_SIZE);

            for (int i = 0; i < CLEAR_LIST_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class NodeCachingLinkedListStateRemoveRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new NodeCachingLinkedList<>(CACHE_NODES);
            contentList = new ArrayList<>(CLEAR_LIST_SIZE);

            for (int i = 0; i < CLEAR_LIST_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class CursorableLinkedListStateRemoveRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new CursorableLinkedList<>();
            contentList = new ArrayList<>(CLEAR_LIST_SIZE);

            for (int i = 0; i < CLEAR_LIST_SIZE; ++i) {
                contentList.add(i);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////


    //// State sortRange ///////////////////////////////////////////////////////
    @State(Scope.Benchmark)
    public static class IndexedLinkedListStateSortRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            contentList = new ArrayList<>(SORT_LIST_SIZE);

            for (int i = 0; i < SORT_LIST_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);
            Collections.shuffle(contentList, random);
        }
    }

    @State(Scope.Benchmark)
    public static class ArrayListStateSortRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            contentList = new ArrayList<>(SORT_LIST_SIZE);

            for (int i = 0; i < SORT_LIST_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);
            Collections.shuffle(contentList, random);
        }
    }

    @State(Scope.Benchmark)
    public static class LinkedListStateSortRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            contentList = new ArrayList<>(SORT_LIST_SIZE);

            for (int i = 0; i < SORT_LIST_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);
            Collections.shuffle(contentList, random);
        }
    }

    @State(Scope.Benchmark)
    public static class TreeListStateSortRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            contentList = new ArrayList<>(SORT_LIST_SIZE);

            for (int i = 0; i < SORT_LIST_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);
            Collections.shuffle(contentList, random);
        }
    }

    @State(Scope.Benchmark)
    public static class NodeCachingLinkedListStateSortRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new NodeCachingLinkedList<>(CACHE_NODES);
            contentList = new ArrayList<>(SORT_LIST_SIZE);

            for (int i = 0; i < SORT_LIST_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);
            Collections.shuffle(contentList, random);
        }
    }

    @State(Scope.Benchmark)
    public static class CursorableLinkedListStateSortRange {
        public List<Integer> list;
        public List<Integer> contentList;

        @Setup(Level.Trial)
        public void setup() {
            list = new CursorableLinkedList<>();
            contentList = new ArrayList<>(SORT_LIST_SIZE);

            for (int i = 0; i < SORT_LIST_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);
            Collections.shuffle(contentList, random);
        }
    }
    ////////////////////////////////////////////////////////////////////////////


    //// State removeInCollection //////////////////////////////////////////////
    @State(Scope.Benchmark)
    public static class IndexedLinkedListStateRemoveInCollection {
        public List<Integer> list;
        public List<Integer> contentList;
        public Set<Integer> set;

        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            contentList = new ArrayList<>(REMOVE_ALL_SIZE);
            set = new HashSet<>();

            for (int i = 0; i < REMOVE_ALL_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);

            while (set.size() < REMOEVE_COLLECTION_CAPACITY) {
                set.add(random.nextInt(contentList.size() + 100));
            }

            Collections.shuffle(contentList, random);
        }
    }

    @State(Scope.Benchmark)
    public static class ArrayListStateRemoveInCollection {
        public List<Integer> list;
        public List<Integer> contentList;
        public Set<Integer> set;

        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            contentList = new ArrayList<>(REMOVE_ALL_SIZE);
            set = new HashSet<>();

            for (int i = 0; i < REMOVE_ALL_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);

            while (set.size() < REMOEVE_COLLECTION_CAPACITY) {
                set.add(random.nextInt(contentList.size() + 100));
            }

            Collections.shuffle(contentList, random);
        }
    }

    @State(Scope.Benchmark)
    public static class LinkedListStateRemoveInCollection {
        public List<Integer> list;
        public List<Integer> contentList;
        public Set<Integer> set;

        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            contentList = new ArrayList<>(REMOVE_ALL_SIZE);
            set = new HashSet<>();

            for (int i = 0; i < REMOVE_ALL_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);

            while (set.size() < REMOEVE_COLLECTION_CAPACITY) {
                set.add(random.nextInt(contentList.size() + 100));
            }

            Collections.shuffle(contentList, random);
        }
    }

    @State(Scope.Benchmark)
    public static class TreeListStateRemoveInCollection {
        public List<Integer> list;
        public List<Integer> contentList;
        public Set<Integer> set;

        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            contentList = new ArrayList<>(REMOVE_ALL_SIZE);
            set = new HashSet<>();

            for (int i = 0; i < REMOVE_ALL_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);

            while (set.size() < REMOEVE_COLLECTION_CAPACITY) {
                set.add(random.nextInt(contentList.size() + 100));
            }

            Collections.shuffle(contentList, random);
        }
    }

    @State(Scope.Benchmark)
    public static class NodeCachingLinkedListStateRemoveInCollection {
        public List<Integer> list;
        public List<Integer> contentList;
        public Set<Integer> set;

        @Setup(Level.Trial)
        public void setup() {
            list = new NodeCachingLinkedList<>(CACHE_NODES);
            contentList = new ArrayList<>(REMOVE_ALL_SIZE);
            set = new HashSet<>();

            for (int i = 0; i < REMOVE_ALL_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);

            while (set.size() < REMOEVE_COLLECTION_CAPACITY) {
                set.add(random.nextInt(contentList.size() + 100));
            }

            Collections.shuffle(contentList, random);
        }
    }

    @State(Scope.Benchmark)
    public static class CursorableLinkedListStateRemoveInCollection {
        public List<Integer> list;
        public List<Integer> contentList;
        public Set<Integer> set;

        @Setup(Level.Trial)
        public void setup() {
            list = new CursorableLinkedList<>();
            contentList = new ArrayList<>(REMOVE_ALL_SIZE);
            set = new HashSet<>();

            for (int i = 0; i < REMOVE_ALL_SIZE; ++i) {
                contentList.add(i);
            }

            Random random = new Random(seed + 3);

            while (set.size() < REMOEVE_COLLECTION_CAPACITY) {
                set.add(random.nextInt(contentList.size() + 100));
            }

            Collections.shuffle(contentList, random);
        }
    }
    ////////////////////////////////////////////////////////////////////////////

    private static Integer getRandomInteger(Random random) {
        return random.nextInt(MAXIMUM_INTEGER + 1);
    }

    //// profileAddFirst ///////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileRoddeListAddFirst() {
        profileAddFirst(new IndexedLinkedList<>(), ADD_FIRST_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListAddFirst() {
        profileAddFirst(new LinkedList<>(), ADD_FIRST_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListAddFirst() {

        profileAddFirst(new ArrayList<>(), ADD_FIRST_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListAddFirst() {
        profileAddFirst(new TreeList<>(), ADD_FIRST_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListAddFirst() {
        profileAddFirst(new NodeCachingLinkedList<>(CACHE_NODES), 
                        ADD_FIRST_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListAddFirst() {
        profileAddFirst(new CursorableLinkedList<>(), ADD_FIRST_OPERATIONS);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileAddLast ////////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileRoddeListAddLast() {
        profileAddLast(new IndexedLinkedList<>(), ADD_LAST_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListAddLast() {
        profileAddLast(new LinkedList<>(), ADD_LAST_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListAddLast() {
        profileAddLast(new ArrayList<>(), ADD_LAST_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListAddLast() {
        profileAddLast(new TreeList<>(), ADD_LAST_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListAddLast() {
        profileAddLast(new NodeCachingLinkedList<>(CACHE_NODES), 
                       ADD_LAST_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListAddLast() {
        profileAddLast(new CursorableLinkedList<>(), ADD_LAST_OPERATIONS);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileAddAtIndex ////////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileRoddeListAddAtIndex() {
        profileAddAtIndex(new IndexedLinkedList<>(), ADD_AT_OPERATIONS);

    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListAddAtIndex() {
        profileAddAtIndex(new LinkedList<>(), ADD_AT_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListAddAtIndex() {
        profileAddAtIndex(new ArrayList<>(), ADD_AT_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListAddAtIndex() {
        profileAddAtIndex(new TreeList<>(), ADD_AT_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListAddAtIndex() {
        profileAddAtIndex(new NodeCachingLinkedList<>(CACHE_NODES),     
                          ADD_AT_OPERATIONS);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListAddAtIndex() {
        profileAddAtIndex(new CursorableLinkedList<>(), ADD_AT_OPERATIONS);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileAddCollection //////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileRoddeListAddCollection() {
        Random random = new Random(seed + 2);
        profileAddCollection(new IndexedLinkedList<>(),
                             ADD_COLLECTION_OPERATIONS, 
                             random);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListAddCollection() {
        Random random = new Random(seed + 2);
        profileAddCollection(new LinkedList<>(),
                             ADD_COLLECTION_OPERATIONS, 
                             random);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListAddCollection() {
        Random random = new Random(seed + 2);
        profileAddCollection(new ArrayList<>(),
                             ADD_COLLECTION_OPERATIONS, 
                             random);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListAddCollection() {
        Random random = new Random(seed + 2);
        profileAddCollection(new TreeList<>(),
                             ADD_COLLECTION_OPERATIONS, 
                             random);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListAddCollection() {
        Random random = new Random(seed + 2);
        profileAddCollection(new NodeCachingLinkedList<>(CACHE_NODES),
                             ADD_COLLECTION_OPERATIONS, 
                             random);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListAddCollection() {
        Random random = new Random(seed + 2);
        profileAddCollection(new CursorableLinkedList<>(),
                             ADD_COLLECTION_OPERATIONS, 
                             random);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileAddCollectionAtIndex ///////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileRoddeListAddCollectionAtIndex() {
        Random random = new Random(seed + 2);
        profileAddCollectionAtIndex(new IndexedLinkedList<>(),
                                    ADD_COLLECTION_AT_OPERATIONS, 
                                    random);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListAddCollectionAtIndex() {
        Random random = new Random(seed + 2);
        profileAddCollectionAtIndex(new LinkedList<>(),
                                    ADD_COLLECTION_AT_OPERATIONS, 
                                    random);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListAddCollectionAtIndex() {
        Random random = new Random(seed + 2);
        profileAddCollectionAtIndex(new ArrayList<>(),
                                    ADD_COLLECTION_AT_OPERATIONS, 
                                    random);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListAddCollectionAtIndex() {
        Random random = new Random(seed + 2);
        profileAddCollectionAtIndex(new TreeList<>(),
                                    ADD_COLLECTION_AT_OPERATIONS, 
                                    random);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListAddCollectionAtIndex() {
        Random random = new Random(seed + 2);
        profileAddCollectionAtIndex(new NodeCachingLinkedList<>(CACHE_NODES),
                                    ADD_COLLECTION_AT_OPERATIONS, 
                                    random);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListAddCollectionAtIndex() {
        Random random = new Random(seed + 2);
        profileAddCollectionAtIndex(new CursorableLinkedList<>(),
                                    ADD_COLLECTION_AT_OPERATIONS, 
                                    random);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileRemoveAt ///////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileRoddeListRemoveAtIndex(
            IndexedLinkedListStateRemoveAt state,
            Blackhole blackhole) {

        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveAt(state.list,
                        state.random,
                        blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListRemoveAtIndex(
            LinkedListStateRemoveAt state,
            Blackhole blackhole) {
        
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveAt(state.list,
                        state.random,
                        blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListRemoveAtIndex(ArrayListStateRemoveAt state,
                                              Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveAt(state.list,
                        state.random,
                        blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListRemoveAtIndex(TreeListStateRemoveAt state, 
                                             Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveAt(state.list,
                        state.random,
                        blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListRemoveAtIndex(
            NodeCachingLinkedListStateRemoveAt state, 
            Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveAt(state.list,
                        state.random,
                        blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListRemoveAtIndex(
            CursorableLinkedListStateRemoveAt state, 
            Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveAt(state.list,
                        state.random,
                        blackhole);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileRemoveObject ///////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileRoddeListRemoveObject(
            IndexedLinkedListStateRemoveObject state,
            Blackhole blackhole) {

        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveObject(state.list, state.random, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListRemoveObject(
            LinkedListStateRemoveObject state,
            Blackhole blackhole) {
        
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveObject(state.list, state.random, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListRemoveObject(ArrayListStateRemoveObject state,
                                             Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveObject(state.list, state.random, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListRemoveObject(TreeListStateRemoveObject state, 
                                            Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveObject(state.list, state.random, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListRemoveObject(
            NodeCachingLinkedListStateRemoveObject state,
            Blackhole blackhole) {
        
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveObject(state.list, state.random, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListRemoveObject(
            TreeListStateRemoveObject state,
            Blackhole blackhole) {
        
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveObject(state.list, state.random, blackhole);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileRemoveObject ///////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileRoddeListRemoveRange(
            IndexedLinkedListStateRemoveRange state) {

        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveRange(state.list);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListRemoveRange(
            LinkedListStateRemoveRange state) {
        
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveRange(state.list);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListRemoveRange(ArrayListStateRemoveRange state) {
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveRange(state.list);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListRemoveRange(TreeListStateRemoveRange state) {
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveRange(state.list);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListRemoveRange(
            NodeCachingLinkedListStateRemoveRange state) {
        
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveRange(state.list);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListRemoveRange(
            CursorableLinkedListStateRemoveRange state) {
        
        state.list.clear();
        state.list.addAll(state.contentList);

        profileRemoveRange(state.list);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileRemoveFirst ////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileRoddeListRemoveFirst(
            IndexedLinkedListStateRemoveFirst state,
            Blackhole blackhole) {

        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveFirst(state.list, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListRemoveFirst(
            LinkedListStateRemoveFirst state,
            Blackhole blackhole) {
        
        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveFirst(state.list, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListRemoveFirst(ArrayListStateRemoveFirst state,
                                            Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveFirst(state.list, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListRemoveFirst(TreeListStateRemoveFirst state, 
                                           Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveFirst(state.list, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListRemoveFirst(
            NodeCachingLinkedListStateRemoveFirst state, 
            Blackhole blackhole) {
        
        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveFirst(state.list, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListRemoveFirst(
            TreeListStateRemoveFirst state, 
            Blackhole blackhole) {
        
        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveFirst(state.list, blackhole);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileRemoveLast /////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileRoddeListRemoveLast(
            IndexedLinkedListStateRemoveLast state,
            Blackhole blackhole) {

        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveLast(state.list, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListRemoveLast(LinkedListStateRemoveLast state,
                                                Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveLast(state.list, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListRemoveLast(ArrayListStateRemoveLast state,
                                           Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveLast(state.list, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListRemoveLast(TreeListStateRemoveLast state, 
                                          Blackhole blackhole) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveLast(state.list, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListRemoveLast(
            NodeCachingLinkedListStateRemoveLast state, 
            Blackhole blackhole) {
        
        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveLast(state.list, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListRemoveLast(
            CursorableLinkedListStateRemoveLast state, 
            Blackhole blackhole) {
        
        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveLast(state.list, blackhole);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileGet ////////////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 0)
    public void profileRoddeListGet(IndexedLinkedListStateGet state,
                                    Blackhole blackhole) {
        profileGet(state.list,
                   GET_OPERATIONS,
                   state.random, 
                   blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListGet(ArrayListStateGet state, 
                                    Blackhole blackhole) {
        profileGet(state.list, 
                   GET_OPERATIONS, 
                   state.random, 
                   blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListGet(LinkedListStateGet state,
                                         Blackhole blackhole) {
        profileGet(state.list, 
                   GET_OPERATIONS,  
                   state.random, 
                   blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListGet(TreeListStateGet state, 
                                   Blackhole blackhole) {

        profileGet(state.list, GET_OPERATIONS, state.random, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListGet(
            NodeCachingLinkedListStateGet state, 
            Blackhole blackhole) {

        profileGet(state.list, GET_OPERATIONS, state.random, blackhole);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListGet(
            CursorableLinkedListStateGet state, 
            Blackhole blackhole) {

        profileGet(state.list, GET_OPERATIONS, state.random, blackhole);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileSortRange //////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 0)
    public void profileRoddeListSortRange(
            IndexedLinkedListStateSortRange state) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileSort(state.list);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListSortRange(ArrayListStateSortRange state) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileSort(state.list);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListSortRange(LinkedListStateSortRange state) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileSort(state.list);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListSortRange(TreeListStateSortRange state) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileSort(state.list);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListSortRange(
            NodeCachingLinkedListStateSortRange state) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileSort(state.list);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListSortRange(
            CursorableLinkedListStateSortRange state) {
        state.list.clear();
        state.list.addAll(state.contentList);
        profileSort(state.list);
    }
    ////////////////////////////////////////////////////////////////////////////


    //// profileRemoveAll //////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 0)
    public void profileRoddeListRemoveAll(
            IndexedLinkedListStateRemoveInCollection state) {

        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveAll(state.list, state.set);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileArrayListRemoveAll(
            ArrayListStateRemoveInCollection state) {

        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveAll(state.list, state.set);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileJavaLinkedListRemoveAll(
            LinkedListStateRemoveInCollection state) {

        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveAll(state.list, state.set);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileTreeListRemoveAll(
            TreeListStateRemoveInCollection state) {

        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveAll(state.list, state.set);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileNodeCachingLinkedListRemoveAll(
            NodeCachingLinkedListStateRemoveInCollection state) {

        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveAll(state.list, state.set);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileCursorableLinkedListRemoveAll(
            CursorableLinkedListStateRemoveInCollection state) {

        state.list.clear();
        state.list.addAll(state.contentList);
        profileRemoveAll(state.list, state.set);
    }
    ////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .forks(0)
                .warmupForks(0)
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(2L))
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(2L))
                .jvmArgsPrepend("-server", "-Xms7G", "-Xmx7G")
                .shouldDoGC(true)
                .timeUnit(TimeUnit.MILLISECONDS)
                .syncIterations(false)
                .build();

        System.out.println("--- Total running times:");

        List<RunResult> runResults = new ArrayList<>(new Runner(opt).run());
        
        Map<String, Double> scoreMap = new HashMap<>();
        
        for (RunResult runResult : runResults) {
            scoreMap.put(runResult.getPrimaryResult().getLabel(), 
                         runResult.getPrimaryResult().getScore());
        }
        
        runResults.sort((r1, r2) -> {
            String label1 = r1.getPrimaryResult().getLabel();
            String label2 = r2.getPrimaryResult().getLabel();
            
            label1 = label1.replace("ArrayList",      "");
            label1 = label1.replace("JavaLinkedList", "");
            label1 = label1.replace("RoddeList",      "");
            label1 = label1.replace("TreeList",       "");
            
            label2 = label2.replace("ArrayList",      "");
            label2 = label2.replace("JavaLinkedList", "");
            label2 = label2.replace("RoddeList",      "");
            label2 = label2.replace("TreeList",       "");
            
            return label1.compareTo(label2);
        });
        
        int index = 0;
        
        System.out.println(
                "|                                         |          |");
        
        for (RunResult rr : runResults) {
            Result result = rr.getPrimaryResult();
            String label = result.getLabel();
            double score = scoreMap.get(label);
            
            System.out.printf("| %37s | %7.3f |\n", label, score);
            
            if (++index % 4 == 0) {
                System.out.println(
                "|                                                  " + 
                "|            |");
            }
        }

        printTotalDurations(runResults);
    }

    private static void printTotalDurations(Collection<RunResult> runResults) {
        Map<String, Double> map = new LinkedHashMap<>();

        map.put("ArrayList", 0.0);
        map.put("JavaLinkedList", 0.0);
        map.put("RoddeList", 0.0);
        map.put("TreeList", 0.0);
        map.put("NodeCachingLinkedList", 0.0);
        map.put("CursorableLinkedList", 0.0);

        for (RunResult runResult : runResults) {
            Result result = runResult.getPrimaryResult();
            String label = result.getLabel();
            double score = result.getScore();

            if (label.contains("ArrayList")) {
                map.put("ArrayList", map.get("ArrayList") + score);
            } else if (label.contains("JavaLinkedList")) {
                map.put("JavaLinkedList", map.get("JavaLinkedList") + score);
            } else if (label.contains("RoddeList")) {
                map.put("RoddeList", map.get("RoddeList") + score);
            } else if (label.contains("TreeList")) {
                map.put("TreeList", map.get("TreeList") + score);
            } else if (label.contains("TreeList")) {
                map.put("NodeCachingLinkedList", 
                        map.get("NodeCachingLinkedList") + score);
            } else if (label.contains("CursorableLinkedList")) {
                map.put("CursorableLinkedList", 
                        map.get("CursorableLinkedList") + score);
            } else {
                System.err.println(
                        "Error: unknown label: "
                                + label 
                                + ", score: " 
                                + score);
            }
        }

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.printf("| Total of %-50s | %-10.3f |\n", 
                              entry.getKey(), 
                              entry.getValue());
        }
    }

    // private methods:
    private void profileAddFirst(List<Integer> list, int operations) {
        for (int i = 0; i < operations; i++) {
            list.add(0, i);
        }
    }

    private void profileAddLast(List<Integer> list, int operations) {
        for (int i = 0; i < operations; i++) {
            list.add(i);
        }
    }

    private void profileAddCollection(List<Integer> list,
                                      int operations, 
                                      Random random) {
        for (int i = 0; i < operations; ++i) {
            List<Integer> col = getCollection(random);
            list.addAll(col);
        }
    }

    private void profileAddCollectionAtIndex(List<Integer> list,
                                             int operations, 
                                             Random random) {
        for (int i = 0; i < operations; ++i) {
            List<Integer> col = getCollection(random);
            int index = random.nextInt(list.size() + 1);
            list.addAll(index, col);
        }
    }

    private void profileRemoveAt(List<Integer> list,
                                 Random random, 
                                 Blackhole blackhole) {
        while (!list.isEmpty()) {
            int index = random.nextInt(list.size());
            Integer i = list.remove(index);
            blackhole.consume(i);
        }
    }

    private void profileRemoveObject(List<Integer> list,
                                     Random random, 
                                     Blackhole blackhole) {
        for (int i = 0; i < REMOVE_OBJECT_OPERATIONS; ++i) {
            Integer value = random.nextInt(REMOVE_COLLECTION_SIZE);
            list.remove((Object) value);
            blackhole.consume(value);
        }
    }

    private void profileRemoveRange(List<Integer> list) {
        if (list instanceof TreeList) {
            int fromIndex = list.size() / 2;
            int toIndex = fromIndex + 1;

            clearFromTreeList(list, fromIndex, toIndex);

            fromIndex = (list.size() - CLEAR_RANGE_SIZE) / 2;
            toIndex = list.size() - fromIndex;

            clearFromTreeList(list, fromIndex, toIndex);
        } else {
            int fromIndex = list.size() / 2;
            int toIndex = fromIndex + 1;

            list.subList(fromIndex, toIndex).clear();

            fromIndex = (list.size() - CLEAR_RANGE_SIZE) / 2;
            toIndex = list.size() - fromIndex;

            list.subList(fromIndex, toIndex).clear();   
        }
    }

    private void clearFromTreeList(List<Integer> list, 
                                   int fromIndex, 
                                   int toIndex) {
        for (int i = fromIndex; i < toIndex; ++i) {
            list.remove(fromIndex);
        }
    }

    private void profileRemoveFirst(List<Integer> list, Blackhole blackhole) {
        if (list instanceof Deque) {
            Deque<Integer> deque = (Deque<Integer>) list;

            while (!deque.isEmpty()) {
                Integer num = deque.removeFirst();
                blackhole.consume(num);
            }
        } else {
            while (!list.isEmpty()) {
                Integer num = list.remove(0);
                blackhole.consume(num);
            }
        }
    }

    private void profileRemoveLast(List<Integer> list, Blackhole blackhole) {
        if (list instanceof Deque) {
            Deque<Integer> deque = (Deque<Integer>) list;

            while (!deque.isEmpty()) {
                Integer num = deque.removeLast();
                blackhole.consume(num);
            }
        } else {
            while (!list.isEmpty()) {
                Integer num = list.remove(list.size() - 1);
                blackhole.consume(num);
            }
        }
    }

    private void profileAddAtIndex(List<Integer> list, int operations) {
        Random random = new Random(seed + 1L);

        for (int i = 0; i < operations; i++) {
            int index = random.nextInt(list.size() + 1);
            Integer value = getRandomInteger(random);
            list.add(index, value);
        }
    }

    private void profileGet(List<Integer> list,
                            int operations, 
                            Random random, 
                            Blackhole blackhole) {
        for (int i = 0; i < operations; i++) {
            Integer j = list.get(random.nextInt(list.size()));
            blackhole.consume(j);
        }
    }

    private void profileSort(List<Integer> list) {
        list.subList(10, list.size() - 10).sort(Integer::compare);
    }

    private void profileRemoveAll(List<Integer> list, 
                                  Collection<Integer> toRemove) {
        if (list instanceof TreeList || list instanceof LinkedList) {
            for (Integer i : toRemove) {
                list.remove(i);
            }
        } else {
            list.removeAll(toRemove);
        }
    }

    private static List<Integer> getCollection(Random random) {
        List<Integer> list = new ArrayList<>();
        int listSize = random.nextInt(MAXIMUM_COLLECTION_SIZE + 1);

        for (int i = 0; i < listSize; ++i) {
            list.add(i);
        }

        return list;
    }
}
