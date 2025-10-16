package fourthtry;

import java.util.LinkedList;
import java.util.Queue;

public class OutboxQueue {
    private Queue<Message> queue = new LinkedList<>();
    private int maxSize;
    private OutboxSignal sharedSignal = new OutboxSignal();


    public OutboxQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void produce(Message message) {
        while (maxSize == queue.size()) {
            Thread.yield();
        }
        queue.add(message);
        System.out.println("Filter Produced: " + message + ":" + queue);
        sharedSignal.modIsEmpty(false);
    }

    public Message consume() {
        while (sharedSignal.getIsEmpty()) {}
        Message message = queue.remove();
        if (queue.isEmpty()) {
            sharedSignal.modIsEmpty(true);
        }
        System.out.println("Server Consumed: " + message + ":" + queue);
        return message;
    }
}
