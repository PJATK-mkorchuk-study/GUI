package task08.tasks;

import task08.data.TestData;
import task08.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public
class P5_CollectBasic {

    public static void go() {

        printHeader("Problem P5 – collect() basics");

        List<Post> posts = TestData.getPosts();
        List<Post> techPosts = posts.stream().filter(p -> p.getCategory().equals("TECH")).collect(Collectors.toList());

//TODO 23

        System.out.println("Number of TECH posts: " + techPosts.size());
        techPosts.forEach(p -> System.out.println("  " + p.toString()));
//TODO 24

        System.out.println("\n--- [2] toSet() – unique categories ---");
        Set<String> categories = posts.stream().map(Post::getCategory).collect(Collectors.toSet());

//TODO 25

        System.out.println("Unique categories: " + categories);

        System.out.println("\n--- [2b] Comparison List vs Set with duplicates ---");
        List<String> authorsList = posts.stream().map(p -> p.getAuthor().getUsername()).collect(Collectors.toList());
//TODO 26

        System.out.println("List (with duplicates): " + authorsList.size() + " elements -> " + authorsList);

        Set<String> authorsSet = posts.stream().map(p -> p.getAuthor().getUsername()).collect(Collectors.toSet());
        System.out.println("Set  (without duplicates): " + authorsSet.size() + " elements -> " + authorsSet);


        System.out.println("\n--- [3] toMap() – post id -> number of likes ---");
        Map<String, Integer> likesById = posts.stream().collect(Collectors.toMap(Post::getId, Post::getLikes));
        likesById.forEach((id, likes) -> System.out.printf("%-5s -> %d %n", id, likes));
//TODO 27

        System.out.println("\n--- [4] List of authors (with duplicates) ---");
        List<String> authorsWithDuplicates = posts.stream().map(p -> p.getAuthor().getUsername()).collect(Collectors.toList());
        System.out.println("Size of authors list (with duplicates): " + authorsWithDuplicates.size());
        System.out.println("List: " + authorsWithDuplicates);
//TODO 28

        System.out.println("\n--- [BONUS] toUnmodifiableList() ---");
        List<String> immutable = posts.stream().map(Post::getCategory).distinct().collect(Collectors.toUnmodifiableList());
        System.out.println("Unmodifiable list of categories: " + immutable);

        try {
            immutable.add("SPORT");
        } catch (UnsupportedOperationException e) {
            System.out.println("Attempt to modify unmodifiable list: " + e.getClass().getSimpleName());
        }

//TODO 29

    }

    private static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" " + title);
        System.out.println("=".repeat(60));
    }
}