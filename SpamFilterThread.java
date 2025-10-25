import java.util.concurrent.ThreadLocalRandom;

public class SpamFilterThread extends Thread {
    private IncomingQueue incomingQueue;
    private DeliveryQueue deliveryQueue;
    private QuarantineQueue quarantineQueue;
    
    private static boolean running = true;
    private static int numStarts;
    private static int numEnds;
    private static boolean endMessageSent;

    private static Object lock = new Object();
    private static Object endLock = new Object();
    
    public SpamFilterThread(String name, IncomingQueue incomingQueue, DeliveryQueue deliveryQueue, QuarantineQueue quarantineQueue) {
        super(name);
        this.incomingQueue = incomingQueue;
        this.deliveryQueue = deliveryQueue;
        this.quarantineQueue = quarantineQueue;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message message = incomingQueue.consume(this);
                useMessage(message);
                Thread.sleep(150);
            } catch (InterruptedException ex) {

            }
        }
        synchronized (endLock) {
            if (!SpamFilterThread.endMessageSent) {
                // Ensure quarantine queue to be empty before sending END message
                while (!quarantineQueue.isEmpty())
                    Thread.yield();
                Message endMessage = new Message("END", false, Type.END_PROGRAM);
                quarantineQueue.produce(endMessage, this);
                deliveryQueue.produce(endMessage, this);
                SpamFilterThread.endMessageSent = true;
            }
        }
        System.out.println(getName() + " FINISHED");
    }

    private void useMessage(Message message) {
        // protect shared variables
        synchronized (lock) {
            if (message.getType() == Type.START_CLIENT) {
                numStarts++;
            } else if (message.getType() == Type.END_CLIENT) {
                numEnds++;
            }

            if (numStarts == numEnds) {
                running = false;
            }
        }
    
        if (!message.getFlag()) {
            deliveryQueue.produce(message, this);
        } else {
            int randomTime = ThreadLocalRandom.current().nextInt(10000, 20001);
            message.setQuarantineTime(randomTime);
            quarantineQueue.produce(message, this);
        }
    
    }


    
}
