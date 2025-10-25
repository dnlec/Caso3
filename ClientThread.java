import java.util.concurrent.ThreadLocalRandom;

public class ClientThread extends Thread {
    private InboxQueue inboxQueue;
    private int numEmails;
    
    public ClientThread(String name, InboxQueue queue, int numEmails) {
        super(name);
        this.inboxQueue = queue;
        this.numEmails = numEmails;
    }

    @Override
    public void run() {
        int emailCounter = 0;
        try {
            inboxQueue.produce(new Message(getName() + "-START", false, Type.START_CLIENT), this);
            while (emailCounter < this.numEmails) {
                Message message = generateMessage(emailCounter);
                inboxQueue.produce(message, this);
                Thread.sleep(100);
                emailCounter++;
            }
            inboxQueue.produce(new Message(getName() + "-END", false, Type.END_CLIENT), this);
        } catch (Exception e) {

        }
        System.out.println(getName() + " FINISHED");
    }

    private Message generateMessage(int emailCount) {
        Message message = new Message(getName() + "-" + emailCount, ThreadLocalRandom.current().nextBoolean(), Type.EMAIL);
        return message;
    }
    
}
