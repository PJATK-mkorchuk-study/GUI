public interface Notification {
    void send(String message);
    String getType();
}

abstract class BaseNotification implements Notification {
    private String recipient;

    BaseNotification(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    abstract protected String formatMessage(String message);
    abstract protected void doSend(String formatted);

    public void send(String message) {
        String formatted = formatMessage(message);
        doSend(formatted);
    }

}


