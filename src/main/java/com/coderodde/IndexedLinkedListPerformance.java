package com.coderodde;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import com.github.coderodde.util.IndexedLinkedList;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.apache.commons.collections4.list.TreeList;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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
        public final List<Integer> list = new IndexedLinkedList<>();
        public final Random random = new Random(seed);
    }
    
    @State(Scope.Benchmark)
    public static class ArrayListState {
        public List<Integer> list = new ArrayList<>();
        public final Random random = new Random(seed);
    }
    
    @State(Scope.Benchmark)
    public static class LinkedListState {
        public List<Integer> list = new IndexedLinkedList<>();
        public final Random random = new Random(seed);
    }
    
    @State(Scope.Benchmark)
    public static class TreeListState {
        public List<Integer> list = new TreeList<>();
        public final Random random = new Random(seed);
    }
    
    private void listsEqual() {
//        listsEqual(roddeList,
//                   linkedList, 
//                   arrayList, 
//                   treeList);
    }
    
    private static void listsEqual(List<Integer>... lists) {
        if (lists.length < 2) {
            throw new IllegalArgumentException("lists.length < 2");
        }

        for (int i = 0; i < lists.length - 1; i++) {
            if (lists[i].size() != lists[lists.length - 1].size()) {
                throw new IllegalStateException("Different size");
            }

            Iterator<Integer> iterator1 = lists[i].iterator();
            Iterator<Integer> iterator2 = lists[lists.length - 1].iterator();

            int elementIndex = 0;

            while (iterator1.hasNext() && iterator2.hasNext()) {
                Integer integer1 = iterator1.next();
                Integer integer2 = iterator2.next();

                if (!integer1.equals(integer2)) {
                    throw new IllegalStateException(
                            "Data mismatch: " + integer1 + " vs. " + 
                            integer2 + " at list " + i + 
                            ", element index: " + elementIndex);
                }

                elementIndex++;
            }

            if (iterator1.hasNext() || iterator2.hasNext()) {
                throw new IllegalStateException("Bad iterators");
            }
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
    @Fork(value = 1)
    public void B_01_profileAddFirstRoddeList(IndexedLinkedListState state) {
        profileAddFirst(state.list,
                        ADD_FIRST_OPERATIONS, 
                        state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_02_profileAddFirstLinkedList(LinkedListState state) {
        profileAddFirst(state.list, 
                        ADD_FIRST_OPERATIONS, 
                        state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_03_profileAddFirstArrayList(ArrayListState state) {
        profileAddFirst(state.list, 
                        ADD_FIRST_OPERATIONS, 
                        state.random);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_04_profileAddFirstTreeList(TreeListState state) {
        profileAddFirst(state.list, 
                        ADD_FIRST_OPERATIONS, 
                        state.random);
    }
    ////////////////////////////////////////////////////////////////////////////
 
    
    //// profileAddLast ////////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_05_profileAddLastRoddeList(IndexedLinkedListState state) {
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
    public void B_06_profileAddLastLinkedList(LinkedListState state) {
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
    public void B_07_profileAddLastArrayList(ArrayListState state) {
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
    public void B_08_profileAddLastTreeList(TreeListState state) {
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
    public void B_09_profileAddAtIndexRoddeList(IndexedLinkedListState state) {
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
    public void B_10_profileAddAtIndexArrayList(ArrayListState state) {
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
    public void B_11_profileAddAtIndexLinkedList(LinkedListState state) {
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
    public void B_12_profileAddAtIndexTreeList(TreeListState state) {
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
    public void B_13_profileAddCollectionRoddeList(
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
    public void B_14_profileAddCollectionArrayList(ArrayListState state) {
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
    public void B_15_profileAddCollectionLinkedList(LinkedListState state) {
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
    public void B_16_profileAddCollectionTreeList(TreeListState state) {
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
    public void B_17_profileAddCollectionAtIndexRoddeList(
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
    public void B_18_profileAddCollectionAtIndexArrayList(
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
    public void B_19_profileAddCollectionAtIndexLinkedList(
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
    public void B_20_profileAddCollectionAtIndexTreeList(TreeListState state) {
        profileAddCollectionAtIndex(state.list, 
                                    ADD_AT_OPERATIONS, 
                                    state.random);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    //// profileGet ////////////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_21_profileGetRoddeList(IndexedLinkedListState state,
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
    public void B_22_profileGetArrayList(ArrayListState state,
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
    public void B_23_profileGetLinkedList(LinkedListState state,
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
    public void B_24_profileGetTreeList(TreeListState state, 
                                        Blackhole blackhole) {
        profileGet(state.list, GET_OPERATIONS, state.random, blackhole);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .warmupForks(0)
                .warmupIterations(1)
                .measurementIterations(1)
                .jvmArgsPrepend("-server", "-Xms7G", "-Xmx7G")
                .forks(1)
                .timeUnit(TimeUnit.MILLISECONDS)
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
