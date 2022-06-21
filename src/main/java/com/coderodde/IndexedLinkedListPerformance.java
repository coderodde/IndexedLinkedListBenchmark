package com.coderodde;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import com.github.coderodde.util.IndexedLinkedList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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

    private static final int ADD_FIRST_OPERATIONS           = 20_000;
    private static final int ADD_LAST_OPERATIONS            = 20_000;
    private static final int ADD_AT_OPERATIONS              = 10_000;
    private static final int ADD_COLLECTION_AT_OPERATIONS   = 2_000;
    private static final int ADD_COLLECTION_OPERATIONS      = 4_000;
    private static final int REMOVE_COLLECTION_SIZE         = 50_000;
    private static final int REMOVE_OBJECT_OPERATIONS       = 15_000;
    private static final int GET_SIZE                       = 25_000;
    private static final int GET_OPERATIONS                 = 10_000;
    private static final int CLEAR_LIST_SIZE                = 100_000;
    private static final int CLEAR_RANGE_SIZE               = 90_000;
    private static final int SORT_LIST_SIZE                 = 100_000;
    
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
    ////////////////////////////////////////////////////////////////////////////

    
    //// State removeRange /////////////////////////////////////////////////////
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
    public void profileLinkedListAddFirst() {
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
    public void profileLinkedListAddLast() {
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
    public void profileLinkedListAddAtIndex() {
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
    public void profileLinkedListAddCollection() {
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
    public void profileLinkedListAddCollectionAtIndex() {
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
    public void profileLinkedListRemoveAtIndex(LinkedListStateRemoveAt state,
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
    public void profileLinkedListRemoveObject(LinkedListStateRemoveObject state,
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
    public void profileLinkedListRemoveRange(LinkedListStateRemoveRange state) {
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
    public void profileLinkedListRemoveFirst(LinkedListStateRemoveFirst state,
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
    public void profileLinkedListRemoveLast(LinkedListStateRemoveLast state,
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
    public void profileLinkedListGet(LinkedListStateGet state,
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
    public void profileLinkedListSortRange(LinkedListStateSortRange state) {
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
        
        Collection<RunResult> runResults = new Runner(opt).run();
        
        for (RunResult rr : runResults) {
            Result result = rr.getPrimaryResult();
            System.out.println(
                    rr.getPrimaryResult().getLabel() 
                            + ": " 
                            + result.getScore());
        }
        
        printTotalDurations(runResults);
    }
    
    private static void printTotalDurations(Collection<RunResult> runResults) {
        Map<String, Double> map = new LinkedHashMap<>();
        
        map.put("ArrayList", 0.0);
        map.put("LinkedList", 0.0);
        map.put("RoddeList", 0.0);
        map.put("TreeList", 0.0);
        
        for (RunResult runResult : runResults) {
            Result result = runResult.getPrimaryResult();
            String label = result.getLabel();
            double score = result.getScore();
            
            if (label.contains("ArrayList")) {
                map.put("ArrayList", map.get("ArrayList") + score);
            } else if (label.contains("LinkedList")) {
                map.put("LinkedList", map.get("LinkedList") + score);
            } else if (label.contains("RoddeList")) {
                map.put("RoddeList", map.get("RoddeList") + score);
            } else if (label.contains("TreeList")) {
                map.put("TreeList", map.get("TreeList") + score);
            }
        }
        
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.printf("%10s: %.3f\n", entry.getKey(), entry.getValue());
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
    
    private static List<Integer> getCollection(Random random) {
        List<Integer> list = new ArrayList<>();
        int listSize = random.nextInt(MAXIMUM_COLLECTION_SIZE + 1);
        
        for (int i = 0; i < listSize; ++i) {
            list.add(i);
        }
        
        return list;
    }
}
