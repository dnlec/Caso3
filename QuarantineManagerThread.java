import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class QuarantineManagerThread extends Thread {
    private QuarantineQueue quarantineQueue;
    private DeliveryQueue deliveryQueue;

    public QuarantineManagerThread(String name, QuarantineQueue quarantineQueue, DeliveryQueue deliveryQueue) {
        super(name);
        this.quarantineQueue = quarantineQueue;
        this.deliveryQueue = deliveryQueue;
    }
    

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            try {
                Thread.sleep(1000); 
                synchronized (quarantineQueue) {
                    Iterator<Message> it = quarantineQueue.getQueue().iterator();

                    while (it.hasNext()) {
                        Message message = it.next();
                        if (message.getType() == Type.END_PROGRAM) {
                            running = false;
                            it.remove(); 
                            break;
                        }
                        message.setQuarantineTime(message.getQuarantineTime() - 1);

                        if (message.getQuarantineTime() <= 0) {
                            it.remove();                             
                            int randomNumber = ThreadLocalRandom.current().nextInt(1, 22);
                            if (randomNumber % 7 == 0) {
                                System.out.println("["+ getName() + "]: Discarded: " + message);
                            } else {
                                deliveryQueue.produce(message, this);
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                running = false;
            }
        }
        System.out.println(getName() + " FINISHED");
    }
}