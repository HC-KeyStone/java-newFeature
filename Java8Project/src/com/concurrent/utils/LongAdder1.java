package com.concurrent.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

/**
 * @author Benjamin Winterberg
 *
 * AtomicLong是作用是对长整形进行原子操作，显而易见，在java1.8中新加入了一个新的原子类LongAdder，该类也可以保证Long类型操作的原子性，
 * 相对于AtomicLong，LongAdder有着更高的性能和更好的表现，可以完全替代AtomicLong的来进行原子操作
 */
public class LongAdder1 {

    private static final int NUM_INCREMENTS = 10000;

    private static LongAdder adder = new LongAdder();

    public static void main(String[] args) {
        testIncrement();
        testAdd();
    }

    private static void testAdd() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(() -> adder.add(2)));

        ConcurrentUtils.stop(executor);

        System.out.format("Add: %d\n", adder.sumThenReset());
    }

    private static void testIncrement() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(adder::increment));

        ConcurrentUtils.stop(executor);

        System.out.format("Increment: Expected=%d; Is=%d\n", NUM_INCREMENTS, adder.sumThenReset());
    }
}
