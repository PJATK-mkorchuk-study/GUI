import java.util.Arrays;
import java.util.Random;

public class Character {
    protected String name;
    protected int strength;

    public Character(String name, int strength) {
        this.name = name;
        this.strength = strength;
    }

    public int performAttack() {
        return 0;
    }

    public String introduceSelf() {
        return name + " (Strength: " + strength + ")";
    }
}

class Warrior extends Character {
    public Warrior(String name, int strength) {
        super(name, strength);
    }

    @Override
    public int performAttack() {
        int damage = (int)(strength * 1.5);
        System.out.println("Warrior " + name + " slashes with a sword dealing " + damage + " damage!");
        return damage;
    }

    @Override
    public String introduceSelf() {
        return "[Warrior] " + super.introduceSelf();
    }
}

class Mage extends Character {
    private Random rand = new Random();

    public Mage(String name, int strength) {
        super(name, strength);
    }

    @Override
    public int performAttack() {
        int damage = 20 + rand.nextInt(15) + (strength / 2);
        System.out.println("Mage " +
                name + " casts a fireball dealing " + damage + " damage!");
        return damage;
    }

    @Override
    public String introduceSelf() {
        return "[Mage] " + super.introduceSelf();
    }
}

class Archer extends Character {
    private Random rand = new Random();


    public Archer(String name, int strength) {
        super(name, strength);
    }

    @Override
    public int performAttack() {
        boolean criticalHit = rand.nextBoolean();
        int damage = super.strength;
        if(criticalHit) {
            damage = damage * 2;
            System.out.println("Archer " + name + " hits the eye! CRITICAL HIT! (" + damage + " dmg)");
        } else {
            System.out.println("Archer " + name + " shoots an arrow dealing " + damage + " damage.");
        }

        return damage;
    }

    @Override
    public String introduceSelf() {
        return "[Archer] " + super.introduceSelf();
    }
}
