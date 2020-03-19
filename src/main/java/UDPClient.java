import java.io.IOException;

interface UDPClient {
    boolean sendMessage(String mes);
    String receiveMessage() throws IOException;
    boolean closeConnection();
}