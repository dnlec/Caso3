import java.util.concurrent.ThreadLocalRandom;

public class ServerThread extends Thread {
    private DeliveryQueue deliveryQueue;

    public ServerThread(String name, DeliveryQueue deliveryQueue) {
        super(name);
        this.deliveryQueue = deliveryQueue;
    }

    @Override
    public void run() {
        while (true) {
            Message message = deliveryQueue.consume(this);
            if (message == null) {
                continue; 
            }
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