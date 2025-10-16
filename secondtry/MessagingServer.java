package secondtry;

import java.util.LinkedList;
import java.util.Queue;

public class MessagingServer {

    int capacity;

    Queue<Message> queue = new LinkedList<>();

    public MessagingServer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(Message message) throws InterruptedException {
        while(queue.size() == capacity) {
            wait();
        }
        queue.add(message);
        System.out.println("Send message: " + message.getId());
        notifyAll();
    }

    public synchronized Message consume() throws InterruptedException {
        while(queue.isEmpty()) {
            wait();
        }
        Message message = queue.poll();
        System.out.println("Consumed: " + message.getId());
        notifyAll();
        return message;
    }

    public static void main(String[] args) {
        MessagingServer ms = new MessagingServer(5);

        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Message m = new Message(i, false);
                    ms.produce(m);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    ms.consume();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start();
        consumerThread.start();
    }
    
}
