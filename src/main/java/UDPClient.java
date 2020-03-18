interface UDPClient {
    boolean sendMessage(String mes);
    String recieveMessage();
    boolean closeConnection();
}