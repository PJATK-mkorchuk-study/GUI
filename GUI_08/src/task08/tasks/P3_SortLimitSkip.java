package task08.tasks;

import task08.data.TestData;
import task08.model.Post;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public
class P3_SortLimitSkip {

    public static void go() {

        printHeader("Problem P3 – sorted(), limit(), skip()");

        List<Post> posts = TestData.getPosts();

        System.out.println("--- [1] TOP 3 posts by likes ---");
        posts.stream().sorted(Comparator.comparingInt(Post::getLikes).reversed()).limit(3).map(p -> String.format("%-12s -> %d", p.getAuthor().getUsername(), p.getLikes())).forEach(System.out::println);
//TODO 11


        System.out.println("\n--- [2] Posts from newest ---");
        posts.stream().sorted(Comparator.comparing(Post::getPublishedAt).reversed()).map(p -> {
            String content = p.getContent();
            int maxLength = Math.min(35, content.length());
            String shortenedContent = content.substring(0, maxLength);

            return String.format("%s | %s", p.getPublishedAt(), shortenedContent);
        }).forEach(System.out::println);
//TODO 12

        System.out.println("\n--- [3] Posts starting from the 4th (skip 3) ---");
        posts.stream().skip(3).map(p -> {
            String content = p.getContent();
            int maxLength = Math.min(35, content.length());
            String shortenedContent = content.substring(0, maxLength);
            return String.format("[%s] %s", p.getId(), shortenedContent);
        }).forEach(System.out::println);
//TODO 13

        System.out.println("\n--- [4] Pagination – page 2, size 4 ---");
        int page     = 2;
        int pageSize = 4;

        List<Post> pageResult = posts.stream().skip((long)((page - 1) * pageSize)).limit(pageSize).collect(Collectors.toList());
        pageResult.forEach(p -> {
            System.out.println(String.format("[%s] %s (%d)", p.getId(), p.getContent().substring(0, Math.min(35, p.getContent().length())), p.getLikes()));

        });
//TODO 14

        System.out.println("\n--- [BONUS] Sorting: category → likes descending ---");
        posts.stream().sorted(Comparator.comparing(Post::getCategory).thenComparing(Comparator.comparingInt(Post::getLikes).reversed())).map(p -> String.format("%-10s | %-12s | %d", p.getCategory(), p.getAuthor().getUsername(), p.getLikes())).forEach(System.out::println);

//TODO 15


    }

    private static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" " + title);
        System.out.println("=".repeat(60));
    }
}