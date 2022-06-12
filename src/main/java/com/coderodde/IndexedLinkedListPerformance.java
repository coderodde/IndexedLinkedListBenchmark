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
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.WarmupMode;

@State(Scope.Benchmark)
public class IndexedLinkedListPerformance {

    private static final int ADD_FIRST_OPERATIONS           = 100_000;
    private static final int ADD_LAST_OPERATIONS            = 100_000;
    private static final int ADD_AT_OPERATIONS              = 10_000;
    private static final int ADD_COLLECTION_AT_OPERATIONS   = 4_000;
    private static final int ADD_LAST_COLLECTION_OPERATIONS = 10_000;
    private static final int REMOVE_VIA_INDEX_OPERATIONS    = 10_000;
    private static final int REMOVE_OBJECT_OPERATIONS       = 5_000;
    private static final int GET_OPERATIONS                 = 5_000;
    private static final int REMOVE_FIRST_OPERATIONS        = 5_000;
    
    private static final int MAXIMUM_INTEGER = 1_000;
    
    private IndexedLinkedList<Integer> roddeList = new IndexedLinkedList<>();
    private LinkedList<Integer> linkedList = new LinkedList<>();
    private ArrayList<Integer> arrayList = new ArrayList<>();
    private TreeList<Integer> treeList = new TreeList<>();
    
    private final long seed = System.currentTimeMillis();
    
    private Random randomJavaUtilLinkedList = new Random(seed);
    private Random randomJavaUtilArrayList  = new Random(seed);
    private Random randomRoddeList          = new Random(seed);
    private Random randomTreeList           = new Random(seed);

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
 
    private void profileAddFirst() {
        profileAddFirstRoddeListV2();
        profileAddFirstLinkedList();
        profileAddFirstArrayList();
        profileAddFirstTreeList();

        listsEqual();
        System.out.println();
    }
 
    @Benchmark
    @Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void profileAddFirstRoddeListV2() {
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
    public void profileAddFirstLinkedList() {
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
    public void profileAddFirstArrayList() {
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
    public void profileAddFirstTreeList() {
        profileAddFirst(treeList, 
                        ADD_FIRST_OPERATIONS, 
                        randomTreeList);
    }
    
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .warmupForks(0)
                .warmupIterations(1)
                .measurementIterations(1)
                .jvmArgsPrepend("-server")
                .forks(1)
                .timeUnit(TimeUnit.MILLISECONDS)
                .build();
        
        new Runner(opt).run();
    }
    
    // private methods:
    private void profileAddFirst(List<Integer> list,
                                 int operations, 
                                 Random random) {
        for (int i = 0; i < operations; i++) {
            list.add(0, getRandomInteger(random));
        }
    }
}
