import java.io.IOException;

interface UDPClient {
    boolean sendMessage(String message, String address, int port);
    String receiveMessage() throws IOException;
    boolean closeConnection() throws IOException;
}