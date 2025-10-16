package fourthtry;

import fourthtry.Message.Type;

public class ServerThread extends Thread {
    private OutboxQueue outboxQueue;

    public ServerThread(OutboxQueue outboxQueue) {
        this.outboxQueue = outboxQueue;
    }

    @Override
    public void run() {
        while (true) {
            Message message = outboxQueue.consume();
            if (message.getType() == Type.EndProgram) {
                System.out.println("END PROGRAM RECEIVED");
            }
        }
    }
}
