import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Server main class.
 * @author Brian Surber
 * @author Abhishek Deo
 */
public class RxPServer {
    public static void main(String[] args) throws IOException {
        System.out.println("File Transfer Server");
        Scanner scanner = new Scanner(System.in);
        if (args.length != 3)
            throw new IOException("Invalid Argument(s)");
        int host = Integer.parseInt(args[0]);
        InetAddress serverAddr = InetAddress.getByName(args[1]);
        int netEmu = Integer.parseInt(args[2]);
        int dest = host - 1;
        RxPServerSocket serverSocket = new RxPServerSocket(host, dest, netEmu, serverAddr);
        Thread serverThread = new ServerReceivingThread(serverSocket);
        serverThread.start();
        while (true) {
            System.out.println("Options:\n");
            System.out.println("1.) Window (Size)\n");
            System.out.println("2.) Terminate\n");
            System.out.println("Please enter an option: ");
            String choice = scanner.nextLine();
            if (choice.contains("Window")) {
                String[] string = choice.split("\\s");
                int size = Integer.parseInt(string[1]);
                serverSocket.updateWindowSize(size);
                System.out.println("Window Updated: " + size);
            } else if (choice.equals("Terminate")) {
                serverSocket.close();
                serverThread.stop();
                serverSocket.getServerThreads().forEach(ServerSendingThread::stop);
                serverSocket.getServerSocket().close();
                System.out.println("Server has stopped");
            }
        }
    }
}
