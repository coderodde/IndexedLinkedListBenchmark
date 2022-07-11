package com.coderodde;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.apache.commons.collections4.list.TreeList;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import com.github.coderodde.util.IndexedLinkedList;

/**
 * Benchmarks for {@link IndexedLinkedList}, including comparisons to
 * other standard list implementations.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1, jvmArgs = {"-server", "-Xms512M", "-Xmx512M"})
public class IndexedLinkedListPerformance2 {

    /** Single object used as a list element. */
    private static final Object ELEMENT = new Object();

    /** Threshold value for adding elements to a list when iterating and modifying.
     * If a random double is above this value, an element will be added.
     */
    private static final double ITERATE_AND_MODIFY_ADD_THRESHOLD = 0.7;

    /** Threshold value for removing elements from a list when iterating and modifying.
     * If a random double is below this value, the list element will be removed.
     */
    private static final double ITERATE_AND_MODIFY_REMOVE_THRESHOLD = 0.3;

    /** Base list benchmark state class.*/
    @State(Scope.Thread)
    public static class ListBenchmarkState {

        /** The number of elements in the lists. */
        @Param({"10", "100", "1000", "10000"})
        private int size;

        /** Seed for random number generation. */
        @Param({"1"})
        private long seed;

        private UniformRandomProvider rng;

        @Setup(Level.Iteration)
        public void setup() {
            rng = RandomSource.XO_RO_SHI_RO_128_PP.create(seed);
        }

        public int getSize() {
            return size;
        }

        public UniformRandomProvider getRng() {
            return rng;
        }
    }

    /** Benchmark state class containing a prepopulated list. */
    public static class PrepopulatedBenchmarkState extends ListBenchmarkState {

        private final Supplier<List<Object>> factory;

        PrepopulatedBenchmarkState(final Supplier<List<Object>> factory) {
            this.factory = factory;
        }

        public List<Object> getPopulatedList() {
            final List<Object> list = factory.get();
            for (int i = 0; i < getSize(); ++i) {
                list.add(ELEMENT);
            }
            return list;
        }
    }

    /** {@link PrepopulatedBenchmarkState} containing an {@link ArrayList} */
    public static class ArrayListPrepopulatedBenchmarkState extends PrepopulatedBenchmarkState {
        public ArrayListPrepopulatedBenchmarkState() {
            super(ArrayList::new);
        }
    }

    /** {@link PrepopulatedBenchmarkState} containing a {@link LinkedList} */
    public static class LinkedListPrepopulatedBenchmarkState extends PrepopulatedBenchmarkState {
        public LinkedListPrepopulatedBenchmarkState() {
            super(LinkedList::new);
        }
    }

    /** {@link PrepopulatedBenchmarkState} containing a {@link TreeList} */
    public static class TreeListPrepopulatedBenchmarkState extends PrepopulatedBenchmarkState {
        public TreeListPrepopulatedBenchmarkState() {
            super(TreeList::new);
        }
    }

    /** {@link PrepopulatedBenchmarkState} containing an {@link IndexedLinkedList} */
    public static class IndexedLinkedListPrepopulatedBenchmarkState extends PrepopulatedBenchmarkState {
        public IndexedLinkedListPrepopulatedBenchmarkState() {
            super(IndexedLinkedList::new);
        }
    }

    /** Add {@code cnt} number of elements one after another to the end of {@code list}.
     * @param list list to add to
     * @param cnt number of elements to add
     * @return list given as an argument
     */
    private static List<Object> addAtEnd(final List<Object> list, final int cnt) {
        for (int i = 0; i < cnt; ++i) {
            list.add(ELEMENT);
        }
        return list;
    }

    /** Add {@code cnt} number of elements one after another to the beginning of {@code list}.
     * @param list list to add to
     * @param cnt number of elements to add
     * @return list given as an argument
     */
    private static List<Object> addAtBeginning(final List<Object> list, final int cnt) {
        for (int i = 0; i < cnt; ++i) {
            list.add(0, ELEMENT);
        }
        return list;
    }

    /** Add {@code cnt} number of elements one after another at random indices within {@code list}.
     * @param list list to add to
     * @param cnt number of elements to add
     * @param rng random provider
     * @return list given as an argument
     */
    private static List<Object> addRandom(final List<Object> list, final int cnt,
            final UniformRandomProvider rng) {
        for (int i = 0; i < cnt; ++i) {
            final int idx = list.isEmpty() ? 0 : rng.nextInt(list.size());
            list.add(idx, ELEMENT);
        }
        return list;
    }

    /** Remove elements from the end of {@code list} until it is empty.
     * @param list list to remove elements from
     * @return list given as an argument
     */
    private static List<Object> removeFromEnd(final List<Object> list) {
        for (int i = list.size() - 1; i >= 0; --i) {
            list.remove(i);
        }
        return list;
    }

    /** Remove elements from the beginning of {@code list} until it is empty.
     * @param list list to remove elements from
     * @return list given as an argument
     */
    private static List<Object> removeFromBeginning(final List<Object> list) {
        for (int s = list.size(); s > 0; --s) {
            list.remove(0);
        }
        return list;
    }

    /** Remove elements from random indices in {@code list} until it is empty.
     * @param list list to remove elements from
     * @param rng random provider
     * @return list given as an argument
     */
    private static List<Object> removeRandom(final List<Object> list, final UniformRandomProvider rng) {
        for (int s = list.size(); s > 0; --s) {
            final int idx = rng.nextInt(s);
            list.remove(idx);
        }
        return list;
    }

    /** Get elements at random indices in {@code list} and pass them to the given black hole.
     * The number of elements fetched is equal to the size of the list.
     * @param list list to fetch elements from
     * @param rng random provider
     * @param bh black hole instance
     * @return list given as an argument
     */
    private static List<Object> getRandom(final List<Object> list, final UniformRandomProvider rng,
            final Blackhole bh) {
        final int size = list.size();
        for (int i = 0; i < size; ++i) {
            final int idx = rng.nextInt(size);
            bh.consume(list.get(idx));
        }
        return list;
    }

    /** Iterate through all elements in {@code list} using the list's iterator. The list elements
     * are passed to the given black hole.
     * @param list list to iterate through
     * @param bh black hole instance
     * @return list given as an argument
     */
    private static List<Object> iterate(final List<Object> list, final Blackhole bh) {
        for (final Object element : list) {
            bh.consume(element);
        }
        return list;
    }

    /** Iterate through all elements in {@code list}, randomly adding or removing list elements
     * during the iteration.
     * @param list list to iterate and modify
     * @param rng random provider
     * @param bh black hole
     * @return list given as an argument
     */
    private static List<Object> iterateAndModify(final List<Object> list, final UniformRandomProvider rng,
            final Blackhole bh) {
        final ListIterator<Object> it = list.listIterator();
        while (it.hasNext()) {
            bh.consume(it.next());

            final double randomValue = rng.nextDouble();
            if (randomValue > ITERATE_AND_MODIFY_ADD_THRESHOLD) {
                it.add(ELEMENT);
            }
            if (randomValue < ITERATE_AND_MODIFY_REMOVE_THRESHOLD) {
                it.remove();
            }
        }
        return list;
    }

    @Benchmark
    public List<Object> arrayListAddAtEnd(final ListBenchmarkState state) {
        return addAtEnd(new ArrayList<>(), state.getSize());
    }

    @Benchmark
    public List<Object> arrayListAddAtBeginning(final ListBenchmarkState state) {
        return addAtBeginning(new ArrayList<>(), state.getSize());
    }

    @Benchmark
    public List<Object> arrayListAddRandom(final ListBenchmarkState state) {
        return addRandom(new ArrayList<>(), state.getSize(), state.getRng());
    }

    @Benchmark
    public List<Object> arrayListRemoveFromEnd(final ArrayListPrepopulatedBenchmarkState state) {
        return removeFromEnd(state.getPopulatedList());
    }

    @Benchmark
    public List<Object> arrayListRemoveFromBeginning(final ArrayListPrepopulatedBenchmarkState state) {
        return removeFromBeginning(state.getPopulatedList());
    }

    @Benchmark
    public List<Object> arrayListRemoveRandom(final ArrayListPrepopulatedBenchmarkState state) {
        return removeRandom(state.getPopulatedList(), state.getRng());
    }

    @Benchmark
    public List<Object> arrayListGetRandom(final ArrayListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return getRandom(state.getPopulatedList(), state.getRng(), bh);
    }

    @Benchmark
    public List<Object> arrayListIterate(final ArrayListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return iterate(state.getPopulatedList(), bh);
    }

    @Benchmark
    public List<Object> arrayListIterateAndModify(final ArrayListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return iterateAndModify(state.getPopulatedList(), state.getRng(), bh);
    }

    @Benchmark
    public List<Object> linkedListAddAtEnd(final ListBenchmarkState state) {
        return addAtEnd(new LinkedList<>(), state.getSize());
    }

    @Benchmark
    public List<Object> linkedListAddAtBeginning(final ListBenchmarkState state) {
        return addAtBeginning(new LinkedList<>(), state.getSize());
    }

    @Benchmark
    public List<Object> linkedListAddRandom(final ListBenchmarkState state) {
        return addRandom(new LinkedList<>(), state.getSize(), state.getRng());
    }

    @Benchmark
    public List<Object> linkedListRemoveFromEnd(final LinkedListPrepopulatedBenchmarkState state) {
        return removeFromEnd(state.getPopulatedList());
    }

    @Benchmark
    public List<Object> linkedListRemoveFromBeginning(final LinkedListPrepopulatedBenchmarkState state) {
        return removeFromBeginning(state.getPopulatedList());
    }

    @Benchmark
    public List<Object> linkedListRemoveRandom(final LinkedListPrepopulatedBenchmarkState state) {
        return removeRandom(state.getPopulatedList(), state.getRng());
    }

    @Benchmark
    public List<Object> linkedListGetRandom(final LinkedListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return getRandom(state.getPopulatedList(), state.getRng(), bh);
    }

    @Benchmark
    public List<Object> linkedListIterate(final LinkedListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return iterate(state.getPopulatedList(), bh);
    }

    @Benchmark
    public List<Object> linkedListIterateAndModify(final LinkedListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return iterateAndModify(state.getPopulatedList(), state.getRng(), bh);
    }

    @Benchmark
    public List<Object> treeListAddAtEnd(final ListBenchmarkState state) {
        return addAtEnd(new TreeList<>(), state.getSize());
    }

    @Benchmark
    public List<Object> treeListAddAtBeginning(final ListBenchmarkState state) {
        return addAtBeginning(new TreeList<>(), state.getSize());
    }

    @Benchmark
    public List<Object> treeListAddRandom(final ListBenchmarkState state) {
        return addRandom(new TreeList<>(), state.getSize(), state.getRng());
    }

    @Benchmark
    public List<Object> treeListRemoveFromEnd(final TreeListPrepopulatedBenchmarkState state) {
        return removeFromEnd(state.getPopulatedList());
    }

    @Benchmark
    public List<Object> treeListRemoveFromBeginning(final TreeListPrepopulatedBenchmarkState state) {
        return removeFromBeginning(state.getPopulatedList());
    }

    @Benchmark
    public List<Object> treeListRemoveRandom(final TreeListPrepopulatedBenchmarkState state) {
        return removeRandom(state.getPopulatedList(), state.getRng());
    }

    @Benchmark
    public List<Object> treeListGetRandom(final TreeListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return getRandom(state.getPopulatedList(), state.getRng(), bh);
    }

    @Benchmark
    public List<Object> treeListIterate(final TreeListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return iterate(state.getPopulatedList(), bh);
    }

    @Benchmark
    public List<Object> treeListIterateAndModify(final TreeListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return iterateAndModify(state.getPopulatedList(), state.getRng(), bh);
    }

    @Benchmark
    public List<Object> indexedLinkedListAddAtEnd(final ListBenchmarkState state) {
        return addAtEnd(new IndexedLinkedList<>(), state.getSize());
    }

    @Benchmark
    public List<Object> indexedLinkedListAddAtBeginning(final ListBenchmarkState state) {
        return addAtBeginning(new IndexedLinkedList<>(), state.getSize());
    }

    @Benchmark
    public List<Object> indexedLinkedListAddRandom(final ListBenchmarkState state) {
        return addRandom(new IndexedLinkedList<>(), state.getSize(), state.getRng());
    }

    @Benchmark
    public List<Object> indexedLinkedListRemoveFromEnd(final IndexedLinkedListPrepopulatedBenchmarkState state) {
        return removeFromEnd(state.getPopulatedList());
    }

    @Benchmark
    public List<Object> indexedLinkedListRemoveFromBeginning(final IndexedLinkedListPrepopulatedBenchmarkState state) {
        return removeFromBeginning(state.getPopulatedList());
    }

    @Benchmark
    public List<Object> indexedLinkedListRemoveRandom(final IndexedLinkedListPrepopulatedBenchmarkState state) {
        return removeRandom(state.getPopulatedList(), state.getRng());
    }

    @Benchmark
    public List<Object> indexedLinkedListGetRandom(final IndexedLinkedListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return getRandom(state.getPopulatedList(), state.getRng(), bh);
    }

    @Benchmark
    public List<Object> indexedLinkedListIterate(final IndexedLinkedListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return iterate(state.getPopulatedList(), bh);
    }

    @Benchmark
    public List<Object> indexedLinkedListIterateAndModify(final IndexedLinkedListPrepopulatedBenchmarkState state,
            final Blackhole bh) {
        return iterateAndModify(state.getPopulatedList(), state.getRng(), bh);
    }
}
