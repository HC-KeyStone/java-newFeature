package com.newmap.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerateMap {

    public static void main(String[] args) {
        //学生的集合
        List<MapComputeTest.Student> students = new ArrayList<>();
        students.add(new MapComputeTest.Student("张三","男",18));
        students.add(new MapComputeTest.Student("李四","男",20));
        students.add(new MapComputeTest.Student("韩梅梅","女",18));
        students.add(new MapComputeTest.Student("小红","女",45));

        formMapFromList(students);
    }

    public static void formMapFromList(List<MapComputeTest.Student> students) {

        Map<String, String> map = students.stream().collect(Collectors.toMap(MapComputeTest.Student::getSex,
                MapComputeTest.Student::getName, (p1, p2) -> {
            System.out.println("Sex:" + p1 + ",Name:" + p2);
            return p2;
        }));

        System.out.println(map);
    }
}
