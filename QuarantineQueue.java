

import java.util.LinkedList;
import java.util.Queue;

public class QuarantineQueue {
    private Queue<Message> queue = new LinkedList<>();

    public void produce(Message message, Thread thread) {
        queue.add(message);
        System.out.println("[" + thread.getName() + "]: Produced: " + message + ":");
    }

    public Message consume(Thread thread) {
        while(queue.isEmpty()) {
            Thread.yield();
        }
        Message message = queue.remove();
        System.out.println("["+ thread.getName() + "]: Consumed: " + message + ":");
        return message;
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
