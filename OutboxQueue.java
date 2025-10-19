import java.util.LinkedList;
import java.util.Queue;

public class OutboxQueue {
    private Queue<Message> queue = new LinkedList<>();
    private int maxSize;
    private OutboxSignal sharedSignal = new OutboxSignal();
    private int serverNumber;

    public OutboxQueue(int maxSize, int serverNumber) {
        this.maxSize = maxSize;
        this.serverNumber = serverNumber;
    }

    public void produce(Message message) {
        while (maxSize == queue.size()) {
            Thread.yield();
        }
        queue.add(message);
        System.out.println("Filter/Manager Produced: " + message + ":" + queue);

        // If END message, send copy to each server to end
        if (message.getType() == Type.END_PROGRAM) {
            for (int i = 0; i < serverNumber-1; i++) {
                queue.add(message);
            }
        }
        
        sharedSignal.modIsEmpty(false);
    }

    public Message consume() {
        synchronized (this) {
            while (sharedSignal.getIsEmpty()) {}
            Message message = queue.remove();
            if (queue.isEmpty()) {
                sharedSignal.modIsEmpty(true);
            }
            System.out.println("Server Consumed: " + message + ":" + queue);
            return message;
        }
    }
}
