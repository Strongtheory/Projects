/**
 * Class for the header structure and related methods.
 * @author Brian Surber
 * @author Abhishek Deo
 */
public class HeaderStruct {
    /**
     * Source Port: 2 bytes - 0 UDP header
     * Destination Port: 2 bytes - 0 UDP header
     * Sequence Number: 4 bytes
     * ACK Number: 4 bytes
     * ACK Flag: 1 bit
     * SYN Flag: 1 bit
     * FIN Flag: 1 bit
     * Active Flag: 1 bit
     * GET Flag: 1 bit
     * POST Flag; 1 bit
     * Checksum: 2 bytes
     * Window Size: 4 bytes
     */
    int sequenceNumber;
    int acknowledgementNumber;
    short sourcePort;
    short destinationPort;
    boolean ackFlag;
    boolean synFlag;
    boolean finFlag;
    boolean getFlag;
    boolean postFlag;
    boolean lastFlag;
    boolean activeFlag;
    boolean payloadFlag;
    short checksum;
    byte[] headerBuffer;

    public final static int HEADER_SIZE = 16;

    public HeaderStruct() {
        super();
    }

    public HeaderStruct(int sequenceNumber, int acknowledgementNumber, short sourcePort, short destinationPort) {
        super();
        this.sequenceNumber = sequenceNumber;
        this.acknowledgementNumber = acknowledgementNumber;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.ackFlag = false;
        this.finFlag = false;
        this.synFlag = false;
        this.activeFlag = false;
        this.lastFlag = false;
        this.getFlag = false;
        this.postFlag = false;
        this.payloadFlag = false;
        this.checksum = 0;
        this.headerBuffer = new byte[HEADER_SIZE];
    }

    public byte[] createHeader() {
        headerBuffer[0] = (byte)(sourcePort >> 8);
        headerBuffer[1] = (byte)(sourcePort & 0xFF);
        headerBuffer[2] = (byte)(destinationPort >> 8);
        headerBuffer[3] = (byte)(destinationPort & 0xFF);
        headerBuffer[4] = (byte)(sequenceNumber >> 24);
        headerBuffer[5] = (byte)(sequenceNumber >> 16);
        headerBuffer[6] = (byte)(sequenceNumber >> 8);
        headerBuffer[7] = (byte)(sequenceNumber & 0xFF);
        headerBuffer[8] = (byte)(acknowledgementNumber >> 24);
        headerBuffer[9] = (byte)(acknowledgementNumber >> 16);
        headerBuffer[10] = (byte)(acknowledgementNumber >> 8);
        headerBuffer[11] = (byte)(acknowledgementNumber & 0xFF);
        headerBuffer[12] = (byte)(HeaderStruct.HEADER_SIZE & 0xFF);
        headerBuffer[13] = 0;
        if (ackFlag)headerBuffer[13] = (byte)
                    (headerBuffer[13] | 0x1);
        if (synFlag)headerBuffer[13] = (byte)
                    (headerBuffer[13] | 0x2);
        if (finFlag)headerBuffer[13] = (byte)
                    (headerBuffer[13] | 0x4);
        if (getFlag)headerBuffer[13] = (byte)
                    (headerBuffer[13] | 0x8);
        if (postFlag)headerBuffer[13] = (byte)
                    (headerBuffer[13] | 0x10);
        if (lastFlag)headerBuffer[13] = (byte)
                    (headerBuffer[13] | 0x20);
        if (activeFlag)headerBuffer[13] = (byte)
                    (headerBuffer[13] | 0x40);
        if (payloadFlag)headerBuffer[13] = (byte)
                (headerBuffer[13] | 0x80);
        headerBuffer[14] = (byte)(checksum >> 8);
        headerBuffer[15] = (byte)(checksum & 0xFF);
        return headerBuffer;
    }

    public void extractHeader(byte[] buffer) {
        sourcePort = (short)(buffer[0] << 8 | 0xFF & buffer[1]);
        destinationPort = (short)(buffer[2] << 8 | 0xFF & buffer[3]);
        sequenceNumber = (buffer[4] << 24 | buffer[5] << 16
                | buffer[6] << 8 | 0xFF & buffer[7]);
        acknowledgementNumber = (buffer[8] << 24 | buffer[9] << 16
                | buffer[10] << 8 | 0xFF & buffer[11]);
        if ((buffer[13] & 0x1) == 0x1)
            ackFlag = true;
        if ((buffer[13] & 0x2) == 0x2)
            synFlag = true;
        if ((buffer[13] & 0x4) == 0x4)
            finFlag = true;
        if ((buffer[13] & 0x8) == 0x8)
            getFlag = true;
        if ((buffer[13] & 0x10) == 0x10)
            postFlag = true;
        if ((buffer[13] & 0x20) == 0x20)
            lastFlag = true;
        if ((buffer[13] & 0x40) == 0x40)
            activeFlag = true;
        if ((buffer[13] & 0x80) == 0x80)
            payloadFlag = true;
        checksum = (short)(buffer[14] << 8 | 0xFF & buffer[15]);
    }

    /* Getters and Setters */

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getAcknowledgementNumber() {
        return acknowledgementNumber;
    }

    public void setAcknowledgementNumber(int acknowledgementNumber) {
        this.acknowledgementNumber = acknowledgementNumber;
    }

    public short getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(short sourcePort) {
        this.sourcePort = sourcePort;
    }

    public short getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(short destinationPort) {
        this.destinationPort = destinationPort;
    }

    public boolean isAckFlag() {
        return ackFlag;
    }

    public void setAckFlag(boolean ackFlag) {
        this.ackFlag = ackFlag;
    }

    public boolean isSynFlag() {
        return synFlag;
    }

    public void setSynFlag(boolean synFlag) {
        this.synFlag = synFlag;
    }

    public boolean isFinFlag() {
        return finFlag;
    }

    public void setFinFlag(boolean finFlag) {
        this.finFlag = finFlag;
    }

    public boolean isGetFlag() {
        return getFlag;
    }

    public void setGetFlag(boolean getFlag) {
        this.getFlag = getFlag;
    }

    public boolean isPostFlag() {
        return postFlag;
    }

    public void setPostFlag(boolean postFlag) {
        this.postFlag = postFlag;
    }

    public boolean isLastFlag() {
        return lastFlag;
    }

    public void setLastFlag(boolean lastFlag) {
        this.lastFlag = lastFlag;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public boolean isPayloadFlag() {
        return payloadFlag;
    }

    public void setPayloadFlag(boolean payloadFlag) {
        this.payloadFlag = payloadFlag;
    }

    public short getChecksum() {
        return checksum;
    }

    public void setChecksum(short checksum) {
        this.checksum = checksum;
    }

    public byte[] getHeaderBuffer() {
        headerBuffer = createHeader();
        return headerBuffer;
    }

    public void setHeaderBuffer(byte[] headerBuffer) {
        this.headerBuffer = headerBuffer;
    }

    /* Getters and Setters */

    /* Extracting methods */

    public short extractSourcePort(byte[] buffer) {
        short srcPort = (short)(buffer[0] << 8 | 0xFF & buffer[1]);
        return srcPort;
    }

    public short extractDestinationPort(byte[] buffer) {
        short destPort = (short)(buffer[2] << 8 | 0xFF & buffer[3]);
        return destPort;
    }

    public int extractSequenceNumber(byte[] buffer) {
        int seqNum = (buffer[4] << 24 | buffer[5] << 16
                | buffer[6] << 8 | 0xFF & buffer[7]);
        return seqNum;
    }

    public int extractAcknowledgementNumber(byte[] buffer) {
        int ackNum = (buffer[8] << 24 | buffer[9] << 16
                | buffer[10] << 8 | 0xFF & buffer[11]);
        return ackNum;
    }

    public boolean extractFlag(byte[] buffer) {
        if ((buffer[13] & 0x1) == 0x1)
            return ackFlag = true;
        else if ((buffer[13] & 0x2) == 0x2)
            return synFlag = true;
        else if ((buffer[13] & 0x4) == 0x4)
            return finFlag = true;
        else if ((buffer[13] & 0x8) == 0x8)
            return getFlag = true;
        else if ((buffer[13] & 0x10) == 0x10)
            return postFlag = true;
        else if ((buffer[13] & 0x20) == 0x20)
            return lastFlag = true;
        else if ((buffer[13] & 0x40) == 0x40)
            return activeFlag = true;
        else
            return false;
    }

    public short extractChecksum(byte[] buffer) {
        short checksumVal = (short)(buffer[14] << 8 | 0xFF & buffer[15]);
        return checksumVal;
    }

    /* Extracting methods */
}
