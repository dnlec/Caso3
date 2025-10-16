package thirdtry;

import java.util.LinkedList;
import java.util.Queue;

public class InboxQueue {
    private Queue<Message> queue = new LinkedList<>();
    private int maxSize;
    private Object IS_NOT_FULL = new Object();
    private Object IS_NOT_EMPTY = new Object();

    public InboxQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void put(Message message) throws InterruptedException {
        while (queue.size() == maxSize) {
            synchronized (IS_NOT_FULL) {
                IS_NOT_FULL.wait();
            }
        }
        queue.add(message);
        System.out.println("Produced...");
        // System.out.println("Produced: " + message + ":" + queue);
        synchronized (IS_NOT_EMPTY) {
            IS_NOT_EMPTY.notify();
        }
    }

    public synchronized Message take() throws InterruptedException {
        while (queue.size() == 0) {
            synchronized (IS_NOT_EMPTY) {
                IS_NOT_EMPTY.wait();
            }
        }
        Message message = queue.remove();
        System.out.println("Consumed: " + message + ":" + queue);
        synchronized (IS_NOT_FULL) {
            IS_NOT_FULL.notify();
        }
        return message;
    }    
}
