import java.io.IOException;

/**
 * Class for handling threading for receiving method.
 * @author Brian Surber
 * @author Abhishek Deo
 */
public class ServerReceivingThread extends Thread {
    private RxPServerSocket serverSocket;

    public ServerReceivingThread(RxPServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            serverSocket.universalListen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
