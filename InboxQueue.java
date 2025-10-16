import java.util.LinkedList;
import java.util.Queue;

public class InboxQueue {
    private Queue<Message> queue = new LinkedList<>();
    private int maxSize;
    private Object IS_NOT_FULL = new Object();
    private Object IS_NOT_EMPTY = new Object();

    InboxQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void add(Message message) {
        queue.add(message);
        notifyIsNotEmpty();
    }

    public Message take() {
        Message message = queue.poll();
        notifyIsNotFull();
        return message;
    }

    public void waitIsNotFull() throws InterruptedException {
        synchronized (IS_NOT_FULL) {
            IS_NOT_FULL.wait();
        }
    }

    public void waitIsNotEmpty() throws InterruptedException {
        synchronized (IS_NOT_EMPTY) {
            IS_NOT_EMPTY.wait();
        }
    }

    public void notifyIsNotEmpty() {
        synchronized (IS_NOT_EMPTY) {
            IS_NOT_EMPTY.notify();
        }
    }

    public void notifyIsNotFull() {
        synchronized (IS_NOT_FULL) {
            IS_NOT_FULL.notify();
        }
    }

    public boolean isFull() {
        return this.queue.size() == this.maxSize;
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public int getSize() {
        return this.queue.size();
    }

    

    
}
