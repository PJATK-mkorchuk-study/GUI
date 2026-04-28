package task08.tasks;

import task08.data.TestData;
import task08.model.Post;
import task08.model.User;

import java.util.List;
import java.util.Optional;

public
class P4_TerminalOperations {

    public static void go() {

        printHeader("Problem P4 – Terminal Operations");

        List<Post> posts = TestData.getPosts();
        List<User> users = TestData.getUsers();

        System.out.println("--- [1] count() – number of NEWS posts ---");
        long newsCount = posts.stream().filter(p -> p.getCategory().equals("NEWS")).count();
//TODO 16

        System.out.println("Number of NEWS posts: " + newsCount);

        System.out.println("\n--- [2] anyMatch() – is there a post with tag 'docker'? ---");
        boolean hasDocker = posts.stream().anyMatch(p -> p.getTags().contains("docker"));
//TODO 17

        System.out.println("Is there a post with 'docker': " + hasDocker);

        System.out.println("\n--- [3] allMatch() – do all have at least 1 comment? ---");
        boolean allHaveComments = posts.stream().allMatch( p -> p.getComments() > 0);
//TODO 18

        System.out.println("Do all have comments: " + allHaveComments);

        System.out.println("\n--- [4] noneMatch() – do none of the posts have negative likes? ---");
        boolean noNegativeLikes = posts.stream().noneMatch(p -> p.getLikes() < 0);
//TODO 19

        System.out.println("Do none have negative likes: " + noNegativeLikes);

        System.out.println("\n--- [5] findFirst() – first LIFESTYLE post ---");
        Optional<Post> firstLifestyle = posts.stream().filter( p -> p.getCategory().equals("LIFESTYLE")).findFirst();

        firstLifestyle.ifPresentOrElse(p -> p.getContent(), () -> System.out.println("There are no posts in the \"LIFESTYLE\" category."));
        String content = firstLifestyle.map(Post::getContent).orElse("Brak treści");
//TODO 20

        System.out.println("Content (orElse): " + content);

        System.out.println("\n--- [6] findFirst() – unverified user from Kraków ---");
        Optional<User> unverifiedKrakow = users.stream().filter(u -> !u.isVerified() && u.getCity().equals("Kraków")).findFirst();

        unverifiedKrakow.ifPresentOrElse(u -> System.out.println(u.getUsername()), () -> System.out.println("no matching user was found"));
//TODO 21


        System.out.println("\n--- [7] Optional – method demonstration ---");
        Optional<Post> maybePost = posts.stream().filter(p -> p.getLikes() > 1000).findFirst();

        System.out.println(maybePost.isPresent());
        System.out.println(maybePost.orElse(null));
        System.out.println(maybePost.orElseGet(() -> {
            return new Post(null, null, "Brak", null, 0, 0, null, null);
        }).getContent());

        try {
            maybePost.orElseThrow(() -> new RuntimeException("Post nie istnieje!"));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
//TODO 22

    }

    private static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" " + title);
        System.out.println("=".repeat(60));
    }
}