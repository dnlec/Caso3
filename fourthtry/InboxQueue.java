package fourthtry;

import java.util.LinkedList;
import java.util.Queue;

public class InboxQueue {
    private Queue<Message> queue = new LinkedList<>();
    private int maxSize;


    public InboxQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void produce(Message message) throws InterruptedException {
        synchronized (this) {
            while (maxSize == queue.size()) {
                wait();
            }
            queue.add(message);
            System.out.println("Produced: " + message.getId() + ":" + queue);
            notify();
        }
    }

    public Message consume() throws InterruptedException {
        Message message;
        synchronized (this) {
            while (queue.isEmpty()) {
                wait();
            }
            message = queue.remove();
            System.out.println("Consumed: " + message.getId() + ":" + queue);
            notify();
        }
        return message;
    }

    public int getSize() {
        return this.queue.size();
    }


    
}
