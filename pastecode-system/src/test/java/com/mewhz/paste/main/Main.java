package com.mewhz.paste.main;

import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("new");
        list.add("world");

        list.forEach(System.out::println);
        System.out.println("======");
        list.stream().filter(s -> s.length() == 5).forEach(System.out::println);
    }
}