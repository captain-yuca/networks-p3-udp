interface UDPClient {
    boolean sendMessage();
    String recieveMessage();
    boolean closeConnection();
}