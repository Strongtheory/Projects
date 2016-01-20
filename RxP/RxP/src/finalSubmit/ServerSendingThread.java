import java.io.IOException;

/**
 * Class for handling threading for sending method.
 * @author Brian Surber
 * @author Abhishek Deo
 */
public class ServerSendingThread extends Thread {
    private RxPServerSocket serverSocket;
    private String message;

    public ServerSendingThread(RxPServerSocket serverSocket, String message) {
        this.serverSocket = serverSocket;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            serverSocket.postMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
