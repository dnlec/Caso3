public class SpamFilterThread extends Thread {
    private InboxQueue inboxQueue;
    private boolean running = false;
    private String name;

    public SpamFilterThread(InboxQueue inboxQueue, String name) {
        this.inboxQueue = inboxQueue;
        this.name = name;
    }

    @Override
    public void run() {
        running = true;
        consume();
    }

    public void consume() {
        while (running) {
            while (inboxQueue.isEmpty()) {
                try {
                    inboxQueue.waitIsNotEmpty();
                } catch (InterruptedException e) {
                    System.out.println("Error while waiting to Consume messages.");
                    break;
                }
            }
            if (!running) {
                break;
            }
            Message message = inboxQueue.take();
            useMessage(message);
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("Consumer Stopped");
    }

    public void stopConsuming() {
        running = false;
        inboxQueue.notifyIsNotEmpty();
    }

    private void useMessage(Message message) {
        System.out.println("Message " + message.getId() + " consumed by consumer " + this.name);
    }


  

    

    
}
