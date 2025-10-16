package thirdtry;

public class SpamFilterThread extends Thread {
    private InboxQueue inboxQueue;
    private String name;

    public SpamFilterThread(InboxQueue inboxQueue, String name) {
        this.inboxQueue = inboxQueue;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = inboxQueue.take();
                // useMessage(message);
                Thread.sleep(150);
            } catch (Exception e) {

            } 
        }
    }

    private void useMessage(Message message) {

    }
    
}
