import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Order {
    private int id;
        private final OrderItem[] items;
    private final Customer customer;
    private final LocalDateTime createdAt;
    private final  Discount discount;

    public Order(int id, OrderItem[] items, Customer customer, Discount discount) {
        this.id = id;
        OrderItem[] copyOfItems = new OrderItem[items.length];
        for(int i = 0; i < copyOfItems.length; i++) {
            copyOfItems[i] = items[i];
        }
        this.items = copyOfItems;
        this.customer = customer;
        this.discount = discount;
        createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Order setDiscount(Discount discount) {
        return new Order(this.id, this.items, this.customer, discount);
    }

    public OrderItem[] getItems() {
        OrderItem[] copy = new OrderItem[this.items.length];
        for(int i = 0; i < copy.length; i++) {
            copy[i] = items[i];
        }
        return copy;
    }

    public double getLineCount() {
        return items.length;
    }

    public int getItemCount() {
        int count = 0;
        for(int i = 0; i < items.length; i++) {
            count += items[i].quantity();
        }

        return count;
    }

    public double calculateSubtotal() {
        double sum = 0;
        for(int i = 0; i < items.length; i++) {
            sum += items[i].totalPrice();
        }
        return sum;
    }

    public double calculateTotal() {
        double subtotal = calculateSubtotal();
        double result = 0;
        if(discount != null) {
            result = discount.apply(subtotal);
        } else {
            result = subtotal;
        }
        return result;
    }


    @Override
    public String toString() {
        return String.format("%s  %f  %f", customer.name(), getItemCount(), calculateTotal());
    }

    class Receipt {
        private final String cashierName;
        private static final String CAFE_NAME = "CAFE UNDER JAVA";
        private static final int WIDTH = 42;

        Receipt(String cashierName) {
            if(cashierName.equals(null) || cashierName.isBlank()) {
                throw new IllegalArgumentException("Cashier name cannot be empty");
            }
            this.cashierName = cashierName;
        }

        private String center(String text) {
            int spacesNeeded = Math.max(0, (WIDTH - text.length()) / 2);

            return " ".repeat(spacesNeeded) + text;
        }

        private String formatLine(String label, double amount) {
            String indentation = "  ";
            String leftPart = indentation + label;


            String rightPart = String.format("%.2f PLN", amount);


            int spacesNeeded = WIDTH - leftPart.length() - rightPart.length();
            spacesNeeded = Math.max(1, spacesNeeded);


            return leftPart + " ".repeat(spacesNeeded) + rightPart;
        }

        private String formatNegLine(String label, double amount) {
            String right = "-" + String.format(Locale.US, "%.2f PLN", amount);
            int spaces = Math.max(1, WIDTH - label.length() - right.length());
            return label + " ".repeat(spaces) + right;
        }

        public String generate() {
            StringBuilder sb = new StringBuilder();
            String thickSep = "=".repeat(WIDTH);
            String thinSep = "-".repeat(WIDTH);

            sb.append(thickSep).append("\n");
            sb.append(center(CAFE_NAME)).append("\n");
            sb.append(thickSep).append("\n");

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            sb.append("Date: ").append(createdAt.format(fmt)).append("\n");
            sb.append("Cashier: ").append(cashierName).append("\n");
            sb.append("Order: #").append(id).append("\n");
            sb.append("Customer: ").append(customer.name()).append(" [").append(customer.loyaltyLevel()).append("]\n");
            sb.append("\n");

            for (int i = 0; i < items.length; i++) {
                sb.append(items[i].formatted()).append("\n");
            }
            sb.append("\n").append(thinSep).append("\n");

            double subtotal = calculateSubtotal();
            sb.append(formatLine("Subtotal:", subtotal)).append("\n");

            if (discount != null) {
                double savings = discount.savings(subtotal);
                sb.append(formatNegLine("Discount: " + discount.getDescription(), savings)).append("\n");
            }

            sb.append(thinSep).append("\n");
            sb.append(formatLine("TOTAL DUE:", calculateTotal())).append("\n");
            sb.append(thickSep).append("\n");
            sb.append(center("Thank you!")).append("\n");
            sb.append(thickSep).append("\n");

            return sb.toString();
        }
    }

    public static class Builder {
        private static final int INITIAL_CAPACITY = 4;
        private final int id;
        private final Customer customer;
        private OrderItem[] items;
        private int size;
        private Discount discount;

        public Builder(int id, Customer customer) {
            if (id <= 0) {
                throw new IllegalArgumentException("Order ID must be greater than zero.");
            }
            if (customer == null) {
                throw new IllegalArgumentException("Customer cannot be equal to null.");
            }
            this.id = id;
            this.customer = customer;
            this.items = new OrderItem[INITIAL_CAPACITY];
            this.size = 0;
        }

        private void grow() {
            OrderItem[] newItems = new OrderItem[items.length * 2];
            for (int i = 0; i < size; i++) {
                newItems[i] = items[i];
            }
            this.items = newItems;
        }

        public Builder addItem(Product product, int quantity) {
            if (size == items.length) {
                grow();
            }
            items[size] = new OrderItem(product, quantity);
            size++;
            return this;
        }

        public Builder addItem(Product product) {
            return addItem(product, 1);
        }

        public Builder withDiscount(Discount discount) {
            this.discount = discount;
            return this;
        }

        public Order build() {
            if (size <= 0) {
                throw new IllegalStateException("Order must contain at least one item.");
            }
            OrderItem[] trimmed = new OrderItem[size];
            for (int i = 0; i < size; i++) {
                trimmed[i] = items[i];
            }
            return new Order(id, trimmed, customer, discount);
        }
    }
}

class Cafe {
    private final String name;
    private Product[] menu;
    private int menuSize;
    private Order[] orders;
    private int orderCount;

    public Cafe(String name, int menuCapacity, int orderCapacity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Cafe name cannot be empty.");
        }
        if (menuCapacity <= 0 || orderCapacity <= 0) {
            throw new IllegalArgumentException("Capacities must be greater than zero.");
        }
        this.name = name;
        this.menu = new Product[menuCapacity];
        this.menuSize = 0;
        this.orders = new Order[orderCapacity];
        this.orderCount = 0;
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be equal to null.");
        }
        if (menuSize == menu.length) {
            Product[] newMenu = new Product[menu.length * 2];
            for (int i = 0; i < menuSize; i++) {
                newMenu[i] = menu[i];
            }
            this.menu = newMenu;
        }
        menu[menuSize] = product;
        menuSize++;
    }

    public boolean removeProduct(String productName) {
        int index = -1;
        for (int i = 0; i < menuSize; i++) {
            if (menu[i].name().equalsIgnoreCase(productName)) {
                index = i;
                break;
            }
        }
        if (index == -1) return false;

        for (int i = index; i < menuSize - 1; i++) {
            menu[i] = menu[i + 1];
        }
        menuSize--;
        menu[menuSize] = null;
        return true;
    }

    public Product[] getProductsByCategory(String category) {
        int count = 0;
        for (int i = 0; i < menuSize; i++) {
            if (menu[i].category().equalsIgnoreCase(category)) {
                count++;
            }
        }
        Product[] result = new Product[count];
        int idx = 0;
        for (int i = 0; i < menuSize; i++) {
            if (menu[i].category().equalsIgnoreCase(category)) {
                result[idx++] = menu[i];
            }
        }
        return result;
    }

    public void sortMenuByPrice() {
        for (int i = 1; i < menuSize; i++) {
            Product key = menu[i];
            int j = i - 1;
            while (j >= 0 && menu[j].price() > key.price()) {
                menu[j + 1] = menu[j];
                j--;
            }
            menu[j + 1] = key;
        }
    }

    public void displayMenu() {
        System.out.println("=== MENU: " + name.toUpperCase() + " ===");
        for (int i = 0; i < menuSize; i++) {
            System.out.println((i + 1) + ". " + menu[i].formatted());
        }
        System.out.println();
    }

    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be equal to null.");
        }
        if (orderCount == orders.length) {
            Order[] newOrders = new Order[orders.length * 2];
            for (int i = 0; i < orderCount; i++) {
                newOrders[i] = orders[i];
            }
            this.orders = newOrders;
        }
        orders[orderCount] = order;
        orderCount++;
    }

    public Order[] getOrdersByCustomer(String customerName) {
        int count = 0;
        for (int i = 0; i < orderCount; i++) {
            if (orders[i].getCustomer().name().equalsIgnoreCase(customerName)) {
                count++;
            }
        }
        Order[] result = new Order[count];
        int idx = 0;
        for (int i = 0; i < orderCount; i++) {
            if (orders[i].getCustomer().name().equalsIgnoreCase(customerName)) {
                result[idx++] = orders[i];
            }
        }
        return result;
    }

    public void sortOrdersByTotal() {
        for (int i = 0; i < orderCount - 1; i++) {
            for (int j = 0; j < orderCount - 1 - i; j++) {
                if (orders[j].calculateTotal() > orders[j + 1].calculateTotal()) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    public String getName() { return name; }
    public int getMenuSize() { return menuSize; }
    public int getOrderCount() { return orderCount; }

    public Order[] getOrders() {
        Order[] copy = new Order[orderCount];
        for (int i = 0; i < orderCount; i++) {
            copy[i] = orders[i];
        }
        return copy;
    }

    public static class Statistics {
        private final Order[] orders;
        private final int count;

        public Statistics(Order[] orders, int count) {
            if (orders == null || count <= 0) {
                throw new IllegalArgumentException("No orders to analyze.");
            }
            this.orders = orders;
            this.count = count;
        }

        public double totalRevenue() {
            double sum = 0;
            for (int i = 0; i < count; i++) {
                sum += orders[i].calculateTotal();
            }
            return sum;
        }

        public double averageOrderValue() {
            return totalRevenue() / count;
        }

        public Order mostExpensiveOrder() {
            Order max = orders[0];
            for (int i = 1; i < count; i++) {
                if (orders[i].calculateTotal() > max.calculateTotal()) {
                    max = orders[i];
                }
            }
            return max;
        }

        public Order cheapestOrder() {
            Order min = orders[0];
            for (int i = 1; i < count; i++) {
                if (orders[i].calculateTotal() < min.calculateTotal()) {
                    min = orders[i];
                }
            }
            return min;
        }

        public int totalItemsSold() {
            int total = 0;
            for (int i = 0; i < count; i++) {
                total += orders[i].getItemCount();
            }
            return total;
        }

        public String summary() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== STATISTICS ===\n");
            sb.append(String.format(Locale.US, "Number of orders: %d\n", count));
            sb.append(String.format(Locale.US, "Units sold: %d\n", totalItemsSold()));
            sb.append(String.format(Locale.US, "Total revenue: %.2f PLN\n", totalRevenue()));
            sb.append(String.format(Locale.US, "Average value: %.2f PLN\n", averageOrderValue()));
            sb.append(String.format(Locale.US, "Most expensive: #%d (%.2f PLN)\n", mostExpensiveOrder().getId(), mostExpensiveOrder().calculateTotal()));
            sb.append(String.format(Locale.US, "Cheapest: #%d (%.2f PLN)\n", cheapestOrder().getId(), cheapestOrder().calculateTotal()));
            return sb.toString();
        }
    }

    public class DailyReport {
        private final String reportDate;

        public DailyReport(String reportDate) {
            if (reportDate == null || reportDate.isBlank()) {
                throw new IllegalArgumentException("Report date cannot be empty.");
            }
            this.reportDate = reportDate;
        }

        public String generate() {
            StringBuilder sb = new StringBuilder();
            String sep = "=".repeat(50);

            sb.append(sep).append("\n");
            sb.append("DAILY REPORT: ").append(name).append("\n");
            sb.append("Date: ").append(reportDate).append("\n");
            sb.append(sep).append("\n\n");

            sb.append("Products in menu: ").append(menuSize).append("\n");
            sb.append("Orders: ").append(orderCount).append("\n\n");

            if (orderCount > 0) {
                sb.append("Order list\n");
                for (int i = 0; i < orderCount; i++) {
                    Order o = orders[i];
                    sb.append(String.format(Locale.US, "#%d %s\n%.2f PLN %d units\n",
                            o.getId(), o.getCustomer().name(), o.calculateTotal(), o.getItemCount()));
                }
                sb.append("\n");
                Statistics stats = new Statistics(orders, orderCount);
                sb.append(String.format(Locale.US, "Total revenue: %.2f PLN\n", stats.totalRevenue()));
                sb.append(String.format(Locale.US, "Average value: %.2f PLN\n", stats.averageOrderValue()));
            } else {
                sb.append("No orders available.\n");
            }

            sb.append("\n").append(sep).append("\n");
            return sb.toString();
        }
    }
}
