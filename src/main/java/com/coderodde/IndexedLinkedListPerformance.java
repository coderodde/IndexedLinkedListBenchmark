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
    
    private IndexedLinkedList<Integer> roddeList  = new IndexedLinkedList<>();
    private LinkedList<Integer>        linkedList = new LinkedList<>();
    private ArrayList<Integer>         arrayList  = new ArrayList<>();
    private TreeList<Integer>          treeList   = new TreeList<>();
    
    private final long seed = System.currentTimeMillis();
    
    private final Random randomJavaUtilLinkedList = new Random(seed);
    private final Random randomJavaUtilArrayList  = new Random(seed);
    private final Random randomRoddeList          = new Random(seed);
    private final Random randomTreeList           = new Random(seed);
    
    private void listsEqual() {
        listsEqual(roddeList,
                   linkedList, 
                   arrayList, 
                   treeList);
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
    public void B_01_profileAddFirstRoddeList() {
        profileAddFirst(roddeList, 
                        ADD_FIRST_OPERATIONS, 
                        randomRoddeList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_02_profileAddFirstLinkedList() {
        profileAddFirst(linkedList, 
                        ADD_FIRST_OPERATIONS, 
                        randomJavaUtilLinkedList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_03_profileAddFirstArrayList() {
        profileAddFirst(arrayList, 
                        ADD_FIRST_OPERATIONS, 
                        randomJavaUtilArrayList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_04_profileAddFirstTreeList() {
        profileAddFirst(treeList, 
                        ADD_FIRST_OPERATIONS, 
                        randomTreeList);
    }
    ////////////////////////////////////////////////////////////////////////////
 
    
    //// profileAddLast ////////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_05_profileAddLastRoddeList() {
        profileAddLast(roddeList, 
                       ADD_LAST_OPERATIONS, 
                       randomRoddeList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_06_profileAddLastLinkedList() {
        profileAddLast(linkedList, 
                       ADD_LAST_OPERATIONS, 
                       randomJavaUtilLinkedList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_07_profileAddLastArrayList() {
        profileAddLast(arrayList, 
                       ADD_LAST_OPERATIONS, 
                       randomJavaUtilArrayList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_08_profileAddLastTreeList() {
        profileAddLast(treeList, 
                       ADD_LAST_OPERATIONS, 
                       randomTreeList);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    //// profileAddAtIndex /////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_09_profileAddAtIndexRoddeList() {
        profileAddAtIndex(roddeList, 
                          ADD_AT_OPERATIONS, 
                          randomRoddeList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_10_profileAddAtIndexArrayList() {
        profileAddAtIndex(arrayList, 
                          ADD_AT_OPERATIONS, 
                          randomJavaUtilArrayList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_11_profileAddAtIndexLinkedList() {
        profileAddAtIndex(linkedList, 
                          ADD_AT_OPERATIONS, 
                          randomJavaUtilLinkedList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_12_profileAddAtIndexTreeList() {
        profileAddAtIndex(treeList, 
                          ADD_AT_OPERATIONS, 
                          randomTreeList);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    //// AddCollection /////////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_13_profileAddCollectionRoddeList() {
        profileAddCollection(roddeList, 
                             ADD_LAST_COLLECTION_OPERATIONS, 
                             randomRoddeList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_14_profileAddCollectionArrayList() {
        profileAddCollection(arrayList, 
                             ADD_LAST_COLLECTION_OPERATIONS, 
                             randomJavaUtilArrayList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_15_profileAddCollectionLinkedList() {
        profileAddCollection(linkedList, 
                             ADD_LAST_COLLECTION_OPERATIONS, 
                             randomJavaUtilLinkedList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_16_profileAddCollectionTreeList() {
        profileAddCollection(treeList, 
                             ADD_LAST_COLLECTION_OPERATIONS, 
                             randomTreeList);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    //// profileAddCollectionAtIndex ///////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_17_profileAddCollectionAtIndexRoddeList() {
        profileAddCollectionAtIndex(roddeList, 
                                    ADD_COLLECTION_AT_OPERATIONS, 
                                    randomRoddeList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_18_profileAddCollectionAtIndexArrayList() {
        profileAddCollectionAtIndex(arrayList, 
                                    ADD_COLLECTION_AT_OPERATIONS, 
                                    randomJavaUtilArrayList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_19_profileAddCollectionAtIndexLinkedList() {
        profileAddCollectionAtIndex(linkedList, 
                                    ADD_AT_OPERATIONS, 
                                    randomJavaUtilLinkedList);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_20_profileAddCollectionAtIndexTreeList() {
        profileAddCollectionAtIndex(treeList, 
                                    ADD_AT_OPERATIONS, 
                                    randomTreeList);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    
    //// profileGet ////////////////////////////////////////////////////////////
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_21_profileGetRoddeList(Blackhole blackhole) {
        profileGet(roddeList, GET_OPERATIONS, randomRoddeList, blackhole);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_22_profileGetArrayList(Blackhole blackhole) {
        profileGet(arrayList, 
                   GET_OPERATIONS, 
                   randomJavaUtilArrayList, 
                   blackhole);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_23_profileGetLinkedList(Blackhole blackhole) {
        profileGet(linkedList, 
                   GET_OPERATIONS,  
                   randomJavaUtilLinkedList, 
                   blackhole);
    }
    
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void B_24_profileGetTreeList(Blackhole blackhole) {
        profileGet(treeList, GET_OPERATIONS, randomTreeList, blackhole);
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
