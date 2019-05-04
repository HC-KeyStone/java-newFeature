package com.newmap.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapComputeTest {

    static class Student {
        String name;
        String sex;
        int age;

        public Student(String name, String sex, int age) {
            this.name = name;
            this.sex = sex;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        public int getAge() {
            return age;
        }

        public String toString() {
            return "Student{name=" + name + ",sex=" + sex + ",age=" + age + "}";
        }
    }

    public static void main(String[] args) {
        //学生的集合
        List<Student> students = new ArrayList<>();
        students.add(new Student("张三","男",18));
        students.add(new Student("李四","男",20));
        students.add(new Student("韩梅梅","女",18));
        students.add(new Student("小红","女",45));

        mapMergeBefore(students);

        mapMergeLamda(students);

        mapComputeIfAbsentBefore(students);

        mapComputeIfAbsentLamda(students);
    }

    /**
     * 现在我们要做一个操作，把list中的对象，按照属性男女分组，然后把年龄汇总。如果是1.8之前的实现
     */
    public static void mapMergeBefore(List<Student> students) {

        System.out.println(" ---------------------- mapMergeBefore ------------------- ");

        //声明接收结果的 map
        Map<String, Integer> resultMap = new HashMap<>();
        for (Student student : students) {
            Integer age = resultMap.get(student.getSex());
            if(age != null){
                resultMap.put(student.getSex(),student.getAge() + age);
            }else {
                resultMap.put(student.getSex(),student.getAge());
            }
        }
        printResult(resultMap);
    }

    /**
     * merge default V merge(K key, V value,BiFunction《? super V, ? super V, ? extends V> remappingFunction)
     * merge 的方法有三个参数 第一个是所选map的key，第二个是需要合并的值，第三个是 如何合并，也就是说合并方法的具体实现
     * 功能大部分与compute相同，不同之处在于BiFunction中apply的参数，入参为oldValue、value
     *
     * 还是看代码更快！！！
     */
    public static void mapMergeLamda(List<Student> students) {

        System.out.println(" -------------------- mapMergeLamda --------------------- ");

        Map<String, Integer> resultMap = new HashMap<>();
        for (Student student : students) {
            resultMap.merge(student.getSex(), student.getAge(), (a, b) -> b + a);
        }
        printResult(resultMap);
    }

    /**
     * 情景：按照男女分组; 如果是1.8之前的实现
     */
    public static void mapComputeIfAbsentBefore(List<Student> students) {

        System.out.println(" ---------------------- mapComputeIfAbsentBefore ------------------- ");

        //声明接收结果的 map
        Map<String, List<Student>> resultMap = new HashMap<>();
        for (Student student : students) {
            List<Student> s = resultMap.get(student.getSex());
            if(null == s){
                s = new ArrayList<>();
                resultMap.put(student.getSex(),s);
            }
            s.add(student);
        }

        System.out.println(resultMap);
    }

    /**
     * computeIfAbsent : computeIfAbsent(K key, Function <? super K, ? extends V> mappingFunction)
     * computeIfAbsent : 的方法有两个参数 第一个是所选map的key，第二个是需要做的操作。这个方法当key值不存在时才起作用。
     */
    public static void mapComputeIfAbsentLamda(List<Student> students) {

        System.out.println(" ---------------------- mapComputeIfAbsentLamda ------------------- ");

        Map<String, List<Student>> resultMap = new HashMap<>();
        for (Student student : students) {
            List<Student> s = resultMap.computeIfAbsent(student.getSex(), k -> new ArrayList<>());
            s.add(student);
        }

        System.out.println(resultMap);
    }

    private static void printResult(Map<String, Integer> map){
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.println("key:"+e.getKey()+"   "+"value:"+e.getValue());
        }
    }
}
