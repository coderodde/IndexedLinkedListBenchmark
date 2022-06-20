package com.coderodde;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import com.github.coderodde.util.IndexedLinkedList;
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
    private static final int ADD_INDEX_SIZE                 = 20_000;
    private static final int REMOVE_VIA_INDEX_OPERATIONS    = 10_000;
    private static final int REMOVE_OBJECT_OPERATIONS       = 5_000;
    private static final int GET_OPERATIONS                 = 10_000;
    private static final int GET_SIZE   = 25_000;
    private static final int REMOVE_FIRST_OPERATIONS        = 5_000;
    
    private static final int MAXIMUM_INTEGER         = 1_000;
    private static final int MAXIMUM_COLLECTION_SIZE = 20;
    
    private static final long seed = System.currentTimeMillis();
    
    
    
    
    //// addCollection /////////////////////////////////////////////////////////
    @State(Scope.Benchmark)
    public static class IndexedLinkedListStateAddCollection {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            random = new Random(seed);
        }
    }
    
    @State(Scope.Benchmark)
    public static class ArrayListStateAddCollection {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            random = new Random(seed);
        }
    }
    
    @State(Scope.Benchmark)
    public static class LinkedListStateAddCollection {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            random = new Random(seed);
        }
    }
    
    @State(Scope.Benchmark)
    public static class TreeListStateAddCollection {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            random = new Random(seed);
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    //// get ///////////////////////////////////////////////////////////////////
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
    
    //// addCollectionAtIndex //////////////////////////////////////////////////
    @State(Scope.Benchmark)
    public static class IndexedLinkedListStateAddCollectionAtIndex {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            random = new Random(seed);
        }
    }
    
    @State(Scope.Benchmark)
    public static class ArrayListStateAddCollectionAtIndex {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            random = new Random(seed);
        }
    }
    
    @State(Scope.Benchmark)
    public static class LinkedListStateAddCollectionAtIndex {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            random = new Random(seed);
        }
    }
    
    @State(Scope.Benchmark)
    public static class TreeListStateAddCollectionAtIndex {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            random = new Random(seed);
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
 
    
    //// profileAddCollectionToTail ////////////////////////////////////////////
//    @Benchmark
//    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
//    @Measurement(iterations = 1)
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    public void profileRoddeListAddCollection(
//            IndexedLinkedListStateAddCollection state) {
//        
//        profileAddCollection(state.list, GET_OPERATIONS);
//    }
//    
//    @Benchmark
//    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
//    @Measurement(iterations = 1)
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    public void profileLinkedListAddCollection(
//            LinkedListStateAddCollection state) {
//        
//        profileAddCollection(state.list, ADD_FIRST_OPERATIONS);
//    }
//    
//    @Benchmark
//    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
//    @Measurement(iterations = 1)
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    public void profileArrayListAddCollection(
//            ArrayListStateAddCollection state) {
//        
//        profileAddCollection(state.list, ADD_FIRST_OPERATIONS);
//    }
//    
//    @Benchmark
//    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
//    @Measurement(iterations = 1)
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    public void profileTreeListAddCollection(TreeListStateAddCollection state) {
//        
//        profileAddCollection(state.list, ADD_FIRST_OPERATIONS);
//    }
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
    
    
    /*
    //// AddCollection /////////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_13_profileAddCollectionRoddeList(
            IndexedLinkedListState state) {
        profileAddCollection(state.list, 
                             ADD_LAST_COLLECTION_OPERATIONS, 
                             state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_14_profileAddCollectionArrayList(ArrayListState state) {
        profileAddCollection(state.list, 
                             ADD_LAST_COLLECTION_OPERATIONS, 
                             state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_15_profileAddCollectionLinkedList(LinkedListState state) {
        profileAddCollection(state.list, 
                             ADD_LAST_COLLECTION_OPERATIONS, 
                             state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_16_profileAddCollectionTreeList(TreeListState state) {
        profileAddCollection(state.list, 
                             ADD_LAST_COLLECTION_OPERATIONS, 
                             state.random);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    //// profileAddCollectionAtIndex ///////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_17_profileAddCollectionAtIndexRoddeList(
            IndexedLinkedListState state) {
        profileAddCollectionAtIndex(state.list, 
                                    ADD_COLLECTION_AT_OPERATIONS, 
                                    state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_18_profileAddCollectionAtIndexArrayList(
            ArrayListState state) {
        profileAddCollectionAtIndex(state.list, 
                                    ADD_COLLECTION_AT_OPERATIONS, 
                                    state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_19_profileAddCollectionAtIndexLinkedList(
            LinkedListState state) {
        profileAddCollectionAtIndex(state.list, 
                                    ADD_AT_OPERATIONS, 
                                    state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_20_profileAddCollectionAtIndexTreeList(TreeListState state) {
        profileAddCollectionAtIndex(state.list, 
                                    ADD_AT_OPERATIONS, 
                                    state.random);
    }
    ////////////////////////////////////////////////////////////////////////////
    */
    
    
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
//                .threads(1)
                .build();
        
        new Runner(opt).run();
    }
    
    private static List<Integer> createRandomCollection(Random random) {
        int size = 1 + random.nextInt(MAXIMUM_COLLECTION_SIZE);

        List<Integer> list = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            list.add(getRandomInteger(random));
        }

        return list;
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
    
    private void profileAddAtIndex(List<Integer> list, int operations) {
        Random random = new Random(seed + 1L);
        
        for (int i = 0; i < operations; i++) {
            int index = random.nextInt(list.size() + 1);
            Integer value = getRandomInteger(random);
            list.add(index, value);
        }
    }
    
    private void profileAddCollectionAtIndex(List<Integer> list,
                                             int operations,
                                             Random random) {
        
        for (int i = 0; i < operations; i++) {
            List<Integer> collection = createRandomCollection(random);
            int index = random.nextInt(list.size() + 1);
            list.addAll(index, collection);
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
    
    private static List<Integer> getCollection(Random random) {
        List<Integer> list = new ArrayList<>();
        int listSize = random.nextInt(MAXIMUM_COLLECTION_SIZE + 1);
        
        for (int i = 0; i < listSize; ++i) {
            list.add(i);
        }
        
        return list;
    }
}
