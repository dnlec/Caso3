import java.util.LinkedList;
import java.util.Queue;

public class DeliveryQueue {
    private Queue<Message> queue = new LinkedList<>();
    private int maxSize;
    private DeliverySignal sharedSignal = new DeliverySignal();
    private int numServers;

    public DeliveryQueue(int maxSize, int serverNumber) {
        this.maxSize = maxSize;
        this.numServers = serverNumber;
    }

    public void produce(Message message, Thread thread) {
        while (maxSize == queue.size()) {
            Thread.yield();
        }
        synchronized (this) {            
            queue.add(message);
            System.out.println("[" + thread.getName() + "]: Produced: " + message + ":" + queue);

            if (message.getType() == Type.END_PROGRAM) {
                for (int i = 0; i < numServers - 1; i++) {
                    queue.add(message);
                }
            }
            
            sharedSignal.modIsEmpty(false);
        }
    }

    public Message consume(Thread thread) {
        while (sharedSignal.getIsEmpty()) {
        }
        
        Message message = null;
        synchronized (this) {
            if (!queue.isEmpty()) { 
                message = queue.remove();
                if (queue.isEmpty()) {
                    sharedSignal.modIsEmpty(true);
                }
                System.out.println("[" + thread.getName() + "]: Consumed: " + message + ":" + queue);
            }
        }
        return message;
    }
}