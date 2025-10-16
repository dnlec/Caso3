package fourthtry;

public class MessagingServer {
    public static void main(String[] args) throws InterruptedException {
        InboxQueue inboxQueue = new InboxQueue(10);
        OutboxQueue outboxQueue = new OutboxQueue(10);

        ClientThread client1 = new ClientThread(inboxQueue, "1", 10);
        ClientThread client2 = new ClientThread(inboxQueue, "2", 10);
        SpamFilterThread consumer1 = new SpamFilterThread(inboxQueue, outboxQueue,"1");
        SpamFilterThread consumer2 = new SpamFilterThread(inboxQueue, outboxQueue,"2");
        ServerThread server1 = new ServerThread(outboxQueue);
        // SpamFilterThread consumer3 = new SpamFilterThread(inboxQueue, outboxQueue, "3");
        // SpamFilterThread consumer4 = new SpamFilterThread(inboxQueue, "4");
        // SpamFilterThread consumer5 = new SpamFilterThread(inboxQueue, "5");

        client1.start();
        client2.start();
        consumer1.start();
        consumer2.start();
        server1.start();
        // consumer3.start();
        // consumer4.start();
        // consumer5.start();

        client1.join();
        client2.join();
        consumer1.join();
        consumer2.join();
        server1.join();


        System.out.println("Messages sent");
    }
}
