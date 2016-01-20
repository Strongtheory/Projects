import java.io.IOException;

/**
 * Class for handling threading for receiving method.
 * @author Brian Surber
 * @author Abhishek Deo
 */
public class ClientReceivingThread extends Thread {
    private RxPClientSocket client;

    public ClientReceivingThread(RxPClientSocket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            client.universalListen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
