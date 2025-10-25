import java.util.concurrent.ThreadLocalRandom;

public class ServerThread extends Thread {
    private OutboxQueue outboxQueue;

    public ServerThread(String name, OutboxQueue outboxQueue) {
        super(name);
        this.outboxQueue = outboxQueue;
    }

    @Override
    public void run() {
        while (true) {
            Message message = outboxQueue.consume(this);
            if (message.getType() == Type.END_PROGRAM) {
                break;
            }
            // Simulate reading with random time
            int processTime = ThreadLocalRandom.current().nextInt(100, 151);
            try {
                Thread.sleep(processTime);
            } catch (InterruptedException e) {

            }
        }
        System.out.println(getName() + " FINISHED");
    }
}
