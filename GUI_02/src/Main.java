public class Main {
    static void main(String[] args) {

    }
}

record Product(String name, double price, String category) {
    Product {
        if(name.equals(null) || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        if(price < 0) {
            throw new IllegalArgumentException("Price must be greater than 0. Received " + price);
        }

        if(category.equals(null) || category.isBlank()) {
            throw new IllegalArgumentException("Product category cannot be empty");
        }
    }



    public String formatted() {
        return String.format("%s (%s) - %.2f PLN", name,  category, price);
    }
}

record Customer(String name, String email, int loyaltyPoints) {
    Customer {
        if(name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        if(email.equals(null) || !email.contains("@")) {
            throw new IllegalArgumentException("Email is not valid. Received email " + email);
        }

        if(loyaltyPoints < 0) {
            throw new IllegalArgumentException("LoyaltyPoints can't be less than 0. Points received: " + loyaltyPoints);
        }
    }

    public String loyaltyLevel() {
        if(loyaltyPoints >= 200) {
            return "GOLD";
        } else if (loyaltyPoints >= 100) {
            return "SILVER";
        } else if (loyaltyPoints >= 50) {
            return "BRONZE";
        } else {
            return "STANDARD";
        }
    }

    public String formatted() {
        return String.format("%s  %d  %s", name, loyaltyPoints, loyaltyLevel());
    }
}

record OrderItem(Product product, double quantity) {
    OrderItem {
        if(product.equals(null)) {
            throw new IllegalArgumentException("product cannot be empty");
        }

        if(quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1. Received: " + quantity);
        }
    }

    public double totalPrice() {
        return product.price() * this.quantity;
    }

    public String formatted() {
        String leftPart = String.format("%f x %s", quantity, product.name());
        String rightPart = String.format("%.2f PLN", totalPrice());
        final int TARGET_WIDTH = 40;
        int spacesNeeded = TARGET_WIDTH - leftPart.length() - rightPart.length();
        spacesNeeded = Math.max(0, spacesNeeded);
        String spaces = " ".repeat(spacesNeeded);

        return leftPart + spaces + rightPart;
    }


}
