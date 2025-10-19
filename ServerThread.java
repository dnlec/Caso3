import java.util.concurrent.ThreadLocalRandom;

public class ServerThread extends Thread {
    private OutboxQueue outboxQueue;

    public ServerThread(OutboxQueue outboxQueue) {
        this.outboxQueue = outboxQueue;
    }

    @Override
    public void run() {
        while (true) {
            Message message = outboxQueue.consume();
            if (message.getType() == Type.END_PROGRAM) {
                System.out.println("END PROGRAM RECEIVED");
                break;
            }
            // Simulate reading with random time
            int processTime = ThreadLocalRandom.current().nextInt(100, 151);
            try {
                Thread.sleep(processTime);
            } catch (InterruptedException e) {

            }
        }
    }
}
