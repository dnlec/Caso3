package fifthtry;


public class MessagingServer {
    public static void main(String[] args) {
        InboxQueue inboxQueue = new InboxQueue(10);

        ClientThread client1 = new ClientThread(inboxQueue, "1", 10);
        ClientThread client2 = new ClientThread(inboxQueue, "2", 10);
        SpamFilterThread consumer1 = new SpamFilterThread(inboxQueue, "1");
        SpamFilterThread consumer2 = new SpamFilterThread(inboxQueue, "2");

        client1.start();
        client2.start();
        consumer1.start();
        consumer2.start();


    }
}
