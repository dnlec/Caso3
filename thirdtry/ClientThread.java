package thirdtry;

public class ClientThread extends Thread {
    private InboxQueue inboxQueue;
    private String name;
    private int emailCount;
    
    private static int currentId = 0;

    public ClientThread(InboxQueue queue, String name, int emailCount) {
        this.inboxQueue = queue;
        this.name = name;
        this.emailCount = emailCount;
    }

    @Override
    public void run() {
        int count = 0;
        while (count != emailCount) {
            try {
                Message message = generateMessage();
                System.out.println("I am going to produce");
                inboxQueue.put(message);
                count++;
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("Client Stopped");
    }


    private synchronized Message generateMessage() {
        Message message = new Message(currentId++, false);
        return message;
    }

    

    
}
