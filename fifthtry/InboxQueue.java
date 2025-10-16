package fifthtry;

import java.util.LinkedList;
import java.util.Queue;

public class InboxQueue {
    private Queue<Message> queue = new LinkedList<>();
    private int maxSize;

    public InboxQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void put(Message message) throws InterruptedException {
        while (queue.size() == maxSize) {
            this.wait();
        }
        queue.add(message);
        System.out.println("Produced: " + message + ":" + queue);
        this.notifyAll();
    }

    public synchronized Message take() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        Message message = queue.remove();
        System.out.println("Consumed: " + message + ":" + queue);
        this.notifyAll();
        return message;
    }    
}
