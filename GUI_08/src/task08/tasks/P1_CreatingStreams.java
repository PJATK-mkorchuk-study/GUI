package task08.tasks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;



public
class P1_CreatingStreams {

    public static void go() {

        printHeader("Problem P1 – Creating Streams");

        System.out.println("--- [1] Stream.of() ---");
        Stream.of("anna_k", "bartek99", "zosia_m").forEach(System.out::println);
//TODO 01

        System.out.println("\n--- [2] Arrays.stream() ---");
        String[] tags = {"java", "news", "photo", "tech"};

        Arrays.stream(tags).map(String::toUpperCase).forEach(System.out::println);
//TODO 02

        System.out.println("\n--- [3] Stream.iterate() – post IDs ---");
        Stream.iterate(1, n -> n + 1).limit(10).map(n -> "POST_" + n).forEach(n -> System.out.print(n + " "));
//TODO 03

        System.out.println();

        System.out.println("\n--- [4] Stream.generate() – random likes ---");
        Random random = new Random(42);

        Stream.generate(() -> random.nextInt(101)).limit(5).forEach(n -> System.out.print(n + " "));
//TODO 04

        System.out.println();

        System.out.println("\n--- [5] Stream.empty() + Optional ---");
        Stream<Object> emptyStream = Stream.empty();
        Optional<Object> result = emptyStream.findFirst();


//TODO 05


        System.out.println("Does result exist: " + result.isPresent());
        System.out.println("Value or default: " + result.orElse("No posts"));

        System.out.println("\n--- [BONUS] Collection.stream() ---");

        List<String> usernames = List.of("anna_k", "bartek99", "zosia_m");
        usernames.stream().forEach(System.out::println);

//TODO 06


    }

    private static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" " + title);
        System.out.println("=".repeat(60));
    }
}