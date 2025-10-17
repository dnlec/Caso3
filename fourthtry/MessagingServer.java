package fourthtry;

public class MessagingServer {
    public static void main(String[] args) throws InterruptedException {
        InboxQueue inboxQueue = new InboxQueue(10);
        OutboxQueue outboxQueue = new OutboxQueue(10, 3);
        QuarantineQueue quarantineQueue = new QuarantineQueue();

        ClientThread client1 = new ClientThread(inboxQueue, "1", 10);
        ClientThread client2 = new ClientThread(inboxQueue, "2", 10);
        SpamFilterThread consumer1 = new SpamFilterThread(inboxQueue, outboxQueue, quarantineQueue, "1");
        SpamFilterThread consumer2 = new SpamFilterThread(inboxQueue, outboxQueue, quarantineQueue, "2");
        ServerThread server1 = new ServerThread(outboxQueue);
        ServerThread server2 = new ServerThread(outboxQueue);
        ServerThread server3 = new ServerThread(outboxQueue);
        QuarantineManagerThread manager = new QuarantineManagerThread(quarantineQueue, outboxQueue);
        // SpamFilterThread consumer3 = new SpamFilterThread(inboxQueue, outboxQueue, "3");
        // SpamFilterThread consumer4 = new SpamFilterThread(inboxQueue, "4");
        // SpamFilterThread consumer5 = new SpamFilterThread(inboxQueue, "5");

        client1.start();
        client2.start();
        consumer1.start();
        consumer2.start();
        server1.start();
        server2.start();
        server3.start();
        manager.start();
        // consumer3.start();
        // consumer4.start();
        // consumer5.start();

        client1.join();
        client2.join();
        consumer1.join();
        consumer2.join();
        server1.join();
        server2.join();
        server3.join();
        manager.join();

        // concurrent modification exception is killing spam filters


        System.out.println("Messages sent");
    }
}
