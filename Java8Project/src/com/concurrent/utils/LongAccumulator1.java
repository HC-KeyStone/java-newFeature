package com.concurrent.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

/**
 * @author Benjamin Winterberg
 *
 * LongAdder类是LongAccumulator的一个特例，LongAccumulator提供了比LongAdder更强大的功能
 * 详见：https://www.cnblogs.com/huangjuncong/p/9152510.html
 */
public class LongAccumulator1 {

    public static void main(String[] args) {
        testAccumulate();
    }

    private static void testAccumulate() {
        LongBinaryOperator op = (x, y) -> 2 * x + y;
        LongAccumulator accumulator = new LongAccumulator(op, 1L);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10)
                .forEach(i -> executor.submit(() -> accumulator.accumulate(i)));

        ConcurrentUtils.stop(executor);

        System.out.format("Add: %d\n", accumulator.getThenReset());
    }
}
