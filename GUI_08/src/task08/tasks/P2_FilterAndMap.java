package task08.tasks;

import task08.data.TestData;
import task08.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public
class P2_FilterAndMap {

    public static void go() {

        printHeader("Problem P2 – filter(), map(), distinct()");

        List<Post> posts = TestData.getPosts();

        System.out.println("--- [1] Posts in TECH category (first 40 characters) ---");
        posts.stream().filter(p -> p.getCategory().equals("TECH")).map(p -> {
            String content = p.getContent();
            int maxLength = Math.min(40, content.length());
            return content.substring(0, maxLength);
        }).forEach(System.out::println);
//TODO 07

        System.out.println("\n--- [2] Unique post authors (distinct) ---");
        posts.stream().map(p -> p.getAuthor().getUsername()).distinct().forEach(System.out::println);
//TODO 08

        System.out.println("\n--- [3] Posts with more than 200 likes ---");
        posts.stream().filter(p -> p.getLikes() > 200).map(p -> String.format("%-12s -> %d", p.getAuthor().getUsername(), p.getLikes())).forEach(System.out::println);
//TODO 09

        System.out.println("\n--- [4] Post contents in UPPERCASE ---");
        List<String> upperContents = posts.stream().map(Post::getContent).map(String::toUpperCase).collect(Collectors.toList());
        upperContents.forEach(System.out::println);
//TODO 10

    }

    private static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" " + title);
        System.out.println("=".repeat(60));
    }
}