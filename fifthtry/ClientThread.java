package fifthtry;

public class ClientThread extends Thread {
    private InboxQueue inboxQueue;
    private String name;
    private int emailCount;

    private static Object lock = new Object();
    
    private static int currentId = 0;

    public ClientThread(InboxQueue queue, String name, int emailCount) {
        this.inboxQueue = queue;
        this.name = name;
        this.emailCount = emailCount;
    }

    @Override
    public void run() {
        int count = 0;
        while (count != emailCount) {
            try {
                Message message = generateMessage();
                inboxQueue.put(message);
                count++;
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("Client Stopped");
    }


    private Message generateMessage() {
        // avoid race conditions with currentId. ensure only one thread can increment the shared variable
        synchronized (ClientThread.lock) {
            Message message = new Message(currentId++, false);
            return message;
        }
    }

    

    
}
