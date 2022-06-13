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
import org.openjdk.jmh.annotations.Group;
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

@State(Scope.Benchmark)
public class IndexedLinkedListPerformance {

    private static final int ADD_FIRST_OPERATIONS           = 20_000;
    private static final int ADD_LAST_OPERATIONS            = 20_000;
    private static final int ADD_AT_OPERATIONS              = 10_000;
    private static final int ADD_COLLECTION_AT_OPERATIONS   = 2_000;
    private static final int ADD_LAST_COLLECTION_OPERATIONS = 4_000;
    private static final int REMOVE_VIA_INDEX_OPERATIONS    = 10_000;
    private static final int REMOVE_OBJECT_OPERATIONS       = 5_000;
    private static final int GET_OPERATIONS                 = 10_000;
    private static final int REMOVE_FIRST_OPERATIONS        = 5_000;
    
    private static final int MAXIMUM_INTEGER         = 1_000;
    private static final int MAXIMUM_COLLECTION_SIZE = 20;
    
    private static final long seed = System.currentTimeMillis();
    
    @State(Scope.Benchmark)
    public static class IndexedLinkedListState {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new IndexedLinkedList<>();
            random = new Random(seed);
        }
    }
    
    @State(Scope.Benchmark)
    public static class ArrayListState {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>();
            random = new Random(seed);
        }
    }
    
    @State(Scope.Benchmark)
    public static class LinkedListState {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new LinkedList<>();
            random = new Random(seed);
        }
    }
    
    @State(Scope.Benchmark)
    public static class TreeListState {
        public List<Integer> list;
        public Random random;
        
        @Setup(Level.Trial)
        public void setup() {
            list = new TreeList<>();
            random = new Random(seed);
        }
    }
    
    private static Integer getRandomInteger(Random random) {
        return random.nextInt(MAXIMUM_INTEGER + 1);
    }
 
    //// profileAddFirst ///////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(0)
    @Group(value = "g1")
    public void Benchmark_1A_profileAddFirstRoddeList(IndexedLinkedListState state) {
        profileAddFirst(state.list,
                        ADD_FIRST_OPERATIONS, 
                        state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(0)
    @Group(value = "g2")
    public void Benchmark_2B_profileAddFirstLinkedList(LinkedListState state) {
        profileAddFirst(state.list, 
                        ADD_FIRST_OPERATIONS, 
                        state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(0)
    @Group(value = "g3")
    public void Benchmark_3B_profileAddFirstArrayList(ArrayListState state) {
        profileAddFirst(state.list, 
                        ADD_FIRST_OPERATIONS, 
                        state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(0)
    @Group(value = "g4")
    public void Benchmark_4B_profileAddFirstTreeList(TreeListState state) {
        profileAddFirst(state.list, 
                        ADD_FIRST_OPERATIONS, 
                        state.random);
    }
    ////////////////////////////////////////////////////////////////////////////
 
    
    //// profileAddLast ////////////////////////////////////////////////////////
    /*
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_05_profileAddLastRoddeList(IndexedLinkedListState state) {
        profileAddLast(state.list, 
                       ADD_LAST_OPERATIONS, 
                       state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_06_profileAddLastLinkedList(LinkedListState state) {
        profileAddLast(state.list, 
                       ADD_LAST_OPERATIONS, 
                       state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_07_profileAddLastArrayList(ArrayListState state) {
        profileAddLast(state.list, 
                       ADD_LAST_OPERATIONS, 
                       state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_08_profileAddLastTreeList(TreeListState state) {
        profileAddLast(state.list, 
                       ADD_LAST_OPERATIONS, 
                       state.random);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    //// profileAddAtIndex /////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_09_profileAddAtIndexRoddeList(IndexedLinkedListState state) {
        profileAddAtIndex(state.list, 
                          ADD_AT_OPERATIONS, 
                          state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_10_profileAddAtIndexArrayList(ArrayListState state) {
        profileAddAtIndex(state.list, 
                          ADD_AT_OPERATIONS, 
                          state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_11_profileAddAtIndexLinkedList(LinkedListState state) {
        profileAddAtIndex(state.list, 
                          ADD_AT_OPERATIONS, 
                          state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void Benchmark_12_profileAddAtIndexTreeList(TreeListState state) {
        profileAddAtIndex(state.list, 
                          ADD_AT_OPERATIONS, 
                          state.random);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
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
    
    //// profileGet ////////////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(0)
    @Group(value = "g1")
    public void Benchmark_1B_profileGetRoddeList(IndexedLinkedListState state,
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
    @Fork(0)
    @Group(value = "g2")
    public void Benchmark_2A_profileGetArrayList(ArrayListState state,
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
    @Fork(0)
    @Group(value = "g3")
    public void Benchmark_3A_profileGetLinkedList(LinkedListState state,
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
    @Fork(0)
    @Group(value = "g4")
    public void Benchmark_4A_profileGetTreeList(TreeListState state, 
                                        Blackhole blackhole) {
        profileGet(state.list, GET_OPERATIONS, state.random, blackhole);
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
                .forks(1)
                .timeUnit(TimeUnit.MILLISECONDS)
                .syncIterations(true)
                .threads(1)
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
    private void profileAddFirst(List<Integer> list,
                                 int operations, 
                                 Random random) {
        for (int i = 0; i < operations; i++) {
            list.add(0, getRandomInteger(random));
        }
    }
    
    private void profileAddLast(List<Integer> list,
                                int operations,
                                Random random) {
        for (int i = 0; i < operations; i++) {
            list.add(list.size(), getRandomInteger(random));
        }
    }
    
    private void profileAddAtIndex(List<Integer> list,
                                   int operations,
                                   Random random) {
        for (int i = 0; i < operations; i++) {
            int index = random.nextInt(list.size() + 1);
            Integer value = getRandomInteger(random);
            list.add(index, value);
        }
    }
    
    private void profileAddCollection(List<Integer> list, 
                                      int operations, 
                                      Random random) {
        for (int i = 0; i < operations; i++) {
            List<Integer> collection = createRandomCollection(random);
            list.addAll(collection);
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
}
