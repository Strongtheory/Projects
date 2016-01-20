import java.io.IOException;

/**
 * Interface used by client and server socket classes.
 * @author Brian Surber
 * @author Abhishek Deo
 */
 interface RxPApi {
    /**
     * Initialize the header for a new connection.
     */
    void init();

    /**
     * Used by server and client to listen for incoming
     * client connections and or packets.
     * @throws IOException
     */
    void universalListen() throws IOException;

    /**
     * Used by client to connect to server.
     * @throws IOException
     */
    void connect() throws IOException;

    /**
     * Used by server to close connection.
     * @throws IOException
     */
     void close() throws IOException;

    /**
     * Add checksum to packet before sending.
     * @param packet
     * @return Packet with computed checksum.
     */
     byte[] attachChecksum(byte[] packet);

    /**
     * Compute checksum of the packet and match.
     * @param packet
     * @return True if checksum matches, false otherwise.
     */
     boolean computeChecksum(byte[] packet);

    /**
     * Allow user to update window size.
     * @param size
     */
     void updateWindowSize(int size);

    /**
     * Used to post message to server.
     * @param message
     * @throws IOException
     */
     void postMessage(String message) throws IOException;

    /**
     * Used to get the message sent.
     * @param message
     * @throws IOException
     */
     void getMessage(String message) throws IOException;

    /**
     * Send payload method.
     * @param data
     * @throws IOException
     */
     void sendData(byte[] data) throws IOException;

    /**
     * Send ack packet.
     * @throws IOException
     */
     void sendAck() throws IOException;

    /**
     * Combine the header information and the payload.
     * @param headerBuffer
     * @param payload
     * @return The complete packet. (Header + Payload)
     */
     byte[] packInformation(byte[] headerBuffer, byte[] payload);

    /**
     * Extract the header from the packet.
     * @param packet
     * @return Header info from packet.
     */
     HeaderStruct extractHeader(byte[] packet);

    /**
     * Extract the payload form the packet.
     * @param packet
     * @return Payload info from packet.
     */
     byte[] extractData(byte[] packet);

    /**
     * Clear the receive buffer.
     */
     void clearReceive();

    /**
     * Handle Flags
     * @param packet
     * @throws IOException
     */
     void handleFlagConnect(byte[] packet) throws IOException;

    /**
     * Get Message from opposite side.
     * @param packet
     * @throws IOException
     */
     void rGMsg(byte[] packet) throws IOException;

    /**
     * Get Posted Message
     * @param packet
     * @throws IOException
     */
     void rPMsg(byte[] packet) throws IOException;

    /**
     * Get Data
     * @param packet
     * @throws IOException
     */
     void rPayloadMsg(byte[] packet) throws IOException;
}
