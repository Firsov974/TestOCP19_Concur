package org.ash;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestConcurrent10 {
    public static void main(String[] args) {

        List<Integer> data = Collections.synchronizedList(new ArrayList<>());

        Arrays.asList(1, 2, 3, 4, 5, 6).parallelStream()
                .map(i -> {
                    data.add(i);
                    return i;
                }) // AVOID STATEFUL LAMBDA EXPRESSIONS!
                .forEachOrdered(i -> System.out.print(i + " "));

        System.out.println();

        for (Integer e : data) {
            System.out.print(e + " ");
        }

        System.out.println();
        System.out.print(Arrays.asList(1,2,3,4,5,6).stream().findAny().get());

        System.out.println();
        System.out.print(Arrays.asList(1,2,3,4,5,6).stream().skip(5).limit(2).findFirst());

        System.out.println();
        System.out.println(Arrays.asList(1,2,3,4,5,6).stream().unordered().parallel());

        System.out.println("Reduce:");
        System.out.println(Arrays.asList('w', 'o', 'l', 'f','4','5')
                .stream()
                .reduce("",(c,s1) -> c + s1 + ":",
                        (s2,s3) -> s2 + s3 ));

        System.out.println();
        System.out.println(Arrays.asList(1,2,3,4,5,6)
                .parallelStream()
                .reduce(0,(a,b) -> (a-b))); // NOT AN ASSOCIATIVE ACCUMULATOR

        System.out.println(Arrays.asList("w","o","l","f")
                .parallelStream()
                .reduce("X",String::concat));

        Stream<String> stream = Stream.of("w", "o", "l", "f").parallel();
        SortedSet<String> set = stream.collect(ConcurrentSkipListSet::new, Set::add,
                Set::addAll);
        System.out.println(set); // [f, l, o, w]

        Stream<String> stream2 = Stream.of("w", "o", "l", "f").parallel();
        Set<String> set2 = stream2.collect(Collectors.toSet());
        System.out.println(set); // [f, w, l, o]

        Stream<String> ohMy = Stream.of("lions", "tigers", "bears").parallel();
        ConcurrentMap<Integer, String> map = ohMy
                .collect(Collectors.toConcurrentMap(String::length, k -> k,
                        (s1, s2) -> s1 + "," + s2));
        System.out.println(map); // {5=lions,bears, 6=tigers}
        System.out.println(map.getClass()); // java.util.concurrent.ConcurrentHashMap

        Stream<String> ohMy2 = Stream.of("lions", "tigers", "bears").parallel();
        ConcurrentMap<Integer, List<String>> map2 = ohMy2.collect(
                Collectors.groupingByConcurrent(String::length));
        System.out.println(map2); // {5=[lions, bears], 6=[tigers]}
    }
}
