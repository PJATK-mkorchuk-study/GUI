public class EmailNotification extends BaseNotification {
    public EmailNotification(String email) {
        super(email);
    }

    @Override
    public String getType() {
        return "EMAIL";
    }

    @Override
    protected String formatMessage(String message) {
        String recipient = getRecipient();
        return String.format("Subject: Notification | %s | %s", recipient, message);
    }

    @Override
    protected void doSend(String formatted) {
        System.out.println("[EMAIL] " + formatted);
    }
}
