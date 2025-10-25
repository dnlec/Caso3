import java.util.concurrent.ThreadLocalRandom;

public class QuarantineManagerThread extends Thread {
    private QuarantineQueue quarantineQueue;
    private OutboxQueue outboxQueue;

    public QuarantineManagerThread(String name, QuarantineQueue quarantineQueue, OutboxQueue outboxQueue) {
        super(name);
        this.quarantineQueue = quarantineQueue;
        this.outboxQueue = outboxQueue;
    }
    

    @Override
    public void run() {
        while (true) {
            Message message = quarantineQueue.consume(this);
            if (message.getType() == Type.END_PROGRAM) {
                break;
            }
            // If multiple of 7, discard message
            int randomNumber = ThreadLocalRandom.current().nextInt(1, 22);
            if (randomNumber % 7 == 0) {
                continue;
            }
            // Process message
            for (int i = message.getQuarantineTime(); i >= 0; i--) {
                message.setQuarantineTime(i);
            }
            outboxQueue.produce(message, this);
        }
        System.out.println(getName() + " FINISHED");
    }
}
