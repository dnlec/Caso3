public class ClientThread extends Thread {
    private InboxQueue inboxQueue;
    private boolean running;
    private String name;
    
    private static int currentId = 0;

    public ClientThread(InboxQueue queue, String name) {
        this.inboxQueue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        running = true;
        produce();
    }

    public void produce() {


        while (running) {
            if(inboxQueue.isFull()) {
                try {
                    inboxQueue.waitIsNotFull();
                } catch (InterruptedException e) {
                    System.out.println("Error while waiting to Produce messages");
                    break;
                }
            }
            if (!running) {
                break;
            }
            inboxQueue.add(generateMessage());
            System.out.println("Queue size: " + this.inboxQueue.getSize());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                
            }
            
        }
        System.out.println("Producer Stopped");
    }

    public void stoProducing() {
        running = false;
        inboxQueue.notifyIsNotFull();
    }

    private Message generateMessage() {
        Message message = new Message(currentId++, false);
        System.out.println("Client " + this.name + " produced message " + message.getId());
        return message;
    }

    

    
}
