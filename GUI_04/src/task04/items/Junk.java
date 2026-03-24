package task04.items;

public
    class Junk
    extends Item {

    private final String description;

    public Junk(String name, double weight, String description) {
        super(name, 0, weight);
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + description + "]";
    }
}