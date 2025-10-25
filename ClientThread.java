import java.util.concurrent.ThreadLocalRandom;

public class ClientThread extends Thread {
    private InboxQueue inboxQueue;
    private int numEmails;
    
    private static int currentId = 0;
    private static Object lock = new Object();

    public ClientThread(String name, InboxQueue queue, int numEmails) {
        super(name);
        this.inboxQueue = queue;
        this.numEmails = numEmails;
    }

    @Override
    public void run() {
        int emailCounter = 0;
        try {
            inboxQueue.produce(new Message(-2, false, Type.START_CLIENT), this);
            while (emailCounter < this.numEmails) {
                Message message = generateMessage(emailCounter);
                inboxQueue.produce(message, this);
                Thread.sleep(100);
                emailCounter++;
            }
            inboxQueue.produce(new Message(-2, false, Type.END_CLIENT), this);
        } catch (Exception e) {

        }
        System.out.println(getName() + " finished");
    }

    private Message generateMessage(int emailCount) {
        synchronized (ClientThread.lock) {
            Message message = new Message(currentId++, ThreadLocalRandom.current().nextBoolean(), Type.EMAIL);
            return message;
        }
    }
    
}
