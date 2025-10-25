import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MessagingServer {
        
    public static void main(String[] args) throws InterruptedException {
        int numClients = 2;
        int numEmails = 20;
        int numFilters = 2;
        int numServers = 3;
        int incomingCapacity = 10;
        int deliveryCapacity = 10;

        File file = new File("resources/parameters.txt");
        try (Scanner sc = new Scanner(file)) {
            int lineCounter = 0;
            while(sc.hasNextLine()) {
                String data = sc.nextLine();
                if (lineCounter == 0) {
                    numClients = Integer.parseInt(data);
                } else if (lineCounter == 1) {
                    numEmails = Integer.parseInt(data);
                } else if (lineCounter == 2) {
                    numFilters = Integer.parseInt(data);
                } else if (lineCounter == 3) {
                    numServers = Integer.parseInt(data);
                } else if (lineCounter == 4) {
                    incomingCapacity = Integer.parseInt(data);
                } else {
                    deliveryCapacity = Integer.parseInt(data);
                }
                lineCounter++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("parameters.txt not found. Exiting.");
            System.exit(1);
        }

        IncomingQueue incomingQueue = new IncomingQueue(incomingCapacity);
        DeliveryQueue deliveryQueue = new DeliveryQueue(deliveryCapacity, numServers);
        QuarantineQueue quarantineQueue = new QuarantineQueue();

        ClientThread[] clients = new ClientThread[numClients];
        SpamFilterThread[] filters = new SpamFilterThread[numFilters];
        ServerThread[] servers = new ServerThread[numServers];
        QuarantineManagerThread manager = new QuarantineManagerThread("Manager", quarantineQueue, deliveryQueue);

        for (int i = 0; i < numClients; i++) {
            ClientThread client = new ClientThread("Client-" + i, incomingQueue, numEmails);
            clients[i] = client;
            clients[i].start();
        }
        for (int i = 0; i < numFilters; i++) {
            SpamFilterThread filter = new SpamFilterThread("Filter-" + i, incomingQueue, deliveryQueue, quarantineQueue);
            filters[i] = filter;
            filters[i].start();
        }
        for (int i = 0; i < numServers; i++) {
            ServerThread server = new ServerThread("Server-" + i, deliveryQueue);
            servers[i] = server;
            servers[i].start();
        }
        manager.start();

        for (int i = 0; i < numClients; i++)
            clients[i].join();
        for (int i = 0; i < numFilters; i++)
            filters[i].join();
        for (int i = 0; i < numServers; i++)
            servers[i].join();
        manager.join();

        System.out.println("Messages sent");
    }
}
