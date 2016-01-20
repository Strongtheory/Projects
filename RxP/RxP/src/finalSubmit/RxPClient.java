import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Client main class.
 * @author Brian Surber
 * @author Abhishek Deo
 */
public class RxPClient {
    public static void main(String[] args) throws IOException {
        RxPClientSocket clientSocket = null;
        Thread clientThread = null;
        Thread sendingThread = null;
        Scanner scanner = new Scanner(System.in);
        if ((args.length < 3) || (args.length > 4))
            throw new IOException("Invalid Argument(s)");
        int clientPort = Integer.parseInt(args[0]);
        InetAddress netEmuIP = InetAddress.getByName(args[1]);
        int netEmu = Integer.parseInt(args[2]);
        int dest = clientPort + 1;
        while (true) {
            boolean option = true;
            System.out.println("Options:\n");
            System.out.println("1.) Connect\n");
            System.out.println("2.) Get (Name)\n");
            System.out.println("3.) Post (Name)\n");
            System.out.println("4.) Window (Size)\n");
            System.out.println("5.) Disconnect\n");
            System.out.println("Please enter an option: ");
            String choice = scanner.nextLine();
            if (choice.equals("Connect")) {
                clientSocket = new RxPClientSocket(clientPort, dest, netEmu, netEmuIP);
                clientThread = new ClientReceivingThread(clientSocket);
                clientThread.start();
                clientSocket.connect();
            } else if (choice.contains("Get")) {
                String[] getIn = choice.split("\\s");
                if (clientSocket != null)
                    clientSocket.getMessage(getIn[1]);
            } else if (choice.contains("Post")) {
                String[] postIn = choice.split("\\s");
                sendingThread = new ClientSendingThread(clientSocket, postIn[1]);
                sendingThread.start();
            } else if (choice.contains("Window")) {
                String[] win = choice.split("\\s");
                int size = Integer.parseInt(win[1]);
                if (clientSocket != null)
                    clientSocket.updateWindowSize(size);
            } else if (choice.equals("Disconnect")) {
                assert clientSocket != null;
                clientSocket.close();
                clientThread.stop();
                if (sendingThread != null)
                    sendingThread.stop();
                clientSocket.getClientSocket().close();
            }
        }
    }
}
