package task04.items;

public
    class Treasure
    extends Item {

    private final String rarity;

    public Treasure(String name, int value, double weight, String rarity) {
        super(name, value, weight);
        this.rarity = rarity;
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", super.toString(), rarity);
    }
}