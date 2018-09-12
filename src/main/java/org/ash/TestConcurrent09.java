package org.ash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class TestConcurrent09 {
    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.asList(1,2,3,4,5,6).stream();
        Stream<Integer> parallelStream = stream.parallel();

        Stream<Integer> parallelStream2 = Arrays.asList(1,2,3,4,5,6).parallelStream();

        Arrays.asList(1,2,3,4,5,6)
                .parallelStream()
                .forEach(s -> System.out.print(s+" "));

        System.out.println();

        Arrays.asList(1,2,3,4,5,6)
                .parallelStream()
                .forEachOrdered(s -> System.out.print(s+" "));

        System.out.println();

        Arrays.asList("jackal","kangaroo","lemur")
                .parallelStream()
                .map(s -> s.toUpperCase())
                .forEach(System.out::println);

        System.out.println();

        Arrays.asList("jackal","kangaroo","lemur")
                .parallelStream()
                .map(s -> {System.out.println(s); return s.toUpperCase();})
                .forEach(System.out::println);

        System.out.println();

        List<Integer> data = Collections.synchronizedList(new ArrayList<>());
        Arrays.asList(1,2,3,4,5,6).parallelStream()
                .map(i -> {data.add(i); return i;}) // AVOID STATEFUL LAMBDA EXPRESSIONS!
                .forEachOrdered(i -> System.out.print(i+" "));
        System.out.println();
        for(Integer e: data) {
            System.out.print(e+" ");
        }

    }
}
