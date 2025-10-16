package fourthtry;

import fourthtry.Message.Type;

public class SpamFilterThread extends Thread {
    private InboxQueue inboxQueue;
    private OutboxQueue outboxQueue;
    
    private String name;
    
    private static boolean running = true;
    private static int numStart;
    private static int numEnd;

    private static Object lock = new Object();


    public SpamFilterThread(InboxQueue inboxQueue, OutboxQueue outboxQueue, String name) {
        this.inboxQueue = inboxQueue;
        this.outboxQueue = outboxQueue;
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
        Message endMessage = new Message(-1, false, Type.EndProgram);
        outboxQueue.produce(endMessage);
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
        }
    
    }


    
}
