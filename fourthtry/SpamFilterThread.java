package fourthtry;

import java.util.concurrent.ThreadLocalRandom;

import fourthtry.Message.Type;

public class SpamFilterThread extends Thread {
    private InboxQueue inboxQueue;
    private OutboxQueue outboxQueue;
    private QuarantineQueue quarantine;
    
    private int id;
    
    private static boolean running = true;
    private static int numStart;
    private static int numEnd;

    private static Object lock = new Object();
    private static Object endLock = new Object();
    private static boolean endMessageSent;


    public SpamFilterThread(InboxQueue inboxQueue, OutboxQueue outboxQueue, QuarantineQueue quarantine, int id) {
        this.inboxQueue = inboxQueue;
        this.outboxQueue = outboxQueue;
        this.quarantine = quarantine;
        this.id = id;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message message = inboxQueue.consume();
                useMessage(message);
                Thread.sleep(150);
            } catch (InterruptedException ex) {

            }
        }
        synchronized (endLock) {
            if (!SpamFilterThread.endMessageSent) {
                while (!quarantine.isEmpty()) {
                    Thread.yield();
                }
                Message endMessage = new Message(-1, false, Type.END_PROGRAM);
                quarantine.produce(endMessage);
                outboxQueue.produce(endMessage);
                SpamFilterThread.endMessageSent = true;
            }
        }
        System.out.println("Filter finished");
    }

    private void useMessage(Message message) {
        // protect shared variables
        synchronized (lock) {
            if (message.getType() == Type.START_CLIENT) {
                numStart++;
            } else if (message.getType() == Type.END_CLIENT) {
                numEnd++;
            }

            if (numStart == numEnd) {
                running = false;
            }
        }
        
    
        if (!message.getFlag()) {
            outboxQueue.produce(message);
        } else {
            int randomTime = ThreadLocalRandom.current().nextInt(10000, 20001);
            message.setQuarantineTime(randomTime);
            quarantine.produce(message);
        }
    
    }


    
}
