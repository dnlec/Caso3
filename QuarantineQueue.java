

import java.util.LinkedList;
import java.util.Queue;

public class QuarantineQueue {
    private Queue<Message> queue = new LinkedList<>();

    public void produce(Message message) {
        queue.add(message);
        System.out.println("Filter Produced: " + message + ":");
    }

    public Message consume() {
        while(queue.isEmpty()) {
            Thread.yield();
        }
        Message message = queue.remove();
        System.out.println("Manager Consumed: " + message + ":");
        return message;
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
