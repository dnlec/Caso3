package fourthtry;

import fourthtry.Message.Type;

public class ClientThread extends Thread {
    private InboxQueue inboxQueue;
    private String name;
    private int emails;
    
    private static int currentId = 0;
    private static Object lock = new Object();

    public ClientThread(InboxQueue queue, String name, int emails) {
        this.inboxQueue = queue;
        this.name = name;
        this.emails = emails;
    }

    @Override
    public void run() {
        int emailCounter = 0;
        while (emailCounter < this.emails) {
            try {
                Message message = generateMessage(emailCounter);
                inboxQueue.produce(message);
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
            emailCounter++;
        }
        System.out.println("client finished");
    }

    private synchronized Message generateMessage(int emailCount) {
        Type type;
        if (emailCount == 0) {
            type = Type.StartClient;
        } else if (emailCount == this.emails-1) {
            type = Type.EndClient;
        } else {
            type = Type.Email;
        }

        synchronized (ClientThread.lock) {
            Message message = new Message(currentId++, false, type);
            return message;
        }
    }

    

    
}
