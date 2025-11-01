import java.util.LinkedList;
import java.util.Queue;

public class IncomingQueue {
    private Queue<Message> queue = new LinkedList<>();
    private int maxSize;


    public IncomingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void produce(Message message, Thread thread) throws InterruptedException {
        synchronized (this) {
            while (maxSize == queue.size()) {
                wait();
            }
            queue.add(message);
            System.out.println("[" + thread.getName() + "]: Produced: " + message.getId() + ":" + queue);
            notify();
        }
    }

    public Message consume(Thread thread) throws InterruptedException {
        Message message;
        synchronized (this) {
            while (queue.isEmpty()) {
                wait();
            }
            message = queue.remove();
            System.out.println("[" + thread.getName() + "]: Consumed: " + message.getId() + ":" + queue);
            notify();
        }
        return message;
    }

    public synchronized int getSize() {
        return this.queue.size();
    }


    
}