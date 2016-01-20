import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.*;

/**
 * Server socket class backend.
 * @author Brian Surber
 * @author Abhishek Deo
 */
public class RxPClientSocket implements RxPApi {
    /**
     * Server Socket
     * address
     * client port
     * dest port
     * network emulator port
     * HeaderStruct
     * Sending Packet
     * Receiving Packet
     * Queue<byte[]> packetList
     * getVal
     * postVal
     * threadList
     * activeVal
     * receiveFile
     * Threading List
     */

    private int srcPort;
    private int destPort;
    private int networkEmulatorPort;
    private int getVal;
    private int postVal;
    private int activeVal;
    private int receiveFile;
    private InetAddress address;
    private HeaderStruct header;
    private DatagramSocket clientSocket;
    private DatagramPacket receivingPacket;
    private DatagramPacket sendingPacket;
    PacketList<byte[]> packetList;
    byte[] packetBuffer;

    private RxPWindow window;
    private RxPTimer timer;
    private RxPTimer fileTimer;
    BufferedOutputStream outputBuffer;
    FileOutputStream fileStream;
    private ArrayList<ClientSendingThread> clientSendingThreads;
    static final int BUFFER_SIZE = 255;

    public RxPClientSocket(int srcPort, int destPort, int networkEmulatorPort,
                           InetAddress address) throws SocketException, FileNotFoundException {
        getVal = 0;
        postVal = 0;
        activeVal = 0;
        this.srcPort= srcPort;
        this.networkEmulatorPort = networkEmulatorPort;
        this.destPort = destPort;
        this.address = address;
        header = new HeaderStruct(0, 0, (short)srcPort, (short)destPort);
        clientSocket = new DatagramSocket(srcPort);
        packetList = new PacketList<>();
        packetBuffer = new byte[BUFFER_SIZE];
        sendingPacket = null;
        receivingPacket = new DatagramPacket(packetBuffer, BUFFER_SIZE);
        clientSendingThreads = new ArrayList<>();
        window = new RxPWindow();
        timer = new RxPTimer();
        fileTimer = new RxPTimer();
    }

    public void setClientSendingThreads(ArrayList<ClientSendingThread> clientSendingThreads) {
        this.clientSendingThreads = clientSendingThreads;
    }

    /**
     * Initialize the header for a new connection.
     */
    @Override
    public void init() {
        packetList = new PacketList<>();
        receivingPacket = new DatagramPacket(packetBuffer, BUFFER_SIZE);
        receiveFile = 0;
        activeVal = 0;
        getVal = 0;
        postVal = 0;
        window = new RxPWindow();
        timer = new RxPTimer();
    }


    /**
     * Used by server and client to listen for incoming
     * client connections and or packets.
     * @throws IOException
     */
    @Override
    public void universalListen() throws IOException {
        while (true) {
            clientSocket.receive(receivingPacket);
            byte[] packet = new byte[receivingPacket.getLength()];
            System.arraycopy(receivingPacket.getData(), 0, packet,
                    0, receivingPacket.getLength());
            if (computeChecksum(packet)) {
                HeaderStruct struct = extractHeader(packet);
                if (struct.isActiveFlag()) {
                    handleFlagConnect(packet);
                } else if (struct.isGetFlag()) {
                    rGMsg(packet);
                } else if (struct.isPostFlag()) {
                    rPMsg(packet);
                } else if (struct.isPayloadFlag()) {
                    rPayloadMsg(packet);
                }
            } else {
                // Do Nothing
            }
        }
    }

    /**
     * Used by client to connect to server.
     *
     * @throws IOException
     */
    @Override
    public void connect() throws IOException {
        header.setActiveFlag(true);
        header.setSynFlag(true);
        header.setSequenceNumber(0);
        timer.startTime();
        System.out.println("Sending SYN = 1");
        while (getActiveVal() == 0) {
            if (timer.isSuspended()) {
                header.setSynFlag(true);
                header.setSequenceNumber(0);
                sendData(null);
                System.out.println("Resending SYN = 1");
                timer.startTime();
            }
        }
        while (getActiveVal() == 1) {
            if (timer.isSuspended()) {
                header.setSynFlag(false);
                header.setSequenceNumber(1);
                sendData(null);
                System.out.println("Resending SYN = 0");
                timer.startTime();
            }
        }
        header.setActiveFlag(false);
        System.out.println("Connected");
    }

    /**
     * Used by server to close connection.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        header.setActiveFlag(true);
        header.setFinFlag(true);
        header.setSequenceNumber(0);
        sendData(null);
        System.out.println("Sending FIN = 1");
        timer.startTime();
        while (this.getActiveVal() == 2) {
            if (timer.isSuspended()) {
                header.setFinFlag(true);
                header.setSequenceNumber(0);
                sendData(null);
                System.out.println("Resending FIN = 1");
                timer.startTime();
            }
        }
        while (this.getActiveVal() == 3) {
            if (timer.isSuspended()) {
                header.setFinFlag(false);
                header.setSequenceNumber(1);
                sendData(null);
                System.out.println("Resending FIN = 0");
                timer.startTime();
            }
        }
        header.setActiveFlag(false);
        System.out.println("Connection closed.");
        init();
    }

    /**
     * Add checksum to packet before sending.
     *
     * @param packet
     * @return Packet with computed checksum.
     */
    @Override
    public byte[] attachChecksum(byte[] packet) {
        int len = packet.length;
        short[] words = new short[len/2];

        for(int i = 0; i+1 < len; i =i + 2){
            if(i+1 >= len){

                short a = (short) (((((int)(packet[i])) & 0x0000FFFF)<<8) | (int)0);
                words[i/2]=a;
            }else{
                short a = (short) (((((int)(packet[i])) & 0x0000FFFF)<<8)  | (((int)(packet[i+1])) & 0x000000FF));
                words[i/2]=a;
            }
        }
        short checksum = 0;
        //adding
        for(int i = 0; i < words.length; i++){
            int tmp = ((int)checksum & 0x0000FFFF) + ((int)words[i] & 0x0000FFFF);
            if((tmp & 0x10000) == 0x10000){
                //System.out.println("overflow");
                tmp++;
            }
            checksum = (short)tmp;
        }
        checksum = (short)(((int)checksum & 0x0000FFFF) ^ 0xFFFF);
        packet[14] = (byte)(checksum>> 8);
        packet[15] = (byte)(checksum& 0xFF);
        return packet;
    }

    /**
     * Compute checksum of the packet and match.
     *
     * @param packet
     * @return True if checksum matches, false otherwise.
     */
    @Override
    public boolean computeChecksum(byte[] packet) {
        boolean check = false;
        int len = packet.length;
        short[] words = new short[len/2];

        for(int i = 0; i+1 < len; i =i + 2){
            if(i+1 >= len){

                short a = (short) (((((int)(packet[i])) & 0x0000FFFF)<<8) | (int)0);
                words[i/2]=a;
            }else{
                short a = (short) (((((int)(packet[i])) & 0x0000FFFF)<<8)  | (((int)(packet[i+1])) & 0x000000FF));
                words[i/2]=a;
            }
        }
        short checksum = 0;
        //adding
        for(int i = 0; i < words.length; i++){

            int tmp = 0;
            tmp = ((int)checksum & 0x0000FFFF) + ((int)words[i] & 0x0000FFFF);
            if((tmp & 0x10000) == 0x10000){
                //System.out.println("overflow");
                tmp++;
            }
            checksum = (short)tmp;
        }
        if ((checksum & 0xFFFF) == 0xFFFF){
            check = true;
        }
        return check;
    }

    /**
     * Allow user to update window size.
     *
     * @param size
     */
    @Override
    public void updateWindowSize(int size) {
        if (activeVal == 2) {
            window.setWindow(size);
        } else {
            System.out.println("Must connect");
        }
    }

    /**
     * Used to post message to server.
     *
     * @param message
     * @throws IOException
     */
    @Override
    public void postMessage(String message) throws IOException {
        if (activeVal == 2) {
            FileInputStream inputStream = new FileInputStream(System.getProperty
                    ("user.dir") + "/" + message);
            byte[] file = message.getBytes();
            header.setPostFlag(true);
            header.setSequenceNumber(0);
            sendData(file);
            header.setPostFlag(false);
            timer.startTime();
            while (getPostVal() == 0) {
                if (timer.isSuspended()) {
                    header.setPostFlag(true);
                    header.setSequenceNumber(0);
                    sendData(file);
                    header.setPostFlag(false);
                    timer.startTime();
                }
            }
            fileTimer.startTime();
            byte[] buffer = new byte[RxPClientSocket.BUFFER_SIZE - HeaderStruct.HEADER_SIZE];
            int payLen = inputStream.read(buffer);
            byte[] payload;
            timer.startTime();
            while (payLen != -1 || !packetList.isEmpty()) {
                if (timer.isSuspended()) {
                    window.setSubsequent(window.getStart());
                    timer.startTime();
                    ArrayList<byte[]> q = packetList.returnArrayList();
                    for (int i = 0; i < q.size(); i++) {
                        if (payLen == -1 && i == (q.size() - 1))
                            header.setLastFlag(true);
                        header.setSequenceNumber(window.getSubsequent());
                        header.setPayloadFlag(true);
                        sendData(q.get(i));
                        header.setPayloadFlag(false);
                        header.setLastFlag(false);
                        window.setSubsequent(window.getSubsequent() + 1);
                    }
                }
                if (window.getSubsequent()
                        <= window.getEnd() && payLen != -1) {
                    payload = new byte[payLen];
                    System.arraycopy(buffer, 0, payload, 0, payLen);
                    payLen = inputStream.read(buffer);
                    if (payLen == -1)
                        header.setLastFlag(true);
                    header.setSequenceNumber(window.getSubsequent());
                    header.setPayloadFlag(true);
                    sendData(payload);
                    header.setPayloadFlag(false);
                    header.setLastFlag(false);
                    window.setSubsequent(window.getSubsequent() + 1);
                    packetList.enqueue(payload);
                }
            }
            inputStream.close();
            setPostVal(0);
            setGetVal(0);
            header.setLastFlag(false);
            System.out.println("Message " + message + " transmitted");
        } else {
            System.out.println("Must connect");
        }
    }

    /**
     * Used to get the message sent.
     *
     * @param message
     * @throws IOException
     */
    @Override
    public void getMessage(String message) throws IOException {
        if (activeVal == 2) {
            byte[] file = message.getBytes();
            header.setGetFlag(true);
            header.setSequenceNumber(0);
            sendData(file);
            header.setGetFlag(false);
            timer.startTime();
            while (getGetVal() == 0) {
                if (timer.isSuspended()) {
                    header.setGetFlag(true);
                    header.setSequenceNumber(0);
                    sendData(file);
                    header.setGetFlag(false);
                    timer.startTime();
                }
            }
        } else {
            System.out.println("Must connect");
        }
    }

    /**
     * @param data
     * @throws IOException
     */
    @Override
    public void sendData(byte[] data) throws IOException {
        header.setAckFlag(false);
        byte[] payload = packInformation(header.getHeaderBuffer(), data);
        payload = attachChecksum(payload);
        sendingPacket = new DatagramPacket(payload, payload.length, address, networkEmulatorPort);
        clientSocket.send(sendingPacket);
    }

    /**
     * Send ack packet.
     *
     * @throws IOException
     */
    @Override
    public void sendAck() throws IOException {
        header.setAckFlag(true);
        byte[] ack = attachChecksum(header.getHeaderBuffer());
        sendingPacket = new DatagramPacket(ack, HeaderStruct.HEADER_SIZE, address, networkEmulatorPort);
        clientSocket.send(sendingPacket);
    }

    /**
     * Combine the header information and the payload.
     *
     * @param headerBuffer
     * @param payload
     * @return The complete packet. (Header + Payload)
     */
    @Override
    public byte[] packInformation(byte[] headerBuffer, byte[] payload) {
        if (payload != null) {
            byte[] combined = new byte[headerBuffer.length + payload.length];
            for (int i = 0; i < headerBuffer.length; i++)
                combined[i] = headerBuffer[i];
            int payLoadCount = 0;
            for (int i = headerBuffer.length; i < headerBuffer.length + payload.length; i++) {
                combined[i] = payload[payLoadCount];
                payLoadCount++;
            }
            return combined;
        } else {
            return headerBuffer;
        }
    }

    /**
     * Extract the header from the packet.
     *
     * @param packet
     * @return Header info from packet.
     */
    @Override
    public HeaderStruct extractHeader(byte[] packet) {
        HeaderStruct struct = new HeaderStruct();
        byte[] hArr = new byte[packet[12]]; // i.e packet header length
        System.arraycopy(packet, 0, hArr, 0, packet[12]);
        struct.extractHeader(hArr);
        return struct;
    }

    /**
     * Extract the payload form the packet.
     *
     * @param packet
     * @return Payload info from packet.
     */
    @Override
    public byte[] extractData(byte[] packet) {
        byte[] data = new byte[packet.length - packet[12]];
        System.arraycopy(packet, packet[12], data, 0, packet.length - packet[12]);;
        return data;
    }

    /**
     * Clear the receive buffer.
     */
    @Override
    synchronized public void clearReceive() {
        receivingPacket = new DatagramPacket(packetBuffer, BUFFER_SIZE);
    }

    /**
     * Handle Flags
     * @param packet
     * @throws IOException
     */
    @Override
    public void handleFlagConnect(byte[] packet) throws IOException {
        HeaderStruct struct = extractHeader(packet);
        int sequenceNum = struct.getSequenceNumber();
        struct.setAcknowledgementNumber(sequenceNum);
        switch (getActiveVal()) {
            case 0:
                if (struct.isSynFlag()) {
                    header.setActiveFlag(true);
                    sendAck();
                    setActiveVal(1);
                } else if (struct.isAckFlag() && header.isSynFlag()) { // SYN + ACK
                    header.setSynFlag(false);
                    header.setSequenceNumber(1);
                    sendData(null);
                    setActiveVal(1);
                } else if (!struct.isFinFlag() && !struct.isAckFlag()) {
                    header.setActiveFlag(true);
                    sendAck();
                    header.setActiveFlag(false);
                }
                break;
            case 1:
                if (!struct.isSynFlag() && !struct.isAckFlag()) {
                    setActiveVal(2);
                    sendAck();
                    header.setActiveFlag(false);
                    System.out.println("Connection Set.");
                    System.out.flush();
                }
                if (struct.isSynFlag() && struct.getSequenceNumber() == 0) {
                    header.setActiveFlag(true);
                    sendAck();
                    header.setActiveFlag(false);
                }
                if (struct.isAckFlag())
                    setActiveVal(2);
                break;
            case 2:
                if (struct.isFinFlag()) {
                    header.setActiveFlag(true);
                    sendAck();
                    setActiveVal(3);
                } else if (struct.isAckFlag() && header.isFinFlag()) {
                    header.setFinFlag(false);
                    header.setSequenceNumber(1);
                    sendData(null);
                    setActiveVal(3);
                } else if(!struct.isSynFlag() && !struct.isAckFlag()) {
                    header.setActiveFlag(true);
                    sendAck();
                    header.setActiveFlag(false);
                }
                break;
            case 3:
                if (!struct.isFinFlag() && !struct.isAckFlag()) {
                    setActiveVal(0);
                    sendAck();
                    header.setActiveFlag(false);
                    System.out.println("Connection closed.");
                    init();
                } else if (struct.isFinFlag() && struct.getSequenceNumber() == 0) {
                    header.setActiveFlag(true);
                    sendAck();
                    header.setActiveFlag(false);
                } else if (struct.isAckFlag()) {
                    setActiveVal(0);
                }
        }
        clearReceive();
    }

    /**
     * Get Message from opposite side.
     * @param packet
     * @throws IOException
     */
    @Override
    public void rGMsg(byte[] packet) throws IOException {
        HeaderStruct struct = extractHeader(packet);
        int seqNum = struct.getSequenceNumber();
        header.setAcknowledgementNumber(seqNum);
        if (struct.isAckFlag()) {
            setGetVal(1);
        } else {
            if (getGetVal() == 0) {
                byte[] payload = extractData(packet);
                String file = new String(payload);
                setGetVal(1);
                ClientSendingThread sendThread = new ClientSendingThread(this, file);
                clientSendingThreads.add(sendThread);
                sendThread.start();
            }
            header.setGetFlag(true);
            sendAck();
            header.setGetFlag(false);
        }
        clearReceive();
    }

    /**
     * Get Posted Message
     * @param packet
     * @throws IOException
     */
    @Override
    public void rPMsg(byte[] packet) throws IOException {
        HeaderStruct struct = extractHeader(packet);
        int seqNum = struct.getSequenceNumber();
        header.setAcknowledgementNumber(seqNum);
        if (getPostVal() == 0) {
            if (struct.isAckFlag()) {
                setPostVal(1);
            } else {
                byte[] payload = extractData(packet);
                String file = new String(payload);
                fileStream = new FileOutputStream(System.getProperty
                        ("user.dir") + "/" + file, true);
                outputBuffer = new BufferedOutputStream(fileStream);
                header.setPostFlag(true);
                sendAck();
                header.setPostFlag(false);
            }
        }
        clearReceive();
    }

    /**
     * Get Data
     * @param packet
     * @throws IOException
     */
    @Override
    synchronized public void rPayloadMsg(byte[] packet) throws IOException {
        HeaderStruct struct = extractHeader(packet);
        if (struct.isAckFlag()) {
            if (struct.getAcknowledgementNumber() == window.getStart()) {
                timer.startTime();
                window.setStart(window.getStart() + 1);
                window.setEnd(window.getEnd() + 1);
                packetList.dequeue();
            }
        } else {
            if (outputBuffer != null) {
                if (receiveFile == struct.getSequenceNumber()) {
                    byte[] payload = extractData(packet);
                    outputBuffer.write(payload, 0, payload.length);
                    outputBuffer.flush();
                    receiveFile++;
                    if (struct.isLastFlag()) {
                        fileStream.close();
                        System.out.println("File received.");
                    }
                }
                if (receiveFile > struct.getSequenceNumber()) {
                    header.setAcknowledgementNumber(struct.getSequenceNumber());
                } else if (struct.getSequenceNumber() > receiveFile) {
                    header.setAcknowledgementNumber(receiveFile - 1);
                }
                header.setPayloadFlag(true);
                sendAck();
                header.setPayloadFlag(false);
            } else {
                throw new IOException("outputBuffer not initialized");
            }
        }
        clearReceive();
    }

    public int getGetVal() {
        return getVal;
    }

    public void setGetVal(int getVal) {
        this.getVal = getVal;
    }

    public int getPostVal() {
        return postVal;
    }

    public void setPostVal(int postVal) {
        this.postVal = postVal;
    }

    public int getActiveVal() {
        return activeVal;
    }

    public void setActiveVal(int activeVal) {
        this.activeVal = activeVal;
    }

    public DatagramSocket getClientSocket() {
        return clientSocket;
    }

    private class RxPTimer {
        private long time;
        public static final double SUSPEND = 0.5;

        public RxPTimer() {
            this.time = 0;
        }

        public void startTime() {
            time = System.currentTimeMillis();
        }

        public boolean isSuspended() {
            if (System.currentTimeMillis() - time > 1000 * SUSPEND)
                return true;
            else
                return false;
        }

        public double getTime() {
            return (double)((System.currentTimeMillis() - time) / 1000);
        }
    }

    /**
     * Class that handles the protocol window.
     */
    private class RxPWindow {
        int window;
        int start;
        int end;
        int subsequent;

        public RxPWindow() {
            super();
            this.window = 5;
            this.start = 0;
            this.end = this.window - 1;
            this.subsequent = 0;
        }

        public int getWindow() {
            return window;
        }

        public void setWindow(int window) {
            this.window = window;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getSubsequent() {
            return subsequent;
        }

        public void setSubsequent(int subsequent) {
            this.subsequent = subsequent;
        }
    }
}
