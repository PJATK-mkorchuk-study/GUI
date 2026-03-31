package task05.shipping;

import task05.model.Product;

public class ShipmentBuilder<T extends Product> {
    private final T cargo;
    private String destination = "UNKNOWN";

    private int priorityOverride = -1;
    private boolean fragile = false;
    private boolean insured = false;

    public ShipmentBuilder(T cargo) {
        this.cargo = cargo;
    }

    public ShipmentBuilder<T> to(String destination) {
        this.destination = destination;
        return this;
    }

    public ShipmentBuilder<T> withPriority(int priority) {
        priorityOverride = priority;
        return this;
    }

    public ShipmentBuilder<T> fragile() {
        fragile = true;
        return this;
    }

    public ShipmentBuilder<T> insured() {
        insured = true;
        return this;
    }

    public Shipment build() {
        int finalPriority = priorityOverride >= 0 ? priorityOverride : cargo.getPriority();
        return new Shipment<T>(cargo, destination, finalPriority, fragile, insured);

    }

}
