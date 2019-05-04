package com.newmap.test;

import java.util.HashMap;
import java.util.Map;

public class MapOfJava8Test {

    public void testMap() {

        Map<Integer, String> map = new HashMap<>();
        /**
         * Map putIfAbsent 如果给定的key不存在（或者key对应的value为null），关联给定的key和给定的value，并返回null；
         * 如果存在，返回当前值（不会把value放进去）；
         */
        for (int i = 0; i < 10; i++) {
            //putIfAbsent 使得我们不用写是否为 null 值的检测语句
            map.putIfAbsent(i, "val_" + i);
        }

        /**
         * Map 支持 Stream 流
         */
        //forEach使用consumer来对map中的每个元素进行操作,执行通用任务。
        map.forEach((key, value) -> System.out.print(key + " : " + value + "; "));
        System.out.println();

        /**
         * Map computeIfPresent key存在并且不为空，计算remappingFunction的值value；
         *      如果value不为空，保存指定key和value的映射关系；
         *      如果value为null，remove（key）；
         *      如果计算value的过程抛出了异常，computeIfPresent方法中会再次抛出，key和其对应的值不会改变;
         */
        //将key为3对应的值(val_3)改为  "val_3"+3*10 = val_330
        map.computeIfPresent(3, (key, val) -> val + key * 10);
        System.out.println(map.get(3)); //val_330

        //将key为9对应的值(val_9)改为  null
        map.computeIfPresent(9, (key, val) -> null);
        System.out.println(map.containsKey(9));  // false   相当于从map中移除了此项


        /**
         * Map computeIfAbsent 如果给定的key不存在（或者key对应的value为null），就去计算mappingFunction的值；
         *      如果mappingFunction的值不为null，就把key=value放进去；
         *      如果mappingFunction的值为null，就不会记录该映射关系，返回值为null；
         *      如果计算mappingFunction的值的过程出现异常，再次抛出异常，不记录映射关系，返回null；
         */
        //将key为23对应的值(null)改为  "val_23"
        map.computeIfAbsent(23, num -> "val_" + num);
        System.out.println(map.containsKey(23)); //true
        map.forEach((key, value) -> System.out.print(key + " : " + value + "; "));
        System.out.println();


        map.put(3, null);
        //如果key为3对应的值为null, 将其值改为"bam"
        map.computeIfAbsent(3, num -> "bam");
        System.out.println(map.get(3)); // bam

        //---------3--------------删除给定键所对应的元素。删除操作还需要满足给定的值需要和map中的值相等：
        map.remove(3, "val3");
        System.out.println(map.get(3));
        ;             // bam

        map.remove(3, "bam");
        System.out.println(map.get(3));

        //----------4--------其他一些帮助性方法：
        map.getOrDefault(44, "404 not found");

        //---------------5----------------合并map中的实体
        //此时map.get(9)=null
        map.merge(9, "val999", (value, newValue) -> value.concat(newValue));
        System.err.println(map.get(9));// val999

        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        System.err.println(map.get(9));// val999concat
    }
}
