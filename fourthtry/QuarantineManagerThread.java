package fourthtry;

import java.util.concurrent.ThreadLocalRandom;

import fourthtry.Message.Type;

public class QuarantineManagerThread extends Thread {
    private QuarantineQueue quarantineQueue;
    private OutboxQueue outboxQueue;

    public QuarantineManagerThread(QuarantineQueue quarantineQueue, OutboxQueue outboxQueue) {
        this.quarantineQueue = quarantineQueue;
        this.outboxQueue = outboxQueue;
    }
    

    @Override
    public void run() {
        while (true) {
            Message message = quarantineQueue.consume();
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
            outboxQueue.produce(message);
        }
        System.out.println("Manager finished");
    }
}
