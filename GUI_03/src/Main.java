public
    class Main {

    public static void main(String[] args) {

        NotificationService service = new NotificationService("OnlineStore");

        service.addChannel(new EmailNotification("jan@pj.edu"));
        service.addChannel(new SmsNotification("+48 22 58 44 500"));

        //TODO 01: implements Notification interface inline

        service.addChannel(new Notification() {
            @Override
            public void send(String message) {
                System.out.println("[PUSH] " + message);
            }

            @Override
            public String getType() {
                return "PUSH";
            }
        });

        //TODO 09: NotificationFilter is @FunctionalInterface
        service.addFilter(message -> !message.isBlank());
        service.addFilter(message -> message.length() <= 200);
        service.addFilter(message -> !message.toLowerCase().contains("spam"));

        //TODO 05
        service.addListener(new NotificationListener() {
            private int successCount = 0;
            private int failCount = 0;

            @Override
            public void onSuccess(String type, String message) {
                successCount++;
                System.out.println("[AUDIT] #" + successCount + " via " + type);
            }

            @Override
            public void onFailure(String type, String message, String reason) {
                failCount++;
                System.out.println("[AUDIT] FAIL #" + failCount + " via " + type + " | " + reason);
            }
        });


        service.sendAll("Your order #1234 has been shipped!");
        service.sendAll("");
        service.sendAll("This is SPAM content");
        service.sendAll("Welcome to our store!");

        service.printHistory();

        //TODO 11
        System.out.println("\n -- EMAIL ONLY --");
        NotificationService.Result[] emailResults = service.getByChannel("EMAIL");
        for(int i = 0; i <= emailResults.length - 1; i++) {
            System.out.println(emailResults[i]);
        }

        //TODO 13
        System.out.println("\n --Sorted by timestamp (newest first)--");
        NotificationService.Result[] sorted = NotificationService.sort(service.getSuccessful(), (result1, result2) -> result2.getTimestamp().compareTo(result1.getTimestamp()));
        for(int i = 0; i < sorted.length - 1; i++) {
            System.out.println(sorted[i].toString());
        }
    }
}