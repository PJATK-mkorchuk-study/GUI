@FunctionalInterface
public interface NotificationFilter {
    boolean accept(String message);
}
