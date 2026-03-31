package task05.shipping;

import task05.model.Product;

public class Shipment<T extends Product> {
    private final T cargo;
    private final String destination;
    private final int priorityOverride;
    private final boolean fragile;
    private final boolean insured;

    public Shipment(T cargo, String destination, int priorityOverride, boolean fragile, boolean insured) {
        this.cargo = cargo;
        this.destination = destination;
        this.priorityOverride = priorityOverride;
        this.fragile = fragile;
        this.insured = insured;
    }

    public T getCargo() {
        return cargo;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return String.format(
                "Shipment [cargo=%s,  dest=%s,  priority=%d, fragile=%b, insured=%b]",
                cargo,
                destination,
                priorityOverride,
                fragile,
                insured);
    }
}
