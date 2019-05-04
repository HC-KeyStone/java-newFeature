package com.lambda.optional.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * isPresent() 与 obj != null 无任何分别;
 * 有 isPresent() 作铺垫的 get() 调用在 IntelliJ IDEA 中会收到告警;
 * 所以 Optional 中我们真正可依赖的应该是除了 isPresent() 和 get() 的其他方法:
 *
 * public<U> Optional<U> map(Function<? super T, ? extends U> mapper)
 * public T orElse(T other)
 * public T orElseGet(Supplier<? extends T> other)
 * public void ifPresent(Consumer<? super T> consumer)
 * public Optional<T> filter(Predicate<? super T> predicate)
 * public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper)
 * public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws
 *
 */
public class LambdaOfOptional {

    public static void main(String[] args) {

//        optionTest1();

        optionTest2();

//        test();
    }

    public static void test() {

        Set<Integer> set = new HashSet<>();
        set.add(12);
        set.add(18);
        set.add(3);
        set.add(8);
        set.add(25);

        List<Integer> list = set.stream().filter(t -> t == 12 && t == 30).collect(Collectors.toList
                ());

        System.out.println(list);

    }

    /**
     * map  是可能无限级联的, 比如再深一层, 获得用户名的大写形式
     * 这要搁在以前, 每一级调用的展开都需要放一个 null 值的判断
     */
    public static void optionTest2() {
        List<String> orders = null;
        Optional<List<String>> order = Optional.ofNullable(orders);

        System.out.println(order.map(u -> u.size()).orElse(0));

        System.out.println(order
                .map(u -> u.get(0))
                .map(name -> name.toUpperCase())
                .orElse(null));
    }

    public static void optionTest1() {

        List<String> names2 = Arrays.asList("peter", null, "anna", "mike", "xenia");
        names2.sort(Comparator.nullsLast(String::compareTo));
        System.out.println(names2);

        List<String> names3 = null;

        Optional<String> str = names3.stream().reduce((x, y) -> x + "_" + y);

//        System.out.println(str);

        /**
         * 存在才对它做点什么
         */
        Optional.ofNullable(names3)
                .ifPresent(list -> list.sort(Comparator.naturalOrder()));

        System.out.println(names3);

        /**
         * 存在即返回, 无则提供默认值
         */
        System.out.println(Optional.ofNullable(names3).orElse(new ArrayList<>()));

        /**
         * 存在即返回, 无则由函数来产生
         */
        System.out.println(Optional.ofNullable(names3).orElseGet(()-> Arrays.asList("default")));
    }

    public static class OptionalTest {

        public static void main(String[] args) {

            Optional<String> optional = Optional.of("bam");

            System.out.println(optional.isPresent());           // true
            System.out.println(optional.get());                 // "bam"
            System.out.println(optional.orElse("fallback"));    // "bam"

            optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
        }
    }
}
