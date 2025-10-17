package fourthtry;

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
            if (message.getType() == Type.EndProgram) {
                break;
            }
            outboxQueue.produce(message);
        }
        System.out.println("Manager finished");
    }
}
