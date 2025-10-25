import java.util.concurrent.ThreadLocalRandom;

public class ClientThread extends Thread {
    private IncomingQueue incomingQueue;
    private int numEmails;
    
    public ClientThread(String name, IncomingQueue incomingQueue, int numEmails) {
        super(name);
        this.incomingQueue = incomingQueue;
        this.numEmails = numEmails;
    }

    @Override
    public void run() {
        int emailCounter = 0;
        try {
            incomingQueue.produce(new Message(getName() + "-START", false, Type.START_CLIENT), this);
            while (emailCounter < this.numEmails) {
                Message message = generateMessage(emailCounter);
                incomingQueue.produce(message, this);
                Thread.sleep(100);
                emailCounter++;
            }
            incomingQueue.produce(new Message(getName() + "-END", false, Type.END_CLIENT), this);
        } catch (Exception e) {

        }
        System.out.println(getName() + " FINISHED");
    }

    private Message generateMessage(int emailCount) {
        Message message = new Message(getName() + "-" + emailCount, ThreadLocalRandom.current().nextBoolean(), Type.EMAIL);
        return message;
    }
    
}
