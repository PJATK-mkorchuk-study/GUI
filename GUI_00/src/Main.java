import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final int MAX_TEAM_SIZE = 5;
        Character[] team = new Character[MAX_TEAM_SIZE];
        int heroCount = 0;

        boolean isRunning = true;

        System.out.println("=== WELCOME TO THE HERO ARENA ===");

        while (isRunning) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Recruit a hero");
            System.out.println("2. Display team");
            System.out.println("3. SIMULATE BATTLE (Polymorphism in action)");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number!");
                continue;
            }

            switch (option) {
                case 1:
                    if (heroCount >= MAX_TEAM_SIZE) {
                        System.out.println("The team is full! You cannot add more characters.");
                    } else {
                        System.out.print("Enter hero name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter strength (1-20): ");
                        int strength = Integer.parseInt(scanner.nextLine());

                        System.out.println("Choose class: 1. Warrior, 2. Mage, 3. Archer");
                        int type = Integer.parseInt(scanner.nextLine());

                        Character newCharacter = null;
                        if (type == 1) newCharacter = new Warrior(name, strength);
                        else if (type == 2) newCharacter = new Mage(name, strength);
                        else if (type == 3) newCharacter = new Archer(name, strength);
                        else System.out.println("Unknown class. Hero was not added.");

                        if (newCharacter != null) {
                            team[heroCount] = newCharacter;
                            heroCount++;
                            System.out.println("Hero added!");
                        }
                    }
                    break;

                case 2:
                    if (heroCount == 0) {
                        System.out.println("The team is empty.");
                    } else {
                        System.out.println("\nYOUR TEAM:");
                        for (int i = 0; i < heroCount; i++) {
                            System.out.println((i + 1) + ". " + team[i].introduceSelf());
                        }
                    }
                    break;

                case 3: // Polymorphism
                    if (heroCount == 0) {
                        System.out.println("There is no one to fight!");
                    } else {
                        System.out.println("\n--- BATTLE STARTED ---");
                        int totalDamage = 0;

                        for (int i = 0; i < heroCount; i++) {
                            totalDamage += team[i].performAttack();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                            }
                        }

                        System.out.println("-----------------------------");
                        System.out.println("Total team damage: " + totalDamage + " points!");
                        if (totalDamage > 100) System.out.println("POWERFUL ATTACK! The enemy was crushed!");
                        else System.out.println("Weak attack. The enemy still stands.");
                    }
                    break;

                case 4:
                    isRunning = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
}
