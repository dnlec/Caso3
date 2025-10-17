package fourthtry;

import java.util.LinkedList;
import java.util.Queue;

public class QuarantineQueue {
    private Queue<Message> queue = new LinkedList<>();


    public void produce(Message message) {
        synchronized (this) {
            queue.add(message);
            System.out.println("Filter Produced: " + message + ":" + queue);
        }
    }

    public Message consume() {
        while(queue.isEmpty()) {
            Thread.yield();
        }
        Message message = queue.remove();
        System.out.println("Manager Consumed: " + message + ":" + queue);
        return message;
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
