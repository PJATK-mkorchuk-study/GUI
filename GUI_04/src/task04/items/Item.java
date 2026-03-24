package task04.items;

// TODO 04
public
    abstract class Item implements Comparable<Item>{

    private final String name;
    private final int value;
    private final double weight;

    public Item(String name, int value, double weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }

//TODO 05
    public int compareTo(Item other) {
        int result = Integer.compare(other.value, this.value);
        if(result == 0) {
            result = name.compareToIgnoreCase(other.name);
        }
        return result;
    }


    public boolean isWorthless() {
        return value == 0;
    }

    @Override
    public String toString() {
        return String.format("%-20s | value: %4d gold | weight: %.1f kg",
                name, value, weight);
    }
}