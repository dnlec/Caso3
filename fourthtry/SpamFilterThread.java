package fourthtry;

import fourthtry.Message.Type;

public class SpamFilterThread extends Thread {
    private InboxQueue inboxQueue;
    private OutboxQueue outboxQueue;
    private QuarantineQueue quarantine;
    
    private String name;
    
    private static boolean running = true;
    private static int numStart;
    private static int numEnd;

    private static Object lock = new Object();
    private static Object endLock = new Object();
    private static boolean endMessageSent;


    public SpamFilterThread(InboxQueue inboxQueue, OutboxQueue outboxQueue, QuarantineQueue quarantine, String name) {
        this.inboxQueue = inboxQueue;
        this.outboxQueue = outboxQueue;
        this.quarantine = quarantine;
        this.name = name;
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
                Message endMessage = new Message(-1, false, Type.EndProgram);
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
            if (message.getType() == Type.StartClient) {
                numStart++;
            } else if (message.getType() == Type.EndClient) {
                numEnd++;
            }

            if (numStart == numEnd) {
                running = false;
            }
        }
        
        

        if (!message.getFlag()) {
            outboxQueue.produce(message);
        } else {
            // sent to quarantine
            quarantine.produce(message);
        }
    
    }


    
}
