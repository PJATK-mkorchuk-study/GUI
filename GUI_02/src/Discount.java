abstract class Discount {
    private final String description;

    Discount(String description) {
        if(description.equals(null) || description.isBlank()) {
            throw new IllegalArgumentException("Discount description can't be empty");
        }
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    abstract public double apply(double originalPrice);

    public double savings(double originalPrice) {
        double discountedPrice = apply(originalPrice);
        return originalPrice - discountedPrice;
    }

    @Override
    public String toString() {
        return "Discount: " + description;
    }


}

class FixedAmountDiscount extends Discount {
    private final double amount;

    public FixedAmountDiscount(double amount) {
        super(String.format("%s", amount));
        if(amount < 0) {
            throw new IllegalArgumentException("Amount can't be less than 0");
        }

        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public double apply(double originalPrice) {
        double result = originalPrice - amount;
        return Math.max(0.0, result);
    }

}

class PercentageDiscount extends Discount {
    private final double percentage;

    public PercentageDiscount(double percentage) {
        super(String.format("%s", percentage));
        if(percentage > 100 || percentage < 0) {
            throw new IllegalArgumentException("Discount percentage must be in the range (0, 100]. Received: " + percentage);
        }
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    @Override
    public double apply(double originalPrice) {
        double discounted = originalPrice * (1 - percentage / 100.0);
        return Math.max(0.0, discounted);
    }
}
