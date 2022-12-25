package com.skypro.diary.teoriya;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {

    public String name;
    public int age;

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();
        map.put("str1", 1);
        map.put("str2", 2);
        map.put("str3", 4);

        for (Map.Entry<String, Integer> entry : map.entrySet()){
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public boolean equals(Object o){
        if (this==o) return true;
        if (o==null||getClass()!= o.getClass()) return false;
        Student student = (Student) o;
        return age==student.age&&Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,age);
    }
}
