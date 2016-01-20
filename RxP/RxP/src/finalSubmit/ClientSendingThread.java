import java.io.IOException;

/**
 * Class for handling threading for sending method.
 * @author Brian Surber
 * @author Abhishek Deo
 */
public class ClientSendingThread extends Thread {
    private RxPClientSocket client;
    private String message;

    public ClientSendingThread(RxPClientSocket client, String message) {
        this.client = client;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            client.postMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
